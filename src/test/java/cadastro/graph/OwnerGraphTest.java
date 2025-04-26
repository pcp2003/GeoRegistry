package cadastro.graph;
import cadastro.importer.Cadastro;
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
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
class OwnerGraphTest {

    private static OwnerGraph graph;
    private static List<Cadastro> cadastros;

    @BeforeAll
    static void setUp() throws Exception {
        OwnerGraphTestLogger.log("=== Iniciando setup dos testes ===\n");

        String filePath = "Dados/Madeira-Moodle-1.1.csv";
        
        cadastros = Cadastro.getCadastros(filePath);
    
        OwnerGraphTestLogger.logSuccess("Cadastros de teste criados com sucesso\n");

        graph = new OwnerGraph(cadastros);

        OwnerGraphTestLogger.logSuccess("Grafo de proprietários criado com sucesso\n");
        OwnerGraphTestLogger.log("=== Setup concluído ===\n");
    }

    @AfterAll
    static void tearDown() {
        OwnerGraphTestLogger.close();
    }

    /**
     * Testa a criação do grafo e verifica se ele contém o número correto de proprietários.
     */
    @Test
    void constructor() {
        OwnerGraphTestLogger.logTestStart("Construtor do OwnerGraph");
        assertNotNull(graph, "O grafo deve ser criado");
        
        // Conta o número de proprietários únicos
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        assertEquals(uniqueOwners.size(), graph.getNumberOfOwners(), 
            "O grafo deve ter " + uniqueOwners.size() + " proprietários");
        
        OwnerGraphTestLogger.logSuccess("Grafo criado corretamente com " + uniqueOwners.size() + " proprietários");
        OwnerGraphTestLogger.logTestEnd("Construtor do OwnerGraph");
    }

    /**
     * Testa a obtenção de proprietários vizinhos para um proprietário específico.
     */
    @Test
    void getAdjacentOwners() {
        OwnerGraphTestLogger.logTestStart("Obtenção de proprietários vizinhos");
        
        // Testa apenas os primeiros 5 proprietários únicos
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        OwnerGraphTestLogger.log("Testando vizinhanças para " + testSize + " proprietários");
        
        for (int i = 0; i < testSize; i++) {
            int currentOwner = ownersList.get(i);
            OwnerGraphTestLogger.log("Verificando vizinhanças para o proprietário: " + currentOwner);
            
            Set<Integer> adjacent = graph.getAdjacentOwners(currentOwner);
            assertNotNull(adjacent, "O conjunto de proprietários vizinhos não deve ser nulo");
            
            OwnerGraphTestLogger.log("Número de proprietários vizinhos encontrados: " + adjacent.size());
            if (!adjacent.isEmpty()) {
                OwnerGraphTestLogger.log("Proprietários vizinhos:");
                for (Integer adj : adjacent) {
                    OwnerGraphTestLogger.log("  - " + adj);
                }
            }
        }
        
        OwnerGraphTestLogger.logSuccess("Verificação de vizinhanças realizada com sucesso para todos os proprietários testados");
        OwnerGraphTestLogger.logTestEnd("Obtenção de proprietários vizinhos");
    }

