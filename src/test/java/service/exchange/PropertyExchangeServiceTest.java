package service.exchange;

import model.Cadastro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import service.OwnerGraph;
import service.PropertyGraph;
import core.Constants;

/**
 * Classe de teste para o PropertyExchangeService.
 * Contém testes unitários para geração de sugestões de troca de propriedades e
 * cálculos de viabilidade.
 * 
 * @author Lei-G
 * @version 1.0
 * 
 * Complexidade Ciclomática por método:
 * - construtor: 1 (1 retorno)
 * - generateExchangeSuggestions: 4 (3 condições if + 1 retorno)
 * - groupPropertiesByOwner: 1 (1 retorno)
 * - calculateFeasibilityScore: 4 (3 condições if + 1 retorno)
 * - calculateAverageAreaImprovement: 4 (3 condições if + 1 retorno)
 * - toString: 1 (1 retorno)
 */
public class PropertyExchangeServiceTest {
    /**
     * Construtor padrão para PropertyExchangeServiceTest.
     * Inicializa os fixtures de teste e objetos mock.
     */
    public PropertyExchangeServiceTest() {
        // Construtor padrão
    }

    private PropertyExchangeService service;
    private Cadastro property1;
    private Cadastro property2;
    private OwnerGraph ownerGraph;
    private PropertyGraph propertyGraph;
    private List<Cadastro> cadastros;
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
        
        property1 = mock(Cadastro.class);
        property2 = mock(Cadastro.class);
        
        // Configurar comportamento padrão das propriedades
        when(property1.getId()).thenReturn(1);
        when(property2.getId()).thenReturn(2);
        when(property1.getOwner()).thenReturn(1);
        when(property2.getOwner()).thenReturn(2);
        when(property1.getArea()).thenReturn(100.0);
        when(property2.getArea()).thenReturn(200.0);
        when(property1.getLength()).thenReturn(10.5);
        when(property2.getLength()).thenReturn(20.5);
        when(property1.getPrice()).thenReturn(100.0);
        when(property2.getPrice()).thenReturn(200.0);
        
        cadastros = new ArrayList<>();
        cadastros.add(property1);
        cadastros.add(property2);
        
        ownerGraph = mock(OwnerGraph.class);
        propertyGraph = mock(PropertyGraph.class);
        
        service = new PropertyExchangeService(ownerGraph, propertyGraph, cadastros);
    }

    /**
     * Testa o construtor - Complexidade Ciclomática: 1
     */
    @Test
    void constructor() {
        assertNotNull(service, "PropertyExchangeService deve ser criado com sucesso");
    }

    /**
     * Testa generateExchangeSuggestions - Complexidade Ciclomática: 4
     */
    @Test
    void generateExchangeSuggestions1() {
        // Mock que nenhuma propriedade é adjacente
        when(propertyGraph.getAdjacentProperties(any())).thenReturn(Collections.emptySet());
        
        List<PropertyExchange> suggestions = service.generateExchangeSuggestions(1);
        assertNotNull(suggestions, "Deve retornar uma lista de sugestões");
        assertTrue(suggestions.isEmpty(), "Deve retornar lista vazia quando nenhuma troca válida é encontrada");
    }

    @Test
    void generateExchangeSuggestions2() {
        // Mock que as propriedades são adjacentes mas têm o mesmo proprietário
        when(propertyGraph.getAdjacentProperties(any())).thenReturn(Collections.singleton(property2));
        when(property2.getOwner()).thenReturn(1); // Mesmo proprietário que property1
        
        List<PropertyExchange> suggestions = service.generateExchangeSuggestions(1);
        assertNotNull(suggestions, "Deve retornar uma lista de sugestões");
        assertTrue(suggestions.isEmpty(), "Deve retornar lista vazia quando nenhuma troca válida é encontrada");
    }

    @Test
    void generateExchangeSuggestions3() {
        // Mock que as propriedades são adjacentes mas têm a mesma área
        when(propertyGraph.getAdjacentProperties(any())).thenReturn(Collections.singleton(property2));
        when(property1.getArea()).thenReturn(100.0);
        when(property2.getArea()).thenReturn(100.0); // Mesma área que property1
        
        List<PropertyExchange> suggestions = service.generateExchangeSuggestions(1);
        assertNotNull(suggestions, "Deve retornar uma lista de sugestões");
        assertTrue(suggestions.isEmpty(), "Deve retornar lista vazia quando nenhuma troca válida é encontrada");
    }
}