package service.exchange;

import core.Constants;
import model.Cadastro;

/**
 * Representa uma sugestão de troca de propriedades entre dois proprietários.
 * Contém informações sobre as propriedades envolvidas e métricas para a troca.
 * 
 * @author Lei-G
 * @version 1.0
 */
public class PropertyExchange {
    private final Cadastro property1;
    private final Cadastro property2;
    private final double priceDifference;
    private final double feasibilityScore;
    private final double averageAreaImprovement;

    /**
     * Cria uma sugestão de troca de propriedades.
     * 
     * @param property1 Primeira propriedade envolvida na troca
     * @param property2 Segunda propriedade envolvida na troca
     * @param priceDifference Diferença de preço entre as propriedades
     * @param feasibilityScore Pontuação que indica a viabilidade da troca
     * @param averageAreaImprovement Melhoria na área média após a troca
     */
    public PropertyExchange(Cadastro property1, Cadastro property2, 
                          double priceDifference, double feasibilityScore,
                          double averageAreaImprovement) {
        this.property1 = property1;
        this.property2 = property2;
        this.priceDifference = priceDifference;
        this.feasibilityScore = feasibilityScore;
        this.averageAreaImprovement = averageAreaImprovement;
    }

    /**
     * Obtém a primeira propriedade envolvida na troca.
     * 
     * @return A primeira propriedade
     */
    public Cadastro getProperty1() {
        return property1;
    }

    /**
     * Obtém a segunda propriedade envolvida na troca.
     * 
     * @return A segunda propriedade
     */
    public Cadastro getProperty2() {
        return property2;
    }

    /**
     * Obtém a diferença de preço entre as propriedades.
     * 
     * @return A diferença de preço em euros
     */
    public double getPriceDifference() {
        return priceDifference;
    }

    /**
     * Obtém a pontuação de viabilidade da troca.
     * Pontuações mais altas indicam trocas mais viáveis.
     * 
     * @return A pontuação de viabilidade
     */
    public double getFeasibilityScore() {
        return feasibilityScore;
    }

    /**
     * Obtém a melhoria na área média após a troca.
     * 
     * @return A melhoria na área média
     */
    public double getAverageAreaImprovement() {
        return averageAreaImprovement;
    }

    @Override
    public String toString() {
        return String.format("Propriedades a ser trocadas: %d (Proprietário %d) e %d (Proprietário %d)\n" +
                           "Diferença de preço: %.2f€ (%.2f€ | %.2f€)\n" +
                           "Viabilidade: %.2f\n" +
                           "Melhoria na área média: %.2f\n",
                           property1.getId(), property1.getOwner(),
                           property2.getId(), property2.getOwner(),
                           priceDifference, property1.getPrice(),property2.getPrice(),feasibilityScore,
                           averageAreaImprovement);
    }
} 