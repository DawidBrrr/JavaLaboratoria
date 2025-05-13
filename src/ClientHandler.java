import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private int currentQuestionIndex = 1;
    private final Map<Integer, String> clientAnswerMap = new HashMap<>();
    private int pointsCounter = 0;
    private final int id;

    public ClientHandler(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            sendMessage(out, "Witamy , Twoje id to : " + id);
            sendMessage(out, "📢 Rozpoczynasz Test");

            while (true) {
                Question question = QuestionHandler.getInstance().getQuestion(currentQuestionIndex);
                if (question == null) {
                    sendMessage(out, "Dzięki za udział! Twoje odpowiedzi dla id :" + id);
                    sendMessage(out,printAnswer());
                    sendMessage(out,"Kończę połączenie");
                    break;
                }

                sendQuestion(out, question, currentQuestionIndex);

                String answer = in.readLine();
                if (isEndMessage(answer)) {
                    sendMessage(out,"Kończysz test na żądanie !");
                    sendMessage(out,printAnswer());
                    sendMessage(out, "Kończę połączenie");
                    break;
                }

                clientAnswerMap.put(currentQuestionIndex, answer.trim());
                checkAnswer(answer);

                currentQuestionIndex++;
            }
        } catch (IOException e) {
            System.err.println("⚠️ Błąd w obsłudze klienta: " + e.getMessage());
        } finally {
            saveAnswerToFile();
            close();
        }
    }

    private void saveAnswerToFile(){
        try {
            FileUtil.addAnswerToFile("id : "+id+ " ; Odpowiedzi : "+ clientAnswerMap.toString());
            FileUtil.addResultToFile("id : "+id+ " ; Wynik  : "+ pointsCounter);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private String printAnswer() throws IOException {
        StringBuilder result = new StringBuilder();
        clientAnswerMap.forEach((key, value) -> result.append("Pytanie ").append(key).append(": ").append(value).append("\n"));
        result.append("Wynik :").append(pointsCounter).append(" p").append('\n');
        return result.toString();
    }

    void checkAnswer(String answer){
        if(answer.trim().equalsIgnoreCase(QuestionHandler.getInstance().getQuestion(currentQuestionIndex).getCorrectAnswerText())){
            pointsCounter++;
        }
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
