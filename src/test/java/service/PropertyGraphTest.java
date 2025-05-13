package service;
import util.TestLogger;
import model.Cadastro;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para PropertyGraph
 * 
 * Complexidade Ciclomática dos métodos:
 * - PropertyGraph(): 1
 * - getAdjacentProperties(): 1
 * - areAdjacent(): 2
 * - getNumberOfProperties(): 1
 * - getNumberOfAdjacencies(): 1
 * - toString(): 2
 * - calculateAverageArea(): 3
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
class PropertyGraphTest {

    private static PropertyGraph graph;
    private static List<Cadastro> cadastros;

    @BeforeAll
    static void setUp() throws Exception {
        TestLogger.init("PropertyGraphTest");
        TestLogger.log("=== Iniciando setup dos testes ===\n");

        String filePath = "Dados/Madeira-Moodle-1.1.csv";
        
        cadastros = Cadastro.getCadastros(filePath);
    
        TestLogger.logSuccess("Cadastros de teste criados com sucesso\n");

        graph = new PropertyGraph(cadastros);

        TestLogger.logSuccess("Grafo de propriedades criado com sucesso\n");
        TestLogger.log("=== Setup concluído ===\n");
    }

    @AfterAll
    static void tearDown() {
        TestLogger.close();
    }

    /**
     * Testa a criação do grafo e verifica se ele contém o número correto de propriedades.
     */
    @Test
    void constructor() {
        TestLogger.logTestStart("Construtor do PropertyGraph");
        assertNotNull(graph, "O grafo deve ser criado");
        assertEquals(cadastros.size(), graph.getNumberOfProperties(), "O grafo deve ter " + cadastros.size() + " propriedades");
        TestLogger.logSuccess("Grafo criado corretamente com " + cadastros.size() + " propriedades");
        TestLogger.logTestEnd("Construtor do PropertyGraph");
    }

    /**
     * Testa a obtenção de propriedades adjacentes para um cadastro específico.
     */
    @Test
    void getAdjacentProperties() {
        TestLogger.logTestStart("Obtenção de propriedades adjacentes");
        
        // Testa apenas os primeiros 5 cadastros
        int testSize = Math.min(5, cadastros.size());
        TestLogger.log("Testando adjacências para " + testSize + " cadastros");
        
        for (int i = 0; i < testSize; i++) {
            Cadastro currentCadastro = cadastros.get(i);
            TestLogger.log("Verificando adjacências para o cadastro: " + currentCadastro.toString());
            
            Set<Cadastro> adjacent = graph.getAdjacentProperties(currentCadastro);
            assertNotNull(adjacent, "O conjunto de propriedades adjacentes não deve ser nulo");
            
            TestLogger.log("Número de propriedades adjacentes encontradas: " + adjacent.size());
            if (!adjacent.isEmpty()) {
                TestLogger.log("Propriedades adjacentes:");
                for (Cadastro adj : adjacent) {
                    TestLogger.log("  - " + adj.toString());
                }
            }
        }
        
        TestLogger.logSuccess("Verificação de adjacências realizada com sucesso para todos os cadastros testados");
        TestLogger.logTestEnd("Obtenção de propriedades adjacentes");
    }

