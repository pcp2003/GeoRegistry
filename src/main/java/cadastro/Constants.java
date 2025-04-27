package cadastro;

/**
 * Classe que contém todas as constantes utilizadas no sistema de cadastro.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class Constants {
    // ================ Constantes de Cadastro ================
    /** Constante para ordenação por ID */
    public static final int SORT_BY_ID = 0;
    /** Constante para ordenação por comprimento */
    public static final int SORT_BY_LENGTH = 1;
    /** Constante para ordenação por área */
    public static final int SORT_BY_AREA = 2;
    /** Constante para ordenação por proprietário */
    public static final int SORT_BY_OWNER = 3;
    /** Constante para ordenação por distrito */
    public static final int SORT_BY_DISTRICT = 4;
    /** Constante para ordenação por município */
    public static final int SORT_BY_MUNICIPALITY = 5;
    /** Constante para ordenação por concelho */
    public static final int SORT_BY_COUNTY = 6;

    /** Índice do campo ID no CSV */
    public static final int ID_INDEX = 0;
    /** Índice do campo comprimento no CSV */
    public static final int LENGTH_INDEX = 3;
    /** Índice do campo área no CSV */
    public static final int AREA_INDEX = 4;
    /** Índice do campo shape no CSV */
    public static final int SHAPE_INDEX = 5;
    /** Índice do campo proprietário no CSV */
    public static final int OWNER_INDEX = 6;
    /** Índice do campo Freguesia no CSV */
    public static final int DISTRICT_INDEX = 7;
    /** Índice do campo Municipio no CSV */
    public static final int MUNICIPALITY_INDEX = 8;
    /** Índice do campo Concelho no CSV */
    public static final int COUNTY_INDEX = 9;

    /** Valor que indica localização não disponível no CSV */
    public static final String NA_VALUE = "NA";

    // ================ Constantes de Erro ================
    /** Mensagem de erro para valores nulos ou vazios */
    public static final String NULL_OR_EMPTY_ERROR = " não pode ser nulo ou vazio";
    /** Mensagem de erro para valores menores ou iguais a zero */
    public static final String ZERO_OR_NEGATIVE_ERROR = " deve ser maior que zero";
    /** Mensagem de erro para geometria inválida */
    public static final String INVALID_GEOMETRY_ERROR = " não é um MultiPolygon";
    /** Mensagem de erro para arquivo vazio */
    public static final String EMPTY_FILE_ERROR = "Nenhum registro válido encontrado no arquivo";
    /** Mensagem de erro para leitura do arquivo */
    public static final String FILE_READ_ERROR = "Erro ao ler o ficheiro CSV";
    /** Mensagem de erro para conversão de valores */
    public static final String NUMBER_CONVERSION_ERROR = "Erro ao converter valores numéricos";
    /** Mensagem de erro para lista vazia */
    public static final String EMPTY_LIST_ERROR = "Lista de cadastros não pode ser nula ou vazia";
    /** Mensagem de erro para cadastros nulos */
    public static final String NULL_CADASTROS_ERROR = "Lista de cadastros não pode ser nula";
    /** Mensagem de erro para cadastros vazios */
    public static final String EMPTY_CADASTROS_ERROR = "Lista de cadastros não pode estar vazia";
    /** Mensagem de erro para elementos nulos */
    public static final String NULL_ELEMENTS_ERROR = "Lista de cadastros não pode conter elementos nulos";
    /** Mensagem de erro para propriedades nulas */
    public static final String NULL_PROPERTY_ERROR = "Propriedades não podem ser nulas";
    /** Mensagem de erro para shape nulo */
    public static final String NULL_SHAPE_ERROR = "Shape não pode ser nulo";
    /** Mensagem de erro para topologia */
    public static final String TOPOLOGY_ERROR = "Erro durante a análise topológica: ";
    /** Mensagem de erro para construção do grafo */
    public static final String GRAPH_BUILD_ERROR = "Erro durante a construção do grafo: ";
    /** Mensagem de erro para adjacência */
    public static final String ADJACENCY_ERROR = "Erro durante a análise de adjacência: ";
    /** Mensagem de erro para proprietário inválido */
    public static final String INVALID_OWNER_ERROR = "ID do proprietário deve ser maior que zero";

    // ================ Constantes da Interface Gráfica ================
    // Configuração da janela
    public static final String WINDOW_TITLE = "Gestão de Propriedades";
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;
    public static final int SHAPE_WINDOW_SIZE = 600;

    // Botões
    public static final String BROWSE_BUTTON_TEXT = "Procurar";
    public static final String IMPORT_BUTTON_TEXT = "Importar";
    public static final String SHOW_MORE_BUTTON_TEXT = "Mais";
    public static final String SHOW_SHAPE_BUTTON_TEXT = "Mostrar shape";
    public static final String AVERAGE_PROPERTY_AREA_BUTTON_TEXT = "Área Média de Propriedades";
    public static final String AVERAGE_OWNER_AREA_BUTTON_TEXT = "Área Média de Proprietários";
    public static final String VIEW_PROPERTY_GRAPH_BUTTON_TEXT = "Grafo de Propriedades";
    public static final String VIEW_OWNER_GRAPH_BUTTON_TEXT = "Grafo de Proprietários";
    public static final String VIEW_GRAPH_BUTTON_TEXT = "Visualizar Grafo";
    public static final String AVERAGE_AREA_BUTTON_TEXT = "Calcular Média";

    // Rótulos
    public static final String FILE_SELECTION_LABEL = "Selecione o arquivo CSV:";
    public static final String DISTRICT_LABEL = "Distrito:";
    public static final String MUNICIPALITY_LABEL = "Município:";
    public static final String COUNTY_LABEL = "Concelho:";

    // Mensagens de erro e aviso
    public static final String ERROR_TITLE = "Erro";
    public static final String WARNING_TITLE = "Aviso";
    public static final String FILE_NOT_FOUND_ERROR = "Arquivo selecionado não existe";
    public static final String SELECT_FILE_WARNING = "Por favor, selecione um arquivo CSV primeiro.";
    public static final String NULL_CADASTRO_ERROR = "Cadastro não pode ser nulo";
    public static final String GRAPH_ERROR = "Erro ao criar grafo de propriedades";
    public static final String AVERAGE_AREA_ERROR = "Erro ao calcular área média";

    // Formatação
    public static final String CADASTRO_INFO_FORMAT = "<html>Id: %s<br>Proprietário: %s<br>Área: %s<br>Comprimento: %s<br>Distrito: %s<br>Município: %s<br>Concelho: %s</html>";
    public static final String SHAPE_WINDOW_TITLE = "Shape - ";
    public static final String AVERAGE_AREA_RESULT_FORMAT = "Área média: %.2f";
    public static final String GRAPH_STRING_FORMAT = "PropertyGraph{properties=[%s], adjacencies=[]}";
    public static final String PROPERTY_SEPARATOR = ", ";

    // Títulos de janelas
    public static final String GRAPH_WINDOW_TITLE = "Visualização do Grafo de Propriedades";
    public static final String AVERAGE_AREA_WINDOW_TITLE = "Cálculo de Área Média";
    public static final String AVERAGE_PROPERTY_AREA_WINDOW_TITLE = "Cálculo de Área Média de Propriedades";
    public static final String AVERAGE_OWNER_AREA_WINDOW_TITLE = "Cálculo de Área Média de Proprietários";
    public static final String PROPERTY_GRAPH_WINDOW_TITLE = "Visualização do Grafo de Propriedades";
    public static final String OWNER_GRAPH_WINDOW_TITLE = "Visualização do Grafo de Proprietários";

    // Ordenação
    public static final String[] SORT_BUTTON_LABELS = {
        "Sort by ID",
        "Sort by Length",
        "Sort by Area",
        "Sort by Owner",
        "Sort by District",
        "Sort by Municipality",
        "Sort by County"
    };

    // Carregamento
    public static final int DEFAULT_CADASTROS_LOAD = 5000;

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
} 