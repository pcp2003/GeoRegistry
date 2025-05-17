package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import core.Constants;

/**
 * Test class for Location
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 1 (1 return)
 * - getPrice: 5 (4 if conditions + 1 return)
 * - toString: 1 (1 return)
 * - freguesia: 1 (1 return)
 * - concelho: 1 (1 return)
 * - distrito: 1 (1 return)
 */
class LocationTest {
    private Location location;

    /**
     * Test constructor - Cyclomatic Complexity: 1
     */
    @Test
    void constructor() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertNotNull(location, "Location should be created successfully");
    }

    /**
     * Test getPrice - Cyclomatic Complexity: 5
     */
    @Test
    void getPrice1() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals(0.0, location.getPrice(), "Should return default price when no specific prices are set");
    }

    @Test
    void getPrice2() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        location.setPriceFreguesia(100.0);
        assertEquals(100.0, location.getPrice(), "Should return freguesia price when set");
    }

    @Test
    void getPrice3() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        location.setPriceConcelho(200.0);
        assertEquals(200.0, location.getPrice(), "Should return concelho price when set");
    }

    @Test
    void getPrice4() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        location.setPriceDistrito(300.0);
        assertEquals(300.0, location.getPrice(), "Should return distrito price when set");
    }

    @Test
    void getPrice5() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        location.setPriceFreguesia(100.0);
        location.setPriceConcelho(200.0);
        location.setPriceDistrito(300.0);
        assertEquals(100.0, location.getPrice(), "Should return freguesia price when all prices are set");
    }

    /**
     * Test toString - Cyclomatic Complexity: 1
     */
    @Test
    void testToString() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        String str = location.toString();
        assertTrue(str.contains("Freguesia"), "String representation should contain freguesia");
        assertTrue(str.contains("Concelho"), "String representation should contain concelho");
        assertTrue(str.contains("Distrito"), "String representation should contain distrito");
    }

    /**
     * Test freguesia - Cyclomatic Complexity: 1
     */
    @Test
    void freguesia() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals("Freguesia", location.freguesia(), "Should return correct freguesia");
    }

    /**
     * Test concelho - Cyclomatic Complexity: 1
     */
    @Test
    void concelho() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals("Concelho", location.concelho(), "Should return correct concelho");
    }

    /**
     * Test distrito - Cyclomatic Complexity: 1
     */
    @Test
    void distrito() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals("Distrito", location.distrito(), "Should return correct distrito");
    }
}