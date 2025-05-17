package cadastro.importer;

<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
=======
>>>>>>> tests:src/test/java/model/CadastroTest.java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

=======
import static org.mockito.Mockito.*;
>>>>>>> tests:src/test/java/model/CadastroTest.java
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Test class for Cadastro
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 4 (3 if conditions + 1 return)
 * - handleId: 2 (1 if condition + 1 return)
 * - handleLength: 2 (1 if condition + 1 return)
 * - handleArea: 2 (1 if condition + 1 return)
 * - handleShape: 2 (1 if condition + 1 return)
 * - handleOwner: 2 (1 if condition + 1 return)
 * - handleLocation: 2 (1 if condition + 1 return)
 * - getPrice: 4 (3 if conditions + 1 return)
 * - setPropretiesNear: 2 (1 if condition + 1 return)
 * - toString: 1 (1 return)
 */
class CadastroTest {
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
    private static final String CSV_PATH = new File("Dados/Madeira-Moodle-1.1.csv").getAbsolutePath();

    private CSVRecord validRecord;

    @BeforeEach
    void setUp() throws Exception {
        CadastroTestLogger.log("Iniciando setup do teste");
        CadastroTestLogger.log("Caminho do arquivo CSV: " + CSV_PATH);

        File csvFile = new File(CSV_PATH);
        if (!csvFile.exists()) {
            CadastroTestLogger.logError("Arquivo CSV não encontrado em: " + CSV_PATH);
            throw new IllegalStateException("Arquivo CSV não encontrado");
        }

        // Lê o arquivo CSV
        try (FileReader reader = new FileReader(csvFile);
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {

            CadastroTestLogger.logSuccess("Arquivo CSV aberto com sucesso");
            List<CSVRecord> records = parser.getRecords();
            CadastroTestLogger.log("Total de registros lidos: " + records.size());

            if (records.size() >= 3) { // Pelo menos 2 registros + cabeçalho
                CadastroTestLogger.log("Criando registros de teste");
                validRecord = records.get(1);
                CadastroTestLogger.logSuccess("Registros criados com sucesso");
            } else {
                CadastroTestLogger.logError("Arquivo CSV não contém registros suficientes");
                throw new IllegalStateException("Arquivo CSV não contém registros suficientes");
            }
        } catch (Exception e) {
            CadastroTestLogger.logError("Erro ao ler arquivo CSV: " + e.getMessage());
            throw e;
        }
    }

    @AfterAll
    static void tearDown() {
        CadastroTestLogger.close();
    }

    @Test
    void constructor() throws Exception {
        CadastroTestLogger.logTestStart("constructor");
        Cadastro cadastro = new Cadastro(validRecord);

        assertNotNull(cadastro, "O cadastro deve ser criado");
        assertNotNull(cadastro.getId(), "O ID deve ser definido");
        assertNotNull(cadastro.getOwner(), "O proprietário deve ser definido");
        assertNotNull(cadastro.getArea(), "A área deve ser definida");
        assertNotNull(cadastro.getLength(), "O comprimento deve ser definido");
        assertNotNull(cadastro.getShape(), "A forma deve ser definida");
        CadastroTestLogger.logSuccess("Teste constructor concluído com sucesso");
        CadastroTestLogger.logTestEnd("constructor");
    }

    @Test
    void constructorInvalid1() {
        CadastroTestLogger.logTestStart("constructorInvalid1");
        // Cria um registro CSV inválido manualmente
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
        try (StringReader reader = new StringReader(String.join(";", invalidValues));
                CSVParser parser = CSVFormat.newFormat(';').parse(reader)) {
            CSVRecord invalidRecord = parser.getRecords().get(0);

            assertThrows(IllegalArgumentException.class, () -> {
                new Cadastro(invalidRecord);
            }, "Deve lançar exceção ao criar cadastro com registro inválido");
        } catch (Exception e) {
            fail("Erro ao criar registro CSV inválido: " + e.getMessage());
        }
        CadastroTestLogger.logSuccess("Teste constructorInvalid1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("constructorInvalid1");
    }

    @Test
    void constructorInvalid2() {
        CadastroTestLogger.logTestStart("constructorInvalid2");
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
        CadastroTestLogger.logSuccess("Teste constructorInvalid2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("constructorInvalid2");
=======
    private CSVRecord mockRecord;
    private List<Cadastro> testCadastros;
    private Cadastro cadastro;

    @BeforeEach
    void setUp() throws ParseException {
        mockRecord = mock(CSVRecord.class);
        when(mockRecord.get(anyInt())).thenReturn("1"); // Default valid values
        when(mockRecord.get(0)).thenReturn("1"); // ID
        when(mockRecord.get(1)).thenReturn("10.5"); // Length
        when(mockRecord.get(2)).thenReturn("100.0"); // Area
        when(mockRecord.get(3)).thenReturn("MULTIPOLYGON (((0 0, 0 1, 1 1, 1 0, 0 0)))"); // Shape
        when(mockRecord.get(4)).thenReturn("1"); // Owner
        when(mockRecord.get(5)).thenReturn("Freguesia1"); // Freguesia
        when(mockRecord.get(6)).thenReturn("Municipio1"); // Municipio
        when(mockRecord.get(7)).thenReturn("Concelho1"); // Concelho

        testCadastros = new ArrayList<>();
        testCadastros.add(new Cadastro(mockRecord));
    }

    /**
     * Test constructor - Cyclomatic Complexity: 4
     */
    @Test
    void constructor1() throws ParseException {
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro, "Cadastro should be created with valid record");
    }

    @Test
    void constructor2() {
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(null),
                "Should throw IllegalArgumentException for null record");
    }

    @Test
    void constructor3() {
        when(mockRecord.get(anyInt())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for record with null values");
    }

    @Test
    void constructor4() {
        when(mockRecord.get(anyInt())).thenReturn("");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for record with empty values");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    /**
     * Test handleId - Cyclomatic Complexity: 2
     */
    @Test
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
    void constructorInvalid3() {
        CadastroTestLogger.logTestStart("constructorInvalid3");
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
        CadastroTestLogger.logSuccess("Teste constructorInvalid3 concluído com sucesso");
        CadastroTestLogger.logTestEnd("constructorInvalid3");
    }

    @Test
    void constructorInvalid4() {
        CadastroTestLogger.logTestStart("constructorInvalid4");
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
        CadastroTestLogger.logSuccess("Teste constructorInvalid4 concluído com sucesso");
        CadastroTestLogger.logTestEnd("constructorInvalid4");
    }

    @Test
    void handleId1() {
        CadastroTestLogger.logTestStart("handleId1");
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
        CadastroTestLogger.logSuccess("Teste handleId1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleId1");
=======
    void handleId1() throws ParseException {
        when(mockRecord.get(0)).thenReturn("123");
        cadastro = new Cadastro(mockRecord);
        assertEquals("123", cadastro.getId(), "Should handle valid ID");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    @Test
    void handleId2() {
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
        CadastroTestLogger.logTestStart("handleId2");
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
        CadastroTestLogger.logSuccess("Teste handleId2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleId2");
=======
        when(mockRecord.get(0)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for null ID");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    /**
     * Test handleLength - Cyclomatic Complexity: 2
     */
    @Test
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
    void handleLength1() {
        CadastroTestLogger.logTestStart("handleLength1");
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
        CadastroTestLogger.logSuccess("Teste handleLength1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleLength1");
=======
    void handleLength1() throws ParseException {
        when(mockRecord.get(1)).thenReturn("100");
        cadastro = new Cadastro(mockRecord);
        assertEquals(100.0, cadastro.getLength(), "Should handle valid length");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    @Test
    void handleLength2() {
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
        CadastroTestLogger.logTestStart("handleLength2");
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
        CadastroTestLogger.logSuccess("Teste handleLength2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleLength2");
=======
        when(mockRecord.get(1)).thenReturn("invalid");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for invalid length");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    /**
     * Test handleArea - Cyclomatic Complexity: 2
     */
    @Test
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
    void handleArea1() {
        CadastroTestLogger.logTestStart("handleArea1");
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
        CadastroTestLogger.logSuccess("Teste handleArea1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleArea1");
=======
    void handleArea1() throws ParseException {
        when(mockRecord.get(2)).thenReturn("1000");
        cadastro = new Cadastro(mockRecord);
        assertEquals(1000.0, cadastro.getArea(), "Should handle valid area");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    @Test
    void handleArea2() {
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
        CadastroTestLogger.logTestStart("handleArea2");
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
        CadastroTestLogger.logSuccess("Teste handleArea2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleArea2");
=======
        when(mockRecord.get(2)).thenReturn("invalid");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for invalid area");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    /**
     * Test handleShape - Cyclomatic Complexity: 2
     */
    @Test
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
    void handleOwner1() {
        CadastroTestLogger.logTestStart("handleOwner1");
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
        CadastroTestLogger.logSuccess("Teste handleOwner1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleOwner1");
    }

    @Test
    void handleOwner2() {
        CadastroTestLogger.logTestStart("handleOwner2");
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
        CadastroTestLogger.logSuccess("Teste handleOwner2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleOwner2");
    }

    @Test
    void handleShape1() throws Exception {
        CadastroTestLogger.logTestStart("handleShape1");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getShape(), "A forma deve ser processada");
        CadastroTestLogger.logSuccess("Teste handleShape1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleShape1");
=======
    void handleShape1() throws ParseException {
        when(mockRecord.get(3)).thenReturn("POLYGON((0 0, 1 0, 1 1, 0 1, 0 0))");
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro.getShape(), "Should handle valid shape");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    @Test
    void handleShape2() {
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
        CadastroTestLogger.logTestStart("handleShape2");
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
        CadastroTestLogger.logSuccess("Teste handleShape2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleShape2");
    }

    @Test
    void handleShape3() {
        CadastroTestLogger.logTestStart("handleShape3");
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
        CadastroTestLogger.logSuccess("Teste handleShape3 concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleShape3");
    }

    @Test
    void getShape() throws Exception {
        CadastroTestLogger.logTestStart("getShape");
        Cadastro cadastro = new Cadastro(validRecord);
        MultiPolygon shape = cadastro.getShape();
        assertNotNull(shape, "A forma deve ser processada");
        CadastroTestLogger.logSuccess("Teste getShape concluído com sucesso");
        CadastroTestLogger.logTestEnd("getShape");
    }

    @Test
    void toStringTest() throws Exception {
        CadastroTestLogger.logTestStart("toString");
        Cadastro cadastro = new Cadastro(validRecord);
        String result = cadastro.toString();
        assertNotNull(result, "A representação em string não deve ser nula");
        assertTrue(result.contains("Cadastro"), "A string deve conter 'Cadastro'");
        CadastroTestLogger.logSuccess("Teste toString concluído com sucesso");
        CadastroTestLogger.logTestEnd("toString");
    }

    @Test
    void handleLocation() throws Exception {
        CadastroTestLogger.logTestStart("handleLocation");
        Cadastro cadastro = new Cadastro(validRecord);
        List<String> locations = cadastro.getLocation();
        assertNotNull(locations, "As localizações devem ser processadas");
        assertFalse(locations.isEmpty(), "A lista de localizações não deve estar vazia");
        CadastroTestLogger.logSuccess("Teste handleLocation concluído com sucesso");
        CadastroTestLogger.logTestEnd("handleLocation");
    }

    @Test
    void getCadastros1() throws Exception {
        CadastroTestLogger.logTestStart("getCadastros1");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        assertNotNull(cadastros, "A lista de cadastros deve ser criada");
        assertFalse(cadastros.isEmpty(), "A lista não deve estar vazia");
        CadastroTestLogger.logSuccess("Teste getCadastros1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("getCadastros1");
    }

    @Test
    void getCadastros2() {
        CadastroTestLogger.logTestStart("getCadastros2");
        assertThrows(Exception.class, () -> {
            Cadastro.getCadastros("arquivo_inexistente.csv");
        }, "Deve lançar exceção ao ler arquivo inválido");
        CadastroTestLogger.logSuccess("Teste getCadastros2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("getCadastros2");
=======
        when(mockRecord.get(3)).thenReturn("invalid");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for invalid shape");
    }

    /**
     * Test handleOwner - Cyclomatic Complexity: 2
     */
    @Test
    void handleOwner1() throws ParseException {
        when(mockRecord.get(4)).thenReturn("John Doe");
        cadastro = new Cadastro(mockRecord);
        assertEquals("John Doe", cadastro.getOwner(), "Should handle valid owner");
    }

    @Test
    void handleOwner2() {
        when(mockRecord.get(4)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for null owner");
    }

    /**
     * Test handleLocation - Cyclomatic Complexity: 2
     */
    @Test
    void handleLocation1() throws ParseException {
        when(mockRecord.get(5)).thenReturn("Freguesia");
        when(mockRecord.get(6)).thenReturn("Concelho");
        when(mockRecord.get(7)).thenReturn("Distrito");
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro.getLocation(), "Should handle valid location");
    }

    @Test
    void handleLocation2() {
        when(mockRecord.get(5)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for null location");
    }

    /**
     * Test getPrice - Cyclomatic Complexity: 4
     */
    @Test
    void getPrice1() throws ParseException {
        cadastro = new Cadastro(mockRecord);
        assertEquals(0.0, cadastro.getPrice(), "Should return default price when no specific prices are set");
    }

    @Test
    void getPrice2() throws ParseException {
        when(mockRecord.get(5)).thenReturn("Freguesia");
        when(mockRecord.get(6)).thenReturn("Concelho");
        when(mockRecord.get(7)).thenReturn("Distrito");
        cadastro = new Cadastro(mockRecord);
        cadastro.getLocation().setPriceFreguesia(100.0);
        assertEquals(100.0, cadastro.getPrice(), "Should return freguesia price when set");
    }

    @Test
    void getPrice3() throws ParseException {
        when(mockRecord.get(5)).thenReturn("Freguesia");
        when(mockRecord.get(6)).thenReturn("Concelho");
        when(mockRecord.get(7)).thenReturn("Distrito");
        cadastro = new Cadastro(mockRecord);
        cadastro.getLocation().setPriceConcelho(200.0);
        assertEquals(200.0, cadastro.getPrice(), "Should return concelho price when set");
    }

    @Test
    void getPrice4() throws ParseException {
        when(mockRecord.get(5)).thenReturn("Freguesia");
        when(mockRecord.get(6)).thenReturn("Concelho");
        when(mockRecord.get(7)).thenReturn("Distrito");
        cadastro = new Cadastro(mockRecord);
        cadastro.getLocation().setPriceDistrito(300.0);
        assertEquals(300.0, cadastro.getPrice(), "Should return distrito price when set");
    }

    /**
     * Test setPropretiesNear - Cyclomatic Complexity: 2
     */
    @Test
    void setPropretiesNear1() throws ParseException {
        cadastro = new Cadastro(mockRecord);
        List<Cadastro> nearby = new ArrayList<>();
        nearby.add(new Cadastro(mockRecord));
        cadastro.setPropretiesNear(nearby);
        assertEquals(1, cadastro.getPropretiesNear().size(), "Should set nearby properties");
    }

    @Test
    void setPropretiesNear2() throws ParseException {
        cadastro = new Cadastro(mockRecord);
        assertThrows(IllegalArgumentException.class, () -> cadastro.setPropretiesNear(null),
                "Should throw IllegalArgumentException for null nearby properties");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }

    /**
     * Test toString - Cyclomatic Complexity: 1
     */
    @Test
<<<<<<< HEAD:src/test/java/cadastro/importer/CadastroTest.java
    void getCadastros3() throws Exception {
        CadastroTestLogger.logTestStart("getCadastros3");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        assertNotNull(cadastros, "A lista de cadastros deve ser criada");
        CadastroTestLogger.logSuccess("Teste getCadastros3 concluído com sucesso");
        CadastroTestLogger.logTestEnd("getCadastros3");
    }

    @Test
    void sortCadastros1() throws Exception {
        CadastroTestLogger.logTestStart("sortCadastros1");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_ID);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        CadastroTestLogger.logSuccess("Teste sortCadastros1 concluído com sucesso");
        CadastroTestLogger.logTestEnd("sortCadastros1");
    }

    @Test
    void sortCadastros2() throws Exception {
        CadastroTestLogger.logTestStart("sortCadastros2");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_LENGTH);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        CadastroTestLogger.logSuccess("Teste sortCadastros2 concluído com sucesso");
        CadastroTestLogger.logTestEnd("sortCadastros2");
    }

    @Test
    void sortCadastros3() throws Exception {
        CadastroTestLogger.logTestStart("sortCadastros3");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_AREA);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        CadastroTestLogger.logSuccess("Teste sortCadastros3 concluído com sucesso");
        CadastroTestLogger.logTestEnd("sortCadastros3");
    }

    @Test
    void sortCadastros4() throws Exception {
        CadastroTestLogger.logTestStart("sortCadastros4");
        List<Cadastro> cadastros = Cadastro.getCadastros(CSV_PATH);
        List<Cadastro> sorted = Cadastro.sortCadastros(cadastros, CadastroConstants.SORT_BY_OWNER);
        assertNotNull(sorted, "A lista ordenada não deve ser nula");
        CadastroTestLogger.logSuccess("Teste sortCadastros4 concluído com sucesso");
        CadastroTestLogger.logTestEnd("sortCadastros4");
    }

    @Test
    void getId() throws Exception {
        CadastroTestLogger.logTestStart("getId");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getId(), "O ID deve ser definido");
        CadastroTestLogger.logSuccess("Teste getId concluído com sucesso");
        CadastroTestLogger.logTestEnd("getId");
    }

    @Test
    void getLength() throws Exception {
        CadastroTestLogger.logTestStart("getLength");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getLength(), "O comprimento deve ser definido");
        CadastroTestLogger.logSuccess("Teste getLength concluído com sucesso");
        CadastroTestLogger.logTestEnd("getLength");
    }

    @Test
    void getArea() throws Exception {
        CadastroTestLogger.logTestStart("getArea");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getArea(), "A área deve ser definida");
        CadastroTestLogger.logSuccess("Teste getArea concluído com sucesso");
        CadastroTestLogger.logTestEnd("getArea");
    }

    @Test
    void getOwner() throws Exception {
        CadastroTestLogger.logTestStart("getOwner");
        Cadastro cadastro = new Cadastro(validRecord);
        assertNotNull(cadastro.getOwner(), "O proprietário deve ser definido");
        CadastroTestLogger.logSuccess("Teste getOwner concluído com sucesso");
        CadastroTestLogger.logTestEnd("getOwner");
    }

    @Test
    void getLocation() throws Exception {
        CadastroTestLogger.logTestStart("getLocation");
        Cadastro cadastro = new Cadastro(validRecord);
        List<String> locations = cadastro.getLocation();
        assertNotNull(locations, "As localizações devem ser processadas");
        assertFalse(locations.isEmpty(), "A lista de localizações não deve estar vazia");
        CadastroTestLogger.logSuccess("Teste getLocation concluído com sucesso");
        CadastroTestLogger.logTestEnd("getLocation");
=======
    void testToString() {
        cadastro = new Cadastro(mockRecord);
        String str = cadastro.toString();
        assertTrue(str.contains("Cadastro{id="), "String representation should contain id");
        assertTrue(str.contains("length="), "String representation should contain length");
        assertTrue(str.contains("area="), "String representation should contain area");
        assertTrue(str.contains("shape="), "String representation should contain shape");
        assertTrue(str.contains("owner="), "String representation should contain owner");
        assertTrue(str.contains("location="), "String representation should contain location");
>>>>>>> tests:src/test/java/model/CadastroTest.java
    }
}