package cadastro.gui;
import cadastro.importer.Cadastro;
import cadastro.graph.PropertyGraph;
import cadastro.graph.OwnerGraph;
import cadastro.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa a interface gráfica do sistema de gestão de propriedades.
 * Esta classe estende JFrame e fornece uma interface para importar, visualizar e gerenciar
 * cadastros de propriedades a partir de arquivos CSV.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class Gui extends JFrame {
    // Declaração dos componentes da interface
    private final JTextField csvPathInput = new JTextField(20);
    private final JButton browseButton = new JButton(Constants.BROWSE_BUTTON_TEXT);
    private final JButton importButton = new JButton(Constants.IMPORT_BUTTON_TEXT);
    private final JButton showMore = new JButton(Constants.SHOW_MORE_BUTTON_TEXT);
    private final JButton viewPropertyGraphButton = new JButton(Constants.VIEW_PROPERTY_GRAPH_BUTTON_TEXT);
    private final JButton viewOwnerGraphButton = new JButton(Constants.VIEW_OWNER_GRAPH_BUTTON_TEXT);
    private final JButton calculatePropertyAverageButton = new JButton(Constants.AVERAGE_PROPERTY_AREA_BUTTON_TEXT);
    private final JButton calculateOwnerAverageButton = new JButton(Constants.AVERAGE_OWNER_AREA_BUTTON_TEXT);
    private final JPanel resultsPanel = new JPanel();
    private int cadastrosResultPointer;
    private final List<JButton> sortButtons = new ArrayList<>();
    private List<Cadastro> cadastros;

    /**
     * Construtor da classe GUI.
     * Inicializa a interface gráfica com todos os componentes necessários,
     * incluindo campos para seleção de arquivo, botões de ação e painéis de resultados.
     * 
     * @throws RuntimeException se houver erro na inicialização da interface
     */
    public Gui() {
        try {
            setTitle(Constants.WINDOW_TITLE);
            setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            
            // Configurar o look and feel para o padrão do sistema
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            initializeComponents();
            setupLayout();
            configureListeners();
        } catch (Exception e) {
            throw new RuntimeException("Falha na inicialização da interface gráfica", e);
        }
    }

    private void initializeComponents() {
        // Configurar o painel principal
        getContentPane().setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        
        csvPathInput.setEditable(false);
        csvPathInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode(Constants.BORDER_COLOR)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        csvPathInput.setBackground(Color.WHITE);
        csvPathInput.setForeground(Color.decode(Constants.TEXT_COLOR));
        
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Estilizar botões principais
        styleButton(browseButton);
        styleButton(importButton);
        styleButton(viewPropertyGraphButton);
        styleButton(viewOwnerGraphButton);
        styleButton(calculatePropertyAverageButton);
        styleButton(calculateOwnerAverageButton);
        styleButton(showMore);
        
        viewPropertyGraphButton.setEnabled(false);
        viewOwnerGraphButton.setEnabled(false);
        calculatePropertyAverageButton.setEnabled(false);
        calculateOwnerAverageButton.setEnabled(false);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.decode(Constants.PRIMARY_COLOR));
        button.setForeground(Color.decode(Constants.BUTTON_TEXT_COLOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 34));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 12f));
        
        // Adicionar efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(Color.decode(Constants.HOVER_COLOR));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(Color.decode(Constants.PRIMARY_COLOR));
                }
            }
        });

        // Estilo para botão desabilitado
        if (!button.isEnabled()) {
            button.setBackground(Color.decode(Constants.DISABLED_COLOR));
            button.setForeground(Color.decode(Constants.BORDER_COLOR));
        }
    }

    private void setupLayout() {
        JPanel filePanel = new JPanel(new BorderLayout(Constants.PADDING, 0));
        filePanel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        filePanel.setBorder(BorderFactory.createEmptyBorder(Constants.PADDING, Constants.PADDING, Constants.PADDING, Constants.PADDING));
        
        JLabel fileLabel = new JLabel(Constants.FILE_SELECTION_LABEL);
        fileLabel.setFont(fileLabel.getFont().deriveFont(Font.BOLD));
        fileLabel.setForeground(Color.decode(Constants.TEXT_COLOR));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        buttonPanel.add(browseButton);
        buttonPanel.add(importButton);
        buttonPanel.add(viewPropertyGraphButton);
        buttonPanel.add(viewOwnerGraphButton);
        buttonPanel.add(calculatePropertyAverageButton);
        buttonPanel.add(calculateOwnerAverageButton);

        filePanel.add(fileLabel, BorderLayout.WEST);
        filePanel.add(csvPathInput, BorderLayout.CENTER);
        filePanel.add(buttonPanel, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.decode(Constants.BACKGROUND_COLOR));

        add(filePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void configureListeners() {
        browseButton.addActionListener(this::browseFile);
        importButton.addActionListener(this::importCadastros);
        showMore.addActionListener(this::moreResults);
        viewPropertyGraphButton.addActionListener(this::showPropertyGraphVisualization);
        viewOwnerGraphButton.addActionListener(this::showOwnerGraphVisualization);
        calculatePropertyAverageButton.addActionListener(this::showPropertyAverageAreaPanel);
        calculateOwnerAverageButton.addActionListener(this::showOwnerAverageAreaPanel);
    }

    /**
     * Abre um diálogo para seleção de arquivo CSV.
     * 
     * @param e O evento de ação que disparou o método
     */
    private void browseFile(ActionEvent e) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.exists()) {
                    throw new IllegalArgumentException(Constants.FILE_NOT_FOUND_ERROR);
                }
                csvPathInput.setText(selectedFile.getAbsolutePath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao selecionar arquivo: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Importa cadastros a partir do arquivo CSV selecionado.
     * 
     * @param e O evento de ação que disparou o método
     */
    private void importCadastros(ActionEvent e) {
        String path = csvPathInput.getText();
        if (path.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    Constants.SELECT_FILE_WARNING,
                    Constants.WARNING_TITLE,
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            cadastros = Cadastro.getCadastros(path);
            
            if (cadastros == null || cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_FILE_ERROR);
            }

            // Habilitar os botões de visualização do grafo e cálculo de média
            viewPropertyGraphButton.setEnabled(true);
            viewOwnerGraphButton.setEnabled(true);
            calculatePropertyAverageButton.setEnabled(true);
            calculateOwnerAverageButton.setEnabled(true);

            initializeSortButtons();
            displayResults();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao importar: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeSortButtons() {
        sortButtons.clear();
        int[] sortTypes = {
            Constants.SORT_BY_ID, 
            Constants.SORT_BY_LENGTH, 
            Constants.SORT_BY_AREA, 
            Constants.SORT_BY_OWNER,
            Constants.SORT_BY_DISTRICT,
            Constants.SORT_BY_MUNICIPALITY,
            Constants.SORT_BY_COUNTY
        };

        for (int i = 0; i < Constants.SORT_BUTTON_LABELS.length; i++) {
            JButton button = new JButton(Constants.SORT_BUTTON_LABELS[i]);
            final int sortType = sortTypes[i];
            button.addActionListener(evento -> sortResults(evento, sortType));
            sortButtons.add(button);
        }
    }

    /**
     * Ordena os resultados de acordo com o critério especificado.
     * 
     * @param e O evento de ação que disparou o método
     * @param sortType O tipo de ordenação a ser aplicada
     */
    private void sortResults(ActionEvent e, int sortType) {
        try {
            if (cadastros == null || cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_LIST_ERROR + "ordenar");
            }

            cadastros = Cadastro.sortCadastros(cadastros, sortType);
            displayResults();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao ordenar: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exibe os resultados dos cadastros importados na interface.
     */
    private void displayResults() {
        try {
            resultsPanel.removeAll();
            addSortButtonsPanel();
            cadastrosResultPointer = 0;
            addResults();
            resultsPanel.add(showMore);
            resultsPanel.revalidate();
            resultsPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao exibir resultados: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSortButtonsPanel() {
        JPanel sortButtonsPanel = new JPanel();
        sortButtonsPanel.setLayout(new BoxLayout(sortButtonsPanel, BoxLayout.X_AXIS));
        for (JButton button : sortButtons) {
            sortButtonsPanel.add(button);
        }
        resultsPanel.add(sortButtonsPanel);
    }

    /**
     * Carrega e exibe mais resultados quando o usuário clica no botão "Mais".
     * 
     * @param e O evento de ação que disparou o método
     */
    private void moreResults(ActionEvent e) {
        try {
            if (cadastros == null || cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_LIST_ERROR + "exibir");
            }

            int toLoad = cadastrosResultPointer + Constants.DEFAULT_CADASTROS_LOAD;
            if (toLoad > cadastros.size()) {
                showMore.setEnabled(false);
            }

            resultsPanel.remove(showMore);
            addResults();
            resultsPanel.add(showMore);
            resultsPanel.revalidate();
            resultsPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar mais resultados: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adiciona um lote de resultados ao painel de visualização.
     */
    private void addResults() {
        try {
            int toLoad = Math.min(cadastrosResultPointer + Constants.DEFAULT_CADASTROS_LOAD, cadastros.size());
            for (int i = cadastrosResultPointer; i < toLoad; i++) {
                showCadastroResult(cadastros.get(i));
            }
            cadastrosResultPointer = toLoad;
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao adicionar resultados ao painel", ex);
        }
    }

    /**
     * Exibe as informações de um cadastro específico no painel de resultados.
     * 
     * @param cadastro O objeto Cadastro a ser exibido
     */
    private void showCadastroResult(Cadastro cadastro) {
        try {
            if (cadastro == null) {
                throw new IllegalArgumentException(Constants.NULL_CADASTRO_ERROR);
            }

            JButton cadastroButton = new JButton(Constants.SHOW_SHAPE_BUTTON_TEXT);
            styleButton(cadastroButton);
            cadastroButton.addActionListener(_ -> showShapeWindow(cadastro));
            cadastroButton.setPreferredSize(new Dimension(130, 34));

            JPanel cardPanel = new JPanel(new BorderLayout(Constants.CARD_PADDING, Constants.CARD_PADDING));
            cardPanel.setBackground(Color.decode(Constants.CARD_BACKGROUND));
            
            // Borda mais sutil com sombra
            cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(105, 103, 115, 40), 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));

            List<String> location = cadastro.getLocation();
            String district = location.size() > 0 ? location.get(0) : "N/A";
            String municipality = location.size() > 1 ? location.get(1) : "N/A";
            String county = location.size() > 2 ? location.get(2) : "N/A";

            // Criar um painel para as informações com GridBagLayout
            JPanel infoPanel = new JPanel(new GridBagLayout());
            infoPanel.setBackground(Color.decode(Constants.CARD_BACKGROUND));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1.0;
            gbc.insets = new Insets(0, 5, 0, 5);

            // ID com destaque em amarelo
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.weightx = 0.15;
            JLabel idLabel = new JLabel("ID: " + cadastro.getId());
            idLabel.setFont(idLabel.getFont().deriveFont(Font.BOLD, 13f));
            idLabel.setForeground(Color.decode(Constants.ACCENT_COLOR));
            infoPanel.add(idLabel, gbc);

            // Proprietário
            gbc.gridx = 1;
            gbc.weightx = 0.35;
            JLabel ownerLabel = new JLabel("Proprietário: " + cadastro.getOwner());
            ownerLabel.setFont(ownerLabel.getFont().deriveFont(12f));
            ownerLabel.setForeground(Color.decode(Constants.TEXT_COLOR));
            infoPanel.add(ownerLabel, gbc);

            // Área
            gbc.gridx = 2;
            gbc.weightx = 0.25;
            JLabel areaLabel = new JLabel("Área: " + cadastro.getArea());
            areaLabel.setFont(areaLabel.getFont().deriveFont(12f));
            areaLabel.setForeground(Color.decode(Constants.TEXT_COLOR));
            infoPanel.add(areaLabel, gbc);

            // Comprimento
            gbc.gridx = 3;
            gbc.weightx = 0.25;
            JLabel lengthLabel = new JLabel("Comprimento: " + cadastro.getLength());
            lengthLabel.setFont(lengthLabel.getFont().deriveFont(12f));
            lengthLabel.setForeground(Color.decode(Constants.TEXT_COLOR));
            infoPanel.add(lengthLabel, gbc);

            // Localização com cor mais suave
            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.gridwidth = 4;
            gbc.weightx = 1.0;
            JLabel locationLabel = new JLabel("Localização: " + district + ", " + municipality + ", " + county);
            locationLabel.setFont(locationLabel.getFont().deriveFont(12f));
            locationLabel.setForeground(Color.decode(Constants.LABEL_COLOR));
            infoPanel.add(locationLabel, gbc);

            // Painel para o botão
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            buttonPanel.setBackground(Color.decode(Constants.CARD_BACKGROUND));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            buttonPanel.add(cadastroButton);

            cardPanel.add(infoPanel, BorderLayout.CENTER);
            cardPanel.add(buttonPanel, BorderLayout.EAST);

            resultsPanel.add(cardPanel);
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            if (cadastrosResultPointer % Constants.DEFAULT_CADASTROS_LOAD == 0) {
                resultsPanel.revalidate();
                resultsPanel.repaint();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao exibir cadastro no painel", ex);
        }
    }

    /**
     * Exibe uma janela com a forma geométrica do cadastro.
     * 
     * @param cadastro O cadastro cuja forma será exibida
     */
    private void showShapeWindow(Cadastro cadastro) {
        try {
            if (cadastro == null) {
                throw new IllegalArgumentException(Constants.NULL_CADASTRO_ERROR);
            }

            JFrame shapeFrame = new JFrame(Constants.SHAPE_WINDOW_TITLE + cadastro.getId());
            shapeFrame.setSize(Constants.SHAPE_WINDOW_SIZE, Constants.SHAPE_WINDOW_SIZE);
            shapeFrame.setLocationRelativeTo(this);

            ShapePanel shapePanel = new ShapePanel(cadastro.getShape());
            shapeFrame.add(shapePanel);

            shapeFrame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao exibir shape: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exibe a visualização do grafo de propriedades em uma nova janela.
     * 
     * @param e O evento de ação que disparou o método
     */
    private void showPropertyGraphVisualization(ActionEvent e) {
        try {
            if (cadastros == null || cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_LIST_ERROR + "visualizar grafo de propriedades");
            }

            // Criar a janela de carregamento
            JFrame loadingFrame = new JFrame(Constants.PROPERTY_GRAPH_WINDOW_TITLE);
            loadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            loadingFrame.setSize(300, 100);
            loadingFrame.setLocationRelativeTo(this);
            
            JPanel loadingPanel = new JPanel(new BorderLayout());
            loadingPanel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
            JLabel loadingLabel = new JLabel("A carregar...", SwingConstants.CENTER);
            loadingLabel.setFont(loadingLabel.getFont().deriveFont(Font.BOLD));
            loadingPanel.add(loadingLabel, BorderLayout.CENTER);
            loadingFrame.add(loadingPanel);
            loadingFrame.setVisible(true);

            // Criar o grafo em uma thread separada
            SwingWorker<PropertyGraph, Void> worker = new SwingWorker<PropertyGraph, Void>() {
                @Override
                protected PropertyGraph doInBackground() {
                    return new PropertyGraph(cadastros);
                }

                @Override
                protected void done() {
                    try {
                        loadingFrame.dispose();
                        PropertyGraph graph = get();
                        
                        // Criar a janela de visualização do grafo
                        JFrame graphFrame = new JFrame(Constants.PROPERTY_GRAPH_WINDOW_TITLE);
                        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
                        PropertyGraphPanel graphPanel = new PropertyGraphPanel(graph);
                        JScrollPane scrollPane = new JScrollPane(graphPanel);
                        scrollPane.setBorder(null);
                        scrollPane.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
                        
                        graphFrame.add(scrollPane);
                        graphFrame.setSize(800, 600);
                        graphFrame.setLocationRelativeTo(Gui.this);
                        graphFrame.setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Gui.this,
                                "Erro ao visualizar grafo de propriedades: " + ex.getMessage(),
                                Constants.ERROR_TITLE,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            };

            worker.execute();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao visualizar grafo de propriedades: " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exibe a visualização do grafo de proprietários em uma nova janela.
     * 
     * @param e O evento de ação que disparou o método
     */
    private void showOwnerGraphVisualization(ActionEvent e) {
        // try {
        //     if (cadastros == null || cadastros.isEmpty()) {
        //         throw new IllegalStateException(Constants.EMPTY_LIST_ERROR + "visualizar grafo de proprietários");
        //     }

        //     // Criar a janela de carregamento
        //     JFrame loadingFrame = new JFrame(Constants.OWNER_GRAPH_WINDOW_TITLE);
        //     loadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //     loadingFrame.setSize(300, 100);
        //     loadingFrame.setLocationRelativeTo(this);
            
        //     JPanel loadingPanel = new JPanel(new BorderLayout());
        //     loadingPanel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
        //     JLabel loadingLabel = new JLabel("A carregar...", SwingConstants.CENTER);
        //     loadingLabel.setFont(loadingLabel.getFont().deriveFont(Font.BOLD));
        //     loadingPanel.add(loadingLabel, BorderLayout.CENTER);
        //     loadingFrame.add(loadingPanel);
        //     loadingFrame.setVisible(true);

        //     // Criar o grafo em uma thread separada
        //     SwingWorker<OwnerGraph, Void> worker = new SwingWorker<OwnerGraph, Void>() {
        //         @Override
        //         protected OwnerGraph doInBackground() {
        //             return new OwnerGraph(cadastros);
        //         }

        //         @Override
        //         protected void done() {
        //             try {
        //                 loadingFrame.dispose();
        //                 OwnerGraph graph = get();
                        
        //                 // Criar a janela de visualização do grafo
        //                 JFrame graphFrame = new JFrame(Constants.OWNER_GRAPH_WINDOW_TITLE);
        //                 graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
        //                 GraphPanel graphPanel = new GraphPanel(graph);
        //                 JScrollPane scrollPane = new JScrollPane(graphPanel);
        //                 scrollPane.setBorder(null);
        //                 scrollPane.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
                        
        //                 graphFrame.add(scrollPane);
        //                 graphFrame.setSize(800, 600);
        //                 graphFrame.setLocationRelativeTo(GUI.this);
        //                 graphFrame.setVisible(true);
        //             } catch (Exception ex) {
        //                 JOptionPane.showMessageDialog(GUI.this,
        //                         "Erro ao visualizar grafo de proprietários: " + ex.getMessage(),
        //                         Constants.ERROR_TITLE,
        //                         JOptionPane.ERROR_MESSAGE);
        //             }
        //         }
        //     };

        //     worker.execute();
        // } catch (Exception ex) {
        //     JOptionPane.showMessageDialog(this,
        //             "Erro ao visualizar grafo de proprietários: " + ex.getMessage(),
        //             Constants.ERROR_TITLE,
        //             JOptionPane.ERROR_MESSAGE);
        // }
    }

    /**
     * Exibe o painel para cálculo da área média de propriedades.
     * 
     * @param e O evento de ação que disparou o método
     */
    private void showPropertyAverageAreaPanel(ActionEvent e) {
        try {
            if (cadastros == null || cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_LIST_ERROR + "calcular área média de propriedades");
            }

            JFrame averageFrame = new JFrame(Constants.AVERAGE_PROPERTY_AREA_WINDOW_TITLE);
            averageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            averageFrame.setSize(400, 200);
            averageFrame.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(4, 2, Constants.PADDING, Constants.PADDING));
            panel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
            panel.setBorder(BorderFactory.createEmptyBorder(Constants.PADDING, Constants.PADDING, Constants.PADDING, Constants.PADDING));

            JTextField districtField = new JTextField();
            JTextField municipalityField = new JTextField();
            JTextField countyField = new JTextField();
            JLabel resultLabel = new JLabel();
            resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD));

            // Estilizar campos de texto
            styleTextField(districtField);
            styleTextField(municipalityField);
            styleTextField(countyField);

            panel.add(new JLabel(Constants.DISTRICT_LABEL));
            panel.add(districtField);
            panel.add(new JLabel(Constants.MUNICIPALITY_LABEL));
            panel.add(municipalityField);
            panel.add(new JLabel(Constants.COUNTY_LABEL));
            panel.add(countyField);

            JButton calculateButton = new JButton(Constants.AVERAGE_PROPERTY_AREA_BUTTON_TEXT);
            styleButton(calculateButton);
            calculateButton.addActionListener(_ -> {
                try {
                    String district = districtField.getText().isEmpty() ? null : districtField.getText();
                    String municipality = municipalityField.getText().isEmpty() ? null : municipalityField.getText();
                    String county = countyField.getText().isEmpty() ? null : countyField.getText();

                    PropertyGraph graph = new PropertyGraph(cadastros);
                    double averageArea = graph.calculateAverageArea(district, municipality, county);
                    resultLabel.setText(String.format(Constants.AVERAGE_AREA_RESULT_FORMAT, averageArea));
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(averageFrame,
                            ex.getMessage(),
                            Constants.ERROR_TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            panel.add(calculateButton);
            panel.add(resultLabel);

            averageFrame.add(panel);
            averageFrame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    Constants.AVERAGE_AREA_ERROR + ": " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exibe o painel para cálculo da área média de proprietários.
     * 
     * @param e O evento de ação que disparou o método
     */
    private void showOwnerAverageAreaPanel(ActionEvent e) {
        try {
            if (cadastros == null || cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_LIST_ERROR + "calcular área média de proprietários");
            }

            JFrame averageFrame = new JFrame(Constants.AVERAGE_OWNER_AREA_WINDOW_TITLE);
            averageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            averageFrame.setSize(400, 200);
            averageFrame.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(4, 2, Constants.PADDING, Constants.PADDING));
            panel.setBackground(Color.decode(Constants.BACKGROUND_COLOR));
            panel.setBorder(BorderFactory.createEmptyBorder(Constants.PADDING, Constants.PADDING, Constants.PADDING, Constants.PADDING));

            JTextField districtField = new JTextField();
            JTextField municipalityField = new JTextField();
            JTextField countyField = new JTextField();
            JLabel resultLabel = new JLabel();
            resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD));

            // Estilizar campos de texto
            styleTextField(districtField);
            styleTextField(municipalityField);
            styleTextField(countyField);

            panel.add(new JLabel(Constants.DISTRICT_LABEL));
            panel.add(districtField);
            panel.add(new JLabel(Constants.MUNICIPALITY_LABEL));
            panel.add(municipalityField);
            panel.add(new JLabel(Constants.COUNTY_LABEL));
            panel.add(countyField);

            JButton calculateButton = new JButton(Constants.AVERAGE_OWNER_AREA_BUTTON_TEXT);
            styleButton(calculateButton);
            calculateButton.addActionListener(_ -> {
                try {
                    String district = districtField.getText().isEmpty() ? null : districtField.getText();
                    String municipality = municipalityField.getText().isEmpty() ? null : municipalityField.getText();
                    String county = countyField.getText().isEmpty() ? null : countyField.getText();

                    OwnerGraph graph = new OwnerGraph(cadastros);
                    double averageArea = graph.calculateAverageArea(district, municipality, county);
                    resultLabel.setText(String.format(Constants.AVERAGE_AREA_RESULT_FORMAT, averageArea));
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(averageFrame,
                            ex.getMessage(),
                            Constants.ERROR_TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            panel.add(calculateButton);
            panel.add(resultLabel);

            averageFrame.add(panel);
            averageFrame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    Constants.AVERAGE_AREA_ERROR + ": " + ex.getMessage(),
                    Constants.ERROR_TITLE,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode(Constants.BORDER_COLOR)),
            BorderFactory.createEmptyBorder(Constants.TEXT_FIELD_PADDING, Constants.TEXT_FIELD_PADDING, Constants.TEXT_FIELD_PADDING, Constants.TEXT_FIELD_PADDING)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.decode(Constants.TEXT_COLOR));
    }
}