package core;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;

/**
 * Classe que contém todos os valores constantes utilizados na aplicação.
 * Inclui textos da interface, cores, dimensões e outros valores de configuração.
 * 
 * @author Lei-G
 * @version 1.0
 */

public class Constants {
    /**
     * Construtor privado para impedir a instanciação.
     * Esta classe deve ser utilizada apenas pelos seus valores constantes.
     */
    private Constants() {
        // Impedir instanciação
    }

    // ================ Constantes de Cadastro ================
    /** Constante para ordenação por ID */
    public static final int SORT_BY_ID = 0;
    /** Constante para ordenação por comprimento */
    public static final int SORT_BY_LENGTH = 1;
    /** Constante para ordenação por área */
    public static final int SORT_BY_AREA = 2;
    /** Constante para ordenação por proprietário */
    public static final int SORT_BY_OWNER = 3;
    /** Constante para ordenação por freguesia (subdivisão administrativa mais pequena) */
    public static final int SORT_BY_FREGUESIA = 4;
    /** Constante para ordenação por concelho (subdivisão administrativa intermédia) */
    public static final int SORT_BY_CONCELHO = 5;
    /** Constante para ordenação por distrito (subdivisão administrativa maior) */
    public static final int SORT_BY_DISTRICT = 6;
    /** Constante da área próxima para avaliação */
    public static final double NEAR_RADIUS = 200;

    /** Índice do campo ID no CSV */
    public static final int ID_INDEX = 0;
    /** Índice do campo comprimento no CSV */
    public static final int LENGTH_INDEX = 3;
    /** Índice do campo área no CSV */
    public static final int AREA_INDEX = 4;
    /** Índice do campo forma geométrica no CSV */
    public static final int SHAPE_INDEX = 5;
    /** Índice do campo proprietário no CSV */
    public static final int OWNER_INDEX = 6;
    /** Índice do campo Freguesia (subdivisão administrativa mais pequena) no CSV */
    public static final int FREGUESIA_INDEX = 7;
    /** Índice do campo Concelho (subdivisão administrativa intermédia) no CSV */
    public static final int CONCELHO_INDEX = 8;
    /** Índice do campo Distrito (subdivisão administrativa maior) no CSV */
    public static final int DISTRICT_INDEX = 9;

    /** Valor que indica localização não disponível no CSV */
    public static final String NA_VALUE = "NA";

    // ================ Constantes de Erro ================
    /** Mensagem de erro para valores nulos ou vazios */
    public static final String NULL_OR_EMPTY_ERROR = " não pode ser nulo ou vazio";
    /** Mensagem de erro para valores menores ou iguais a zero */
    public static final String ZERO_OR_NEGATIVE_ERROR = " deve ser maior que zero";
    /** Mensagem de erro para geometria inválida */
    public static final String INVALID_GEOMETRY_ERROR = " não é um MultiPolygon";
    /** Mensagem de erro para ficheiro vazio */
    public static final String EMPTY_FILE_ERROR = "Nenhum registo válido encontrado no ficheiro";
    /** Mensagem de erro para leitura do ficheiro */
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
    /** Mensagem de erro para forma geométrica nula */
    public static final String NULL_SHAPE_ERROR = "Forma geométrica não pode ser nula";
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
    /** Título exibido na janela principal da aplicação */
    public static final String WINDOW_TITLE = "Gestão de Propriedades";
    /** Largura da janela principal da aplicação em píxeis */
    public static final int WINDOW_WIDTH = 1600;
    /** Altura da janela principal da aplicação em píxeis */
    public static final int WINDOW_HEIGHT = 900;
    /** Tamanho da janela de visualização da forma geométrica em píxeis */
    public static final int SHAPE_WINDOW_SIZE = 600;

    // Botões
    /** Texto para o botão de procurar */
    public static final String BROWSE_BUTTON_TEXT = "Procurar";
    /** Texto para o botão de importar */
    public static final String IMPORT_BUTTON_TEXT = "Importar";
    /** Texto para o botão de mostrar mais */
    public static final String SHOW_MORE_BUTTON_TEXT = "Mais";
    /** Texto para o botão de mostrar forma geométrica */
    public static final String SHOW_SHAPE_BUTTON_TEXT = "Mostrar forma";
    /** Texto para o botão de área média de propriedades */
    public static final String AVERAGE_PROPERTY_AREA_BUTTON_TEXT = "Área Média de Propriedades";
    /** Texto para o botão de área média de proprietários */
    public static final String AVERAGE_OWNER_AREA_BUTTON_TEXT = "Área Média de Proprietários";
    /** Texto para o botão de grafo de propriedades */
    public static final String VIEW_PROPERTY_GRAPH_BUTTON_TEXT = "Grafo de Propriedades";
    /** Texto para o botão de grafo de proprietários */
    public static final String VIEW_OWNER_GRAPH_BUTTON_TEXT = "Grafo de Proprietários";
    /** Texto para o botão de visualizar grafo */
    public static final String VIEW_GRAPH_BUTTON_TEXT = "Visualizar Grafo";
    /** Texto para o botão de calcular média */
    public static final String AVERAGE_AREA_BUTTON_TEXT = "Calcular Média";
    /** Texto para o botão de sugestões de troca */
    public static final String PROPERTY_EXCHANGE_BUTTON_TEXT = "Sugestões de Troca";

