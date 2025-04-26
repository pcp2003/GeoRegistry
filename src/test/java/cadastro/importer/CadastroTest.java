package cadastro.importer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.MultiPolygon;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import cadastro.TestLogger;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para Cadastro
 * 
 * Complexidade Ciclomática dos métodos:
 * - Cadastro(): 5
 * - handleId(): 2
 * - handleLength(): 2
 * - handleArea(): 2
 * - handleShape(): 3
 * - handleOwner(): 2
 * - handleLocation(): 1
 * - getCadastros(): 3
 * - sortCadastros(): 4
 * - toString(): 1
 * - getId(): 1
 * - getLength(): 1
 * - getArea(): 1
 * - getShape(): 1
 * - getOwner(): 1
 * - getLocation(): 1
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
class CadastroTest {
    private static final String CSV_PATH = new File("Dados/Madeira-Moodle-1.1.csv").getAbsolutePath();
    private static CSVRecord validRecord;

    @BeforeAll
    static void setUp() throws Exception {
        TestLogger.init("CadastroTest");
        TestLogger.log("=== Iniciando setup dos testes ===");
        TestLogger.log("Carregando arquivo: " + CSV_PATH);
        
        File csvFile = new File(CSV_PATH);
        if (!csvFile.exists()) {
            throw new IllegalStateException("Arquivo CSV não encontrado em: " + CSV_PATH);
        }
        
        try (FileReader reader = new FileReader(csvFile);
             CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            
            List<CSVRecord> records = parser.getRecords();
            TestLogger.log("Registros encontrados: " + records.size());
            
            if (records.size() > 1) {
                validRecord = records.get(1);   
                TestLogger.logSuccess("Cadastros de teste criados com sucesso");
            } else {
                throw new IllegalStateException("Arquivo CSV não contém registros suficientes");
            }
        }
        
        TestLogger.log("=== Setup concluído ===\n");
    }

    @AfterAll
    static void tearDown() {
        TestLogger.close();
    }

    @Test
    void constructor() throws Exception {
        TestLogger.logTestStart("Construtor do Cadastro");
        
        TestLogger.log("Criando cadastro com registro válido");
        Cadastro cadastro = new Cadastro(validRecord);
        
        TestLogger.log("Verificando atributos do cadastro");
        assertNotNull(cadastro, "O cadastro deve ser criado");
        assertNotNull(cadastro.getId(), "O ID deve ser definido");
        assertNotNull(cadastro.getOwner(), "O proprietário deve ser definido");
        assertNotNull(cadastro.getArea(), "A área deve ser definida");
        assertNotNull(cadastro.getLength(), "O comprimento deve ser definido");
        assertNotNull(cadastro.getShape(), "A forma deve ser definida");
        
        TestLogger.logSuccess("Cadastro criado com sucesso");
        TestLogger.logTestEnd("Construtor do Cadastro");
    }

    @Test
    void constructorInvalid1() {
        TestLogger.logTestStart("Construtor com ID inválido");
        
        String[] invalidValues = {
                "a", // id invalido
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        
        TestLogger.log("Testando criação com ID inválido: " + invalidValues[0]);
        
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
             CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            
            CSVRecord invalidRecord = parser.getRecords().get(0);
            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com ID inválido");
            
            TestLogger.logSuccess("Exceção lançada corretamente para ID inválido");
        } catch (Exception e) {
            TestLogger.logError("Erro ao criar registro CSV inválido: " + e.getMessage());
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        
        TestLogger.logTestEnd("Construtor com ID inválido");
    }

    @Test
    void constructorInvalid2() {
        TestLogger.logTestStart("constructorInvalid2");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "a", // comprimento invalido
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste constructorInvalid2 concluído com sucesso");
        TestLogger.logTestEnd("constructorInvalid2");
    }

    @Test
    void constructorInvalid3() {
        TestLogger.logTestStart("constructorInvalid3");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "a", // área invalida
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste constructorInvalid3 concluído com sucesso");
        TestLogger.logTestEnd("constructorInvalid3");
    }

    @Test
    void constructorInvalid4() {
        TestLogger.logTestStart("constructorInvalid4");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "a", // owner invalido
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste constructorInvalid4 concluído com sucesso");
        TestLogger.logTestEnd("constructorInvalid4");
    }

    @Test
    void handleId1() {
        TestLogger.logTestStart("handleId1");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "0", // id a zero
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleId1 concluído com sucesso");
        TestLogger.logTestEnd("handleId1");
    }

    @Test
    void handleId2() {
        TestLogger.logTestStart("handleId2");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                " ", // id vazio
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleId2 concluído com sucesso");
        TestLogger.logTestEnd("handleId2");
    }

    @Test
    void handleLength1() {
        TestLogger.logTestStart("handleLength1");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "0", // comprimento a zero
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleLength1 concluído com sucesso");
        TestLogger.logTestEnd("handleLength1");
    }

    @Test
    void handleLength2() {
        TestLogger.logTestStart("handleLength2");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                " ", // comprimento vazio
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleLength2 concluído com sucesso");
        TestLogger.logTestEnd("handleLength2");
    }

    @Test
    void handleArea1() {
        TestLogger.logTestStart("handleArea1");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "0", // área a zero
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleArea1 concluído com sucesso");
        TestLogger.logTestEnd("handleArea1");
    }

    @Test
    void handleArea2() {
        TestLogger.logTestStart("handleArea2");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                " ", // área vazia
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleArea2 concluído com sucesso");
        TestLogger.logTestEnd("handleArea2");
    }

    @Test
    void handleOwner1() {
        TestLogger.logTestStart("handleOwner1");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                "0", // owner a zero
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleOwner1 concluído com sucesso");
        TestLogger.logTestEnd("handleOwner1");
    }

    @Test
    void handleOwner2() {
        TestLogger.logTestStart("handleOwner2");
        // Cria um registro CSV inválido manualmente
        String[] invalidValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "MULTIPOLYGON(((0 0, 0 1, 1 1, 1 0, 0 0)))", // shape
                " ", // owner invalido
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleOwner2 concluído com sucesso");
        TestLogger.logTestEnd("handleOwner2");
    }

    @Test
    void handleShape1() throws Exception {
        TestLogger.logTestStart("handleShape1");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getShape(), "A forma deve ser processada");
        TestLogger.logSuccess("Teste handleShape1 concluído com sucesso");
        TestLogger.logTestEnd("handleShape1");
    }

