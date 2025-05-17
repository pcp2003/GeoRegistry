package service.exchange;

import model.Cadastro;
import service.OwnerGraph;
import service.PropertyGraph;

import java.util.*;

/**
 * Serviço para gerar e gerir sugestões de troca de propriedades.
 * Analisa propriedades e proprietários para encontrar trocas potencialmente benéficas.
 * 
 * @author Lei-G
 * @version 1.0
 */
public class PropertyExchangeService {
    private final PropertyGraph propertyGraph;
    private final List<Cadastro> cadastros;

    /**
     * Cria um novo serviço de troca de propriedades.
     * 
     * @param ownerGraph Grafo que representa as relações entre proprietários
     * @param propertyGraph Grafo que representa as adjacências entre propriedades
     * @param cadastros Lista de todas as propriedades no sistema
     */
    public PropertyExchangeService(OwnerGraph ownerGraph, PropertyGraph propertyGraph, List<Cadastro> cadastros) {
        this.propertyGraph = propertyGraph;
        this.cadastros = cadastros;
    }

    /**
     * Gera sugestões de troca de propriedades que maximizam a área média por proprietário.
     * 
     * @param proprietario Proprietário a quem sugerir troca
     * @return Lista de sugestões de troca ordenadas por viabilidade e melhoria na área média
     * @throws IllegalArgumentException se o número máximo de sugestões for menor ou igual a zero
     */
    public List<PropertyExchange> generateExchangeSuggestions(int proprietario) {
        List<PropertyExchange> suggestions = new ArrayList<>();
        Map<Integer, List<Cadastro>> propertiesByOwner = groupPropertiesByOwner();

        List<Cadastro> propriedadesProprietario = propertiesByOwner.get(proprietario);
        if (propriedadesProprietario == null || propriedadesProprietario.isEmpty()) {
            return suggestions; // Nenhuma propriedade encontrada para esse proprietário
        }

        for (Map.Entry<Integer, List<Cadastro>> entry : propertiesByOwner.entrySet()) {
            int otherOwner = entry.getKey();
            if (proprietario >= otherOwner) continue; // Evita duplicados e auto-troca

            List<Cadastro> propriedadesProposta = entry.getValue();

            for (Cadastro prop1 : propriedadesProprietario) {
                for (Cadastro prop2 : propriedadesProposta) {
                    // Verifica se são propriedades adjacentes
                    if (!propertyGraph.getAdjacentProperties(prop1).contains(prop2)) {
                        continue;
                    }

                    // Filtra propriedades do mesmo dono (embora já deva estar garantido pelo loop)
                    if (prop1.getOwner() == prop2.getOwner()) {
                        continue;
                    }

                    // Ignora trocas entre propriedades com áreas iguais (considerando precisão)
                    if (Double.compare(prop1.getArea(), prop2.getArea()) == 0) {
                        continue;
                    }

                    double priceDiff = Math.abs(prop1.getPrice() - prop2.getPrice());
                    double feasibilityScore = calculateFeasibilityScore(prop1, prop2);
                    double avgAreaImprovement = calculateAverageAreaImprovement(prop1, prop2, propriedadesProprietario, propriedadesProposta);

                    suggestions.add(new PropertyExchange(prop1, prop2, priceDiff, feasibilityScore, avgAreaImprovement));
                }
            }
        }

        // Ordena as sugestões por viabilidade e melhoria na área média
        suggestions.sort((s1, s2) -> {
            double score1 = s1.getFeasibilityScore() + s1.getAverageAreaImprovement();
            double score2 = s2.getFeasibilityScore() + s2.getAverageAreaImprovement();
            return Double.compare(score2, score1); // Ordem decrescente
        });

        return suggestions;
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
     * Quanto menor a diferença de preço entre as propriedades, maior a viabilidade.
     * 
     * @param prop1 Primeira propriedade
     * @param prop2 Segunda propriedade
     * @return Pontuação de viabilidade (0-1)
     */
    private double calculateFeasibilityScore(Cadastro prop1, Cadastro prop2) {
        double areaPrice1 = prop1.getPrice();
        double areaPrice2 = prop2.getPrice();

        double maxPrice = Math.max(areaPrice1, areaPrice2);
        double minPrice = Math.min(areaPrice1, areaPrice2);
        
        // Quanto mais próxima a razão entre os preços for de 1, maior a viabilidade
        return minPrice / maxPrice;
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