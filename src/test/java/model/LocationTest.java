package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import core.Constants;

/**
 * Classe de teste para o registo Location.
 * Contém testes unitários para a construção e acesso às propriedades de Location.
 * 
 * @author Lei-G
 * @version 1.0
 * 
 * Complexidade Ciclomática por método:
 * - construtor: 2 (1 condição + 1 retorno)
 * - getFreguesia: 1 (1 retorno)
 * - getConcelho: 1 (1 retorno)
 * - getDistrict: 1 (1 retorno)
 * - toString: 1 (1 retorno)
 */
public class LocationTest {
    private Location location;

    /**
     * Construtor padrão para LocationTest.
     * Inicializa os fixtures de teste.
     */
    public LocationTest() {
        // Construtor padrão
    }

    /**
     * Testa o construtor - Complexidade Ciclomática: 1
     */
    @Test
    void constructor() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertNotNull(location, "Location deve ser criada com sucesso");
    }

    /**
     * Testa getPrice - Complexidade Ciclomática: 4
     */
    @Test
    void getPrice1() {
        location = new Location("nonexistent", "nonexistent", "nonexistent");
        assertEquals(Constants.COUNTRY_PRICE.get("portugal"), location.getPrice(), 
            "Deve retornar o preço padrão quando não há preços específicos definidos");
    }

    @Test
    void getPrice2() {
        location = new Location("lisboa", "nonexistent", "nonexistent");
        assertTrue(location.getPrice() > 0, "Deve retornar o preço da freguesia quando definido");
    }

    @Test
    void getPrice3() {
        location = new Location("nonexistent", "porto", "nonexistent");
        assertTrue(location.getPrice() > 0, "Deve retornar o preço do concelho quando definido");
    }

    @Test
    void getPrice4() {
        location = new Location("nonexistent", "nonexistent", "lisboa");
        assertTrue(location.getPrice() > 0, "Deve retornar o preço do distrito quando definido");
    }

    /**
     * Testa toString - Complexidade Ciclomática: 1
     */
    @Test
    void testToString() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        String str = location.toString();
        assertTrue(str.contains("Freguesia"), "Representação em string deve conter freguesia");
        assertTrue(str.contains("Concelho"), "Representação em string deve conter concelho");
        assertTrue(str.contains("Distrito"), "Representação em string deve conter distrito");
    }

    /**
     * Testa freguesia - Complexidade Ciclomática: 1
     */
    @Test
    void freguesia() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals("Freguesia", location.freguesia(), "Deve retornar a freguesia correta");
    }

    /**
     * Testa concelho - Complexidade Ciclomática: 1
     */
    @Test
    void concelho() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals("Concelho", location.concelho(), "Deve retornar o concelho correto");
    }

    /**
     * Testa distrito - Complexidade Ciclomática: 1
     */
    @Test
    void distrito() {
        location = new Location("Freguesia", "Concelho", "Distrito");
        assertEquals("Distrito", location.distrito(), "Deve retornar o distrito correto");
    }
}