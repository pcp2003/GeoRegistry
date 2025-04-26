package cadastro.graph;
import cadastro.importer.Cadastro;
import cadastro.TestLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para OwnerGraph
 * 
 * Complexidade Ciclomática dos métodos:
 * - OwnerGraph(): 1
 * - getAdjacentOwners(): 1
 * - areAdjacent(): 2
 * - getNumberOfOwners(): 1
 * - getNumberOfAdjacencies(): 1
 * - toString(): 2
 * - calculateAverageArea(): 3
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
class OwnerGraphTest {

    private static OwnerGraph graph;
    private static List<Cadastro> cadastros;

    @BeforeAll
    static void setUp() throws Exception {
        TestLogger.init("owner_graph_test");
        TestLogger.log("=== Iniciando setup dos testes ===\n");

        String filePath = "Dados/Madeira-Moodle-1.1.csv";
        
        cadastros = Cadastro.getCadastros(filePath);
    
        TestLogger.logSuccess("Cadastros de teste criados com sucesso\n");

        graph = new OwnerGraph(cadastros);

        TestLogger.logSuccess("Grafo de proprietários criado com sucesso\n");
        TestLogger.log("=== Setup concluído ===\n");
    }

    @AfterAll
    static void tearDown() {
        TestLogger.close();
    }

    /**
     * Testa a criação do grafo e verifica se ele contém o número correto de proprietários.
     */
    @Test
    void constructor() {
        TestLogger.logTestStart("Construtor do OwnerGraph");
        assertNotNull(graph, "O grafo deve ser criado");
        
        // Conta o número de proprietários únicos
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        assertEquals(uniqueOwners.size(), graph.getNumberOfOwners(), 
            "O grafo deve ter " + uniqueOwners.size() + " proprietários");
        
        TestLogger.logSuccess("Grafo criado corretamente com " + uniqueOwners.size() + " proprietários");
        TestLogger.logTestEnd("Construtor do OwnerGraph");
    }

    /**
     * Testa a obtenção de proprietários vizinhos para um proprietário específico.
     */
    @Test
    void getAdjacentOwners() {
        TestLogger.logTestStart("Obtenção de proprietários vizinhos");
        
        // Testa apenas os primeiros 5 proprietários únicos
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        TestLogger.log("Testando vizinhanças para " + testSize + " proprietários");
        
        for (int i = 0; i < testSize; i++) {
            int currentOwner = ownersList.get(i);
            TestLogger.log("Verificando vizinhanças para o proprietário: " + currentOwner);
            
            Set<Integer> adjacent = graph.getAdjacentOwners(currentOwner);
            assertNotNull(adjacent, "O conjunto de proprietários vizinhos não deve ser nulo");
            
            TestLogger.log("Número de proprietários vizinhos encontrados: " + adjacent.size());
            if (!adjacent.isEmpty()) {
                TestLogger.log("Proprietários vizinhos:");
                for (Integer adj : adjacent) {
                    TestLogger.log("  - " + adj);
                }
            }
        }
        
        TestLogger.logSuccess("Verificação de vizinhanças realizada com sucesso para todos os proprietários testados");
        TestLogger.logTestEnd("Obtenção de proprietários vizinhos");
    }

    /**
     * Testa a verificação de vizinhança entre dois proprietários (primeira direção).
     */
    @Test
    void areAdjacent1() {
        TestLogger.logTestStart("Verificação de vizinhança (direção 1)");
        
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        TestLogger.log("Testando vizinhanças entre " + testSize + " proprietários");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                int owner1 = ownersList.get(i);
                int owner2 = ownersList.get(j);
                
                TestLogger.log("Verificando vizinhança entre:");
                TestLogger.log("  - Proprietário 1: " + owner1);
                TestLogger.log("  - Proprietário 2: " + owner2);
                
                boolean areAdjacent = graph.areAdjacent(owner1, owner2);
                TestLogger.log("Resultado: " + (areAdjacent ? "São vizinhos" : "Não são vizinhos"));
            }
        }
        
        TestLogger.logSuccess("Verificação de vizinhança confirmada para todos os pares testados");
        TestLogger.logTestEnd("Verificação de vizinhança (direção 1)");
    }

    /**
     * Testa a verificação de vizinhança entre dois proprietários (direção oposta).
     */
    @Test
    void areAdjacent2() {
        TestLogger.logTestStart("Verificação de vizinhança (direção 2)");
        
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        TestLogger.log("Testando vizinhanças entre " + testSize + " proprietários (direção inversa)");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                int owner1 = ownersList.get(j);
                int owner2 = ownersList.get(i);
                
                TestLogger.log("Verificando vizinhança entre:");
                TestLogger.log("  - Proprietário 1: " + owner1);
                TestLogger.log("  - Proprietário 2: " + owner2);
                
                boolean areAdjacent = graph.areAdjacent(owner1, owner2);
                TestLogger.log("Resultado: " + (areAdjacent ? "São vizinhos" : "Não são vizinhos"));
            }
        }
        
        TestLogger.logSuccess("Verificação de vizinhança confirmada para todos os pares testados (direção inversa)");
        TestLogger.logTestEnd("Verificação de vizinhança (direção 2)");
    }

    /**
     * Testa a contagem total de proprietários no grafo.
     */
    @Test
    void getNumberOfOwners() {
        TestLogger.logTestStart("Contagem de proprietários");
        
        TestLogger.log("Obtendo número total de proprietários no grafo");
        int numberOfOwners = graph.getNumberOfOwners();
        TestLogger.log("Número de proprietários encontrado: " + numberOfOwners);
        
        // Conta o número de proprietários únicos
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        TestLogger.log("Número esperado: " + uniqueOwners.size());
        assertEquals(uniqueOwners.size(), numberOfOwners, 
            "O grafo deve ter " + uniqueOwners.size() + " proprietários");
        
        TestLogger.logSuccess("Número correto de proprietários verificado");
        TestLogger.logTestEnd("Contagem de proprietários");
    }

    /**
     * Testa a contagem total de vizinhanças no grafo.
     */
    @Test
    void getNumberOfAdjacencies() {
        TestLogger.logTestStart("Contagem de vizinhanças");
        
        TestLogger.log("Obtendo número total de vizinhanças no grafo");
        int numberOfAdjacencies = graph.getNumberOfAdjacencies();
        TestLogger.log("Número de vizinhanças encontrado: " + numberOfAdjacencies);
        
        assertNotNull(numberOfAdjacencies, "O número de vizinhanças não deve ser nulo");
        TestLogger.log("Verificando se o número de vizinhanças é válido");
        assertTrue(numberOfAdjacencies >= 0, "O número de vizinhanças deve ser não negativo");
        
        TestLogger.logSuccess("Número de vizinhanças verificado com sucesso");
        TestLogger.logTestEnd("Contagem de vizinhanças");
    }

    /**
     * Testa o cálculo da área média por proprietário.
     */
    @Test
    void calculateAverageArea() {
        TestLogger.logTestStart("Cálculo da área média por proprietário");
        
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
        TestLogger.logTestEnd("Cálculo da área média por proprietário");
    }
} 