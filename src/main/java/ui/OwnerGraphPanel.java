package ui;

import service.OwnerGraph;
import model.Cadastro;
import core.Constants;
import org.locationtech.jts.geom.MultiPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Set;

/**
 * Painel responsável por renderizar o grafo de proprietários.
 * Exibe os proprietários e suas adjacências de forma visual, onde:
 * - Os proprietários são representados por círculos preenchidos em verde
 * - As adjacências são representadas por linhas em laranja
 * - O grafo é automaticamente dimensionado e centralizado no painel
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class OwnerGraphPanel extends JPanel {
    /** O grafo de proprietários a ser visualizado */
    private final OwnerGraph ownerGraph;
    
    /**
     * Constrói um OwnerGraphPanel com o grafo de proprietários especificado.
     * O painel será dimensionado para 800x600 pixels e terá fundo branco.
     *
     * @param ownerGraph O grafo de proprietários a ser visualizado
     * @throws IllegalArgumentException se o grafo for nulo
     */
    public OwnerGraphPanel(OwnerGraph ownerGraph) {
        if (ownerGraph == null) {
            throw new IllegalArgumentException(Constants.NULL_GRAPH_ERROR);
        }
        this.ownerGraph = ownerGraph;
        setBackground(Color.WHITE);
        setPreferredSize(Constants.GRAPH_PANEL_SIZE);
    }

    /**
     * Pinta o componente, renderizando o grafo de proprietários.
     * Primeiro desenha as adjacências e depois os proprietários,
     * garantindo que as adjacências fiquem atrás dos proprietários.
     * 
     * @param g O objeto Graphics usado para pintura
     */
    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            AffineTransform transform = calculateTransform();
            g2d.transform(transform);

            // Desenha as adjacências primeiro (para ficarem atrás dos proprietários)
            drawAdjacencies(g2d);
            
            // Depois desenha os proprietários
            for (Cadastro cadastro : ownerGraph.getProperties()) {
                drawOwner(g2d, cadastro);
            }
        } catch (Exception e) {
            drawError(g);
        }
    }

    /**
     * Desenha uma mensagem de erro no painel.
     * 
     * @param g O objeto Graphics usado para pintura
     */
    private void drawError(Graphics g) {
        g.setColor(Color.RED);
        g.drawString(Constants.RENDER_ERROR, 10, 20);
    }

    /**
     * Calcula a transformação necessária para centralizar e dimensionar o grafo.
     * A transformação inclui:
     * 1. Translação para o centro do painel
     * 2. Inversão do eixo Y (coordenadas do mundo para coordenadas da tela)
     * 3. Escala para caber no painel
     * 4. Translação para a origem do grafo
     *
     * @return O objeto AffineTransform representando a transformação
     */
    private AffineTransform calculateTransform() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        // Calcula os limites do grafo
        for (Cadastro cadastro : ownerGraph.getProperties()) {
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
        transform.translate(getWidth() / 2, getHeight() / 2);
        transform.scale(1, -1);
        transform.scale(scale, scale);
        transform.translate(-(minX + width / 2), -(minY + height / 2));

        return transform;
    }

    /**
     * Desenha um proprietário no objeto Graphics2D.
     * O proprietário é desenhado como um círculo preenchido em verde
     * com borda verde.
     *
     * @param g2d O objeto Graphics2D usado para desenho
     * @param cadastro A propriedade do proprietário a ser desenhada
     */
    private void drawOwner(Graphics2D g2d, Cadastro cadastro) {
        MultiPolygon shape = cadastro.getShape();
        if (shape != null) {
            double x = shape.getCentroid().getX();
            double y = shape.getCentroid().getY();
            double radius = 5.0; // Tamanho do círculo representando o proprietário
            
            g2d.setColor(new Color(0, 128, 0)); // Verde escuro
            g2d.fill(new java.awt.geom.Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
            g2d.setColor(new Color(0, 100, 0)); // Verde mais escuro para a borda
            g2d.draw(new java.awt.geom.Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
        }
    }

    /**
     * Desenha todas as adjacências do grafo.
     * As adjacências são desenhadas como linhas laranja
     * conectando os centroides das propriedades dos proprietários.
     *
     * @param g2d O objeto Graphics2D usado para desenho
     */
    private void drawAdjacencies(Graphics2D g2d) {
        g2d.setColor(Constants.ADJACENCY_LINE);
        g2d.setStroke(new BasicStroke(Constants.ADJACENCY_STROKE_WIDTH));

        for (Cadastro cadastro : ownerGraph.getProperties()) {
            Set<Cadastro> adjacents = ownerGraph.getAdjacentProperties(cadastro);
            for (Cadastro adjacent : adjacents) {
                drawAdjacencyLine(g2d, cadastro, adjacent);
            }
        }
    }

    /**
     * Desenha uma linha de adjacência entre duas propriedades.
     * A linha é desenhada entre os centroides das propriedades.
     *
     * @param g2d O objeto Graphics2D usado para desenho
     * @param prop1 Primeira propriedade
     * @param prop2 Segunda propriedade
     */
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
} 