    // Rótulos
    /** Rótulo para o campo de seleção de ficheiro */
    public static final String FILE_SELECTION_LABEL = "Selecione o ficheiro CSV:";
    /** Rótulo para o campo de distrito (maior subdivisão administrativa) */
    public static final String DISTRICT_LABEL = "Distrito:";
    /** Rótulo para o campo de município (subdivisão administrativa intermédia) */
    public static final String MUNICIPALITY_LABEL = "Município:";
    /** Rótulo para o campo de concelho (subdivisão administrativa mais pequena) */
    public static final String COUNTY_LABEL = "Concelho:";

    // Mensagens de erro e aviso
    /** Título para diálogos de erro */
    public static final String ERROR_TITLE = "Erro";
    /** Título para diálogos de aviso */
    public static final String WARNING_TITLE = "Aviso";
    /** Mensagem de erro quando o ficheiro selecionado não existe */
    public static final String FILE_NOT_FOUND_ERROR = "Ficheiro selecionado não existe";
    /** Mensagem de aviso quando nenhum ficheiro está selecionado */
    public static final String SELECT_FILE_WARNING = "Por favor, selecione um ficheiro CSV primeiro.";
    /** Mensagem de erro quando o cadastro é nulo */
    public static final String NULL_CADASTRO_ERROR = "Cadastro não pode ser nulo";
    /** Mensagem de erro quando a criação do grafo de propriedades falha */
    public static final String GRAPH_ERROR = "Erro ao criar grafo de propriedades";
    /** Mensagem de erro para cálculo de área média */
    public static final String AVERAGE_AREA_ERROR = "Erro ao calcular área média";

    // Formatação
    /** Formato HTML para exibir informações do cadastro */
    public static final String CADASTRO_INFO_FORMAT = "<html>Id: %s<br>Proprietário: %s<br>Área: %s<br>Comprimento: %s<br>Distrito: %s<br>Município: %s<br>Concelho: %s</html>";
    /** Título para a janela de forma geométrica */
    public static final String SHAPE_WINDOW_TITLE = "Forma - ";
    /** Formato para o resultado da área média */
    public static final String AVERAGE_AREA_RESULT_FORMAT = "Área média: %.2f";
    /** Formato para a representação em texto do grafo */
    public static final String GRAPH_STRING_FORMAT = "PropertyGraph{properties=[%s], adjacencies=[]}";
    /** Separador utilizado entre propriedades na representação em texto */
    public static final String PROPERTY_SEPARATOR = ", ";
    /** Separador utilizado entre sugestões na representação em texto */
    public static final String SUGGESTION_SEPARATOR = "-----------------------------";

    // Títulos de janelas
    /** Título para a janela do grafo de propriedades */
    public static final String PROPERTY_GRAPH_WINDOW_TITLE = "Visualização do Grafo de Propriedades";
    /** Título para a janela do grafo de proprietários */
    public static final String OWNER_GRAPH_WINDOW_TITLE = "Visualização do Grafo de Proprietários";
    /** Título para a janela de sugestões de troca */
    public static final String PROPERTY_EXCHANGE_WINDOW_TITLE = "Sugestões de Troca de Propriedades";
    /** Título da janela de cálculo de área média de propriedades */
    public static final String AVERAGE_PROPERTY_AREA_WINDOW_TITLE = "Cálculo de Área Média de Propriedades";
    /** Título da janela de cálculo de área média de proprietários */
    public static final String AVERAGE_OWNER_AREA_WINDOW_TITLE = "Cálculo de Área Média de Proprietários";

    // Ordenação
    /** Rótulos para os botões de ordenação */
    public static final String[] SORT_BUTTON_LABELS = {
        "Ordenar por ID",
        "Ordenar por Comprimento",
        "Ordenar por Área",
        "Ordenar por Proprietário",
        "Ordenar por Freguesia",
        "Ordenar por Concelho",
        "Ordenar por Distrito"
    };

