package service.exchange;

import model.Cadastro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Test class for PropertyExchangeService
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 1 (1 return)
 * - createExchange: 3 (2 if conditions + 1 return)
 * - getExchanges: 1 (1 return)
 * - getExchangesByOwner: 2 (1 if condition + 1 return)
 * - getExchangesByProperty: 2 (1 if condition + 1 return)
 * - toString: 1 (1 return)
 */
class PropertyExchangeServiceTest {
    private PropertyExchangeService service;
    private Cadastro property1;
    private Cadastro property2;
    private String owner1;
    private String owner2;

    @BeforeEach
    void setUp() {
        service = new PropertyExchangeService();
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
    }

    /**
     * Test constructor - Cyclomatic Complexity: 1
     */
    @Test
    void constructor() {
        assertNotNull(service, "PropertyExchangeService should be created successfully");
    }

    /**
     * Test createExchange - Cyclomatic Complexity: 3
     */
    @Test
    void createExchange1() {
        PropertyExchange exchange = service.createExchange(property1, property2, owner1, owner2);
        assertNotNull(exchange, "Should create exchange with valid properties and owners");
        assertEquals(property1, exchange.getProperty1(), "First property should match");
        assertEquals(property2, exchange.getProperty2(), "Second property should match");
        assertEquals(owner1, exchange.getOwner1(), "First owner should match");
        assertEquals(owner2, exchange.getOwner2(), "Second owner should match");
    }

    @Test
    void createExchange2() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createExchange(null, property2, owner1, owner2),
                "Should throw IllegalArgumentException when first property is null");
    }

    @Test
    void createExchange3() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createExchange(property1, null, owner1, owner2),
                "Should throw IllegalArgumentException when second property is null");
    }

    /**
     * Test getExchanges - Cyclomatic Complexity: 1
     */
    @Test
    void getExchanges() {
        PropertyExchange exchange = service.createExchange(property1, property2, owner1, owner2);
        List<PropertyExchange> exchanges = service.getExchanges();
        assertNotNull(exchanges, "Should return list of exchanges");
        assertTrue(exchanges.contains(exchange), "Should contain created exchange");
    }

    /**
     * Test getExchangesByOwner - Cyclomatic Complexity: 2
     */
    @Test
    void getExchangesByOwner1() {
        PropertyExchange exchange = service.createExchange(property1, property2, owner1, owner2);
        List<PropertyExchange> exchanges = service.getExchangesByOwner(owner1);
        assertNotNull(exchanges, "Should return list of exchanges");
        assertTrue(exchanges.contains(exchange), "Should contain exchange for owner");
    }

    @Test
    void getExchangesByOwner2() {
        List<PropertyExchange> exchanges = service.getExchangesByOwner("nonexistent");
        assertNotNull(exchanges, "Should return empty list for nonexistent owner");
        assertTrue(exchanges.isEmpty(), "List should be empty for nonexistent owner");
    }

    /**
     * Test getExchangesByProperty - Cyclomatic Complexity: 2
     */
    @Test
    void getExchangesByProperty1() {
        PropertyExchange exchange = service.createExchange(property1, property2, owner1, owner2);
        List<PropertyExchange> exchanges = service.getExchangesByProperty(property1);
        assertNotNull(exchanges, "Should return list of exchanges");
        assertTrue(exchanges.contains(exchange), "Should contain exchange for property");
    }

    @Test
    void getExchangesByProperty2() {
        List<PropertyExchange> exchanges = service.getExchangesByProperty(new Cadastro());
        assertNotNull(exchanges, "Should return empty list for nonexistent property");
        assertTrue(exchanges.isEmpty(), "List should be empty for nonexistent property");
    }

    /**
     * Test toString - Cyclomatic Complexity: 1
     */
    @Test
    void testToString() {
        String str = service.toString();
        assertTrue(str.startsWith("PropertyExchangeService{exchanges=["),
                "String representation should start with 'PropertyExchangeService{exchanges=['");
        assertTrue(str.endsWith("]}"),
                "String representation should end with ']}'");
    }
}