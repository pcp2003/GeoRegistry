package cadastro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por gerenciar os logs dos testes
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
public class TestLogger {
    private static final String LOG_DIR = "test-results";
    private static BufferedWriter writer;
    private static final String SEPARATOR = "=".repeat(80);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Inicializa o logger para um arquivo específico
     * 
     * @param logFile Nome do arquivo de log (sem extensão)
     */
    public static void init(String logFile) {
        try {
            // Cria o diretório se não existir
            new File(LOG_DIR).mkdirs();
            
            // Fecha o writer anterior se existir
            if (writer != null) {
                writer.close();
            }
            
            // Inicializa o writer em modo append
            writer = new BufferedWriter(new FileWriter(LOG_DIR + "/" + logFile + ".log", true));
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o logger: " + e.getMessage());
        }
    }

    /**
     * Registra uma mensagem de log com timestamp
     * 
     * @param message Mensagem a ser registrada
     */
    public static void log(String message) {
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.write(String.format("[%s] %s%n", timestamp, message));
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao registrar log: " + e.getMessage());
        }
    }

    /**
     * Registra o início de um teste
     * 
     * @param testName Nome do teste
     */
    public static void logTestStart(String testName) {
        try {
            writer.write(SEPARATOR + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao registrar separador: " + e.getMessage());
        }
        log(String.format("Iniciando teste: %s", testName));
    }

    /**
     * Registra o fim de um teste
     * 
     * @param testName Nome do teste
     */
    public static void logTestEnd(String testName) {
        log(String.format("Finalizando teste: %s", testName));
        try {
            writer.write(SEPARATOR + "\n\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao registrar separador: " + e.getMessage());
        }
    }

    /**
     * Registra uma mensagem de sucesso
     * 
     * @param message Mensagem de sucesso
     */
    public static void logSuccess(String message) {
        log(String.format("SUCESSO: %s", message));
    }

    /**
     * Registra uma mensagem de erro
     * 
     * @param message Mensagem de erro
     */
    public static void logError(String message) {
        log(String.format("ERRO: %s", message));
    }

    /**
     * Fecha o arquivo de log
     */
    public static void close() {
        try {
            if (writer != null) {
                writer.close();
                writer = null;
            }
        } catch (IOException e) {
            System.err.println("Erro ao fechar o logger: " + e.getMessage());
        }
    }
} 