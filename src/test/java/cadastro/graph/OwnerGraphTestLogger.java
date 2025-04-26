package cadastro.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para logging dos testes do OwnerGraph
 * 
 * @author Lei-G
 * @date 2024-04-06 21:30
 */
public class OwnerGraphTestLogger {
    private static final String LOG_FILE = "test-results/owner_graph_test.log";
    private static PrintWriter writer;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        writer.println(timestamp + " - " + message);
        writer.flush();
    }

    public static void logSuccess(String message) {
        log("SUCESSO: " + message);
    }

    public static void logTestStart(String testName) {
        log("\n=== Iniciando teste: " + testName + " ===");
    }

    public static void logTestEnd(String testName) {
        log("=== Teste concluído: " + testName + " ===\n");
    }

    public static void close() {
        if (writer != null) {
            writer.close();
        }
    }
} 