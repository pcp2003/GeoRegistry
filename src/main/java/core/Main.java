package core;

import ui.Gui;

/**
 * Classe principal do Sistema de Gestão de Propriedades.
 * Contém o ponto de entrada da aplicação e inicializa a interface gráfica.
 * 
 * @author Lei-G
 * @version 1.0
 */
public class Main {
    /**
     * Construtor privado para impedir a instanciação.
     * Esta classe deve ser utilizada apenas pelo seu método main.
     */
    private Main() {
        // Impedir instanciação
    }

    /**
     * Inicia a aplicação.
     *
     * @param args Argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {
        Gui visualizador = new Gui();
        visualizador.setVisible(true);
    }
}