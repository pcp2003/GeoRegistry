package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Constants
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 1 (1 return)
 * - getPrice: 4 (3 if conditions + 1 return)
 * - getPriceFreguesia: 2 (1 if condition + 1 return)
 * - getPriceConcelho: 2 (1 if condition + 1 return)
 * - getPriceDistrito: 2 (1 if condition + 1 return)
 * - getPricePais: 2 (1 if condition + 1 return)
 */
class ConstantsTest {
    private Constants constants;

    /**
     * Test constructor - Cyclomatic Complexity: 1
     */
    @Test
    void constructor() {
        constants = new Constants();
        assertNotNull(constants, "Constants should be created successfully");
    }

    /**
     * Test getPrice - Cyclomatic Complexity: 4
     */
    @Test
    void getPrice1() {
        constants = new Constants();
        assertEquals(0.0, constants.getPrice("nonexistent", "nonexistent", "nonexistent"),
                "Should return default price for nonexistent location");
    }

    @Test
    void getPrice2() {
        constants = new Constants();
        assertTrue(constants.getPrice("lisboa", "nonexistent", "nonexistent") > 0,
                "Should return freguesia price when available");
    }

    @Test
    void getPrice3() {
        constants = new Constants();
        assertTrue(constants.getPrice("nonexistent", "porto", "nonexistent") > 0,
                "Should return concelho price when available");
    }

    @Test
    void getPrice4() {
        constants = new Constants();
        assertTrue(constants.getPrice("nonexistent", "nonexistent", "lisboa") > 0,
                "Should return distrito price when available");
    }

    /**
     * Test getPriceFreguesia - Cyclomatic Complexity: 2
     */
    @Test
    void getPriceFreguesia1() {
        constants = new Constants();
        assertTrue(constants.getPriceFreguesia("lisboa") > 0,
                "Should return price for existing freguesia");
    }

    @Test
    void getPriceFreguesia2() {
        constants = new Constants();
        assertEquals(0.0, constants.getPriceFreguesia("nonexistent"),
                "Should return default price for nonexistent freguesia");
    }

    /**
     * Test getPriceConcelho - Cyclomatic Complexity: 2
     */
    @Test
    void getPriceConcelho1() {
        constants = new Constants();
        assertTrue(constants.getPriceConcelho("porto") > 0,
                "Should return price for existing concelho");
    }

    @Test
    void getPriceConcelho2() {
        constants = new Constants();
        assertEquals(0.0, constants.getPriceConcelho("nonexistent"),
                "Should return default price for nonexistent concelho");
    }

    /**
     * Test getPriceDistrito - Cyclomatic Complexity: 2
     */
    @Test
    void getPriceDistrito1() {
        constants = new Constants();
        assertTrue(constants.getPriceDistrito("lisboa") > 0,
                "Should return price for existing distrito");
    }

    @Test
    void getPriceDistrito2() {
        constants = new Constants();
        assertEquals(0.0, constants.getPriceDistrito("nonexistent"),
                "Should return default price for nonexistent distrito");
    }

    /**
     * Test getPricePais - Cyclomatic Complexity: 2
     */
    @Test
    void getPricePais1() {
        constants = new Constants();
        assertTrue(constants.getPricePais("portugal") > 0,
                "Should return price for existing country");
    }

    @Test
    void getPricePais2() {
        constants = new Constants();
        assertEquals(0.0, constants.getPricePais("nonexistent"),
                "Should return default price for nonexistent country");
    }
}