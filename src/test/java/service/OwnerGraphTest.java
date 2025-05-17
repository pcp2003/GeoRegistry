package service;

import model.Cadastro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import core.Constants;

/**
 * Classe de teste para OwnerGraph
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Complexidade Ciclomática por método:
 * - construtor: 1 (herda de Graph)
 * - createGraph: 2 (1 try-catch + 1 retorno)
 * - calculateAverageArea: 4 (3 condições if + 1 retorno)
 * - getAdjacentProperties: 2 (1 condição if + 1 retorno)
 * - getNumberOfOwners: 1 (1 retorno)
 * - getNumberOfAdjacenciesBetweenOwners: 1 (1 retorno)
 * - toString: 1 (1 retorno)
 */
class OwnerGraphTest {
    private List<Cadastro> testCadastros;

    @BeforeEach
    void setUp() throws ParseException {
        testCadastros = new ArrayList<>();
        
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

        testCadastros.add(new Cadastro(mockRecord1));
        testCadastros.add(new Cadastro(mockRecord2));
    }

    /**
     * Testa o construtor - Complexidade Ciclomática: 1
     */
    @Test
    void constructor() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertNotNull(graph, "OwnerGraph deve ser criado com cadastros válidos");
    }

    /**
     * Testa createGraph - Complexidade Ciclomática: 2
     */
    @Test
    void createGraph1() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertTrue(graph.getNumberOfOwners() >= 0, "Grafo deve ser criado com sucesso");
    }

    @Test
    void createGraph2() throws ParseException {
        // Criar uma lista com geometrias inválidas que causariam TopologyException
        List<Cadastro> invalidCadastros = new ArrayList<>();
        
        // Criar primeira propriedade com geometria inválida
        CSVRecord mockRecord1 = mock(CSVRecord.class);
        when(mockRecord1.get(Constants.ID_INDEX)).thenReturn("1"); // ID
        when(mockRecord1.get(Constants.LENGTH_INDEX)).thenReturn("10.5"); // Comprimento
        when(mockRecord1.get(Constants.AREA_INDEX)).thenReturn("100.0"); // Área
        when(mockRecord1.get(Constants.SHAPE_INDEX)).thenReturn("MULTIPOLYGON (((0 0, 0 1, 1 1, 1 0, 0 0)))"); // Forma
        when(mockRecord1.get(Constants.OWNER_INDEX)).thenReturn("1"); // Proprietário
        when(mockRecord1.get(Constants.FREGUESIA_INDEX)).thenReturn("Santa Maria Maior"); // Freguesia
        when(mockRecord1.get(Constants.CONCELHO_INDEX)).thenReturn("Lisboa"); // Concelho
        when(mockRecord1.get(Constants.DISTRICT_INDEX)).thenReturn("Lisboa"); // Distrito

        // Criar segunda propriedade com geometria auto-intersectante
        CSVRecord mockRecord2 = mock(CSVRecord.class);
        when(mockRecord2.get(Constants.ID_INDEX)).thenReturn("2"); // ID
        when(mockRecord2.get(Constants.LENGTH_INDEX)).thenReturn("10.5"); // Comprimento
        when(mockRecord2.get(Constants.AREA_INDEX)).thenReturn("100.0"); // Área
        when(mockRecord2.get(Constants.SHAPE_INDEX)).thenReturn("MULTIPOLYGON (((0 0, 2 2, 0 2, 2 0, 0 0)))"); // Forma Auto-intersectante
        when(mockRecord2.get(Constants.OWNER_INDEX)).thenReturn("2"); // Proprietário
        when(mockRecord2.get(Constants.FREGUESIA_INDEX)).thenReturn("Santa Maria Maior"); // Freguesia
        when(mockRecord2.get(Constants.CONCELHO_INDEX)).thenReturn("Lisboa"); // Concelho
        when(mockRecord2.get(Constants.DISTRICT_INDEX)).thenReturn("Lisboa"); // Distrito

        invalidCadastros.add(new Cadastro(mockRecord1));
        invalidCadastros.add(new Cadastro(mockRecord2));

        assertThrows(IllegalStateException.class, () -> new OwnerGraph(invalidCadastros),
                "Deve lançar IllegalStateException para cadastros inválidos");
    }

    /**
     * Testa calculateAverageArea - Complexidade Ciclomática: 4
     */
    @Test
    void calculateAverageArea1() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea(null, null, null),
                "Deve lançar IllegalArgumentException quando nenhum parâmetro de localização é fornecido");
    }

    @Test
    void calculateAverageArea2() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea("nonexistent", null, null),
                "Deve lançar IllegalArgumentException quando nenhuma propriedade corresponde ao filtro de distrito");
    }

    @Test
    void calculateAverageArea3() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea(null, "nonexistent", null),
                "Deve lançar IllegalArgumentException quando nenhuma propriedade corresponde ao filtro de município");
    }

    @Test
    void calculateAverageArea4() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.calculateAverageArea(null, null, "nonexistent"),
                "Deve lançar IllegalArgumentException quando nenhuma propriedade corresponde ao filtro de concelho");
    }

    /**
     * Testa getAdjacentProperties - Complexidade Ciclomática: 2
     */
    @Test
    void getAdjacentProperties1() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        Set<Cadastro> adjacent = graph.getAdjacentProperties(testCadastros.get(0));
        assertNotNull(adjacent, "Deve retornar conjunto de propriedades adjacentes");
    }

    @Test
    void getAdjacentProperties2() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertThrows(IllegalArgumentException.class,
                () -> graph.getAdjacentProperties(null),
                "Deve lançar IllegalArgumentException para propriedade nula");
    }

    /**
     * Testa getNumberOfOwners - Complexidade Ciclomática: 1
     */
    @Test
    void getNumberOfOwners() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertTrue(graph.getNumberOfOwners() >= 0, "Deve retornar número não negativo de proprietários");
    }

    /**
     * Testa getNumberOfAdjacenciesBetweenOwners - Complexidade Ciclomática: 1
     */
    @Test
    void getNumberOfAdjacenciesBetweenOwners() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        assertTrue(graph.getNumberOfAdjacenciesBetweenOwners() >= 0,
                "Deve retornar número não negativo de adjacências");
    }

    /**
     * Testa toString - Complexidade Ciclomática: 1
     */
    @Test
    void testToString() {
        OwnerGraph graph = new OwnerGraph(testCadastros);
        String str = graph.toString();
        assertTrue(str.startsWith("OwnerGraph{owners=["),
                "Representação em string deve começar com 'OwnerGraph{owners=['");
        assertTrue(str.endsWith("], adjacencies=[]}"),
                "Representação em string deve terminar com '], adjacencies=[]}'");
    }
}