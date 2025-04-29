package cadastro.graph;

import cadastro.importer.Cadastro;
import cadastro.Constants;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Classe que representa um grafo de propriedades, onde os vértices são cadastros (propriedades)
 * e as arestas representam adjacências físicas entre as propriedades.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class PropertyGraph extends Graph {
    /**
     * Constrói um grafo de propriedades a partir de uma lista de cadastros.
     * 
     * @param cadastros Lista de cadastros que serão os vértices do grafo
     * @throws IllegalArgumentException se a lista de cadastros for nula ou vazia
     */
    public PropertyGraph(List<Cadastro> cadastros) {
        super(cadastros);
        buildGraph();
    }

    /**
     * Constrói o grafo verificando adjacências entre todas as propriedades.
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
     * Retorna o conjunto de propriedades adjacentes a uma propriedade específica.
     * 
     * @param property A propriedade cujas adjacências serão retornadas
     * @return Conjunto de propriedades adjacentes
     * @throws IllegalArgumentException se a propriedade for nula
     */
    public Set<Cadastro> getAdjacentProperties(Cadastro property) {
        if (property == null) {
            throw new IllegalArgumentException(Constants.NULL_PROPERTY_ERROR);
        }

        return Collections.unmodifiableSet(propertyAdjacencyList.getOrDefault(property, new HashSet<>()));
    }
    
    /**
     * Retorna o número total de adjacências no grafo.
     * 
     * @return Número de adjacências
     */
    public int getNumberOfAdjacenciesBetweenProperties() {
        int count = 0;
        for (Set<Cadastro> adjacents : propertyAdjacencyList.values()) {
            count += adjacents.size();
        }
        return count / 2;
    }

    /**
     * Retorna uma representação em string do grafo, mostrando cada propriedade
     * e suas adjacências.
     * 
     * @return String representando o grafo
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
     * Calcula a área média das propriedades em uma determinada região.
     * 
     * @param district Distrito para filtrar (opcional)
     * @param municipality Município para filtrar (opcional)
     * @param county Concelho para filtrar (opcional)
     * @return A área média das propriedades
     * @throws IllegalArgumentException se não houver propriedades na área especificada
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