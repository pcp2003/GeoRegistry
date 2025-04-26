package cadastro.graph;

import cadastro.importer.Cadastro;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Classe que representa um grafo de proprietários, onde os vértices são proprietários
 * e as arestas representam a relação de vizinhança entre os proprietários.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class OwnerGraph {
    private final Map<Integer, Set<Integer>> neighboursList;
    private final Map<Integer, List<Cadastro>> ownerProperties;

    /**
     * Constrói um grafo de proprietários a partir de uma lista de cadastros.
     * 
     * @param cadastros Lista de cadastros que serão usados para construir o grafo
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

        this.neighboursList = new HashMap<>();
        this.ownerProperties = new HashMap<>();
        buildGraph(cadastros);
    }

    /**
     * Constrói o grafo verificando as relações de vizinhança entre os proprietários.
     * @throws TopologyException se ocorrer um erro durante a análise topológica
     */
    private void buildGraph(List<Cadastro> cadastros) {
        try {
            // Primeiro, agrupa as propriedades por proprietário
            for (Cadastro cadastro : cadastros) {
                int owner = cadastro.getOwner();
                ownerProperties.computeIfAbsent(owner, _ -> new ArrayList<>()).add(cadastro);
            }

            // Depois, verifica adjacências entre propriedades de proprietários diferentes
            for (int i = 0; i < cadastros.size(); i++) {
                for (int j = i + 1; j < cadastros.size(); j++) {
                    Cadastro prop1 = cadastros.get(i);
                    Cadastro prop2 = cadastros.get(j);
                    
                    if (arePropertiesPhysicallyAdjacent(prop1, prop2)) {
                        int owner1 = prop1.getOwner();
                        int owner2 = prop2.getOwner();
                        
                        if (owner1 != owner2) {
                            addAdjacency(owner1, owner2);
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
     * @param owner1 Primeiro proprietário
     * @param owner2 Segundo proprietário
     */
    private void addAdjacency(int owner1, int owner2) {
        neighboursList.computeIfAbsent(owner1, _ -> new HashSet<>()).add(owner2);
        neighboursList.computeIfAbsent(owner2, _ -> new HashSet<>()).add(owner1);
    }

    /**
     * Retorna o conjunto de proprietários adjacentes a um proprietário específico.
     * 
     * @param owner O proprietário cujas adjacências serão retornadas
     * @return Conjunto de proprietários adjacentes
     */
    public Set<Integer> getAdjacentOwners(int owner) {
        return Collections.unmodifiableSet(neighboursList.getOrDefault(owner, new HashSet<>()));
    }
    
    /**
     * Verifica se dois proprietários são adjacentes no grafo.
     * 
     * @param owner1 Primeiro proprietário
     * @param owner2 Segundo proprietário
     * @return true se os proprietários são adjacentes, false caso contrário
     */
    public boolean areAdjacent(int owner1, int owner2) {
        return neighboursList.containsKey(owner1) && neighboursList.get(owner1).contains(owner2);
    }
    
    /**
     * Retorna o número total de proprietários no grafo.
     * 
     * @return Número de proprietários
     */
    public int getNumberOfOwners() {
        return ownerProperties.size();
    }
    
    /**
     * Retorna o número total de adjacências no grafo.
     * 
     * @return Número de adjacências
     */
    public int getNumberOfAdjacencies() {
        int count = 0;
        for (Set<Integer> adjacents : neighboursList.values()) {
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
        List<Integer> owners = new ArrayList<>(ownerProperties.keySet());
        for (int i = 0; i < owners.size(); i++) {
            sb.append(owners.get(i));
            if (i < owners.size() - 1) {
                sb.append(GraphConstants.PROPERTY_SEPARATOR);
            }
        }
        sb.append("], adjacencies=[]}");
        return sb.toString();
    }

    /**
     * Retorna a lista de propriedades de um proprietário específico.
     * 
     * @param owner O proprietário cujas propriedades serão retornadas
     * @return Lista de propriedades do proprietário
     */
    public List<Cadastro> getOwnerProperties(int owner) {
        return Collections.unmodifiableList(ownerProperties.getOrDefault(owner, new ArrayList<>()));
    }
}