    @Test
    void handleShape2() {
        TestLogger.logTestStart("handleShape2");
        // Cria um registro CSV com forma inválida
        String[] invalidShapeValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "POLYGON((0 0, 0 1, 1 1, 1 0, 0 0))", // shape inválido
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidShapeValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidShapeRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidShapeRecord);
            }, "Deve lançar exceção ao processar forma inválida");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV com forma inválida: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleShape2 concluído com sucesso");
        TestLogger.logTestEnd("handleShape2");
    }

    @Test
    void handleShape3() {
        TestLogger.logTestStart("handleShape3");
        // Cria um registro CSV com forma inválida
        String[] invalidShapeValues = {
                "1", // id
                "7343148", // random par_id
                "2", // random par_number
                "10.0", // comprimento
                "100.0", // área
                "INVALID_SHAPE", // shape inválido (não é um MULTIPOLYGON válido)
                "1", // owner
                "Lisboa", // location1
                "Portugal" // location2
        };
        try (StringReader reader = new StringReader(String.join(";", invalidShapeValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidShapeRecord = parser.getRecords().get(0);

            assertThrows(org.locationtech.jts.io.ParseException.class, () -> {
                new Cadastro(invalidShapeRecord);
            }, "Deve lançar exceção ao processar forma inválida");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV com forma inválida: " + e.getMessage());
        }
        TestLogger.logSuccess("Teste handleShape3 concluído com sucesso");
        TestLogger.logTestEnd("handleShape3");
    }

    @Test
    void getShape() throws Exception {
        TestLogger.logTestStart("getShape");
        Cadastro cadastro = new Cadastro(validRecord);
        MultiPolygon shape = cadastro.getShape();
        assertNotNull(shape, "A forma deve ser processada");
        TestLogger.logSuccess("Teste getShape concluído com sucesso");
        TestLogger.logTestEnd("getShape");
    }

    @Test
    void toStringTest() throws Exception {
        TestLogger.logTestStart("toString");
        Cadastro cadastro = new Cadastro(validRecord);
        String result = cadastro.toString();
        assertNotNull(result, "A representação em string não deve ser nula");
        assertTrue(result.contains("Cadastro"), "A string deve conter 'Cadastro'");
        TestLogger.logSuccess("Teste toString concluído com sucesso");
        TestLogger.logTestEnd("toString");
    }

    @Test
    void handleLocation() throws Exception {
        TestLogger.logTestStart("handleLocation");
        Cadastro cadastro = new Cadastro(validRecord);
        List<String> locations = cadastro.getLocation();
        assertNotNull(locations, "As localizações devem ser processadas");
        assertFalse(locations.isEmpty(), "A lista de localizações não deve estar vazia");
        TestLogger.logSuccess("Teste handleLocation concluído com sucesso");
        TestLogger.logTestEnd("handleLocation");
    }

    @Test
    void getCadastros1() throws Exception {
        TestLogger.logTestStart("getCadastros1");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        assertNotNull(cadastros, "A lista de cadastros deve ser criada");
        assertFalse(cadastros.isEmpty(), "A lista não deve estar vazia");
        TestLogger.logSuccess("Teste getCadastros1 concluído com sucesso");
        TestLogger.logTestEnd("getCadastros1");
    }

    @Test
    void getCadastros2() {
        TestLogger.logTestStart("getCadastros2");
        assertThrows(Exception.class, () -> {
            Cadastro.getCadastros("arquivo_inexistente.csv");
        }, "Deve lançar exceção ao ler arquivo inválido");
        TestLogger.logSuccess("Teste getCadastros2 concluído com sucesso");
        TestLogger.logTestEnd("getCadastros2");
    }

    @Test
    void getCadastros3() throws Exception {
        TestLogger.logTestStart("getCadastros3");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        assertNotNull(cadastros, "A lista de cadastros deve ser criada");
        TestLogger.logSuccess("Teste getCadastros3 concluído com sucesso");
        TestLogger.logTestEnd("getCadastros3");
    }

    @Test
    void sortCadastros1() throws Exception {
        TestLogger.logTestStart("sortCadastros1");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_ID);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        TestLogger.logSuccess("Teste sortCadastros1 concluído com sucesso");
        TestLogger.logTestEnd("sortCadastros1");
    }

    @Test
    void sortCadastros2() throws Exception {
        TestLogger.logTestStart("sortCadastros2");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_LENGTH);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        TestLogger.logSuccess("Teste sortCadastros2 concluído com sucesso");
        TestLogger.logTestEnd("sortCadastros2");
    }

    @Test
    void sortCadastros3() throws Exception {
        TestLogger.logTestStart("sortCadastros3");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_AREA);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        TestLogger.logSuccess("Teste sortCadastros3 concluído com sucesso");
        TestLogger.logTestEnd("sortCadastros3");
    }

    @Test
    void sortCadastros4() throws Exception {
        TestLogger.logTestStart("sortCadastros4");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_OWNER);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        TestLogger.logSuccess("Teste sortCadastros4 concluído com sucesso");
        TestLogger.logTestEnd("sortCadastros4");
    }

    @Test
    void getId() throws Exception {
        TestLogger.logTestStart("getId");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getId(), "O ID deve ser definido");
        TestLogger.logSuccess("Teste getId concluído com sucesso");
        TestLogger.logTestEnd("getId");
    }

    @Test
    void getLength() throws Exception {
        TestLogger.logTestStart("getLength");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getLength(), "O comprimento deve ser definido");
        TestLogger.logSuccess("Teste getLength concluído com sucesso");
        TestLogger.logTestEnd("getLength");
    }

    @Test
    void getArea() throws Exception {
        TestLogger.logTestStart("getArea");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getArea(), "A área deve ser definida");
        TestLogger.logSuccess("Teste getArea concluído com sucesso");
        TestLogger.logTestEnd("getArea");
    }

    @Test
    void getOwner() throws Exception {
        TestLogger.logTestStart("getOwner");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getOwner(), "O proprietário deve ser definido");
        TestLogger.logSuccess("Teste getOwner concluído com sucesso");
        TestLogger.logTestEnd("getOwner");
    }

    @Test
    void getLocation() throws Exception {
        TestLogger.logTestStart("getLocation");
        Cadastro cadastro = new Cadastro(validRecord);
        List<String> locations = cadastro.getLocation();
        assertNotNull(locations, "As localizações devem ser processadas");
        assertFalse(locations.isEmpty(), "A lista de localizações não deve estar vazia");
        TestLogger.logSuccess("Teste getLocation concluído com sucesso");
        TestLogger.logTestEnd("getLocation");
    }

    @Test
    void testHandleLocation() throws Exception {
        TestLogger.logTestStart("testHandleLocation");
        
        // Obter cadastros do arquivo CSV
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        TestLogger.log("Total de cadastros carregados: " + cadastros.size());
        
        // Verificar se temos cadastros
        assertNotNull(cadastros, "A lista de cadastros não deve ser nula");
        assertFalse(cadastros.isEmpty(), "A lista de cadastros não deve estar vazia");
        
        // Testar o primeiro cadastro
        Cadastro cadastro = cadastros.get(0);
        List<String> locations = cadastro.getLocation();
        TestLogger.log("Localizações do primeiro cadastro: " + locations);
        
        // Verificar se as localizações foram extraídas corretamente
        assertNotNull(locations, "A lista de localizações não deve ser nula");
        assertFalse(locations.isEmpty(), "A lista de localizações não deve estar vazia");
        
        // Verificar se não há valores "NA" na lista
        assertFalse(locations.contains(CadastroConstants.NA_VALUE), 
            "A lista de localizações não deve conter valores 'NA'");
        
        // Verificar se a ordem das localizações está correta
        // Assumindo que o CSV tem a ordem: Freguesia, Municipio, Concelho
        if (locations.size() >= 3) {
            TestLogger.log("Freguesia: " + locations.get(0));
            TestLogger.log("Municipio: " + locations.get(1));
            TestLogger.log("Concelho: " + locations.get(2));
            
            assertNotNull(locations.get(0), "Freguesia não deve ser nula");
            assertNotNull(locations.get(1), "Municipio não deve ser nulo");
            assertNotNull(locations.get(2), "Concelho não deve ser nulo");
        } else {
            TestLogger.logError("Lista de localizações tem menos de 3 elementos: " + locations.size());
            fail("A lista de localizações deve ter pelo menos 3 elementos (Freguesia, Municipio, Concelho)");
        }
        
        // Verificar se os valores não estão vazios
        for (int i = 0; i < locations.size(); i++) {
            String location = locations.get(i);
            assertFalse(location.trim().isEmpty(), 
                "Localização " + i + " não deve estar vazia: " + location);
        }
        
        TestLogger.logSuccess("Teste testHandleLocation concluído com sucesso");
        TestLogger.logTestEnd("testHandleLocation");
    }
}