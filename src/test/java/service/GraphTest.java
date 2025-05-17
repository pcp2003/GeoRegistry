package service;

import model.Cadastro;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;
import core.Constants;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de teste para a classe Graph.
 * Contém testes unitários para todas as funcionalidades do grafo, incluindo construção,
 * adição de adjacências e filtragem de propriedades.
 */
class GraphTest {
    private List<Cadastro> testCadastros;
    private Map<Cadastro, Set<Cadastro>> adjacencyMap;

    @BeforeEach
    void setUp() throws ParseException {
        testCadastros = new ArrayList<>();
        adjacencyMap = new HashMap<>();

        // Criar primeira propriedade
        CSVRecord mockRecord1 = mock(CSVRecord.class);
        when(mockRecord1.get(Constants.ID_INDEX)).thenReturn("1"); // ID
        when(mockRecord1.get(Constants.LENGTH_INDEX)).thenReturn("10.5"); // Comprimento
        when(mockRecord1.get(Constants.AREA_INDEX)).thenReturn("100.0"); // Área
        when(mockRecord1.get(Constants.SHAPE_INDEX)).thenReturn("MULTIPOLYGON (((0 0, 0 1, 1 1, 1 0, 0 0)))"); // Forma
        when(mockRecord1.get(Constants.OWNER_INDEX)).thenReturn("1"); // Proprietário
        when(mockRecord1.get(Constants.FREGUESIA_INDEX)).thenReturn("Santa Maria Maior"); // Freguesia
        when(mockRecord1.get(Constants.CONCELHO_INDEX)).thenReturn("Lisboa"); // Concelho
        when(mockRecord1.get(Constants.DISTRICT_INDEX)).thenReturn("Lisboa"); // Distrito

        // Criar segunda propriedade com geometria adjacente
        CSVRecord mockRecord2 = mock(CSVRecord.class);
        when(mockRecord2.get(Constants.ID_INDEX)).thenReturn("2"); // ID
        when(mockRecord2.get(Constants.LENGTH_INDEX)).thenReturn("10.5"); // Comprimento
        when(mockRecord2.get(Constants.AREA_INDEX)).thenReturn("100.0"); // Área
        when(mockRecord2.get(Constants.SHAPE_INDEX)).thenReturn("MULTIPOLYGON (((1 0, 1 1, 2 1, 2 0, 1 0)))"); // Forma Adjacente
        when(mockRecord2.get(Constants.OWNER_INDEX)).thenReturn("2"); // Proprietário
        when(mockRecord2.get(Constants.FREGUESIA_INDEX)).thenReturn("Santa Maria Maior"); // Freguesia
        when(mockRecord2.get(Constants.CONCELHO_INDEX)).thenReturn("Lisboa"); // Concelho
        when(mockRecord2.get(Constants.DISTRICT_INDEX)).thenReturn("Lisboa"); // Distrito

        Cadastro cadastro1 = new Cadastro(mockRecord1);
        Cadastro cadastro2 = new Cadastro(mockRecord2);
        testCadastros.add(cadastro1);
        testCadastros.add(cadastro2);
    }

    /**
     * Testa o construtor com lista válida de cadastros
     */
    @Test
    void constructor1() {
        Graph graph = new Graph(testCadastros);
        assertNotNull(graph);
    }

