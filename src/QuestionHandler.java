import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionHandler {
    private final Map<Integer, Question> questionsMap;
    private static QuestionHandler instance;

    private QuestionHandler() throws IOException {
        questionsMap = new ConcurrentHashMap<>();
        parseQuestions(FileUtil.loadQuestionFromFile());
    }

    public static synchronized QuestionHandler getInstance() {
        if (instance == null) {
            try {
                instance = new QuestionHandler();
            } catch (IOException e) {
                throw new RuntimeException("Błąd wczytania pytań !");
            }
        }
        return instance;
    }

    public void parseQuestions(String questionsRaw) {
        String[] lines = questionsRaw.split("\n");

        StringBuilder questionContent = new StringBuilder();
        String correctAnswerKey = null;
        int questionIndex = 0;

        for (String line : lines) {
            line = line.trim();

            // Pusta linia = koniec jednego pytania
            if (line.isEmpty()) {
                continue;
            }

            // Jeśli linia to np. "{C}" – oznacza odpowiedź do poprzedniego pytania
            if (line.matches("^\\{[A-Da-d]}$")) {
                correctAnswerKey = line.substring(1, 2).toUpperCase();

                if (questionContent.length() > 0 && correctAnswerKey != null) {
                    String finalContent = questionContent.toString().trim();
                    Question q = new Question(finalContent, correctAnswerKey);
                    questionsMap.put(++questionIndex, q);

                    // Resetujemy dane na kolejne pytanie
                    questionContent.setLength(0);
                    correctAnswerKey = null;
                }

            } else {
                // Dodajemy kolejną linię pytania
                if (questionContent.length() > 0) {
                    questionContent.append("\n");
                }
                questionContent.append(line);
            }
        }
    }


    public Map<Integer, Question> getQuestionsMap() {
        return questionsMap;
    }

    public Question getQuestion(int id) {
        return questionsMap.get(id);
    }
}