    // Carregamento
    /** Número de cadastros a carregar de cada vez */
    public static final int DEFAULT_CADASTROS_LOAD = 5000;

    // Cores
    /** Cor primária para elementos da interface (Bistre) */
    public static final String PRIMARY_COLOR = "#352208";      // Bistre - para botões principais
    /** Cor secundária para elementos da interface (Ecru) */
    public static final String SECONDARY_COLOR = "#e1bb80";    // Ecru - para elementos de destaque
    /** Cor de fundo para cartões */
    public static final String BACKGROUND_COLOR = "#f9f1e5";   // Ecru-900 - para fundo geral
    /** Cor de fundo para cartões */
    public static final String CARD_BACKGROUND = "#FFFFFF";    // Branco - para cartões
    /** Cor para texto em elementos da interface (Bistre) */
    public static final String TEXT_COLOR = "#352208";         // Bistre - para texto principal
    /** Cor para a borda dos elementos da interface */
    public static final String BORDER_COLOR = "#7b6b43";       // Coyote - para bordas
    /** Cor para efeitos de hover (Bistre-600) */
    public static final String HOVER_COLOR = "#835514";        // Bistre-600 - para hover
    /** Cor para texto dos botões */
    public static final String BUTTON_TEXT_COLOR = "#FFFFFF";  // Branco - para texto dos botões
    /** Cor para elementos desativados (Coyote-800) */
    public static final String DISABLED_COLOR = "#d1c7ac";     // Coyote-800 - para elementos desativados
    /** Cor para cabeçalhos (Bistre) */
    public static final String HEADER_COLOR = "#352208";       // Bistre - para cabeçalhos
    /** Cor para elementos de destaque */
    public static final String ACCENT_COLOR = "#e1bb80";       // Ecru - para acentos e destaques
    /** Cor para rótulos secundários (Coyote) */
    public static final String LABEL_COLOR = "#7b6b43";        // Coyote - para rótulos secundários
    
    // Estilos
    /** Raio para cantos arredondados em píxeis */
    public static final int CORNER_RADIUS = 5;
    /** Espaçamento entre cartões em píxeis */
    public static final int PADDING = 8;
    /** Espaçamento entre cartões em píxeis */
    public static final int CARD_SPACING = 4;
    /** Preenchimento para cartões em píxeis */
    public static final int CARD_PADDING = 6;
    /** Preenchimento para campos de texto em píxeis */
    public static final int TEXT_FIELD_PADDING = 6;

    // ================ Constantes do PropertyGraphPanel ================
    /** Mensagem de erro para grafo nulo */
    public static final String NULL_GRAPH_ERROR = "Grafo de propriedades não pode ser nulo";
    /** Mensagem de erro para renderização */
    public static final String RENDER_ERROR = "Erro ao renderizar grafo de propriedades";
    /** Dimensão padrão do painel do grafo */
    public static final Dimension GRAPH_PANEL_SIZE = new Dimension(800, 600);
    /** Fator de escala para o grafo */
    public static final double GRAPH_SCALE_FACTOR = 0.8;
    /** Cor de preenchimento das propriedades (azul semi-transparente) */
    public static final Color PROPERTY_FILL = new Color(70, 130, 180, 150);
    /** Cor da borda das propriedades (azul) */
    public static final Color PROPERTY_BORDER = Color.BLUE;
    /** Cor das linhas de adjacência (laranja semi-transparente) */
    public static final Color ADJACENCY_LINE = new Color(255, 69, 0, 180);
    /** Espessura das linhas de adjacência */
    public static final float ADJACENCY_STROKE_WIDTH = 2.0f;

    // ================ Constantes de Zoom e Pan ================
    /** Fator mínimo de zoom */
    public static final double MIN_ZOOM = 0.1;
    /** Fator máximo de zoom */
    public static final double MAX_ZOOM = 100.0;
    /** Fator de incremento/decremento do zoom */
    public static final double ZOOM_STEP = 1.0;
    /** Cor do cursor de pan (cinza semi-transparente) */
    public static final Color PAN_CURSOR_COLOR = new Color(128, 128, 128, 128);
    /** Espessura da linha do cursor de pan */
    public static final float PAN_CURSOR_STROKE_WIDTH = 1.0f;