    /**
     * Testa o construtor com lista nula
     */
    @Test
    void constructor2() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(null));
    }

    /**
     * Testa o construtor com lista vazia
     */
    @Test
    void constructor3() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(new ArrayList<>()));
    }

    /**
     * Testa o construtor com lista contendo elemento nulo
     */
    @Test
    void constructor4() {
        List<Cadastro> cadastrosWithNull = new ArrayList<>();
        cadastrosWithNull.add(null);
        assertThrows(IllegalArgumentException.class, () -> new Graph(cadastrosWithNull));
    }

    /**
     * Testa adição de adjacência válida
     */
    @Test
    void addAdjacency1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(1);
        Graph.addAdjacency(prop1, prop2, adjacencyMap);
        assertTrue(adjacencyMap.containsKey(prop1));
        assertTrue(adjacencyMap.get(prop1).contains(prop2));
    }

    /**
     * Testa adição de adjacência com primeira propriedade nula
     */
    @Test
    void addAdjacency2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.addAdjacency(null, testCadastros.get(0), adjacencyMap));
    }

    /**
     * Testa adição de adjacência com segunda propriedade nula
     */
    @Test
    void addAdjacency3() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.addAdjacency(testCadastros.get(0), null, adjacencyMap));
    }

    /**
     * Testa verificação de adjacência física válida
     */
    @Test
    void arePropertiesPhysicallyAdjacent1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(1);
        assertTrue(Graph.arePropertiesPhysicallyAdjacent(prop1, prop2));
    }

    /**
     * Testa verificação de adjacência física com primeira propriedade nula
     */
    @Test
    void arePropertiesPhysicallyAdjacent2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.arePropertiesPhysicallyAdjacent(null, testCadastros.get(0)));
    }

    /**
     * Testa verificação de adjacência física com segunda propriedade nula
     */
    @Test
    void arePropertiesPhysicallyAdjacent3() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.arePropertiesPhysicallyAdjacent(testCadastros.get(0), null));
    }

    /**
     * Testa verificação de adjacência válida
     */
    @Test
    void areAdjacent1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(1);
        Graph.addAdjacency(prop1, prop2, adjacencyMap);
        assertTrue(Graph.areAdjacent(prop1, prop2, adjacencyMap));
    }

    /**
     * Testa verificação de adjacência com primeira propriedade nula
     */
    @Test
    void areAdjacent2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.areAdjacent(null, testCadastros.get(0), adjacencyMap));
    }

    /**
     * Testa filtragem de cadastros sem critérios de localização
     */
    @Test
    void filterCadastrosByLocation1() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, null, null, null);
        assertEquals(testCadastros.size(), filtered.size());
    }

    /**
     * Testa filtragem de cadastros por freguesia inexistente
     */
    @Test
    void filterCadastrosByLocation2() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, "test", null, null);
        assertTrue(filtered.isEmpty());
    }

    /**
     * Testa filtragem de cadastros por concelho inexistente
     */
    @Test
    void filterCadastrosByLocation3() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, null, "test", null);
        assertTrue(filtered.isEmpty());
    }

    /**
     * Testa filtragem de cadastros por distrito inexistente
     */
    @Test
    void filterCadastrosByLocation4() {
        List<Cadastro> filtered = Graph.filterCadastrosByLocation(testCadastros, null, null, "test");
        assertTrue(filtered.isEmpty());
    }

    /**
     * Testa obtenção de propriedades adjacentes válida
     */
    @Test
    void getAdjacent1() {
        Cadastro prop1 = testCadastros.get(0);
        Cadastro prop2 = testCadastros.get(1);
        Graph.addAdjacency(prop1, prop2, adjacencyMap);
        Set<Cadastro> adjacent = Graph.getAdjacent(prop1, adjacencyMap);
        assertTrue(adjacent.contains(prop2));
    }

    /**
     * Testa obtenção de propriedades adjacentes com propriedade nula
     */
    @Test
    void getAdjacent2() {
        assertThrows(IllegalArgumentException.class,
                () -> Graph.getAdjacent(null, adjacencyMap));
    }

    /**
     * Testa obtenção do número de propriedades
     */
    @Test
    void getNumberOfProperties() {
        Graph graph = new Graph(testCadastros);
        assertEquals(testCadastros.size(), graph.getNumberOfProperties());
    }

    /**
     * Testa obtenção das propriedades
     */
    @Test
    void getProperties() {
        Graph graph = new Graph(testCadastros);
        assertEquals(testCadastros, graph.getProperties());
    }

    /**
     * Testa obtenção dos cadastros
     */
    @Test
    void getCadastros() {
        Graph graph = new Graph(testCadastros);
        assertEquals(testCadastros, graph.getCadastros());
    }
}
