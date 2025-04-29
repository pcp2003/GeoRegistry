package cadastro.graph;

import cadastro.importer.Cadastro;
import cadastro.Constants;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Classe que representa um grafo de proprietários, onde os vértices são proprietários
 * e as arestas representam propriedades adjacentes entre proprietários diferentes.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class OwnerGraph extends Graph {
    private final Map<Integer, Set<Integer>> adjacencyList;

    /**
     * Construtor da classe OwnerGraph.
     * Cria um grafo a partir da lista de cadastros.
     * 
     * @param cadastros Lista de cadastros para criar o grafo
     * @throws IllegalArgumentException se a lista de cadastros for nula ou vazia
     */
    public OwnerGraph(List<Cadastro> cadastros) {
        super(cadastros);
        this.adjacencyList = new HashMap<>();
        createGraph();
    }

    private void createGraph() {
        try {
            // Primeiro, criar o grafo de propriedades
            for (int i = 0; i < cadastros.size(); i++) {
                for (int j = i + 1; j < cadastros.size(); j++) {
                    Cadastro prop1 = cadastros.get(i);
                    Cadastro prop2 = cadastros.get(j);
                    
                    if (arePropertiesPhysicallyAdjacent(prop1, prop2)) {
                        addAdjacency(prop1, prop2, propertyAdjacencyList);
                        
                        // Se os proprietários forem diferentes, adicionar adjacência no grafo de proprietários
                        if (prop1.getOwner() != prop2.getOwner()) {
                            addAdjacency(prop1.getOwner(), prop2.getOwner(), adjacencyList);
                        }
                    }
                }
            }
        } catch (TopologyException e) {
            throw new IllegalStateException(Constants.GRAPH_BUILD_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Calcula a área média por proprietário, considerando propriedades adjacentes do mesmo proprietário
     * como uma única propriedade.
     * 
     * @param district Distrito para filtrar (opcional)
     * @param municipality Município para filtrar (opcional)
     * @param county Concelho para filtrar (opcional)
     * @return A área média por proprietário
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

        // Calcular média por proprietário, considerando propriedades adjacentes como uma única
        Map<Integer, Double> ownerAreas = new HashMap<>();
        Set<Cadastro> processedProperties = new HashSet<>();
        
        for (Cadastro cadastro : filteredCadastros) {
            if (processedProperties.contains(cadastro)) {
                continue;
            }
            
            int owner = cadastro.getOwner();
            double totalArea = cadastro.getArea();
            processedProperties.add(cadastro);
            
            // Encontrar todas as propriedades adjacentes do mesmo proprietário
            Set<Cadastro> adjacentProperties = getAdjacent(cadastro, propertyAdjacencyList);
            for (Cadastro adjacent : adjacentProperties) {
                if (adjacent.getOwner() == owner && !processedProperties.contains(adjacent)) {
                    totalArea += adjacent.getArea();
                    processedProperties.add(adjacent);
                }
            }
            
            ownerAreas.merge(owner, totalArea, Double::sum);
        }
        
        return ownerAreas.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
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
     * Retorna o número total de proprietários no grafo.
     * 
     * @return Número de proprietários
     */
    public int getNumberOfOwners() {
        return adjacencyList.size();
    }
    
    /**
     * Retorna o número total de adjacências no grafo.
     * 
     * @return Número de adjacências
     */
    public int getNumberOfAdjacenciesBetweenOwners() {
        int count = 0;
        for (Set<Integer> adjacents : adjacencyList.values()) {
            count += adjacents.size();
        }
        return count / 2;
    }

    /**
     * Retorna uma representação em string do grafo, mostrando cada proprietário
     * e suas adjacências.
     * 
     * @return String representando o grafo
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OwnerGraph{owners=[");
        for (int i = 0; i < adjacencyList.size(); i++) {
            sb.append(adjacencyList.keySet().toArray()[i]);
            if (i < adjacencyList.size() - 1) {
                sb.append(Constants.PROPERTY_SEPARATOR);
            }
        }
        sb.append("], adjacencies=[]}");
        return sb.toString();
    }
}