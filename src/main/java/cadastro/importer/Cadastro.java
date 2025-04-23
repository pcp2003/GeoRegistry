package cadastro.importer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Classe que representa um cadastro de propriedade, contendo informações como
 * identificador, comprimento, área, forma geométrica, proprietário e
 * localização.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class Cadastro {
    private final int id;
    private final double length;
    private final double area;
    private final MultiPolygon shape;
    private final int owner;
    private final List<String> location;

    /**
     * Constrói um objeto Cadastro a partir de um registro CSV.
     * 
     * @param record O registro CSV contendo os dados do cadastro
     * @throws ParseException           Se houver erro ao processar a geometria WKT
     * @throws IllegalArgumentException Se houver erro ao converter valores
     *                                  numéricos
     */
    public Cadastro(CSVRecord record) throws ParseException {
        try {
            this.id = handleId(record.get(CadastroConstants.ID_INDEX));
            this.length = handleLength(record.get(CadastroConstants.LENGTH_INDEX));
            this.area = handleArea(record.get(CadastroConstants.AREA_INDEX));
            this.shape = handleShape(record.get(CadastroConstants.SHAPE_INDEX));
            this.owner = handleOwner(record.get(CadastroConstants.OWNER_INDEX));
            this.location = handleLocation(record);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(CadastroConstants.NUMBER_CONVERSION_ERROR, e);
        }
    }

    /**
     * Verifica o campo id da propriedade.
     * 
     * @param record A string contendo a id
     * @return O ID da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se o id for null ou se for menor ou igual
     *                                  a zero
     */
    private int handleId(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new IllegalArgumentException("ID" + CadastroConstants.NULL_OR_EMPTY_ERROR);
        }
        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            throw new IllegalArgumentException("ID" + CadastroConstants.ZERO_OR_NEGATIVE_ERROR);
        }
        return id;
    }

    /**
     * Verifica o campo length da propriedade.
     * 
     * @param record A string contendo a length
     * @return A Length da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se o comprimento for null ou se for menor ou
     *                                  igual a zero
     */
    private double handleLength(String lengthStr) {
        if (lengthStr == null || lengthStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Comprimento" + CadastroConstants.NULL_OR_EMPTY_ERROR);
        }
        double length = Double.parseDouble(lengthStr);
        if (length <= 0) {
            throw new IllegalArgumentException("Comprimento" + CadastroConstants.ZERO_OR_NEGATIVE_ERROR);
        }
        return length;
    }

    /**
     * Verifica o campo area da propriedade.
     * 
     * @param record A string contendo a area
     * @return A Área da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se a área for null ou se for menor ou igual
     *                                  a zero
     */
    private double handleArea(String areaStr) {
        if (areaStr == null || areaStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Área" + CadastroConstants.NULL_OR_EMPTY_ERROR);
        }
        double area = Double.parseDouble(areaStr);
        if (area <= 0) {
            throw new IllegalArgumentException("Área" + CadastroConstants.ZERO_OR_NEGATIVE_ERROR);
        }
        return area;
    }

    /**
     * Processa a string WKT (Well-Known Text) para criar um objeto MultiPolygon.
     * 
     * @param record A string WKT contendo a geometria
     * @return O objeto MultiPolygon correspondente
     * @throws ParseException           Se houver erro ao processar a geometria
     * @throws IllegalArgumentException Se a geometria não for um MultiPolygon
     */
    private MultiPolygon handleShape(String record) throws ParseException {
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(record);
            if (geometry instanceof MultiPolygon multiPolygon) {
                return multiPolygon;
            } else {
                throw new IllegalArgumentException(record + CadastroConstants.INVALID_GEOMETRY_ERROR);
            }
        } catch (ParseException e) {
            throw e;
        }
    }

    /**
     * Verifica o campo owner da propriedade.
     * 
     * @param record A string contendo a owner
     * @return O Owner da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se o owner for null ou se for menor ou igual
     *                                  a zero
     */
    private int handleOwner(String ownerStr) {
        if (ownerStr == null || ownerStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner" + CadastroConstants.NULL_OR_EMPTY_ERROR);
        }
        int owner = Integer.parseInt(ownerStr);
        if (owner <= 0) {
            throw new IllegalArgumentException("Owner" + CadastroConstants.ZERO_OR_NEGATIVE_ERROR);
        }
        return owner;
    }

    /**
     * Processa as localizações do registro CSV, removendo valores "NA".
     * 
     * @param record O registro CSV contendo as localizações
     * @return Lista de localizações processadas
     */
    private List<String> handleLocation(CSVRecord record) {
        // Obter as localizações usando os índices específicos
        String freguesia = record.get(CadastroConstants.DISTRICT_INDEX);
        String municipio = record.get(CadastroConstants.MUNICIPALITY_INDEX);
        String concelho = record.get(CadastroConstants.COUNTY_INDEX);
        
        // Verificar se alguma localização é nula
        if (freguesia.equals(CadastroConstants.NA_VALUE) || municipio.equals(CadastroConstants.NA_VALUE) || concelho.equals(CadastroConstants.NA_VALUE)) {
            throw new IllegalArgumentException("Localizações não podem ser nulas para o registro: " + id);
        }
        
        List<String> locations = new ArrayList<>();

        // Adicionar apenas valores não-NA
        locations.add(freguesia);
        locations.add(municipio);
        locations.add(concelho);
        
        return locations;
    }

    /**
     * Lê um arquivo CSV e retorna uma lista de cadastros.
     * 
     * @param path O caminho do arquivo CSV
     * @return Lista de cadastros lidos do arquivo
     * @throws Exception Se houver erro ao ler ou processar o arquivo
     */
    public static List<Cadastro> getCadastros(String path) throws Exception {
        List<Cadastro> cadastros = new ArrayList<>();

        try (Reader in = new FileReader(path);
                CSVParser parser = CSVFormat.newFormat(';').parse(in)) {

            List<CSVRecord> records = parser.getRecords();
            int totalRecords = records.size();
            int skippedRecords = 0;

            for (int i = 1; i < totalRecords; i++) {
                try {
                    Cadastro cadastro = new Cadastro(records.get(i));
                    cadastros.add(cadastro);
                } catch (IllegalArgumentException | ParseException e) {
                    skippedRecords++;
                }
            }

            if (cadastros.isEmpty()) {
                throw new IllegalStateException(CadastroConstants.EMPTY_FILE_ERROR);
            }

            System.out.println("Total de cadastros: " + cadastros.size());  
            System.out.println("Total de registros ignorados: " + skippedRecords);
            return cadastros;
        } catch (IOException e) {
            throw new Exception(CadastroConstants.FILE_READ_ERROR, e);
        }
    }

    /**
     * Ordena uma lista de cadastros de acordo com o critério especificado.
     * 
     * @param cadastros A lista de cadastros a ser ordenada
     * @param sortType  O tipo de ordenação (ID, comprimento, área ou proprietário)
     * @return A lista de cadastros ordenada
     * @throws Exception Se o tipo de ordenação for inválido
     */
    public static List<Cadastro> sortCadastros(List<Cadastro> cadastros, int sortType) throws Exception {
        switch (sortType) {
            case CadastroConstants.SORT_BY_ID:
                cadastros.sort(Comparator.comparingInt(Cadastro::getId));
                break;
            case CadastroConstants.SORT_BY_LENGTH:
                cadastros.sort(Comparator.comparingDouble(Cadastro::getLength));
                break;
            case CadastroConstants.SORT_BY_AREA:
                cadastros.sort(Comparator.comparingDouble(Cadastro::getArea));
                break;
            case CadastroConstants.SORT_BY_OWNER:
                cadastros.sort(Comparator.comparingInt(Cadastro::getOwner));
                break;
            case CadastroConstants.SORT_BY_DISTRICT:
                cadastros.sort((c1, c2) -> {
                    String district1 = c1.getLocation().get(0);
                    String district2 = c2.getLocation().get(0);
                    return district1.compareToIgnoreCase(district2);
                });
                break;
            case CadastroConstants.SORT_BY_MUNICIPALITY:
                cadastros.sort((c1, c2) -> {
                    String municipality1 = c1.getLocation().get(1);
                    String municipality2 = c2.getLocation().get(1);
                    return municipality1.compareToIgnoreCase(municipality2);
                });
                break;
            case CadastroConstants.SORT_BY_COUNTY:
                cadastros.sort((c1, c2) -> {
                    String county1 = c1.getLocation().get(2);
                    String county2 = c2.getLocation().get(2);
                    return county1.compareToIgnoreCase(county2);
                });
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }
        return cadastros;
    }
    
    /**
     * Calcula a área média das propriedades para uma área geográfica/administrativa específica.
     * 
     * @param cadastros Lista de cadastros
     * @param freguesia Nome da freguesia (pode ser null)
     * @param municipio Nome do município (pode ser null)
     * @param concelho Nome do concelho (pode ser null)
     * @return Área média das propriedades na área especificada
     * @throws IllegalArgumentException Se não houver propriedades na área ou se nenhum parâmetro for fornecido
     */
    public static double calculateAverageArea(List<Cadastro> cadastros, String freguesia, String municipio, String concelho) {
        if (cadastros == null || cadastros.isEmpty()) {
            throw new IllegalArgumentException("Lista de cadastros não pode ser nula ou vazia");
        }

        if (freguesia == null && municipio == null && concelho == null) {
            throw new IllegalArgumentException("Pelo menos um parâmetro de localização deve ser fornecido");
        }

        // Filtrar cadastros pela área especificada
        List<Cadastro> filteredCadastros = cadastros.stream()
            .filter(cadastro -> {
                List<String> locations = cadastro.getLocation();
                if (locations.size() < 3) return false;
                
                boolean matchesFreguesia = freguesia == null || locations.get(0).equals(freguesia);
                boolean matchesMunicipio = municipio == null || locations.get(1).equals(municipio);
                boolean matchesConcelho = concelho == null || locations.get(2).equals(concelho);
                
                return matchesFreguesia && matchesMunicipio && matchesConcelho;
            })
            .toList();

        if (filteredCadastros.isEmpty()) {
            StringBuilder areaInfo = new StringBuilder("Não há propriedades na área especificada: ");
            if (freguesia != null) areaInfo.append("Freguesia=").append(freguesia).append(" ");
            if (municipio != null) areaInfo.append("Município=").append(municipio).append(" ");
            if (concelho != null) areaInfo.append("Concelho=").append(concelho);
            throw new IllegalArgumentException(areaInfo.toString());
        }

        // Calcular a área média
        double totalArea = filteredCadastros.stream()
            .mapToDouble(Cadastro::getArea)
            .sum();

        return totalArea / filteredCadastros.size();
    }

    /**
     * Retorna uma representação em string do cadastro.
     * 
     * @return String contendo os dados do cadastro
     */
    @Override
    public String toString() {
        return "Cadastro{" +
                "id=" + id +
                ", length=" + length +
                ", area=" + area +
                ", shape=" + shape +
                ", owner=" + owner +
                ", location=" + location +
                '}';
    }

    /**
     * Retorna o ID do cadastro.
     * 
     * @return O ID do cadastro
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o comprimento do cadastro.
     * 
     * @return O comprimento do cadastro
     */
    public double getLength() {
        return length;
    }

    /**
     * Retorna a área do cadastro.
     * 
     * @return A área do cadastro
     */
    public double getArea() {
        return area;
    }

    /**
     * Retorna a forma geométrica do cadastro.
     * 
     * @return O objeto MultiPolygon representando a forma
     */
    public MultiPolygon getShape() {
        return shape;
    }

    /**
     * Retorna o ID do proprietário do cadastro.
     * 
     * @return O ID do proprietário
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Retorna a lista de localizações do cadastro.
     * 
     * @return Lista de localizações
     */
    public List<String> getLocation() {
        return location;
    }
}
