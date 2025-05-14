package service.exchange;

import model.Cadastro;

/**
 * Classe que representa uma sugestão de troca de propriedades entre dois proprietários.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class PropertyExchange {
    private final Cadastro property1;
    private final Cadastro property2;
    private final double areaDifference;
    private final double feasibilityScore;
    private final double averageAreaImprovement;

    /**
     * Constrói uma sugestão de troca de propriedades.
     * 
     * @param property1 Primeira propriedade envolvida na troca
     * @param property2 Segunda propriedade envolvida na troca
     * @param areaDifference Diferença de área entre as propriedades
     * @param feasibilityScore Pontuação de viabilidade da troca (0-1)
     * @param averageAreaImprovement Melhoria na área média proporcionada pela troca
     */
    public PropertyExchange(Cadastro property1, Cadastro property2, 
                          double areaDifference, double feasibilityScore,
                          double averageAreaImprovement) {
        this.property1 = property1;
        this.property2 = property2;
        this.areaDifference = areaDifference;
        this.feasibilityScore = feasibilityScore;
        this.averageAreaImprovement = averageAreaImprovement;
    }

    public Cadastro getProperty1() {
        return property1;
    }

    public Cadastro getProperty2() {
        return property2;
    }

    public double getAreaDifference() {
        return areaDifference;
    }

    public double getFeasibilityScore() {
        return feasibilityScore;
    }

    public double getAverageAreaImprovement() {
        return averageAreaImprovement;
    }

    @Override
    public String toString() {
        return String.format("Propriedades a ser trocadas: %d (Proprietário %d) e %d (Proprietário %d)\n" +
                           "Diferença de área: %.2f\n" +
                           "Viabilidade: %.2f\n" +
                           "Melhoria na área média: %.2f",
                           property1.getId(), property1.getOwner(),
                           property2.getId(), property2.getOwner(),
                           areaDifference, feasibilityScore,
                           averageAreaImprovement);
    }
} 