package cadastro.gui;

/**
 * Classe que armazena todas as constantes utilizadas na interface gráfica.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class GUIConstants {
    // Constantes para configuração da janela
    public static final String WINDOW_TITLE = "Gestão de Propriedades";
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;
    public static final int SHAPE_WINDOW_SIZE = 600;

    // Constantes para botões
    public static final String BROWSE_BUTTON_TEXT = "Procurar";
    public static final String IMPORT_BUTTON_TEXT = "Importar";
    public static final String SHOW_MORE_BUTTON_TEXT = "Mais";
    public static final String SHOW_SHAPE_BUTTON_TEXT = "Mostrar shape";
    public static final String AVERAGE_PROPERTY_AREA_BUTTON_TEXT = "Área Média de Propriedades";
    public static final String AVERAGE_OWNER_AREA_BUTTON_TEXT = "Área Média de Proprietários";
    public static final String VIEW_PROPERTY_GRAPH_BUTTON_TEXT = "Grafo de Propriedades";
    public static final String VIEW_OWNER_GRAPH_BUTTON_TEXT = "Grafo de Proprietários";

    // Constantes para rótulos
    public static final String FILE_SELECTION_LABEL = "Selecione o arquivo CSV:";

    // Constantes para mensagens de erro e aviso
    public static final String ERROR_TITLE = "Erro";
    public static final String WARNING_TITLE = "Aviso";
    public static final String FILE_NOT_FOUND_ERROR = "Arquivo selecionado não existe";
    public static final String SELECT_FILE_WARNING = "Por favor, selecione um arquivo CSV primeiro.";
    public static final String EMPTY_FILE_ERROR = "Nenhum cadastro foi importado do arquivo";
    public static final String EMPTY_LIST_ERROR = "Nenhum cadastro para ";
    public static final String NULL_CADASTRO_ERROR = "Cadastro não pode ser nulo";

    // Constantes para formatação
    public static final String CADASTRO_INFO_FORMAT = "<html>Id: %s<br>Proprietário: %s<br>Área: %s<br>Comprimento: %s<br>Distrito: %s<br>Município: %s<br>Concelho: %s</html>";
    public static final String SHAPE_WINDOW_TITLE = "Shape - ";

    // Constantes para ordenação
    public static final String[] SORT_BUTTON_LABELS = {
        "Sort by ID",
        "Sort by Length",
        "Sort by Area",
        "Sort by Owner",
        "Sort by District",
        "Sort by Municipality",
        "Sort by County"
    };

    // Constante para carregamento de cadastros
    public static final int DEFAULT_CADASTROS_LOAD = 5000;

    // Graph visualization constants
    public static final String VIEW_GRAPH_BUTTON_TEXT = "Visualizar Grafo";
    public static final String GRAPH_WINDOW_TITLE = "Visualização do Grafo de Propriedades";
    public static final String GRAPH_ERROR = "Erro ao criar grafo de propriedades";

    // Average Area Panel Constants
    public static final String AVERAGE_AREA_BUTTON_TEXT = "Calcular Média";
    public static final String AVERAGE_AREA_WINDOW_TITLE = "Cálculo de Área Média";
    public static final String DISTRICT_LABEL = "Distrito:";
    public static final String MUNICIPALITY_LABEL = "Município:";
    public static final String COUNTY_LABEL = "Concelho:";
    public static final String AVERAGE_AREA_RESULT_FORMAT = "Área média: %.2f";
    public static final String AVERAGE_AREA_ERROR = "Erro ao calcular área média";

    // Cores
    public static final String PRIMARY_COLOR = "#352208";      // Bistre - para botões principais
    public static final String SECONDARY_COLOR = "#e1bb80";    // Ecru - para elementos de destaque
    public static final String BACKGROUND_COLOR = "#f9f1e5";   // Ecru-900 - para fundo geral
    public static final String CARD_BACKGROUND = "#FFFFFF";    // Branco - para cartões
    public static final String TEXT_COLOR = "#352208";         // Bistre - para texto principal
    public static final String BORDER_COLOR = "#7b6b43";       // Coyote - para bordas
    public static final String HOVER_COLOR = "#835514";        // Bistre-600 - para hover
    public static final String BUTTON_TEXT_COLOR = "#FFFFFF";  // Branco - para texto dos botões
    public static final String DISABLED_COLOR = "#d1c7ac";     // Coyote-800 - para elementos desabilitados
    public static final String HEADER_COLOR = "#352208";       // Bistre - para cabeçalhos
    public static final String ACCENT_COLOR = "#e1bb80";       // Ecru - para acentos e destaques
    public static final String LABEL_COLOR = "#7b6b43";        // Coyote - para labels secundários
    
    // Estilos
    public static final int CORNER_RADIUS = 5;
    public static final int PADDING = 8;
    public static final int CARD_SPACING = 4;
    public static final int BUTTON_PADDING = 8;
    public static final int TEXT_FIELD_PADDING = 6;
    public static final int CARD_PADDING = 6;

    // Average Area Panel Constants
    public static final String AVERAGE_PROPERTY_AREA_WINDOW_TITLE = "Cálculo de Área Média de Propriedades";
    public static final String AVERAGE_OWNER_AREA_WINDOW_TITLE = "Cálculo de Área Média de Proprietários";
    public static final String PROPERTY_GRAPH_WINDOW_TITLE = "Visualização do Grafo de Propriedades";
    public static final String OWNER_GRAPH_WINDOW_TITLE = "Visualização do Grafo de Proprietários";
} 