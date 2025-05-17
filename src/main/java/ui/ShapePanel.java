package ui;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import core.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Random;

/**
 * Painel para renderização de formas geométricas com estilo personalizado.
 * Suporta vários tipos de geometria e fornece visualização interativa.
 * 
 * @author Lei-G
 * @version 1.0
 * @see javax.swing.JPanel
 * @see org.locationtech.jts.geom.Geometry
 * @see org.locationtech.jts.geom.Polygon
 * @see org.locationtech.jts.geom.MultiPolygon
 */
public class ShapePanel extends JPanel {
    
    private final Geometry geometry;
    private final Color color;
    private final String id;

    /**
     * Cria um novo painel para visualização de uma forma geométrica.
     * 
     * @param geometry A geometria a visualizar
     * @param id Identificador único para geração de cor
     */
    public ShapePanel(Geometry geometry, String id) {
        if (geometry == null) {
            throw new IllegalArgumentException(Constants.NULL_GEOMETRY_ERROR);
        }
        this.geometry = geometry;
        this.color = generateColorFromId(id);
        this.id = id;
        setBackground(Color.WHITE);
    }


    private Color generateColorFromId(String id) {
        Random rand = new Random(id.hashCode()); // Semear com o ID para garantir cor consistente

        int r = rand.nextInt(256); // 0-255
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);

        return new Color(r, g, b);
    }

    /**
     * Paints the component, rendering the geometric shape.
     * Applies transformations to center and scale the shape
     * appropriately on the panel.
     *
     * @param g Graphics object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            AffineTransform transform = calculateTransform();
            g2d.transform(transform);
            drawGeometry(g2d, geometry);
        } catch (Exception e) {
            drawError(g);
        }
    }



    /**
     * Draws a message error on the panel.
     * 
     * @param g Graphics object used for painting
     */
    private void drawError(Graphics g) {
        g.setColor(Color.RED);
        g.drawString(Constants.RENDER_GEOMETRY_ERROR, 10, 20);
    }

    /**
     * Calculates the transformation needed to center and scale the shape.
     * The transformation includes translation, scaling, and Y axis inversion.
     *
     * @return AffineTransform object representing the transformation
     * @throws IllegalStateException if there is an error calculating the transformation
     */
    private AffineTransform calculateTransform() {
        try {
            double minX = geometry.getEnvelopeInternal().getMinX();
            double maxX = geometry.getEnvelopeInternal().getMaxX();
            double minY = geometry.getEnvelopeInternal().getMinY();
            double maxY = geometry.getEnvelopeInternal().getMaxY();
            
            if (Double.isInfinite(minX) || Double.isInfinite(maxX) || 
                Double.isInfinite(minY) || Double.isInfinite(maxY)) {
                throw new IllegalStateException(Constants.INVALID_COORDINATES_ERROR);
            }

            double width = maxX - minX;
            double height = maxY - minY;
            
            if (width <= 0 || height <= 0) {
                throw new IllegalStateException(Constants.INVALID_DIMENSIONS_ERROR);
            }

            double scaleX = (getWidth() * Constants.SHAPE_SCALE_FACTOR) / width;
            double scaleY = (getHeight() * Constants.SHAPE_SCALE_FACTOR) / height;
            double scale = Math.min(scaleX, scaleY);

            AffineTransform transform = new AffineTransform();
            transform.translate(getWidth() / 2, getHeight() / 2);
            transform.scale(1, -1);
            transform.scale(scale, scale);
            transform.translate(-(minX + width / 2), -(minY + height / 2));
            
            return transform;
        } catch (Exception e) {
            throw new IllegalStateException(Constants.TRANSFORM_ERROR + e.getMessage(), e);
        }
    }

    /**
     * Draws geometry on the Graphics2D object.
     * Supports MultiPolygon and Polygon.
     *
     * @param g2d Graphics context
     * @param geom Geometry to draw
     */
    private void drawGeometry(Graphics2D g2d, Geometry geom) {
        try {
            if (geom instanceof MultiPolygon multiPolygon) {
                for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
                    Polygon polygon = (Polygon) multiPolygon.getGeometryN(i);
                    drawPolygon(g2d, polygon);
                }
            } else if (geom instanceof Polygon polygon) {
                drawPolygon(g2d, polygon);
            } else {
                throw new IllegalArgumentException(Constants.UNSUPPORTED_GEOMETRY_ERROR + geom.getClass().getSimpleName());
            }
        } catch (Exception e) {
            throw new RuntimeException(Constants.DRAW_GEOMETRY_ERROR, e);
        }
    }

    /**
     * Draws a polygon with custom styling.
     * Fills with semi-transparent blue, outlines in blue.
     * Holes are filled with background color and outlined in red.
     *
     * @param g2d Graphics context
     * @param polygon Polygon to draw
     */
    private void drawPolygon(Graphics2D g2d, Polygon polygon) {
        try {
            if (polygon == null) {
                throw new IllegalArgumentException(Constants.NULL_GEOMETRY_ERROR);
            }

            Path2D path = toPath2D(polygon.getExteriorRing());
            g2d.setColor(color);
            g2d.fill(path);
            g2d.setColor(color);
            g2d.draw(path);

            for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
                Path2D hole = toPath2D(polygon.getInteriorRingN(i));
                g2d.setColor(getBackground());
                g2d.fill(hole);
                g2d.setColor(Constants.HOLE_BORDER);
                g2d.draw(hole);
            }
        } catch (Exception e) {
            throw new IllegalStateException(Constants.DRAW_POLYGON_ERROR, e);
        }
    }

    /**
     * Converts a Geometry object to a Path2D object.
     * Creates a closed path from the geometry coordinates.
     *
     * @param geometry Geometry to convert
     * @return Path2D object representing the geometry
     * @throws IllegalArgumentException if the geometry is null or invalid
     */
    private Path2D toPath2D(Geometry geometry) {
        try {
            if (geometry == null) {
                throw new IllegalArgumentException(Constants.NULL_GEOMETRY_ERROR);
            }

            Path2D path = new Path2D.Double();
            for (int i = 0; i < geometry.getNumPoints(); i++) {
                double x = geometry.getCoordinates()[i].x;
                double y = geometry.getCoordinates()[i].y;
                
                if (Double.isInfinite(x) || Double.isInfinite(y)) {
                    throw new IllegalArgumentException(Constants.INVALID_COORDINATES_ERROR);
                }

                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.closePath();
            return path;
        } catch (Exception e) {
            throw new RuntimeException(Constants.CONVERT_GEOMETRY_ERROR, e);
        }
    }
}