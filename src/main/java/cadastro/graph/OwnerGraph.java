package cadastro.graph;

import cadastro.importer.Cadastro;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Classe que representa um grafo de proprietários, onde os vértices são proprietários
 * e as arestas representam propriedades adjacentes entre proprietários diferentes.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class OwnerGraph {
    private final List<Cadastro> cadastros;
    private final Map<Integer, Set<Integer>> adjacencyList;
    private final Map<Cadastro, Set<Cadastro>> propertyAdjacencyList;

    /**
     * Construtor da classe OwnerGraph.
     * Cria um grafo a partir da lista de cadastros.
     * 
     * @param cadastros Lista de cadastros para criar o grafo
     * @throws IllegalArgumentException se a lista de cadastros for nula ou vazia
     */
    public OwnerGraph(List<Cadastro> cadastros) {
        if (cadastros == null) {
            throw new IllegalArgumentException(GraphConstants.NULL_CADASTROS_ERROR);
        }
        if (cadastros.isEmpty()) {
            throw new IllegalArgumentException(GraphConstants.EMPTY_CADASTROS_ERROR);
        }
        if (cadastros.contains(null)) {
            throw new IllegalArgumentException(GraphConstants.NULL_ELEMENTS_ERROR);
        }

        this.cadastros = cadastros;
        this.adjacencyList = new HashMap<>();
        this.propertyAdjacencyList = new HashMap<>();
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
                        addPropertyAdjacency(prop1, prop2);
                        
                        // Se os proprietários forem diferentes, adicionar adjacência no grafo de proprietários
                        if (prop1.getOwner() != prop2.getOwner()) {
                            addAdjacency(prop1.getOwner(), prop2.getOwner());
                        }
                    }
                }
            }
        } catch (TopologyException e) {
            throw new IllegalStateException(GraphConstants.GRAPH_BUILD_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Verifica se duas propriedades são fisicamente adjacentes.
     * 
     * @param prop1 Primeira propriedade
     * @param prop2 Segunda propriedade
     * @return true se as propriedades são adjacentes, false caso contrário
     * @throws IllegalArgumentException se alguma das propriedades for nula
     * @throws TopologyException se ocorrer um erro durante a análise topológica
     */
    private boolean arePropertiesPhysicallyAdjacent(Cadastro prop1, Cadastro prop2) {
        if (prop1 == null || prop2 == null) {
            throw new IllegalArgumentException(GraphConstants.NULL_PROPERTY_ERROR);
        }

        try {
            MultiPolygon shape1 = prop1.getShape();
            MultiPolygon shape2 = prop2.getShape();
            
            if (shape1 == null || shape2 == null) {
                return false;
            }

            return shape1.touches(shape2) || 
                   (shape1.intersects(shape2) && !shape1.within(shape2) && !shape2.within(shape1));
        } catch (TopologyException e) {
            throw new IllegalStateException(GraphConstants.ADJACENCY_ERROR + e.getMessage(), e);
        }
    }
    
    /**
     * Adiciona uma adjacência entre dois proprietários no grafo.
     * 
     * @param owner1 ID do primeiro proprietário
     * @param owner2 ID do segundo proprietário
     * @throws IllegalArgumentException se algum dos IDs for inválido
     */
    private void addAdjacency(int owner1, int owner2) {
        if (owner1 <= 0 || owner2 <= 0) {
            throw new IllegalArgumentException(GraphConstants.INVALID_OWNER_ERROR);
        }

        adjacencyList.computeIfAbsent(owner1, _ -> new HashSet<>()).add(owner2);
        adjacencyList.computeIfAbsent(owner2, _ -> new HashSet<>()).add(owner1);
    }

    /**
     * Adiciona uma adjacência entre duas propriedades no grafo.
     * 
     * @param prop1 Primeira propriedade
     * @param prop2 Segunda propriedade
     * @throws IllegalArgumentException se alguma das propriedades for nula
     */
    private void addPropertyAdjacency(Cadastro prop1, Cadastro prop2) {
        if (prop1 == null || prop2 == null) {
            throw new IllegalArgumentException(GraphConstants.NULL_PROPERTY_ERROR);
        }

        propertyAdjacencyList.computeIfAbsent(prop1, _ -> new HashSet<>()).add(prop2);
        propertyAdjacencyList.computeIfAbsent(prop2, _ -> new HashSet<>()).add(prop1);
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
            throw new IllegalArgumentException(GraphConstants.NULL_PROPERTY_ERROR);
        }

        return Collections.unmodifiableSet(propertyAdjacencyList.getOrDefault(property, new HashSet<>()));
    }

    /**
     * Retorna o conjunto de proprietários adjacentes a um proprietário específico.
     * 
     * @param ownerId O ID do proprietário cujas adjacências serão retornadas
     * @return Conjunto de IDs de proprietários adjacentes
     * @throws IllegalArgumentException se o ID do proprietário for inválido
     */
    public Set<Integer> getAdjacentOwners(int ownerId) {
        if (ownerId <= 0) {
            throw new IllegalArgumentException(GraphConstants.INVALID_OWNER_ERROR);
        }

        return Collections.unmodifiableSet(adjacencyList.getOrDefault(ownerId, new HashSet<>()));
    }
    
    /**
     * Verifica se dois proprietários são adjacentes no grafo.
     * 
     * @param owner1 ID do primeiro proprietário
     * @param owner2 ID do segundo proprietário
     * @return true se os proprietários são adjacentes, false caso contrário
     * @throws IllegalArgumentException se algum dos IDs for inválido
     */
    public boolean areAdjacent(int owner1, int owner2) {
        if (owner1 <= 0 || owner2 <= 0) {
            throw new IllegalArgumentException(GraphConstants.INVALID_OWNER_ERROR);
        }

        return adjacencyList.containsKey(owner1) && adjacencyList.get(owner1).contains(owner2);
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
    public int getNumberOfAdjacencies() {
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
                sb.append(GraphConstants.PROPERTY_SEPARATOR);
            }
        }
        sb.append("], adjacencies=[]}");
        return sb.toString();
    }

    /**
     * Retorna a lista de todos os cadastros usados para criar o grafo.
     * 
     * @return Lista de cadastros
     */
    public List<Cadastro> getCadastros() {
        return cadastros;
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

        // Filtrar cadastros pela localização
        List<Cadastro> filteredCadastros = cadastros.stream()
            .filter(cadastro -> {
                List<String> locations = cadastro.getLocation();
                if (locations.size() < 3) return false;
                
                boolean matchesDistrict = district == null || locations.get(0).equals(district);
                boolean matchesMunicipality = municipality == null || locations.get(1).equals(municipality);
                boolean matchesCounty = county == null || locations.get(2).equals(county);
                
                return matchesDistrict && matchesMunicipality && matchesCounty;
            })
            .toList();

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
            Set<Cadastro> adjacentProperties = getAdjacentProperties(cadastro);
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
}