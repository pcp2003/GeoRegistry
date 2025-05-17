package ui;

import service.PropertyGraph;
import model.Cadastro;
import core.Constants;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

/**
 * Painel para visualização de grafos de propriedades com funcionalidades interativas.
 * Suporta zoom, deslocamento e seleção de propriedades.
 * 
 * @author Lei-G
 * @version 1.0
 */
public class PropertyGraphPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
    /** Graph representing property adjacencies */
    private final PropertyGraph propertyGraph;

    /** Last point recorded during mouse interaction */
    private Point2D lastPoint;

    /** Current pan offset for the view */
    private Point2D panOffset = new Point2D.Double(0, 0);

    /** Current zoom factor for the view */
    private double zoomFactor = 1.0;

    /** Map of property IDs to their visual paths */
    private final Map<Integer, Path2D> propertyPaths = new HashMap<>();

    /** Map of property IDs to their assigned colors */
    private final Map<Integer, Color> propertyColors = new HashMap<>();

    /** Cached affine transform for rendering */
    private AffineTransform cachedTransform = null;

    /** Flag indicating if the transform needs to be recalculated */
    private boolean transformDirty = true;

    /**
     * Cria um novo painel para visualização do grafo de propriedades.
     * 
     * @param propertyGraph O grafo de propriedades a visualizar
     */
    public PropertyGraphPanel(PropertyGraph propertyGraph) {
        if (propertyGraph == null) {
            throw new IllegalArgumentException(Constants.NULL_GRAPH_ERROR);
        }
        this.propertyGraph = propertyGraph;
        setBackground(Color.WHITE);
        setPreferredSize(Constants.GRAPH_PANEL_SIZE);
        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        for (Cadastro cadastro : propertyGraph.getProperties()) {
            if (cadastro.getShape() != null) {
                propertyPaths.put(cadastro.getId(), toPath2D(cadastro.getShape()));
                propertyColors.put(cadastro.getId(), generateColorFromId(cadastro.getId()));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (transformDirty) {
                cachedTransform = calculateTransform();
                transformDirty = false;
            }
            g2d.setTransform(cachedTransform);

            drawAdjacencies(g2d);

            for (Cadastro cadastro : propertyGraph.getProperties()) {
                drawProperty(g2d, cadastro);
            }
        } catch (Exception e) {
            drawError((Graphics2D) g);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (!e.isControlDown()) return;  // Apenas aplica zoom se Ctrl estiver pressionado

        double oldZoom = zoomFactor;
        double newZoom = zoomFactor;

        if (e.getWheelRotation() < 0) {
            newZoom = Math.min(zoomFactor + Constants.ZOOM_STEP, Constants.MAX_ZOOM);
        } else {
            newZoom = Math.max(zoomFactor - Constants.ZOOM_STEP, Constants.MIN_ZOOM);
        }

        if (oldZoom != newZoom) {
            try {
                if (transformDirty) {
                    cachedTransform = calculateTransform();
                    transformDirty = false;
                }

                Point2D mousePoint = e.getPoint();
                AffineTransform inverse = cachedTransform.createInverse();
                Point2D worldBeforeZoom = inverse.transform(mousePoint, null);

                zoomFactor = newZoom;
                transformDirty = true;
                cachedTransform = calculateTransform();

                inverse = cachedTransform.createInverse();
                Point2D worldAfterZoom = inverse.transform(mousePoint, null);

                double dx = worldAfterZoom.getX() - worldBeforeZoom.getX();
                double dy = worldAfterZoom.getY() - worldBeforeZoom.getY();

                panOffset.setLocation(
                        panOffset.getX() + dx * cachedTransform.getScaleX(),
                        panOffset.getY() - dy * cachedTransform.getScaleY()
                );

                transformDirty = true;
                repaint();
            } catch (NoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPoint = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPoint = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastPoint != null) {
            double dx = e.getX() - lastPoint.getX();
            double dy = e.getY() - lastPoint.getY();
            panOffset.setLocation(panOffset.getX() + dx, panOffset.getY() + dy);
            lastPoint = e.getPoint();
            transformDirty = true;
            repaint();
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}

    private void drawError(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawString(Constants.RENDER_ERROR, 10, 20);
    }

    private AffineTransform calculateTransform() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Cadastro cadastro : propertyGraph.getProperties()) {
            MultiPolygon shape = cadastro.getShape();
            if (shape != null) {
                minX = Math.min(minX, shape.getEnvelopeInternal().getMinX());
                maxX = Math.max(maxX, shape.getEnvelopeInternal().getMaxX());
                minY = Math.min(minY, shape.getEnvelopeInternal().getMinY());
                maxY = Math.max(maxY, shape.getEnvelopeInternal().getMaxY());
            }
        }

        double width = maxX - minX;
        double height = maxY - minY;
        double scaleX = (getWidth() * Constants.GRAPH_SCALE_FACTOR) / width;
        double scaleY = (getHeight() * Constants.GRAPH_SCALE_FACTOR) / height;
        double scale = Math.min(scaleX, scaleY);

        AffineTransform transform = new AffineTransform();
        transform.translate(getWidth() / 2 + panOffset.getX(), getHeight() / 2 + panOffset.getY());
        transform.scale(1, -1);
        transform.scale(scale * zoomFactor, scale * zoomFactor);
        transform.translate(-(minX + width / 2), -(minY + height / 2));
        return transform;
    }

    private void drawProperty(Graphics2D g2d, Cadastro cadastro) {
        Path2D path = propertyPaths.get(cadastro.getId());
        Color color = propertyColors.get(cadastro.getId());
        if (path != null && color != null) {
            g2d.setColor(color);
            g2d.fill(path);
            g2d.draw(path);
        }
    }

    private Color generateColorFromId(int id) {
        int r = (id * 37) % 256;
        int g = (id * 67) % 256;
        int b = (id * 97) % 256;
        return new Color(r, g, b);
    }

    private void drawAdjacencies(Graphics2D g2d) {
        g2d.setColor(Constants.ADJACENCY_LINE);
        g2d.setStroke(new BasicStroke(Constants.ADJACENCY_STROKE_WIDTH));

        for (Cadastro cadastro : propertyGraph.getProperties()) {
            Set<Cadastro> adjacents = propertyGraph.getAdjacentProperties(cadastro);
            for (Cadastro adjacent : adjacents) {
                drawAdjacencyLine(g2d, cadastro, adjacent);
            }
        }
    }

    private void drawAdjacencyLine(Graphics2D g2d, Cadastro prop1, Cadastro prop2) {
        MultiPolygon shape1 = prop1.getShape();
        MultiPolygon shape2 = prop2.getShape();

        if (shape1 != null && shape2 != null) {
            double x1 = shape1.getCentroid().getX();
            double y1 = shape1.getCentroid().getY();
            double x2 = shape2.getCentroid().getX();
            double y2 = shape2.getCentroid().getY();

            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }

    private Path2D toPath2D(Geometry geometry) {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < geometry.getNumPoints(); i++) {
            double x = geometry.getCoordinates()[i].x;
            double y = geometry.getCoordinates()[i].y;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.closePath();
        return path;
    }
}
