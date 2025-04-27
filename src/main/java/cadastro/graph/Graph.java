package cadastro.graph;

import cadastro.importer.Cadastro;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Classe base que representa um grafo genérico, contendo todas as funcionalidades
 * comuns entre os diferentes tipos de grafos no sistema de cadastro.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class Graph {
    protected final List<Cadastro> cadastros;
    protected final Map<Cadastro, Set<Cadastro>> propertyAdjacencyList;

    /**
     * Construtor da classe base.
     * 
     * @param cadastros Lista de cadastros para criar o grafo
     * @throws IllegalArgumentException se a lista de cadastros for nula ou vazia
     */
    public Graph(List<Cadastro> cadastros) {
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
        this.propertyAdjacencyList = new HashMap<>();
    }

    /**
     * Adiciona uma adjacência entre dois elementos no grafo.
     * 
     * @param <T> Tipo dos elementos (Integer para proprietários, Cadastro para propriedades)
     * @param element1 Primeiro elemento
     * @param element2 Segundo elemento
     * @param adjacencyMap Mapa de adjacência a ser utilizado
     * @throws IllegalArgumentException se algum dos elementos for inválido
     */
    public static <T> void addAdjacency(T element1, T element2, Map<T, Set<T>> adjacencyMap) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException(GraphConstants.NULL_PROPERTY_ERROR);
        }

        if (element1 instanceof Integer && element2 instanceof Integer) {
            int owner1 = (Integer) element1;
            int owner2 = (Integer) element2;
            if (owner1 <= 0 || owner2 <= 0) {
                throw new IllegalArgumentException(GraphConstants.INVALID_OWNER_ERROR);
            }
        }

        adjacencyMap.computeIfAbsent(element1, _ -> new HashSet<>()).add(element2);
        adjacencyMap.computeIfAbsent(element2, _ -> new HashSet<>()).add(element1);
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
    public static boolean arePropertiesPhysicallyAdjacent(Cadastro prop1, Cadastro prop2) {
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
     * Verifica se dois elementos são adjacentes no grafo.
     * 
     * @param <T> Tipo dos elementos
     * @param element1 Primeiro elemento
     * @param element2 Segundo elemento
     * @param adjacencyMap Mapa de adjacência a ser utilizado
     * @return true se os elementos são adjacentes, false caso contrário
     * @throws IllegalArgumentException se algum dos elementos for nulo
     */
    public static <T> boolean areAdjacent(T element1, T element2, Map<T, Set<T>> adjacencyMap) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException(GraphConstants.NULL_PROPERTY_ERROR);
        }

        return adjacencyMap.containsKey(element1) && adjacencyMap.get(element1).contains(element2);
    }

    /**
     * Filtra os cadastros por localização.
     * 
     * @param cadastros Lista de cadastros a ser filtrada
     * @param district Distrito
     * @param municipality Município
     * @param county Concelho
     * @return Lista de cadastros filtrados
     */
    public static List<Cadastro> filterCadastrosByLocation(List<Cadastro> cadastros, String district, String municipality, String county) {
        return cadastros.stream()
            .filter(cadastro -> {
                List<String> locations = cadastro.getLocation();
                if (locations.size() < 3) return false;
                
                boolean matchesDistrict = district == null || locations.get(0).equals(district);
                boolean matchesMunicipality = municipality == null || locations.get(1).equals(municipality);
                boolean matchesCounty = county == null || locations.get(2).equals(county);
                
                return matchesDistrict && matchesMunicipality && matchesCounty;
            })
            .toList();
    }

    /**
     * Retorna o conjunto de elementos adjacentes a um elemento específico.
     * 
     * @param <T> Tipo dos elementos
     * @param element O elemento cujas adjacências serão retornadas
     * @param adjacencyMap Mapa de adjacência a ser utilizado
     * @return Conjunto de elementos adjacentes
     * @throws IllegalArgumentException se o elemento for nulo
     */
    public static <T> Set<T> getAdjacent(T element, Map<T, Set<T>> adjacencyMap) {
        if (element == null) {
            throw new IllegalArgumentException(GraphConstants.NULL_PROPERTY_ERROR);
        }

        return Collections.unmodifiableSet(adjacencyMap.getOrDefault(element, new HashSet<>()));
    }

    /**
     * Retorna o número total de propriedades no grafo.
     * 
     * @return Número de propriedades
     */
    public int getNumberOfProperties() {
        return cadastros.size();
    }

    /**
     * Retorna a lista de todas as propriedades no grafo.
     * 
     * @return Lista de propriedades
     */
    public List<Cadastro> getProperties() {
        return cadastros;
    }
     
    /**
     * Retorna a lista de todos os cadastros usados para criar o grafo.
     * 
     * @return Lista de cadastros
     */
    public List<Cadastro> getCadastros() {
        return cadastros;
    }
} 