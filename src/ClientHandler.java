import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private int currentQuestionIndex = 1;
    private final Map<Integer, String> clientAnswerMap = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            sendMessage(out, "📢 Test startuje. Wpisz 'end' aby zakończyć.");

            while (true) {
                Question question = QuestionHandler.getInstance().getQuestion(currentQuestionIndex);
                if (question == null) {
                    sendMessage(out, "✅ Koniec pytań. Dzięki za udział!");
                    break;
                }

                sendQuestion(out, question, currentQuestionIndex);

                String answer = in.readLine();
                if (isEndMessage(answer)) {
                    sendMessage(out, "🛑 Zakończono test. Zapisuję odpowiedzi.");
                    break;
                }

                clientAnswerMap.put(currentQuestionIndex, answer.trim());
                currentQuestionIndex++;
            }

            saveAnswerToFile();

        } catch (   IOException e) {
            System.err.println("⚠️ Błąd w obsłudze klienta: " + e.getMessage());
        } finally {
            close();
        }
    }

    private void sendMessage(BufferedWriter out, String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    private void sendQuestion(BufferedWriter out, Question question, int index) throws IOException {
        String questionText = String.format(
            "Pytanie %d:\n%s\n", index, question.getQuestionContent()
        );
        sendMessage(out, questionText);
    }

    private boolean isEndMessage(String input) {
        return input == null || input.equalsIgnoreCase("end");
    }

    private void saveAnswerToFile() throws IOException {
        StringBuilder result = new StringBuilder();
        clientAnswerMap.forEach((key, value) -> result.append("Pytanie ").append(key).append(": ").append(value).append("\n"));
        FileUtil.addAnswerToFile(result.toString());
    }

    private void close() {
        try {
            if (!socket.isClosed()) socket.close();
            System.out.println("🔒 Połączenie zakończone: " + socket.getInetAddress());
        } catch (IOException e) {
            System.err.println("❌ Błąd przy zamykaniu socketu: " + e.getMessage());
        }
    }
}
