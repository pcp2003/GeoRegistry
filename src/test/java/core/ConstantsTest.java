package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para Constants
 *
 * @author [user.name]
 * @date [current date and time]
 */
class ConstantsTest {
    /**
     * Testa os valores das constantes
     */
    @Test
    void testConstants() {
        // Testa constantes de ordenação
        assertEquals(0, Constants.SORT_BY_ID);
        assertEquals(1, Constants.SORT_BY_LENGTH);
        assertEquals(2, Constants.SORT_BY_AREA);
        assertEquals(3, Constants.SORT_BY_OWNER);
        assertEquals(4, Constants.SORT_BY_FREGUESIA);
        assertEquals(5, Constants.SORT_BY_CONCELHO);
        assertEquals(6, Constants.SORT_BY_DISTRICT);

        // Testa índices do CSV
        assertEquals(0, Constants.ID_INDEX);
        assertEquals(3, Constants.LENGTH_INDEX);
        assertEquals(4, Constants.AREA_INDEX);
        assertEquals(5, Constants.SHAPE_INDEX);
        assertEquals(6, Constants.OWNER_INDEX);
        assertEquals(7, Constants.FREGUESIA_INDEX);
        assertEquals(8, Constants.CONCELHO_INDEX);
        assertEquals(9, Constants.DISTRICT_INDEX);

        // Testa mensagens de erro
        assertNotNull(Constants.NULL_OR_EMPTY_ERROR);
        assertNotNull(Constants.ZERO_OR_NEGATIVE_ERROR);
        assertNotNull(Constants.INVALID_GEOMETRY_ERROR);
        assertNotNull(Constants.EMPTY_FILE_ERROR);
        assertNotNull(Constants.FILE_READ_ERROR);
        assertNotNull(Constants.NUMBER_CONVERSION_ERROR);
        assertNotNull(Constants.EMPTY_LIST_ERROR);
        assertNotNull(Constants.NULL_CADASTROS_ERROR);
        assertNotNull(Constants.EMPTY_CADASTROS_ERROR);
        assertNotNull(Constants.NULL_ELEMENTS_ERROR);
        assertNotNull(Constants.NULL_PROPERTY_ERROR);
        assertNotNull(Constants.NULL_SHAPE_ERROR);
        assertNotNull(Constants.TOPOLOGY_ERROR);
        assertNotNull(Constants.GRAPH_BUILD_ERROR);
        assertNotNull(Constants.ADJACENCY_ERROR);
        assertNotNull(Constants.INVALID_OWNER_ERROR);

        // Testa constantes da interface gráfica
        assertNotNull(Constants.WINDOW_TITLE);
        assertTrue(Constants.WINDOW_WIDTH > 0);
        assertTrue(Constants.WINDOW_HEIGHT > 0);
        assertTrue(Constants.SHAPE_WINDOW_SIZE > 0);

        // Testa textos dos botões
        assertNotNull(Constants.BROWSE_BUTTON_TEXT);
        assertNotNull(Constants.IMPORT_BUTTON_TEXT);
        assertNotNull(Constants.SHOW_MORE_BUTTON_TEXT);
        assertNotNull(Constants.SHOW_SHAPE_BUTTON_TEXT);
        assertNotNull(Constants.AVERAGE_PROPERTY_AREA_BUTTON_TEXT);
        assertNotNull(Constants.AVERAGE_OWNER_AREA_BUTTON_TEXT);
        assertNotNull(Constants.VIEW_PROPERTY_GRAPH_BUTTON_TEXT);
        assertNotNull(Constants.VIEW_OWNER_GRAPH_BUTTON_TEXT);
        assertNotNull(Constants.VIEW_GRAPH_BUTTON_TEXT);
        assertNotNull(Constants.AVERAGE_AREA_BUTTON_TEXT);
        assertNotNull(Constants.PROPERTY_EXCHANGE_BUTTON_TEXT);

        // Testa rótulos
        assertNotNull(Constants.FILE_SELECTION_LABEL);
        assertNotNull(Constants.DISTRICT_LABEL);
        assertNotNull(Constants.MUNICIPALITY_LABEL);
        assertNotNull(Constants.COUNTY_LABEL);

        // Testa títulos de erro e aviso
        assertNotNull(Constants.ERROR_TITLE);
        assertNotNull(Constants.WARNING_TITLE);

        // Testa mapas de preços
        assertNotNull(Constants.FREGUESIA_PRICE);
        assertNotNull(Constants.CONCELHO_PRICE);
        assertNotNull(Constants.DISTRICT_PRICE);
        assertNotNull(Constants.COUNTRY_PRICE);
        assertTrue(Constants.COUNTRY_PRICE.containsKey("portugal"));
    }
}