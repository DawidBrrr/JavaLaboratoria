import java.io.IOException;
import java.util.HashMap;
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
        Map<String, String> options = new HashMap<>();
        String correctAnswerKey = null;
        int questionIndex = 0;

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.matches("^\\{[A-Da-d]}$")) {
                correctAnswerKey = line.substring(1, 2).toUpperCase();

                if (questionContent.length() > 0 && correctAnswerKey != null) {
                    String finalContent = questionContent.toString().trim();
                    String correctAnswerText = options.get(correctAnswerKey);

                    if (correctAnswerText == null) {
                        throw new IllegalArgumentException("Brak odpowiedzi dla klucza: " + correctAnswerKey);
                    }

                    Question q = new Question(finalContent, correctAnswerKey, correctAnswerText);
                    questionsMap.put(++questionIndex, q);

                    // Reset na kolejne pytanie
                    questionContent.setLength(0);
                    options.clear();
                    correctAnswerKey = null;
                }

            } else if (line.matches("^[A-Da-d]\\)\\s+.*$")) {
                String optionKey = line.substring(0, 1).toUpperCase();
                String optionText = line.substring(2).trim();
                options.put(optionKey, optionText);

                // Dodajemy do treści pytania (bo chcesz, by content = pytanie + odpowiedzi)
                questionContent.append("\n").append(line);
            } else {
                // Treść pytania (pierwsza linia)
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
