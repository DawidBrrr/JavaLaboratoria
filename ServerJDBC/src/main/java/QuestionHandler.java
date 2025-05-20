import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionHandler {
    private static QuestionHandler instance;

    private QuestionHandler() {}

    public static synchronized QuestionHandler getInstance() {
        if (instance == null) {
            instance = new QuestionHandler();
        }
        return instance;
    }

    public Map<Integer, Question> getQuestionsMap() throws SQLException {
        CachedRowSet crs = DatabaseUtil.getInstance().getQuestions();
        return parseQuestions(crs);
    }

    public Question getQuestion(int id) throws SQLException {
        return getQuestionsMap().get(id);
    }

    private Map<Integer, Question> parseQuestions(CachedRowSet crs) throws SQLException {
        Map<Integer, Question> map = new ConcurrentHashMap<>();

        try {
            while (crs.next()) {
                int questionId = crs.getInt("id");
                String questionText = crs.getString("question");
                String answerA = crs.getString("answer_a");
                String answerB = crs.getString("answer_b");
                String answerC = crs.getString("answer_c");
                String answerD = crs.getString("answer_d");
                String correctAnswerText = crs.getString("correct_answer");

                String correctAnswerKey = determineCorrectKey(answerA, answerB, answerC, answerD, correctAnswerText);
                String formattedQuestion = formatQuestion(questionText, answerA, answerB, answerC, answerD);

                map.put(questionId, new Question(formattedQuestion, correctAnswerKey, correctAnswerText));
            }
        } finally {
            crs.close();
        }
        return map;
    }

    private String determineCorrectKey(String a, String b, String c, String d, String correct) {
        if (correct.equals(a)) return "A";
        if (correct.equals(b)) return "B";
        if (correct.equals(c)) return "C";
        if (correct.equals(d)) return "D";
        throw new IllegalArgumentException("Nieprawidłowa odpowiedź dla: " + correct);
    }

    private String formatQuestion(String question, String a, String b, String c, String d) {
        return String.format("%s\nA) %s\nB) %s\nC) %s\nD) %s",
                question, a, b, c, d);
    }
}
