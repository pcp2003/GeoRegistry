package service.exchange;

import model.Cadastro;
import service.OwnerGraph;
import service.PropertyGraph;

import java.util.*;

/**
 * Serviço responsável por gerar sugestões de troca de propriedades entre proprietários,
 * considerando a maximização da área média e a viabilidade das trocas.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class PropertyExchangeService {
    private final PropertyGraph propertyGraph;
    private final List<Cadastro> cadastros;

    /**
     * Constrói o serviço de troca de propriedades.
     * 
     * @param propertyGraph Grafo de propriedades
     * @param cadastros Lista de cadastros
     */
    public PropertyExchangeService(OwnerGraph ownerGraph, PropertyGraph propertyGraph, List<Cadastro> cadastros) {
        this.propertyGraph = propertyGraph;
        this.cadastros = cadastros;
    }

    /**
     * Gera sugestões de troca de propriedades que maximizam a área média por proprietário.
     * 
     * @param maxSuggestions Número máximo de sugestões a serem geradas
     * @return Lista de sugestões de troca ordenadas por viabilidade e melhoria na área média
     * @throws IllegalArgumentException se o número máximo de sugestões for menor ou igual a zero
     */
    public List<PropertyExchange> generateExchangeSuggestions(int maxSuggestions) {
        if (maxSuggestions <= 0) {
            throw new IllegalArgumentException("O número máximo de sugestões deve ser maior que zero");
        }

        List<PropertyExchange> suggestions = new ArrayList<>();
        Map<Integer, List<Cadastro>> propertiesByOwner = groupPropertiesByOwner();

        // Para cada par de proprietários diferentes
        for (Map.Entry<Integer, List<Cadastro>> entry1 : propertiesByOwner.entrySet()) {
            int owner1 = entry1.getKey();
            List<Cadastro> properties1 = entry1.getValue();

            for (Map.Entry<Integer, List<Cadastro>> entry2 : propertiesByOwner.entrySet()) {
                int owner2 = entry2.getKey();
                if (owner1 >= owner2) continue; // Evita duplicados

                List<Cadastro> properties2 = entry2.getValue();

                // Para cada par de propriedades dos dois proprietários
                for (Cadastro prop1 : properties1) {
                    for (Cadastro prop2 : properties2) {
                        // Verifica se as propriedades são adjacentes
                        if (propertyGraph.getAdjacentProperties(prop1).contains(prop2)) {
                            double areaDiff = Math.abs(prop1.getArea() - prop2.getArea());
                            double feasibilityScore = calculateFeasibilityScore(prop1, prop2);
                            double avgAreaImprovement = calculateAverageAreaImprovement(prop1, prop2, properties1, properties2);
                            suggestions.add(new PropertyExchange(prop1, prop2, areaDiff, feasibilityScore, avgAreaImprovement));
                        }
                    }
                }
            }
        }

        // Ordena as sugestões por viabilidade e melhoria na área média
        suggestions.sort((s1, s2) -> {
            double score1 = s1.getFeasibilityScore() * s1.getAverageAreaImprovement();
            double score2 = s2.getFeasibilityScore() * s2.getAverageAreaImprovement();
            return Double.compare(score2, score1); // Ordem decrescente
        });

        // Retorna apenas o número máximo de sugestões solicitado
        return suggestions.subList(0, Math.min(maxSuggestions, suggestions.size()));
    }

    /**
     * Agrupa as propriedades por proprietário.
     * 
     * @return Mapa de proprietário para lista de suas propriedades
     */
    private Map<Integer, List<Cadastro>> groupPropertiesByOwner() {
        Map<Integer, List<Cadastro>> propertiesByOwner = new HashMap<>();
        for (Cadastro cadastro : cadastros) {
            propertiesByOwner.computeIfAbsent(cadastro.getOwner(), k -> new ArrayList<>()).add(cadastro);
        }
        return propertiesByOwner;
    }

    /**
     * Calcula a pontuação de viabilidade de uma troca (0-1).
     * Quanto menor a diferença de área entre as propriedades, maior a viabilidade.
     * 
     * @param prop1 Primeira propriedade
     * @param prop2 Segunda propriedade
     * @return Pontuação de viabilidade (0-1)
     */
    private double calculateFeasibilityScore(Cadastro prop1, Cadastro prop2) {
        double area1 = prop1.getArea();
        double area2 = prop2.getArea();
        double maxArea = Math.max(area1, area2);
        double minArea = Math.min(area1, area2);
        
        // Quanto mais próxima a razão entre as áreas for de 1, maior a viabilidade
        return minArea / maxArea;
    }

    /**
     * Calcula a melhoria na área média que uma troca proporcionaria.
     * 
     * @param prop1 Primeira propriedade
     * @param prop2 Segunda propriedade
     * @param properties1 Lista de propriedades do primeiro proprietário
     * @param properties2 Lista de propriedades do segundo proprietário
     * @return Melhoria na área média
     */
    private double calculateAverageAreaImprovement(Cadastro prop1, Cadastro prop2, List<Cadastro> properties1, List<Cadastro> properties2) {
        
        // Calcula a área total atual de cada proprietário
        double totalArea1 = properties1.stream().mapToDouble(Cadastro::getArea).sum();
        double totalArea2 = properties2.stream().mapToDouble(Cadastro::getArea).sum();

        // Calcula a área média atual por proprietário
        double currentAvgArea1 = totalArea1 / properties1.size();
        double currentAvgArea2 = totalArea2 / properties2.size();
        double currentAvgArea = (currentAvgArea1 + currentAvgArea2) / 2;
        
        // Calcula a área total após a troca
        double newTotalArea1 = totalArea1 - prop1.getArea() + prop2.getArea();
        double newTotalArea2 = totalArea2 - prop2.getArea() + prop1.getArea();
        
        // Calcula a nova área média por proprietário
        double newAvgArea1 = newTotalArea1 / properties1.size();
        double newAvgArea2 = newTotalArea2 / properties2.size();
        double newAvgArea = (newAvgArea1 + newAvgArea2) / 2;
        
        // Retorna a melhoria percentual
        return (newAvgArea - currentAvgArea) / currentAvgArea;
    }
} 