package core;

import ui.Gui;

/**
 * Application entry point.
 * Initializes and displays the GUI.
 * 
 * @author [Lei-G]
 * @version 1.0
 */
public class Main {
    /**
     * Starts the application.
     *
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        Gui visualizador = new Gui();
        visualizador.setVisible(true);
    }
}