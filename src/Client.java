import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = ServerConfig.getServerHost();
    private static final int SERVER_PORT = ServerConfig.getServerPort();

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Połączono z serwerem: " + SERVER_HOST + ":" + SERVER_PORT);
            System.out.println("Aby zakończyć test, wpisz 'end'.\n");

            String line;
            while ((line = in.readLine()) != null) {
                String fullMessage = readFullBlock(in, line);
                System.out.println(fullMessage);
                if (isEndMessage(fullMessage)) break;

                if (isQuestionBlock(fullMessage)) {
                    String answer = readUserAnswer(scanner);
                    sendAnswer(out, answer);
                }
            }

            System.out.println("\nRozłączono z serwerem.");

        } catch (IOException e) {
            System.err.println("❌ Błąd klienta: " + e.getMessage());
        }
    }

    private String readFullBlock(BufferedReader in, String firstLine) throws IOException {
        StringBuilder block = new StringBuilder(firstLine);
        while (in.ready()) {
            String nextLine = in.readLine();
            if (nextLine == null || nextLine.trim().isEmpty()) break;
            block.append("\n").append(nextLine);
        }
        return block.toString();
    }

    private boolean isQuestionBlock(String message) {
        return message.contains("?");
    }

    private String readUserAnswer(Scanner scanner) {
        System.out.print("Twoja odpowiedź: ");
        long start = System.currentTimeMillis();
        String answer = scanner.nextLine().trim();
        long duration = System.currentTimeMillis() - start;

        if (duration > 30000) {
            System.out.println("⏱️ Przekroczono limit czasu 30s. Brak odpowiedzi.");
            return "";
        }
        return answer;
    }

    private void sendAnswer(BufferedWriter out, String answer) throws IOException {
        out.write(answer + "\n");
        out.flush();
    }

    private boolean isEndMessage(String msg) {
        return msg.contains("Kończę połączenie") || msg.contains("Zakończono test");
    }
}