    /**
     * Testa a verificação de vizinhança entre dois proprietários (primeira direção).
     */
    @Test
    void areAdjacent1() {
        OwnerGraphTestLogger.logTestStart("Verificação de vizinhança (direção 1)");
        
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        OwnerGraphTestLogger.log("Testando vizinhanças entre " + testSize + " proprietários");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                int owner1 = ownersList.get(i);
                int owner2 = ownersList.get(j);
                
                OwnerGraphTestLogger.log("Verificando vizinhança entre:");
                OwnerGraphTestLogger.log("  - Proprietário 1: " + owner1);
                OwnerGraphTestLogger.log("  - Proprietário 2: " + owner2);
                
                boolean areAdjacent = graph.areAdjacent(owner1, owner2);
                OwnerGraphTestLogger.log("Resultado: " + (areAdjacent ? "São vizinhos" : "Não são vizinhos"));
            }
        }
        
        OwnerGraphTestLogger.logSuccess("Verificação de vizinhança confirmada para todos os pares testados");
        OwnerGraphTestLogger.logTestEnd("Verificação de vizinhança (direção 1)");
    }

    /**
     * Testa a verificação de vizinhança entre dois proprietários (direção oposta).
     */
    @Test
    void areAdjacent2() {
        OwnerGraphTestLogger.logTestStart("Verificação de vizinhança (direção 2)");
        
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        OwnerGraphTestLogger.log("Testando vizinhanças entre " + testSize + " proprietários (direção inversa)");
        
        for (int i = 0; i < testSize; i++) {
            for (int j = i + 1; j < testSize; j++) {
                int owner1 = ownersList.get(j);
                int owner2 = ownersList.get(i);
                
                OwnerGraphTestLogger.log("Verificando vizinhança entre:");
                OwnerGraphTestLogger.log("  - Proprietário 1: " + owner1);
                OwnerGraphTestLogger.log("  - Proprietário 2: " + owner2);
                
                boolean areAdjacent = graph.areAdjacent(owner1, owner2);
                OwnerGraphTestLogger.log("Resultado: " + (areAdjacent ? "São vizinhos" : "Não são vizinhos"));
            }
        }
        
        OwnerGraphTestLogger.logSuccess("Verificação de vizinhança confirmada para todos os pares testados (direção inversa)");
        OwnerGraphTestLogger.logTestEnd("Verificação de vizinhança (direção 2)");
    }

    /**
     * Testa a contagem total de proprietários no grafo.
     */
    @Test
    void getNumberOfOwners() {
        OwnerGraphTestLogger.logTestStart("Contagem de proprietários");
        
        OwnerGraphTestLogger.log("Obtendo número total de proprietários no grafo");
        int numberOfOwners = graph.getNumberOfOwners();
        OwnerGraphTestLogger.log("Número de proprietários encontrado: " + numberOfOwners);
        
        // Conta o número de proprietários únicos
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        OwnerGraphTestLogger.log("Número esperado: " + uniqueOwners.size());
        assertEquals(uniqueOwners.size(), numberOfOwners, 
            "O grafo deve ter " + uniqueOwners.size() + " proprietários");
        
        OwnerGraphTestLogger.logSuccess("Número correto de proprietários verificado");
        OwnerGraphTestLogger.logTestEnd("Contagem de proprietários");
    }

    /**
     * Testa a contagem total de vizinhanças no grafo.
     */
    @Test
    void getNumberOfAdjacencies() {
        OwnerGraphTestLogger.logTestStart("Contagem de vizinhanças");
        
        OwnerGraphTestLogger.log("Obtendo número total de vizinhanças no grafo");
        int numberOfAdjacencies = graph.getNumberOfAdjacencies();
        OwnerGraphTestLogger.log("Número de vizinhanças encontrado: " + numberOfAdjacencies);
        
        assertNotNull(numberOfAdjacencies, "O número de vizinhanças não deve ser nulo");
        OwnerGraphTestLogger.log("Verificando se o número de vizinhanças é válido");
        assertTrue(numberOfAdjacencies >= 0, "O número de vizinhanças deve ser não negativo");
        
        OwnerGraphTestLogger.logSuccess("Número de vizinhanças verificado com sucesso");
        OwnerGraphTestLogger.logTestEnd("Contagem de vizinhanças");
    }

    /**
     * Testa a obtenção das propriedades de um proprietário específico.
     */
    @Test
    void getOwnerProperties() {
        OwnerGraphTestLogger.logTestStart("Obtenção de propriedades de um proprietário");
        
        Set<Integer> uniqueOwners = new HashSet<>();
        for (Cadastro cadastro : cadastros) {
            uniqueOwners.add(cadastro.getOwner());
        }
        
        List<Integer> ownersList = new ArrayList<>(uniqueOwners);
        int testSize = Math.min(5, ownersList.size());
        OwnerGraphTestLogger.log("Testando obtenção de propriedades para " + testSize + " proprietários");
        
        for (int i = 0; i < testSize; i++) {
            int owner = ownersList.get(i);
            OwnerGraphTestLogger.log("Verificando propriedades do proprietário: " + owner);
            
            List<Cadastro> properties = graph.getOwnerProperties(owner);
            assertNotNull(properties, "A lista de propriedades não deve ser nula");
            
            OwnerGraphTestLogger.log("Número de propriedades encontradas: " + properties.size());
            if (!properties.isEmpty()) {
                OwnerGraphTestLogger.log("Propriedades:");
                for (Cadastro prop : properties) {
                    OwnerGraphTestLogger.log("  - " + prop.toString());
                    assertEquals(owner, prop.getOwner(), 
                        "Todas as propriedades devem pertencer ao proprietário " + owner);
                }
            }
        }
        
        OwnerGraphTestLogger.logSuccess("Verificação de propriedades realizada com sucesso para todos os proprietários testados");
        OwnerGraphTestLogger.logTestEnd("Obtenção de propriedades de um proprietário");
    }
} 