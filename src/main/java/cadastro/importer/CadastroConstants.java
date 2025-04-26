package cadastro.importer;

/**
 * Classe que contém constantes e valores estáticos utilizados na classe Cadastro.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class CadastroConstants {
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
} 