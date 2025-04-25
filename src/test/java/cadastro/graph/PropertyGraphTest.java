package cadastro.graph;
import cadastro.importer.Cadastro;
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
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
class PropertyGraphTest {

    private static PropertyGraph graph;
    private static List<Cadastro> cadastros;

    @BeforeAll
    static void setUp() throws Exception {
        PropertyGraphTestLogger.log("=== Iniciando setup dos testes ===\n");

        String filePath = "Dados/Madeira-Moodle-1.1.csv";
        
        cadastros = Cadastro.getCadastros(filePath);
    
        PropertyGraphTestLogger.logSuccess("Cadastros de teste criados com sucesso\n");

        graph = new PropertyGraph(cadastros);

        PropertyGraphTestLogger.logSuccess("Grafo de propriedades criado com sucesso\n");
        PropertyGraphTestLogger.log("=== Setup concluído ===\n");
    }

    @AfterAll
    static void tearDown() {
        PropertyGraphTestLogger.close();
    }

    /**
     * Testa a criação do grafo e verifica se ele contém o número correto de propriedades.
     */

    @Test
    void constructor() {

        PropertyGraphTestLogger.logTestStart("Construtor do PropertyGraph");
        assertNotNull(graph, "O grafo deve ser criado");
        assertEquals(cadastros.size(), graph.getNumberOfProperties(), "O grafo deve ter " + cadastros.size() + " propriedades");
        PropertyGraphTestLogger.logSuccess("Grafo criado corretamente com " + cadastros.size() + " propriedades");
        PropertyGraphTestLogger.logTestEnd("Construtor do PropertyGraph");
    }

    /**
     * Testa a obtenção de propriedades adjacentes para um cadastro específico.
     */
    @Test
    void getAdjacentProperties() {

        PropertyGraphTestLogger.logTestStart("Obtenção de propriedades adjacentes");
        
        // Testa apenas os primeiros 5 cadastros
        int testSize = Math.min(5, cadastros.size());
        PropertyGraphTestLogger.log("Testando adjacências para " + testSize + " cadastros");
        
        for (int i = 0; i < testSize; i++) {
            Cadastro currentCadastro = cadastros.get(i);
            PropertyGraphTestLogger.log("Verificando adjacências para o cadastro: " + currentCadastro.toString());
            
            Set<Cadastro> adjacent = graph.getAdjacentProperties(currentCadastro);
            assertNotNull(adjacent, "O conjunto de propriedades adjacentes não deve ser nulo");
            
            PropertyGraphTestLogger.log("Número de propriedades adjacentes encontradas: " + adjacent.size());
            if (!adjacent.isEmpty()) {
                PropertyGraphTestLogger.log("Propriedades adjacentes:");
                for (Cadastro adj : adjacent) {
                    PropertyGraphTestLogger.log("  - " + adj.toString());
                }
            }
        }
        
        PropertyGraphTestLogger.logSuccess("Verificação de adjacências realizada com sucesso para todos os cadastros testados");
        PropertyGraphTestLogger.logTestEnd("Obtenção de propriedades adjacentes");
    }

    /**
     * Testa a verificação de adjacência entre duas propriedades (primeira direção).
     */
    @Test
    void areAdjacent1() {
        PropertyGraphTestLogger.logTestStart("Verificação de adjacência (direção 1)");
        
        int testSize = Math.min(5, cadastros.size());
        PropertyGraphTestLogger.log("Testando adjacências entre " + testSize + " cadastros");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                Cadastro cadastro1 = cadastros.get(i);
                Cadastro cadastro2 = cadastros.get(j);
                
                PropertyGraphTestLogger.log("Verificando adjacência entre:");
                PropertyGraphTestLogger.log("  - Cadastro 1: " + cadastro1.toString());
                PropertyGraphTestLogger.log("  - Cadastro 2: " + cadastro2.toString());
                
                boolean areAdjacent = graph.areAdjacent(cadastro1, cadastro2);
                PropertyGraphTestLogger.log("Resultado: " + (areAdjacent ? "São adjacentes" : "Não são adjacentes"));
                
                assertFalse(areAdjacent, "Os cadastros não devem ser adjacentes");
            }
        }
        
        PropertyGraphTestLogger.logSuccess("Verificação de não adjacência confirmada para todos os pares testados");
        PropertyGraphTestLogger.logTestEnd("Verificação de adjacência (direção 1)");
    }

    /**
     * Testa a verificação de adjacência entre duas propriedades (direção oposta).
     */
    @Test
    void areAdjacent2() {
        PropertyGraphTestLogger.logTestStart("Verificação de adjacência (direção 2)");
        
        int testSize = Math.min(5, cadastros.size());
        PropertyGraphTestLogger.log("Testando adjacências entre " + testSize + " cadastros (direção inversa)");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                Cadastro cadastro1 = cadastros.get(j);
                Cadastro cadastro2 = cadastros.get(i);
                
                PropertyGraphTestLogger.log("Verificando adjacência entre:");
                PropertyGraphTestLogger.log("  - Cadastro 1: " + cadastro1.toString());
                PropertyGraphTestLogger.log("  - Cadastro 2: " + cadastro2.toString());
                
                boolean areAdjacent = graph.areAdjacent(cadastro1, cadastro2);
                PropertyGraphTestLogger.log("Resultado: " + (areAdjacent ? "São adjacentes" : "Não são adjacentes"));
                
                assertFalse(areAdjacent, "Os cadastros não devem ser adjacentes");
            }
        }
        
        PropertyGraphTestLogger.logSuccess("Verificação de não adjacência confirmada para todos os pares testados (direção inversa)");
        PropertyGraphTestLogger.logTestEnd("Verificação de adjacência (direção 2)");
    }

    /**
     * Testa a contagem total de propriedades no grafo.
     */
    @Test
    void getNumberOfProperties() {
        PropertyGraphTestLogger.logTestStart("Contagem de propriedades");
        
        PropertyGraphTestLogger.log("Obtendo número total de propriedades no grafo");
        int numberOfProperties = graph.getNumberOfProperties();
        PropertyGraphTestLogger.log("Número de propriedades encontrado: " + numberOfProperties);
        PropertyGraphTestLogger.log("Número esperado: " + cadastros.size());
        
        assertEquals(cadastros.size(), numberOfProperties, "O grafo deve ter " + cadastros.size() + " propriedades");
        
        PropertyGraphTestLogger.logSuccess("Número correto de propriedades verificado");
        PropertyGraphTestLogger.logTestEnd("Contagem de propriedades");
    }

    /**
     * Testa a contagem total de adjacências no grafo.
     */
    @Test
    void getNumberOfAdjacencies() {
        PropertyGraphTestLogger.logTestStart("Contagem de adjacências");
        
        PropertyGraphTestLogger.log("Obtendo número total de adjacências no grafo");
        int numberOfAdjacencies = graph.getNumberOfAdjacencies();
        PropertyGraphTestLogger.log("Número de adjacências encontrado: " + numberOfAdjacencies);
        
        assertNotNull(numberOfAdjacencies, "O número de adjacências não deve ser nulo");
        PropertyGraphTestLogger.log("Verificando se o número de adjacências é válido");
        assertTrue(numberOfAdjacencies >= 0, "O número de adjacências deve ser não negativo");
        
        PropertyGraphTestLogger.logSuccess("Número de adjacências verificado com sucesso");
        PropertyGraphTestLogger.logTestEnd("Contagem de adjacências");
    }

}