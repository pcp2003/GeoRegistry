package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import core.Constants;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Classe de teste para o modelo Cadastro.
 * Contém testes unitários para todas as funcionalidades do Cadastro, incluindo construção,
 * acesso às propriedades e operações geométricas.
 * 
 * @author Lei-G
 * @version 1.0
 * 
 * Complexidade Ciclomática por método:
 * - construtor: 4 (3 condições if + 1 retorno)
 * - handleId: 2 (1 condição if + 1 retorno)
 * - handleLength: 2 (1 condição if + 1 retorno)
 * - handleArea: 2 (1 condição if + 1 retorno)
 * - handleShape: 2 (1 condição if + 1 retorno)
 * - handleOwner: 2 (1 condição if + 1 retorno)
 * - handleLocation: 2 (1 condição if + 1 retorno)
 * - getPrice: 4 (3 condições if + 1 retorno)
 * - toString: 1 (1 retorno)
 */
public class CadastroTest {
    @Mock
    private CSVRecord mockRecord;
    private List<Cadastro> testCadastros;
    private Cadastro cadastro;

    /**
     * Construtor padrão para CadastroTest.
     * Inicializa os fixtures de teste e objetos mock.
     */
    public CadastroTest() {
        // Construtor padrão
    }

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        
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

        testCadastros = new ArrayList<>();
        testCadastros.add(new Cadastro(mockRecord));
    }

    /**
     * Testa o construtor - Complexidade Ciclomática: 4
     */
    @Test
    void constructor1() throws ParseException {
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro, "Cadastro deve ser criado com registo válido");
    }

    @Test
    void constructor2() {
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(null),
                "Deve lançar IllegalArgumentException para registo nulo");
    }

    @Test
    void constructor3() {
        when(mockRecord.get(anyInt())).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para registo com valores nulos");
    }

    @Test
    void constructor4() {
        when(mockRecord.get(anyInt())).thenReturn("");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para registo com valores vazios");
    }

    /**
     * Testa handleId - Complexidade Ciclomática: 2
     */
    @Test
    void handleId1() throws ParseException {
        when(mockRecord.get(Constants.ID_INDEX)).thenReturn("123");
        cadastro = new Cadastro(mockRecord);
        assertEquals(123, cadastro.getId(), "Deve processar ID válido");
    }

    @Test
    void handleId2() {
        when(mockRecord.get(Constants.ID_INDEX)).thenReturn("0");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para ID inválido");
    }

    /**
     * Testa handleLength - Complexidade Ciclomática: 2
     */
    @Test
    void handleLength1() throws ParseException {
        when(mockRecord.get(Constants.LENGTH_INDEX)).thenReturn("100");
        cadastro = new Cadastro(mockRecord);
        assertEquals(100.0, cadastro.getLength(), "Deve processar comprimento válido");
    }

    @Test
    void handleLength2() {
        when(mockRecord.get(Constants.LENGTH_INDEX)).thenReturn("0");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para comprimento inválido");
    }

    /**
     * Testa handleArea - Complexidade Ciclomática: 2
     */
    @Test
    void handleArea1() throws ParseException {
        when(mockRecord.get(Constants.AREA_INDEX)).thenReturn("1000");
        cadastro = new Cadastro(mockRecord);
        assertEquals(1000.0, cadastro.getArea(), "Deve processar área válida");
    }

    @Test
    void handleArea2() {
        when(mockRecord.get(Constants.AREA_INDEX)).thenReturn("0");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para área inválida");
    }

    /**
     * Testa handleShape - Complexidade Ciclomática: 2
     */
    @Test
    void handleShape1() throws ParseException {
        when(mockRecord.get(Constants.SHAPE_INDEX)).thenReturn("MULTIPOLYGON (((0 0, 1 0, 1 1, 0 1, 0 0)))");
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro.getShape(), "Deve processar forma válida");
    }

    @Test
    void handleShape2() {
        when(mockRecord.get(Constants.SHAPE_INDEX)).thenReturn("invalid");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para forma inválida");
    }

    /**
     * Testa handleOwner - Complexidade Ciclomática: 2
     */
    @Test
    void handleOwner1() throws ParseException {
        when(mockRecord.get(Constants.OWNER_INDEX)).thenReturn("123");
        cadastro = new Cadastro(mockRecord);
        assertEquals(123, cadastro.getOwner(), "Deve processar proprietário válido");
    }

    @Test
    void handleOwner2() {
        when(mockRecord.get(Constants.OWNER_INDEX)).thenReturn("0");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para proprietário inválido");
    }

    /**
     * Testa handleLocation - Complexidade Ciclomática: 2
     */
    @Test
    void handleLocation1() throws ParseException {
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn("Freguesia");
        when(mockRecord.get(Constants.CONCELHO_INDEX)).thenReturn("Concelho");
        when(mockRecord.get(Constants.DISTRICT_INDEX)).thenReturn("Distrito");
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro.getLocation(), "Deve processar localização válida");
    }

    @Test
    void handleLocation2() {
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn(Constants.NA_VALUE);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Deve lançar IllegalArgumentException para localização inválida");
    }

    /**
     * Testa getPrice - Complexidade Ciclomática: 4
     */
    @Test
    void getPrice1() throws ParseException {
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn("nonexistent");
        when(mockRecord.get(Constants.CONCELHO_INDEX)).thenReturn("nonexistent");
        when(mockRecord.get(Constants.DISTRICT_INDEX)).thenReturn("nonexistent");
        cadastro = new Cadastro(mockRecord);
        assertTrue(cadastro.getPrice() > 0, "Deve retornar um preço válido");
    }

    @Test
    void getPrice2() throws ParseException {
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn("lisboa");
        when(mockRecord.get(Constants.CONCELHO_INDEX)).thenReturn("nonexistent");
        when(mockRecord.get(Constants.DISTRICT_INDEX)).thenReturn("nonexistent");
        cadastro = new Cadastro(mockRecord);
        assertTrue(cadastro.getPrice() > 0, "Deve retornar um preço válido para freguesia");
    }

    @Test
    void getPrice3() throws ParseException {
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn("nonexistent");
        when(mockRecord.get(Constants.CONCELHO_INDEX)).thenReturn("porto");
        when(mockRecord.get(Constants.DISTRICT_INDEX)).thenReturn("nonexistent");
        cadastro = new Cadastro(mockRecord);
        assertTrue(cadastro.getPrice() > 0, "Deve retornar um preço válido para concelho");
    }

    @Test
    void getPrice4() throws ParseException {
        when(mockRecord.get(Constants.FREGUESIA_INDEX)).thenReturn("nonexistent");
        when(mockRecord.get(Constants.CONCELHO_INDEX)).thenReturn("nonexistent");
        when(mockRecord.get(Constants.DISTRICT_INDEX)).thenReturn("lisboa");
        cadastro = new Cadastro(mockRecord);
        assertTrue(cadastro.getPrice() > 0, "Deve retornar um preço válido para distrito");
    }

    /**
     * Testa toString - Complexidade Ciclomática: 1
     */
    @Test
    void testToString() throws ParseException {
        cadastro = new Cadastro(mockRecord);
        String str = cadastro.toString();
        assertTrue(str.contains("Id: 1"), "Representação em string deve conter ID");
        assertTrue(str.contains("Proprietário: 1"), "Representação em string deve conter proprietário");
        assertTrue(str.contains("Área: 100.0"), "Representação em string deve conter área");
        assertTrue(str.contains("Comprimento: 10.5"), "Representação em string deve conter comprimento");
    }
}