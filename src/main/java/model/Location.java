package model;

import core.Constants;

/**
 * Record que representa uma localização com freguesia, concelho e distrito.
 * 
 * @param freguesia A freguesia (subdivisão administrativa mais pequena) da localização
 * @param concelho O concelho (subdivisão administrativa intermédia) da localização
 * @param distrito O distrito (subdivisão administrativa maior) da localização
 * 
 * @author Lei-G
 * @version 1.0
 */
public record Location(String freguesia, String concelho, String distrito) {

    /**
     * Obtém o preço por metro quadrado para esta localização.
     * O preço é determinado pela hierarquia da localização (freguesia > concelho > distrito > país).
     * 
     * @return O preço por metro quadrado em euros
     */
    public int getPrice() {
        if (Constants.FREGUESIA_PRICE.containsKey(freguesia.toLowerCase())) {
            return Constants.FREGUESIA_PRICE.get(freguesia.toLowerCase());
        }

        if (Constants.CONCELHO_PRICE.containsKey(concelho.toLowerCase())) {
            return Constants.CONCELHO_PRICE.get(concelho.toLowerCase());
        }

        if (Constants.DISTRICT_PRICE.containsKey(distrito.toLowerCase())) {
            return Constants.DISTRICT_PRICE.get(distrito.toLowerCase());
        }

        return Constants.COUNTRY_PRICE.get("portugal");
    }

    @Override
    public String toString() {
        return freguesia + ", " + concelho + ", " + distrito;
    }
}
