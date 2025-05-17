package service;

import model.Cadastro;
import model.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Test class for Graph
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 4 (3 if conditions + 1 return)
 * - addAdjacency: 3 (2 if conditions + 1 return)
 * - arePropertiesPhysicallyAdjacent: 3 (2 if conditions + 1 return)
 * - areAdjacent: 2 (1 if condition + 1 return)
 * - filterCadastrosByLocation: 4 (3 if conditions + 1 return)
 * - getAdjacent: 2 (1 if condition + 1 return)
 * - getNumberOfProperties: 1 (1 return)
 * - getProperties: 1 (1 return)
 * - getCadastros: 1 (1 return)
 */
class GraphTest {
    private List<Cadastro> testCadastros;
    private Map<Cadastro, Set<Cadastro>> adjacencyMap;

    @BeforeEach
    void setUp() throws ParseException {
        testCadastros = new ArrayList<>();
        adjacencyMap = new HashMap<>();
        
        // Create mock cadastros for testing
        CSVRecord mockRecord = mock(CSVRecord.class);
        when(mockRecord.get(anyInt())).thenReturn("1");
        testCadastros.add(new Cadastro(mockRecord));
    }

    /**
     * Test constructor - Cyclomatic Complexity: 4
     */
    @Test
    void constructor1() {
        Graph graph = new Graph(testCadastros);
        assertNotNull(graph, "Graph should be created with valid cadastros");
    }

    @Test
    void constructor2() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(null),
                "Should throw IllegalArgumentException for null cadastros");
    }

    @Test
    void constructor3() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(new ArrayList<>()),
                "Should throw IllegalArgumentException for empty cadastros");
    }

    @Test
    void constructor4() {
        List<Cadastro> cadastrosWithNull = new ArrayList<>();
        cadastrosWithNull.add(null);
        assertThrows(IllegalArgumentException.class, () -> new Graph(cadastrosWithNull),
                "Should throw IllegalArgumentException for cadastros containing null");
    }

    /**
     * Test addAdjacency - Cyclomatic Complexity: 3
     */
    @Test
    void addAdjacency1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(0);
        Graph.addAdjacency(prop1, prop2, adjacencyMap);
        assertTrue(adjacencyMap.containsKey(prop1), "Adjacency map should contain first property");
        assertTrue(adjacencyMap.containsKey(prop2), "Adjacency map should contain second property");
    }

    @Test
    void addAdjacency2() {
        assertThrows(IllegalArgumentException.class, 
                () -> Graph.addAdjacency(null, testCadastros.get(0), adjacencyMap),
                "Should throw IllegalArgumentException for null first property");
    }

    @Test
    void addAdjacency3() {
        assertThrows(IllegalArgumentException.class, 
                () -> Graph.addAdjacency(testCadastros.get(0), null, adjacencyMap),
                "Should throw IllegalArgumentException for null second property");
    }

    /**
     * Test arePropertiesPhysicallyAdjacent - Cyclomatic Complexity: 3
     */
    @Test
    void arePropertiesPhysicallyAdjacent1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(0);
        assertTrue(Graph.arePropertiesPhysicallyAdjacent(prop1, prop2),
                "Properties should be physically adjacent");
    }

    @Test
    void arePropertiesPhysicallyAdjacent2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.arePropertiesPhysicallyAdjacent(null, testCadastros.get(0)),
                "Should throw IllegalArgumentException for null first property");
    }

    @Test
    void arePropertiesPhysicallyAdjacent3() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.arePropertiesPhysicallyAdjacent(testCadastros.get(0), null),
                "Should throw IllegalArgumentException for null second property");
    }

    /**
     * Test areAdjacent - Cyclomatic Complexity: 2
     */
    @Test
    void areAdjacent1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(0);
        Graph.addAdjacency(prop1, prop2, adjacencyMap);
        assertTrue(Graph.areAdjacent(prop1, prop2, adjacencyMap),
                "Properties should be adjacent");
    }

    @Test
    void areAdjacent2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.areAdjacent(null, testCadastros.get(0), adjacencyMap),
                "Should throw IllegalArgumentException for null property");
    }

    /**
     * Test filterCadastrosByLocation - Cyclomatic Complexity: 4
     */
    @Test
    void filterCadastrosByLocation1() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, null, null, null);
        assertEquals(testCadastros.size(), filtered.size(),
                "Should return all cadastros when no filters are applied");
    }

    @Test
    void filterCadastrosByLocation2() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, "test", null, null);
        assertTrue(filtered.isEmpty(),
                "Should return empty list when no cadastros match district filter");
    }

    @Test
    void filterCadastrosByLocation3() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, null, "test", null);
        assertTrue(filtered.isEmpty(),
                "Should return empty list when no cadastros match municipality filter");
    }

    @Test
    void filterCadastrosByLocation4() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, null, null, "test");
        assertTrue(filtered.isEmpty(),
                "Should return empty list when no cadastros match county filter");
    }

    /**
     * Test getAdjacent - Cyclomatic Complexity: 2
     */
    @Test
    void getAdjacent1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(0);
        Graph.addAdjacency(prop1, prop2, adjacencyMap);
        Set<Cadastro> adjacent = Graph.getAdjacent(prop1, adjacencyMap);
        assertTrue(adjacent.contains(prop2),
                "Should return set containing adjacent property");
    }

    @Test
    void getAdjacent2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.getAdjacent(null, adjacencyMap),
                "Should throw IllegalArgumentException for null property");
    }

    /**
     * Test getNumberOfProperties - Cyclomatic Complexity: 1
     */
    @Test
    void getNumberOfProperties() {
        Graph graph = new Graph(testCadastros);
        assertEquals(testCadastros.size(), graph.getNumberOfProperties(),
                "Should return correct number of properties");
    }

    /**
     * Test getProperties - Cyclomatic Complexity: 1
     */
    @Test
    void getProperties() {
        Graph graph = new Graph(testCadastros);
        assertEquals(testCadastros, graph.getProperties(),
                "Should return list of all properties");
    }

    /**
     * Test getCadastros - Cyclomatic Complexity: 1
     */
    @Test
    void getCadastros() {
        Graph graph = new Graph(testCadastros);
        assertEquals(testCadastros, graph.getCadastros(),
                "Should return list of all cadastros");
    }
}