package service;

import model.Cadastro;
import core.Constants;
import model.Location;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.TopologyException;

import java.util.*;

/**
 * Classe base para grafos de gestão de propriedades.
 * Implementa funcionalidades comuns para diferentes tipos de grafos no sistema.
 * 
 * @author Lei-G
 * @version 1.0
 */
public class Graph {
    /** Lista de todas as propriedades no grafo */
    protected final List<Cadastro> cadastros;
    
    /** Mapa que armazena as relações de adjacência entre propriedades */
    protected final Map<Cadastro, Set<Cadastro>> propertyAdjacencyList;

    /**
     * Cria um grafo a partir de uma lista de propriedades.
     * 
     * @param cadastros Lista de propriedades para construir o grafo
     * @throws IllegalArgumentException if list is null or empty
     */
    public Graph(List<Cadastro> cadastros) {
        if (cadastros == null) {
            throw new IllegalArgumentException(Constants.NULL_CADASTROS_ERROR);
        }
        if (cadastros.isEmpty()) {
            throw new IllegalArgumentException(Constants.EMPTY_CADASTROS_ERROR);
        }
        if (cadastros.contains(null)) {
            throw new IllegalArgumentException(Constants.NULL_ELEMENTS_ERROR);
        }

        this.cadastros = cadastros;
        this.propertyAdjacencyList = new HashMap<>();
    }

    /**
     * Adds an adjacency between two elements.
     * 
     * @param <T> Element type (Integer for owners, Cadastro for properties)
     * @param element1 First element
     * @param element2 Second element
     * @param adjacencyMap Adjacency map to use
     */
    public static <T> void addAdjacency(T element1, T element2, Map<T, Set<T>> adjacencyMap) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException(Constants.NULL_PROPERTY_ERROR);
        }

        if (element1 instanceof Integer && element2 instanceof Integer) {
            int owner1 = (Integer) element1;
            int owner2 = (Integer) element2;
            if (owner1 <= 0 || owner2 <= 0) {
                throw new IllegalArgumentException(Constants.INVALID_OWNER_ERROR);
            }
        }

        adjacencyMap.computeIfAbsent(element1, _ -> new HashSet<>()).add(element2);
        adjacencyMap.computeIfAbsent(element2, _ -> new HashSet<>()).add(element1);
    }

    /**
     * Checks if two properties are physically adjacent.
     * 
     * @param prop1 First property
     * @param prop2 Second property
     * @return true if properties are adjacent
     */
    public static boolean arePropertiesPhysicallyAdjacent(Cadastro prop1, Cadastro prop2) {
        if (prop1 == null || prop2 == null) {
            throw new IllegalArgumentException(Constants.NULL_PROPERTY_ERROR);
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
            throw new IllegalStateException(Constants.ADJACENCY_ERROR + e.getMessage(), e);
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
            throw new IllegalArgumentException(Constants.NULL_PROPERTY_ERROR);
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
                Location location = cadastro.getLocation();
                if (location == null) return false;
                
                boolean matchesDistrict = district == null || location.freguesia().equals(district);
                boolean matchesMunicipality = municipality == null || location.concelho().equals(municipality);
                boolean matchesCounty = county == null || location.distrito().equals(county);
                
                return matchesDistrict && matchesMunicipality && matchesCounty;
            })
            .toList();
    }

    /**
     * Returns adjacent elements for a given element.
     * 
     * @param <T> Element type
     * @param element Target element
     * @param adjacencyMap Adjacency map to use
     * @return Set of adjacent elements
     */
    public static <T> Set<T> getAdjacent(T element, Map<T, Set<T>> adjacencyMap) {
        if (element == null) {
            throw new IllegalArgumentException(Constants.NULL_PROPERTY_ERROR);
        }

        return Collections.unmodifiableSet(adjacencyMap.getOrDefault(element, new HashSet<>()));
    }

    /**
     * Returns total number of properties.
     * 
     * @return Number of properties
     */
    public int getNumberOfProperties() {
        return cadastros.size();
    }

    /**
     * Returns list of all properties.
     * 
     * @return List of properties
     */
    public List<Cadastro> getProperties() {
        return cadastros;
    }
     
    /**
     * Returns list of all cadastros.
     * 
     * @return List of cadastros
     */
    public List<Cadastro> getCadastros() {
        return cadastros;
    }
} 