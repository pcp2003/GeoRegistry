package service.exchange;

import model.Cadastro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import core.Constants;

/**
 * Classe de teste para PropertyExchange
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Complexidade Ciclomática por método:
 * - construtor: 1 (1 retorno)
 * - getProperty1: 1 (1 retorno)
 * - getProperty2: 1 (1 retorno)
 * - getPriceDifference: 1 (1 retorno)
 * - getFeasibilityScore: 1 (1 retorno)
 * - getAverageAreaImprovement: 1 (1 retorno)
 * - toString: 1 (1 retorno)
 */
class PropertyExchangeTest {
    private Cadastro property1;
    private Cadastro property2;
    private PropertyExchange exchange;
    private CSVRecord mockRecord;

    @BeforeEach
    void setUp() throws ParseException {
        mockRecord = mock(CSVRecord.class);
        
        // Configurar valores padrão
        when(mockRecord.get(anyInt())).thenReturn("1"); // Valores válidos padrão
        when(mockRecord.get(Constants.ID_INDEX)).thenReturn("1"); // ID
        when(mockRecord.get(Constants.LENGTH_INDEX)).thenReturn("10.5"); // Comprimento
        when(mockRecord.get(Constants.AREA_INDEX)).thenReturn("100.0"); // Área
        when(mockRecord.get(Constants.SHAPE_INDEX)).thenReturn("MULTIPOLYGON (((0 0, 0 1, 1 1, 1 0, 0 0)))"); // Forma
        when(mockRecord.get(Constants.OWNER_INDEX)).thenReturn("1"); // Proprietário
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn("Freguesia1"); // Freguesia
        when(mockRecord.get(Constants.CONCELHO_INDEX)).thenReturn("Municipio1"); // Município
        when(mockRecord.get(Constants.DISTRICT_INDEX)).thenReturn("Concelho1"); // Concelho
        
        property1 = new Cadastro(mockRecord);
        property2 = new Cadastro(mockRecord);
    }

    @Test
    void constructor() {
        double priceDifference = 100.0;
        double feasibilityScore = 0.8;
        double averageAreaImprovement = 50.0;
        
        exchange = new PropertyExchange(property1, property2, priceDifference, feasibilityScore, averageAreaImprovement);
        assertNotNull(exchange, "PropertyExchange deve ser criado com sucesso");
    }

    @Test
    void getProperty1() {
        exchange = new PropertyExchange(property1, property2, 100.0, 0.8, 50.0);
        assertEquals(property1, exchange.getProperty1(), "Deve retornar a primeira propriedade");
    }

    @Test
    void getProperty2() {
        exchange = new PropertyExchange(property1, property2, 100.0, 0.8, 50.0);
        assertEquals(property2, exchange.getProperty2(), "Deve retornar a segunda propriedade");
    }

    @Test
    void getPriceDifference() {
        double priceDifference = 100.0;
        exchange = new PropertyExchange(property1, property2, priceDifference, 0.8, 50.0);
        assertEquals(priceDifference, exchange.getPriceDifference(), "Deve retornar a diferença de preço correta");
    }

    @Test
    void getFeasibilityScore() {
        double feasibilityScore = 0.8;
        exchange = new PropertyExchange(property1, property2, 100.0, feasibilityScore, 50.0);
        assertEquals(feasibilityScore, exchange.getFeasibilityScore(), "Deve retornar o score de viabilidade correto");
    }

    @Test
    void getAverageAreaImprovement() {
        double averageAreaImprovement = 50.0;
        exchange = new PropertyExchange(property1, property2, 100.0, 0.8, averageAreaImprovement);
        assertEquals(averageAreaImprovement, exchange.getAverageAreaImprovement(), "Deve retornar a melhoria na área média correta");
    }

    @Test
    void testToString() {
        exchange = new PropertyExchange(property1, property2, 100.0, 0.8, 50.0);
        String str = exchange.toString();
        assertTrue(str.contains("Propriedades a ser trocadas"), "Representação em string deve conter informações da troca de propriedades");
        assertTrue(str.contains("Diferença de preço"), "Representação em string deve conter diferença de preço");
        assertTrue(str.contains("Viabilidade"), "Representação em string deve conter score de viabilidade");
        assertTrue(str.contains("Melhoria na área média"), "Representação em string deve conter melhoria na área média");
    }
}