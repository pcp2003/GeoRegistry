package service;

import model.Cadastro;
import core.Constants;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Graph representing property adjacencies.
 * Vertices are properties, edges represent physical adjacencies.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class PropertyGraph extends Graph {
    /**
     * Creates a property graph from a list of properties.
     * 
     * @param cadastros List of properties
     * @throws IllegalArgumentException if list is null or empty
     */
    public PropertyGraph(List<Cadastro> cadastros) {
        super(cadastros);
        buildGraph();
    }

    /**
     * Builds the graph by checking adjacencies between all properties.
     * @throws TopologyException se ocorrer um erro durante a análise topológica
     */
    private void buildGraph() {
        try {
            for (int i = 0; i < cadastros.size(); i++) {
                for (int j = i + 1; j < cadastros.size(); j++) {
                    Cadastro prop1 = cadastros.get(i);
                    Cadastro prop2 = cadastros.get(j);
                    
                    if (arePropertiesPhysicallyAdjacent(prop1, prop2)) {
                        addAdjacency(prop1, prop2, propertyAdjacencyList);
                    }
                }
            }
        } catch (TopologyException e) {
            throw new IllegalStateException(Constants.GRAPH_BUILD_ERROR + e.getMessage(), e);
        }
    }

    

    /**
     * Returns properties adjacent to a given property.
     * 
     * @param property Target property
     * @return Set of adjacent properties
     * @throws IllegalArgumentException if property is null
     */
    public Set<Cadastro> getAdjacentProperties(Cadastro property) {
        if (property == null) {
            throw new IllegalArgumentException(Constants.NULL_PROPERTY_ERROR);
        }

        return Collections.unmodifiableSet(propertyAdjacencyList.getOrDefault(property, new HashSet<>()));
    }
    
    /**
     * Returns total number of adjacencies.
     * 
     * @return Number of adjacencies
     */
    public int getNumberOfAdjacenciesBetweenProperties() {
        int count = 0;
        for (Set<Cadastro> adjacents : propertyAdjacencyList.values()) {
            count += adjacents.size();
        }
        return count / 2;
    }

    /**
     * Returns string representation of the graph.
     * 
     * @return Graph representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PropertyGraph{properties=[");
        for (int i = 0; i < cadastros.size(); i++) {
            sb.append(cadastros.get(i).toString());
            if (i < cadastros.size() - 1) {
                sb.append(Constants.PROPERTY_SEPARATOR);
            }
        }
        sb.append("], adjacencies=[]}");
        return sb.toString();
    }

    /**
     * Calculates average property area in a region.
     * 
     * @param district District filter (optional)
     * @param municipality Municipality filter (optional)
     * @param county County filter (optional)
     * @return Average area
     * @throws IllegalArgumentException if no properties are found in the specified area
     */
    public double calculateAverageArea(String district, String municipality, String county) {
        if (district == null && municipality == null && county == null) {
            throw new IllegalArgumentException("Pelo menos um parâmetro de localização deve ser fornecido");
        }

        List<Cadastro> filteredCadastros = filterCadastrosByLocation(cadastros, district, municipality, county);

        if (filteredCadastros.isEmpty()) {
            StringBuilder areaInfo = new StringBuilder("Não há propriedades na área especificada: ");
            if (district != null) areaInfo.append("Distrito=").append(district).append(" ");
            if (municipality != null) areaInfo.append("Município=").append(municipality).append(" ");
            if (county != null) areaInfo.append("Concelho=").append(county);
            throw new IllegalArgumentException(areaInfo.toString());
        }

        return filteredCadastros.stream()
                .mapToDouble(Cadastro::getArea)
                .average()
                .orElse(0.0);
    }
}