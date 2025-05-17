package model;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.locationtech.jts.awt.PointShapeFactory;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import core.Constants;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
 * Representa um cadastro de propriedade no sistema.
 * Contém informações sobre a localização, geometria e proprietário da propriedade.
 * 
 * @author Lei-G
 * @version 1.0
 */
public class Cadastro {
    private final int id;
    private final double length;
    private final double area;
    private final MultiPolygon shape;
    private final int owner;
    private final Location location;
    private int propriedadesNear;

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
            this.id = handleId(record.get(Constants.ID_INDEX));
            this.length = handleLength(record.get(Constants.LENGTH_INDEX));
            this.area = handleArea(record.get(Constants.AREA_INDEX));
            this.shape = handleShape(record.get(Constants.SHAPE_INDEX));
            this.owner = handleOwner(record.get(Constants.OWNER_INDEX));
            this.location = handleLocation(record);
            this.propriedadesNear = 0;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Constants.NUMBER_CONVERSION_ERROR, e);
        }
    }

    /**
     * Verifica o campo id da propriedade.
     * 
     * @param idStr A string contendo a id
     * @return O ID da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se o id for null ou se for menor ou igual
     *                                  a zero
     */
    private int handleId(String idStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            throw new IllegalArgumentException("ID" + Constants.NULL_OR_EMPTY_ERROR);
        }
        int id = Integer.parseInt(idStr);
        if (id <= 0) {
            throw new IllegalArgumentException("ID" + Constants.ZERO_OR_NEGATIVE_ERROR);
        }
        return id;
    }

    /**
     * Verifica o campo length da propriedade.
     * 
     * @param lengthStr A string contendo a length
     * @return A Length da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se o comprimento for null ou se for menor ou
     *                                  igual a zero
     */
    private double handleLength(String lengthStr) {
        if (lengthStr == null || lengthStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Comprimento" + Constants.NULL_OR_EMPTY_ERROR);
        }
        double length = Double.parseDouble(lengthStr);
        if (length <= 0) {
            throw new IllegalArgumentException("Comprimento" + Constants.ZERO_OR_NEGATIVE_ERROR);
        }
        return length;
    }

    /**
     * Verifica o campo area da propriedade.
     * 
     * @param areaStr A string contendo a area
     * @return A Área da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se a área for null ou se for menor ou igual
     *                                  a zero
     */
    private double handleArea(String areaStr) {
        if (areaStr == null || areaStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Área" + Constants.NULL_OR_EMPTY_ERROR);
        }
        double area = Double.parseDouble(areaStr);
        if (area <= 0) {
            throw new IllegalArgumentException("Área" + Constants.ZERO_OR_NEGATIVE_ERROR);
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
                throw new IllegalArgumentException(record + Constants.INVALID_GEOMETRY_ERROR);
            }
        } catch (ParseException e) {
            throw e;
        }
    }

    /**
     * Verifica o campo owner da propriedade.
     * 
     * @param ownerStr A string contendo a owner
     * @return O Owner da propriedade do Cadastro correspondente
     * @throws IllegalArgumentException Se o owner for null ou se for menor ou igual
     *                                  a zero
     */
    private int handleOwner(String ownerStr) {
        if (ownerStr == null || ownerStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner" + Constants.NULL_OR_EMPTY_ERROR);
        }
        int owner = Integer.parseInt(ownerStr);
        if (owner <= 0) {
            throw new IllegalArgumentException("Owner" + Constants.ZERO_OR_NEGATIVE_ERROR);
        }
        return owner;
    }

    /**
     * Processa as localizações do registro CSV, removendo valores "NA".
     * 
     * @param record O registro CSV contendo as localizações
     * @return Lista de localizações processadas
     */
    private Location handleLocation(CSVRecord record) {
        // Obter as localizações usando os índices específicos
        String freguesia = record.get(Constants.FREGUESIA_INDEX);
        String municipio = record.get(Constants.CONCELHO_INDEX);
        String concelho = record.get(Constants.DISTRICT_INDEX);
        
        // Verificar se alguma localização é nula
        if (freguesia.equals(Constants.NA_VALUE) || municipio.equals(Constants.NA_VALUE) || concelho.equals(Constants.NA_VALUE)) {
            throw new IllegalArgumentException("Localizações não podem ser nulas para o registro: " + id);
        }
        
        return new Location(freguesia,municipio,concelho);
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

            HashMap<Location, Integer> locationCount = new HashMap<>();

            for (int i = 1; i < totalRecords; i++) {
                try {
                    Cadastro cadastro = new Cadastro(records.get(i));
                    cadastros.add(cadastro);
                    Location loc = cadastro.getLocation();
                    locationCount.put(loc, locationCount.getOrDefault(loc, 0) + 1);
                } catch (IllegalArgumentException | ParseException e) {
                    skippedRecords++;
                }
            }

            if (cadastros.isEmpty()) {
                throw new IllegalStateException(Constants.EMPTY_FILE_ERROR);
            }

            for(Cadastro c : cadastros){
                c.setPropretiesNear(cadastros);
            }


            System.out.println("Total de cadastros: " + cadastros.size());  
            System.out.println("Total de registros ignorados: " + skippedRecords);

            // Mostra a contagem por localização
            System.out.println("Contagem por localização:");
            for (Map.Entry<Location, Integer> entry : locationCount.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue() + " (Preço: " + entry.getKey().getPrice() + " €/m²)");
            }


            return cadastros;
        } catch (IOException e) {
            throw new Exception(Constants.FILE_READ_ERROR, e);
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
            case Constants.SORT_BY_ID:
                cadastros.sort(Comparator.comparingInt(Cadastro::getId));
                break;
            case Constants.SORT_BY_LENGTH:
                cadastros.sort(Comparator.comparingDouble(Cadastro::getLength));
                break;
            case Constants.SORT_BY_AREA:
                cadastros.sort(Comparator.comparingDouble(Cadastro::getArea));
                break;
            case Constants.SORT_BY_OWNER:
                cadastros.sort(Comparator.comparingInt(Cadastro::getOwner));
                break;
            case Constants.SORT_BY_FREGUESIA:
                cadastros.sort((c1, c2) -> {
                    String district1 = c1.getLocation().freguesia();
                    String district2 = c2.getLocation().freguesia();
                    return district1.compareToIgnoreCase(district2);
                });
                break;
            case Constants.SORT_BY_CONCELHO:
                cadastros.sort((c1, c2) -> {
                    String municipality1 = c1.getLocation().concelho();
                    String municipality2 = c2.getLocation().concelho();
                    return municipality1.compareToIgnoreCase(municipality2);
                });
                break;
            case Constants.SORT_BY_DISTRICT:
                cadastros.sort((c1, c2) -> {
                    String county1 = c1.getLocation().distrito();
                    String county2 = c2.getLocation().distrito();
                    return county1.compareToIgnoreCase(county2);
                });
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }
        return cadastros;
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
    public Location getLocation() {
        return location;
    }

    /**
     * Calcula o preço total da propriedade com base na sua localização e propriedades circundantes.
     * 
     * @return O preço total da propriedade
     */
    public double getPrice() {
        double basePrice = location.getPrice(); // preço base €/m²
        double multiplier = 1.0;

        if (propriedadesNear > 20) {
            multiplier = 1.3; // zona densa
        } else if (propriedadesNear > 10) {
            multiplier = 1.15; // zona moderadamente densa
        } else if (propriedadesNear < 5) {
            multiplier = 0.85; // zona pouco povoada
        }

        return area * basePrice * multiplier;
    }

    /**
     * Obtém o número de propriedades dentro do raio definido desta propriedade.
     * 
     * @return Número de propriedades próximas
     */
    public int getPropretiesNear() {
        return propriedadesNear;
    }

    private void setPropretiesNear(List<Cadastro> cadastros) {
        if (shape != null && cadastros != null){
            Point center = shape.getInteriorPoint();

            // Cria um buffer circular à volta do ponto central com o raio fornecido
            Geometry area = center.buffer(Constants.NEAR_RADIUS); // 'radius' deve estar na mesma unidade que os pontos (graus/metros)

            int count = 0;
            for (Cadastro cadastro : cadastros) {
                Geometry propertyLocation = cadastro.getShape(); // supondo que existe este método
                if (propertyLocation != null && area.contains(propertyLocation)) {
                    count++;
                }
            }

            this.propriedadesNear = count;
        }
    }
}
