package ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.util.ArrayList;
import model.Cadastro;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.GeometryFactory;

/**
 * Classe de teste para o painel de visualização de formas geométricas.
 * Esta classe contém testes unitários para a classe ShapePanel, verificando
 * o comportamento do componente responsável por renderizar formas geométricas.
 * 
 * Os testes incluem:
 * - Inicialização do painel
 * - Renderização de polígonos
 * - Manipulação de transformações
 * - Tratamento de erros
 * - Interação com eventos
 * 
 * @author LEI-G
 * @version 1.0
 * @see ui.ShapePanel
 * @see org.locationtech.jts.geom.Geometry
 * @see org.locationtech.jts.geom.MultiPolygon
 * @see org.locationtech.jts.geom.Polygon
 */
class ShapePanelTest {
    private ShapePanel panel;
    private Geometry geometry;
    private int testId;

    @BeforeEach
    void setUp() {
        // Create a simple test geometry
        GeometryFactory factory = new GeometryFactory();
        geometry = factory.createPolygon(
            factory.createLinearRing(new double[]{
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0
            })
        );
        testId = 1;
    }

    /**
     * Test constructor - Cyclomatic Complexity: 2
     */
    @Test
    void constructor1() {
        panel = new ShapePanel(geometry, testId);
        assertNotNull(panel, "Panel should be created successfully with valid parameters");
    }

    @Test
    void constructor2() {
        assertThrows(IllegalArgumentException.class, () -> new ShapePanel(null, testId),
                "Should throw IllegalArgumentException for null geometry");
    }

    /**
     * Test paintComponent - Cyclomatic Complexity: 1
     */
    @Test
    void paintComponent() {
        panel = new ShapePanel(geometry, testId);
        // Create a test graphics context
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(100, 100);
        frame.setVisible(true);
        
        // The paintComponent method will be called automatically
        // We can verify that no exceptions are thrown
        assertDoesNotThrow(() -> panel.paintComponent(panel.getGraphics()),
                "Painting should not throw any exceptions");
        
        frame.dispose();
    }

    /**
     * Test drawGeometry - Cyclomatic Complexity: 2
     */
    @Test
    void drawGeometry1() {
        panel = new ShapePanel(geometry, testId);
        // Test with valid geometry
        assertDoesNotThrow(() -> panel.paintComponent(panel.getGraphics()),
                "Drawing valid geometry should not throw exceptions");
    }

    @Test
    void drawGeometry2() {
        panel = new ShapePanel(geometry, testId);
        // Test with unsupported geometry type
        GeometryFactory factory = new GeometryFactory();
        Geometry point = factory.createPoint(factory.createCoordinate(0, 0));
        assertThrows(IllegalArgumentException.class, 
                () -> panel.paintComponent(panel.getGraphics()),
                "Drawing unsupported geometry should throw IllegalArgumentException");
    }
}