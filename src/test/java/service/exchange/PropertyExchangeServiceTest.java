package service.exchange;

import util.TestLogger;
import model.Cadastro;
import service.OwnerGraph;
import service.PropertyGraph;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para PropertyExchangeService
 * 
 * Complexidade Ciclomática dos métodos:
 * - PropertyExchangeService(): 1
 * - generateExchangeSuggestions(): 3
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
class PropertyExchangeServiceTest {

    private static PropertyExchangeService service;
    private static List<Cadastro> cadastros;
    private static OwnerGraph ownerGraph;
    private static PropertyGraph propertyGraph;

    @BeforeAll
    static void setUp() throws Exception {
        TestLogger.init("PropertyExchangeServiceTest");
        TestLogger.log("=== Iniciando setup dos testes ===\n");
        
        String filePath = "Dados/Madeira-Moodle-1.1.csv";
        
        cadastros = Cadastro.getCadastros(filePath);
        TestLogger.logSuccess("Cadastros de teste criados com sucesso\n");
        
        ownerGraph = new OwnerGraph(cadastros);
        propertyGraph = new PropertyGraph(cadastros);
        service = new PropertyExchangeService(ownerGraph, propertyGraph, cadastros);

        TestLogger.logSuccess("Serviço de troca de propriedades criado com sucesso\n");
        TestLogger.log("=== Setup concluído ===\n");
    }

    @AfterAll
    static void tearDown() {
        TestLogger.close();
    }

    /**
     * Testa a criação do serviço e verifica se ele foi inicializado corretamente.
     */
    @Test
    void constructor() {
        TestLogger.logTestStart("Construtor do PropertyExchangeService");
        assertNotNull(service, "O serviço deve ser criado");
        TestLogger.logSuccess("Serviço criado corretamente");
        TestLogger.logTestEnd("Construtor do PropertyExchangeService");
    }

    /**
     * Testa a geração de sugestões de troca com diferentes números máximos de sugestões.
     */
    @Test
    void generateExchangeSuggestions() {
        TestLogger.logTestStart("Geração de sugestões de troca");
        
        // Testa diferentes números máximos de sugestões
        int[] maxSuggestions = {1, 5, 10, 20};
        
        for (int max : maxSuggestions) {
            TestLogger.log("Testando com " + max + " sugestões máximas");
            
            List<PropertyExchange> suggestions = service.generateExchangeSuggestions(max);
            assertNotNull(suggestions, "A lista de sugestões não deve ser nula");
            assertTrue(suggestions.size() <= max, "O número de sugestões não deve exceder o máximo especificado");
            
            // Verifica se as sugestões estão ordenadas corretamente
            for (int i = 0; i < suggestions.size() - 1; i++) {
                PropertyExchange current = suggestions.get(i);
                PropertyExchange next = suggestions.get(i + 1);
                
                double currentScore = current.getFeasibilityScore() * current.getAverageAreaImprovement();
                double nextScore = next.getFeasibilityScore() * next.getAverageAreaImprovement();
                
                assertTrue(currentScore >= nextScore, "As sugestões devem estar ordenadas por pontuação decrescente");
            }
            
            // Verifica se as propriedades em cada sugestão são adjacentes
            for (PropertyExchange suggestion : suggestions) {
                Cadastro prop1 = suggestion.getProperty1();
                Cadastro prop2 = suggestion.getProperty2();
                
                assertTrue(propertyGraph.getAdjacentProperties(prop1).contains(prop2), "As propriedades em cada sugestão devem ser adjacentes");
                
                // Verifica se os proprietários são diferentes
                assertNotEquals(prop1.getOwner(), prop2.getOwner(), "As propriedades devem pertencer a proprietários diferentes");
            }
            
            TestLogger.log("Número de sugestões geradas: " + suggestions.size());
            if (!suggestions.isEmpty()) {
                TestLogger.log("Primeira sugestão:");
                TestLogger.log(suggestions.get(0).toString());
            }
        }
        
        TestLogger.logSuccess("Geração de sugestões testada com sucesso para diferentes números máximos");
        TestLogger.logTestEnd("Geração de sugestões de troca");
    }

    /**
     * Testa a geração de sugestões com número máximo inválido.
     */
    @Test
    void generateExchangeSuggestionsWithInvalidMax() {
        TestLogger.logTestStart("Geração de sugestões com número máximo inválido");
        
        // Testa com número máximo negativo
        assertThrows(IllegalArgumentException.class, () -> {
            service.generateExchangeSuggestions(-1);
        }, "Deve lançar exceção para número máximo negativo");
        
        // Testa com número máximo zero
        assertThrows(IllegalArgumentException.class, () -> {
            service.generateExchangeSuggestions(0);
        }, "Deve lançar exceção para número máximo zero");
        
        TestLogger.logSuccess("Testes com números máximos inválidos concluídos com sucesso");
        TestLogger.logTestEnd("Geração de sugestões com número máximo inválido");
    }
} 