    // ================ Constantes do ShapePanel ================
    /** Mensagem de erro para geometria nula */
    public static final String NULL_GEOMETRY_ERROR = "Geometria não pode ser nula";
    /** Mensagem de erro para renderização */
    public static final String RENDER_GEOMETRY_ERROR = "Erro ao renderizar forma geométrica";
    /** Mensagem de erro para coordenadas inválidas */
    public static final String INVALID_COORDINATES_ERROR = "Coordenadas inválidas na geometria";
    /** Mensagem de erro para dimensões inválidas */
    public static final String INVALID_DIMENSIONS_ERROR = "Dimensões inválidas na geometria";
    /** Mensagem de erro para transformação */
    public static final String TRANSFORM_ERROR = "Erro ao calcular transformação: ";
    /** Mensagem de erro para geometria não suportada */
    public static final String UNSUPPORTED_GEOMETRY_ERROR = "Tipo de geometria não suportado: ";
    /** Mensagem de erro para desenho de geometria */
    public static final String DRAW_GEOMETRY_ERROR = "Erro ao desenhar geometria";
    /** Mensagem de erro para desenho de polígono */
    public static final String DRAW_POLYGON_ERROR = "Erro ao desenhar polígono";
    /** Mensagem de erro para conversão de geometria */
    public static final String CONVERT_GEOMETRY_ERROR = "Erro ao converter geometria para Path2D";
    /** Fator de escala para a forma */
    public static final double SHAPE_SCALE_FACTOR = 0.8;
    /** Cor de preenchimento do polígono (azul semi-transparente) */
    public static final Color POLYGON_FILL = new Color(70, 130, 180, 150);
    /** Cor da borda do polígono (azul) */
    public static final Color POLYGON_BORDER = Color.BLUE;
    /** Cor da borda dos buracos (vermelho) */
    public static final Color HOLE_BORDER = Color.RED;

    // ================ Constantes do Preço das Localizações ================

    /** Mapa de preços por metro quadrado para cada freguesia (subdivisão administrativa mais pequena) */
    public static final Map<String, Integer> FREGUESIA_PRICE = Map.ofEntries(
            Map.entry("arco da calheta", 3436),
            Map.entry("calheta", 3436),
            Map.entry("canhas", 2830),
            Map.entry("curral das freiras", 2517),
            Map.entry("câmara de lobos", 2517),
            Map.entry("caniço", 2269),
            Map.entry("são roque", 3542),
            Map.entry("madalena do mar", 2830),
            Map.entry("santo antónio", 3542),
            Map.entry("paul do mar", 3436),
            Map.entry("porto da cruz", 2343),
            Map.entry("faial", 1500),
            Map.entry("são martinho", 3696),
            Map.entry("tabua", 2698),
            Map.entry("santa maria maior", 3542),
            Map.entry("machico", 2343),
            Map.entry("estreito de câmara de lobos", 2517),
            Map.entry("sé", 3684),
            Map.entry("fajã da ovelha", 3436),
            Map.entry("prazeres", 3436),
            Map.entry("jardim da serra", 2517),
            Map.entry("água de pena", 2343),
            Map.entry("imaculado coração de maria", 3542),
            Map.entry("campanário", 2698),
            Map.entry("jardim do mar", 3436),
            Map.entry("santo antónio da serra", 2343),
            Map.entry("estreito da calheta", 3436),
            Map.entry("são pedro", 3542),
            Map.entry("caniçal", 2343),
            Map.entry("são gonçalo", 3542),
            Map.entry("santa luzia", 3542),
            Map.entry("quinta grande", 2517),
            Map.entry("monte", 3542),
            Map.entry("ponta do pargo", 3436),
            Map.entry("ponta do sol", 2830)
    );

    /** Mapa de preços por metro quadrado para cada concelho (subdivisão administrativa intermédia) */
    public static final Map<String, Integer> CONCELHO_PRICE = Map.ofEntries(
            Map.entry("funchal", 3542),
            Map.entry("calheta", 3436),
            Map.entry("ponta do sol", 2830),
            Map.entry("ribeira brava", 2698),
            Map.entry("câmara de lobos", 2517),
            Map.entry("santa cruz", 2269),
            Map.entry("machico", 2343),
            Map.entry("são vicente", 2034),
            Map.entry("santana", 1500)
    );

    /** Mapa de preços por metro quadrado para cada distrito (maior subdivisão administrativa) */
    public static final Map<String, Integer> DISTRICT_PRICE = Map.of(
            "ilha da madeira", 3251
    );

    /** Mapa de preços por metro quadrado para cada país */
    public static final Map<String, Integer> COUNTRY_PRICE = Map.of(
            "portugal", 2827
    );
} 