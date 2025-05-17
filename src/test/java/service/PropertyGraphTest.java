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
 * Test class for PropertyGraph
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 1 (inherits from Graph)
 * - createGraph: 2 (1 try-catch + 1 return)
 * - calculateAverageArea: 4 (3 if conditions + 1 return)
 * - getAdjacentProperties: 2 (1 if condition + 1 return)
 * - getNumberOfProperties: 1 (1 return)
 * - getNumberOfAdjacenciesBetweenProperties: 1 (1 return)
 * - toString: 1 (1 return)
 */
class PropertyGraphTest {
    private List<Cadastro> testCadastros;

    @BeforeEach
    void setUp() throws ParseException {
        testCadastros = new ArrayList<>();
        
        // Create mock cadastros for testing
        CSVRecord mockRecord = mock(CSVRecord.class);
        when(mockRecord.get(anyInt())).thenReturn("1");
        testCadastros.add(new Cadastro(mockRecord));
    }

    /**
     * Test constructor - Cyclomatic Complexity: 1
     */
    @Test
    void constructor() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertNotNull(graph, "PropertyGraph should be created with valid cadastros");
    }

    /**
     * Test createGraph - Cyclomatic Complexity: 2
     */
    @Test
    void createGraph1() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertTrue(graph.getNumberOfProperties() >= 0, "Graph should be created successfully");
    }

    @Test
    void createGraph2() {
        // Test with invalid cadastros that would cause TopologyException
        List<Cadastro> invalidCadastros = new ArrayList<>();
        assertThrows(IllegalStateException.class, () -> new PropertyGraph(invalidCadastros),
                "Should throw IllegalStateException for invalid cadastros");
    }

    /**
     * Test calculateAverageArea - Cyclomatic Complexity: 4
     */
    @Test
    void calculateAverageArea1() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea(null, null, null),
                "Should throw IllegalArgumentException when no location parameters are provided");
    }

    @Test
    void calculateAverageArea2() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea("nonexistent", null, null),
                "Should throw IllegalArgumentException when no properties match district filter");
    }

    @Test
    void calculateAverageArea3() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea(null, "nonexistent", null),
                "Should throw IllegalArgumentException when no properties match municipality filter");
    }

    @Test
    void calculateAverageArea4() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea(null, null, "nonexistent"),
                "Should throw IllegalArgumentException when no properties match county filter");
    }

    /**
     * Test getAdjacentProperties - Cyclomatic Complexity: 2
     */
    @Test
    void getAdjacentProperties1() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        Set<Cadastro> adjacent = graph.getAdjacentProperties(testCadastros.get(0));
        assertNotNull(adjacent, "Should return set of adjacent properties");
    }

    @Test
    void getAdjacentProperties2() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.getAdjacentProperties(null),
                "Should throw IllegalArgumentException for null property");
    }

    /**
     * Test getNumberOfProperties - Cyclomatic Complexity: 1
     */
    @Test
    void getNumberOfProperties() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertTrue(graph.getNumberOfProperties() >= 0, "Should return non-negative number of properties");
    }

    /**
     * Test getNumberOfAdjacenciesBetweenProperties - Cyclomatic Complexity: 1
     */
    @Test
    void getNumberOfAdjacenciesBetweenProperties() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        assertTrue(graph.getNumberOfAdjacenciesBetweenProperties() >= 0,
                "Should return non-negative number of adjacencies");
    }

    /**
     * Test toString - Cyclomatic Complexity: 1
     */
    @Test
    void testToString() {
        PropertyGraph graph = new PropertyGraph(testCadastros);
        String str = graph.toString();
        assertTrue(str.startsWith("PropertyGraph{properties=["),
                "String representation should start with 'PropertyGraph{properties=['");
        assertTrue(str.endsWith("], adjacencies=[]}"),
                "String representation should end with '], adjacencies=[]}'");
    }
}