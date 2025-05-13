package ui;

import model.Cadastro;
import service.OwnerGraph;
import service.PropertyGraph;
import service.exchange.PropertyExchange;
import service.exchange.PropertyExchangeService;
import core.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Painel responsável por exibir sugestões de troca de propriedades.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class PropertyExchangePanel extends JPanel {

    private final JFrame parentFrame;
    private final List<Cadastro> cadastros;

    private PropertyExchangeService exchangeService;
    private JTextArea suggestionsArea;
    private JSpinner maxSuggestionsSpinner;

    /**
     * Constrói o painel de sugestões de troca.
     * 
     * @param cadastros Lista de cadastros
     * @param parentFrame Janela pai para posicionamento da tela de carregamento
     */
    public PropertyExchangePanel(List<Cadastro> cadastros, JFrame parentFrame) {
        this.cadastros = cadastros;
        this.parentFrame = parentFrame;
        
        // Criar os grafos
        OwnerGraph ownerGraph = new OwnerGraph(cadastros);
        PropertyGraph propertyGraph = new PropertyGraph(cadastros);
        exchangeService = new PropertyExchangeService(ownerGraph, propertyGraph, cadastros);

        // Inicializar o painel
        initializePanel();
    }

    /**
     * Inicializa os componentes do painel após o carregamento dos grafos.
     */
    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        
        // Painel superior com controles
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        
        JLabel maxSuggestionsLabel = new JLabel("Número máximo de sugestões:");
        this.maxSuggestionsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        JButton generateButton = new JButton("Gerar Sugestões");
        
        controlPanel.add(maxSuggestionsLabel);
        controlPanel.add(maxSuggestionsSpinner);
        controlPanel.add(generateButton);
        
        // Área de texto para exibir as sugestões
        this.suggestionsArea = new JTextArea();
        suggestionsArea.setEditable(false);
        suggestionsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(suggestionsArea);
        
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Adiciona o listener do botão
        generateButton.addActionListener(e -> generateSuggestions());

        // Atualiza o painel
        revalidate();
        repaint();
    }

    /**
     * Gera e exibe as sugestões de troca.
     */
    private void generateSuggestions() {
        try {
            if (exchangeService == null) {
                JOptionPane.showMessageDialog(this,
                        "Aguarde a inicialização do serviço...",
                        Constants.WARNING_TITLE,
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int maxSuggestions = (Integer) maxSuggestionsSpinner.getValue();

            // Gerar as sugestões
            List<PropertyExchange> suggestions = exchangeService.generateExchangeSuggestions(maxSuggestions);
            
            // Exibir as sugestões
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < suggestions.size(); i++) {
                sb.append("Sugestão ").append(i + 1).append(":\n");
                sb.append(suggestions.get(i).toString()).append("\n\n");
            }
            
            suggestionsArea.setText(sb.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar sugestões: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
} 