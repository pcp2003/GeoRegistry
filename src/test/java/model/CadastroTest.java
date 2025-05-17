package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.apache.commons.csv.CSVRecord;
import static org.mockito.Mockito.*;
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
    }

    /**
     * Test handleId - Cyclomatic Complexity: 2
     */
    @Test
    void handleId1() throws ParseException {
        when(mockRecord.get(0)).thenReturn("123");
        cadastro = new Cadastro(mockRecord);
        assertEquals("123", cadastro.getId(), "Should handle valid ID");
    }

    @Test
    void handleId2() {
        when(mockRecord.get(0)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for null ID");
    }

    /**
     * Test handleLength - Cyclomatic Complexity: 2
     */
    @Test
    void handleLength1() throws ParseException {
        when(mockRecord.get(1)).thenReturn("100");
        cadastro = new Cadastro(mockRecord);
        assertEquals(100.0, cadastro.getLength(), "Should handle valid length");
    }

    @Test
    void handleLength2() {
        when(mockRecord.get(1)).thenReturn("invalid");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for invalid length");
    }

    /**
     * Test handleArea - Cyclomatic Complexity: 2
     */
    @Test
    void handleArea1() throws ParseException {
        when(mockRecord.get(2)).thenReturn("1000");
        cadastro = new Cadastro(mockRecord);
        assertEquals(1000.0, cadastro.getArea(), "Should handle valid area");
    }

    @Test
    void handleArea2() {
        when(mockRecord.get(2)).thenReturn("invalid");
        assertThrows(IllegalArgumentException.class, () -> new Cadastro(mockRecord),
                "Should throw IllegalArgumentException for invalid area");
    }

    /**
     * Test handleShape - Cyclomatic Complexity: 2
     */
    @Test
    void handleShape1() throws ParseException {
        when(mockRecord.get(3)).thenReturn("POLYGON((0 0, 1 0, 1 1, 0 1, 0 0))");
        cadastro = new Cadastro(mockRecord);
        assertNotNull(cadastro.getShape(), "Should handle valid shape");
    }

    @Test
    void handleShape2() {
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
    }

    /**
     * Test toString - Cyclomatic Complexity: 1
     */
    @Test
    void testToString() {
        cadastro = new Cadastro(mockRecord);
        String str = cadastro.toString();
        assertTrue(str.contains("Cadastro{id="), "String representation should contain id");
        assertTrue(str.contains("length="), "String representation should contain length");
        assertTrue(str.contains("area="), "String representation should contain area");
        assertTrue(str.contains("shape="), "String representation should contain shape");
        assertTrue(str.contains("owner="), "String representation should contain owner");
        assertTrue(str.contains("location="), "String representation should contain location");
    }
}