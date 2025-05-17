package service.exchange;

import model.Cadastro;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PropertyExchange
 *
 * @author [user.name]
 * @date [current date and time]
 * 
 * Cyclomatic Complexity by method:
 * - constructor: 1 (1 return)
 * - getProperty1: 1 (1 return)
 * - getProperty2: 1 (1 return)
 * - getOwner1: 1 (1 return)
 * - getOwner2: 1 (1 return)
 * - toString: 1 (1 return)
 */
class PropertyExchangeTest {
    private Cadastro property1;
    private Cadastro property2;
    private String owner1;
    private String owner2;
    private PropertyExchange exchange;

    @Test
    void constructor() {
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
        exchange = new PropertyExchange(property1, property2, owner1, owner2);
        assertNotNull(exchange, "PropertyExchange should be created successfully");
    }

    @Test
    void getProperty1() {
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
        exchange = new PropertyExchange(property1, property2, owner1, owner2);
        assertEquals(property1, exchange.getProperty1(), "Should return the first property");
    }

    @Test
    void getProperty2() {
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
        exchange = new PropertyExchange(property1, property2, owner1, owner2);
        assertEquals(property2, exchange.getProperty2(), "Should return the second property");
    }

    @Test
    void getOwner1() {
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
        exchange = new PropertyExchange(property1, property2, owner1, owner2);
        assertEquals(owner1, exchange.getOwner1(), "Should return the first owner");
    }

    @Test
    void getOwner2() {
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
        exchange = new PropertyExchange(property1, property2, owner1, owner2);
        assertEquals(owner2, exchange.getOwner2(), "Should return the second owner");
    }

    @Test
    void testToString() {
        property1 = new Cadastro();
        property2 = new Cadastro();
        owner1 = "Owner1";
        owner2 = "Owner2";
        exchange = new PropertyExchange(property1, property2, owner1, owner2);
        String str = exchange.toString();
        assertTrue(str.contains("PropertyExchange{property1="), "String representation should contain property1");
        assertTrue(str.contains("property2="), "String representation should contain property2");
        assertTrue(str.contains("owner1="), "String representation should contain owner1");
        assertTrue(str.contains("owner2="), "String representation should contain owner2");
    }
}