package core;

import ui.Gui;

/**
 * Classe principal que serve como ponto de entrada da aplicação.
 * Inicializa a interface gráfica e a torna visível.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class Main {
    /**
     * Método principal que inicia a aplicação.
     *
     * @param args Argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        Gui visualizador = new Gui();
        visualizador.setVisible(true);
    }
}