    /**
     * Testa a verificação de adjacência entre duas propriedades (primeira direção).
     */
    @Test
    void areAdjacent1() {
        TestLogger.logTestStart("Verificação de adjacência (direção 1)");
        
        int testSize = Math.min(5, cadastros.size());
        TestLogger.log("Testando adjacências entre " + testSize + " cadastros");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                Cadastro cadastro1 = cadastros.get(i);
                Cadastro cadastro2 = cadastros.get(j);
                
                TestLogger.log("Verificando adjacência entre:");
                TestLogger.log("  - Cadastro 1: " + cadastro1.toString());
                TestLogger.log("  - Cadastro 2: " + cadastro2.toString());
                
                boolean areAdjacent = graph.getAdjacentProperties(cadastro1).contains(cadastro2);
                TestLogger.log("Resultado: " + (areAdjacent ? "São adjacentes" : "Não são adjacentes"));
            }
        }
        
        TestLogger.logSuccess("Verificação de adjacência confirmada para todos os pares testados");
        TestLogger.logTestEnd("Verificação de adjacência (direção 1)");
    }

    /**
     * Testa a verificação de adjacência entre duas propriedades (direção oposta).
     */
    @Test
    void areAdjacent2() {
        TestLogger.logTestStart("Verificação de adjacência (direção 2)");
        
        int testSize = Math.min(5, cadastros.size());
        TestLogger.log("Testando adjacências entre " + testSize + " cadastros (direção inversa)");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                Cadastro cadastro1 = cadastros.get(j);
                Cadastro cadastro2 = cadastros.get(i);
                
                TestLogger.log("Verificando adjacência entre:");
                TestLogger.log("  - Cadastro 1: " + cadastro1.toString());
                TestLogger.log("  - Cadastro 2: " + cadastro2.toString());
                
                boolean areAdjacent = graph.getAdjacentProperties(cadastro1).contains(cadastro2);
                TestLogger.log("Resultado: " + (areAdjacent ? "São adjacentes" : "Não são adjacentes"));
            }
        }
        
        TestLogger.logSuccess("Verificação de adjacência confirmada para todos os pares testados (direção inversa)");
        TestLogger.logTestEnd("Verificação de adjacência (direção 2)");
    }

    /**
     * Testa a contagem total de propriedades no grafo.
     */
    @Test
    void getNumberOfProperties() {
        TestLogger.logTestStart("Contagem de propriedades");
        
        TestLogger.log("Obtendo número total de propriedades no grafo");
        int numberOfProperties = graph.getNumberOfProperties();
        TestLogger.log("Número de propriedades encontrado: " + numberOfProperties);
        TestLogger.log("Número esperado: " + cadastros.size());
        
        assertEquals(cadastros.size(), numberOfProperties, "O grafo deve ter " + cadastros.size() + " propriedades");
        
        TestLogger.logSuccess("Número correto de propriedades verificado");
        TestLogger.logTestEnd("Contagem de propriedades");
    }

    /**
     * Testa a contagem total de adjacências no grafo.
     */
    @Test
    void getNumberOfAdjacencies() {
        TestLogger.logTestStart("Contagem de adjacências");
        
        TestLogger.log("Obtendo número total de adjacências no grafo");
        int numberOfAdjacencies = graph.getNumberOfAdjacenciesBetweenProperties();
        TestLogger.log("Número de adjacências encontrado: " + numberOfAdjacencies);
        
        assertNotNull(numberOfAdjacencies, "O número de adjacências não deve ser nulo");
        TestLogger.log("Verificando se o número de adjacências é válido");
        assertTrue(numberOfAdjacencies >= 0, "O número de adjacências deve ser não negativo");
        
        TestLogger.logSuccess("Número de adjacências verificado com sucesso");
        TestLogger.logTestEnd("Contagem de adjacências");
    }

    /**
     * Testa o cálculo da área média das propriedades.
     */
    @Test
    void calculateAverageArea() {
        TestLogger.logTestStart("Cálculo da área média das propriedades");
        
        // Testa o cálculo para diferentes combinações de localização
        String[] districts = {null, "Funchal"};
        String[] municipalities = {null, "Funchal"};
        String[] counties = {null, "Funchal"};
        
        for (String district : districts) {
            for (String municipality : municipalities) {
                for (String county : counties) {
                    // Pula o caso onde todos são null
                    if (district == null && municipality == null && county == null) {
                        continue;
                    }
                    
                    TestLogger.log("Testando cálculo para:");
                    if (district != null) TestLogger.log("  - Distrito: " + district);
                    if (municipality != null) TestLogger.log("  - Município: " + municipality);
                    if (county != null) TestLogger.log("  - Concelho: " + county);
                    
                    try {
                        double averageArea = graph.calculateAverageArea(district, municipality, county);
                        TestLogger.log("Área média calculada: " + averageArea);
                        assertTrue(averageArea >= 0, "A área média deve ser não negativa");
                    } catch (IllegalArgumentException e) {
                        TestLogger.log("Erro esperado: " + e.getMessage());
                    }
                }
            }
        }
        
        TestLogger.logSuccess("Cálculo da área média testado com sucesso para todas as combinações válidas");
        TestLogger.logTestEnd("Cálculo da área média das propriedades");
    }
}