package ui;

import service.OwnerGraph;
import model.Cadastro;
import core.Constants;
import org.locationtech.jts.geom.MultiPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Painel responsável por renderizar o grafo de proprietários.
 * Exibe os proprietários e suas adjacências de forma visual, onde:
 * - Os proprietários são representados por círculos preenchidos em verde
 * - As adjacências são representadas por linhas em laranja
 * - O grafo é automaticamente dimensionado e centralizado no painel
 * - Suporta zoom in/out usando a roda do mouse
 * - Suporta pan (mover a tela) arrastando com o mouse
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class OwnerGraphPanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {
    /** O grafo de proprietários a ser visualizado */
    private final OwnerGraph ownerGraph;
    
    /** Fator de zoom atual */
    private double zoomFactor = 1.0;
    
    /** Ponto de referência para o pan */
    private Point2D lastPoint;
    
    /** Deslocamento atual do pan */
    private Point2D panOffset = new Point2D.Double(0, 0);
    
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
        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
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
            
            // Agrupa propriedades por proprietário
            Map<Integer, List<Cadastro>> propertiesByOwner = new HashMap<>();
            for (Cadastro cadastro : ownerGraph.getProperties()) {
                propertiesByOwner.computeIfAbsent(cadastro.getOwner(), _ -> new ArrayList<>()).add(cadastro);
            }
            
            // Desenha um ponto por proprietário, no centro médio de suas propriedades
            for (Map.Entry<Integer, List<Cadastro>> entry : propertiesByOwner.entrySet()) {
                drawOwnerAtAverageCenter(g2d, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            drawError(g);
        }
    }

    /**
     * Manipula o evento da roda do mouse para controlar o zoom.
     * 
     * @param e O evento da roda do mouse
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double oldZoom = zoomFactor;
        
        // Calcula o novo fator de zoom
        if (e.getWheelRotation() < 0) {
            // Zoom in
            zoomFactor = Math.min(zoomFactor + Constants.ZOOM_STEP, Constants.MAX_ZOOM);
        } else {
            // Zoom out
            zoomFactor = Math.max(zoomFactor - Constants.ZOOM_STEP, Constants.MIN_ZOOM);
        }
        
        // Se o zoom mudou, repinta o painel
        if (oldZoom != zoomFactor) {
            repaint();
        }
    }

    /**
     * Manipula o evento de pressionar o botão do mouse.
     * Inicia o pan quando o botão esquerdo é pressionado.
     * 
     * @param e O evento do mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPoint = e.getPoint();
        }
    }

    /**
     * Manipula o evento de soltar o botão do mouse.
     * Finaliza o pan quando o botão esquerdo é solto.
     * 
     * @param e O evento do mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lastPoint = null;
        }
    }

    /**
     * Manipula o evento de arrastar o mouse.
     * Atualiza o pan quando o mouse é arrastado com o botão esquerdo pressionado.
     * 
     * @param e O evento do mouse
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastPoint != null) {
            double dx = e.getX() - lastPoint.getX();
            double dy = e.getY() - lastPoint.getY();
            
            // Atualiza o deslocamento do pan
            panOffset.setLocation(panOffset.getX() + dx, panOffset.getY() + dy);
            lastPoint = e.getPoint();
            
            repaint();
        }
    }

    // Métodos não utilizados da interface MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}

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
     * 5. Aplicação do fator de zoom
     * 6. Aplicação do deslocamento do pan
     *
     * @return O objeto AffineTransform representando a transformação
     */
    private AffineTransform calculateTransform() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        // Agrupa propriedades por proprietário
        Map<Integer, List<Cadastro>> propertiesByOwner = new HashMap<>();
        for (Cadastro cadastro : ownerGraph.getProperties()) {
            propertiesByOwner.computeIfAbsent(cadastro.getOwner(), _ -> new ArrayList<>()).add(cadastro);
        }

        // Calcula os limites do grafo usando os centros médios
        for (List<Cadastro> ownerProperties : propertiesByOwner.values()) {
            double totalX = 0;
            double totalY = 0;
            int validProperties = 0;

            for (Cadastro cadastro : ownerProperties) {
                MultiPolygon shape = cadastro.getShape();
                if (shape != null) {
                    totalX += shape.getCentroid().getX();
                    totalY += shape.getCentroid().getY();
                    validProperties++;
                }
            }

            if (validProperties > 0) {
                double avgX = totalX / validProperties;
                double avgY = totalY / validProperties;
                minX = Math.min(minX, avgX);
                maxX = Math.max(maxX, avgX);
                minY = Math.min(minY, avgY);
                maxY = Math.max(maxY, avgY);
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

    /**
     * Desenha um proprietário no centro médio de suas propriedades.
     * O tamanho do círculo é proporcional ao número de propriedades.
     * 
     * @param g2d O objeto Graphics2D usado para desenho
     * @param ownerId ID do proprietário
     * @param properties Lista de propriedades do proprietário
     */
    private void drawOwnerAtAverageCenter(Graphics2D g2d, int ownerId, List<Cadastro> properties) {
        double totalX = 0;
        double totalY = 0;
        int validProperties = 0;

        // Calcula a soma dos centroides
        for (Cadastro cadastro : properties) {
            MultiPolygon shape = cadastro.getShape();
            if (shape != null) {
                totalX += shape.getCentroid().getX();
                totalY += shape.getCentroid().getY();
                validProperties++;
            }
        }

        // Se houver propriedades válidas, desenha o ponto no centro médio
        if (validProperties > 0) {
            double avgX = totalX / validProperties;
            double avgY = totalY / validProperties;
            
            // Ajusta o raio baseado no número de propriedades
            double baseRadius = 5.0;
            double radius = baseRadius * Math.sqrt(validProperties);
            
            // Desenha o círculo
            g2d.setColor(new Color(0, 128, 0)); // Verde escuro
            g2d.fill(new java.awt.geom.Ellipse2D.Double(avgX - radius, avgY - radius, radius * 2, radius * 2));
            g2d.setColor(new Color(0, 100, 0)); // Verde mais escuro para a borda
            g2d.draw(new java.awt.geom.Ellipse2D.Double(avgX - radius, avgY - radius, radius * 2, radius * 2));
            
            // Salva a transformação atual
            AffineTransform oldTransform = g2d.getTransform();
            
            // Remove a transformação para desenhar o texto
            g2d.setTransform(new AffineTransform());
            
            // Converte as coordenadas do mundo para coordenadas da tela
            Point2D screenPoint = oldTransform.transform(new Point2D.Double(avgX, avgY), null);
            Point2D radiusPoint = oldTransform.transform(new Point2D.Double(avgX + radius, avgY), null);
            double screenRadius = radiusPoint.getX() - screenPoint.getX();
            
            // Desenha o ID do proprietário
            g2d.setColor(Color.WHITE);
            String ownerIdStr = String.valueOf(ownerId);
            
            // Ajusta o tamanho da fonte para ocupar 60% do diâmetro do círculo
            double targetWidth = screenRadius * 1.2; // Diâmetro * 0.6
            Font originalFont = g2d.getFont();
            Font newFont = findFontSizeToFit(g2d, ownerIdStr, targetWidth, originalFont);
            g2d.setFont(newFont);
            
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(ownerIdStr);
            int textHeight = fm.getHeight();
            
            g2d.drawString(ownerIdStr, 
                (float)(screenPoint.getX() - textWidth/2), 
                (float)(screenPoint.getY() + textHeight/4));
            
            // Restaura a fonte e transformação original
            g2d.setFont(originalFont);
            g2d.setTransform(oldTransform);
        }
    }

    /**
     * Encontra o tamanho de fonte adequado para que o texto caiba na largura desejada.
     * 
     * @param g2d O contexto gráfico
     * @param text O texto a ser medido
     * @param targetWidth A largura alvo desejada
     * @param baseFont A fonte base para derivar o novo tamanho
     * @return Uma nova fonte com o tamanho ajustado
     */
    private Font findFontSizeToFit(Graphics2D g2d, String text, double targetWidth, Font baseFont) {
        float size = 1.0f;
        Font font;
        FontMetrics fm;
        
        do {
            font = baseFont.deriveFont(size);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            size += 0.5f;
        } while (fm.stringWidth(text) < targetWidth);
        
        // Volta um passo para garantir que não passou do tamanho
        return baseFont.deriveFont(size - 0.5f);
    }

    // /**
    //  * Desenha todas as adjacências do grafo.
    //  * As adjacências são desenhadas como linhas laranja
    //  * conectando os centroides das propriedades dos proprietários.
    //  *
    //  * @param g2d O objeto Graphics2D usado para desenho
    //  */
    // private void drawAdjacencies(Graphics2D g2d) {
    //     g2d.setColor(Constants.ADJACENCY_LINE);
    //     g2d.setStroke(new BasicStroke(Constants.ADJACENCY_STROKE_WIDTH));

    //     for (Cadastro cadastro : ownerGraph.getProperties()) {
    //         Set<Cadastro> adjacents = ownerGraph.getAdjacentProperties(cadastro);
    //         for (Cadastro adjacent : adjacents) {
    //             drawAdjacencyLine(g2d, cadastro, adjacent);
    //         }
    //     }
    // }

    // /**
    //  * Desenha uma linha de adjacência entre duas propriedades.
    //  * A linha é desenhada entre os centroides das propriedades.
    //  *
    //  * @param g2d O objeto Graphics2D usado para desenho
    //  * @param prop1 Primeira propriedade
    //  * @param prop2 Segunda propriedade
    //  */
    // private void drawAdjacencyLine(Graphics2D g2d, Cadastro prop1, Cadastro prop2) {
    //     MultiPolygon shape1 = prop1.getShape();
    //     MultiPolygon shape2 = prop2.getShape();
        
    //     if (shape1 != null && shape2 != null) {
    //         double x1 = shape1.getCentroid().getX();
    //         double y1 = shape1.getCentroid().getY();
    //         double x2 = shape2.getCentroid().getX();
    //         double y2 = shape2.getCentroid().getY();
            
    //         g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    //     }
    // }
} 