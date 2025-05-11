import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Question {
    private final String content;
    private final String correctAnswerKey;

    public Question(String content,  String correctAnswerKey) {
        this.content = content.trim();
        this.correctAnswerKey = correctAnswerKey.trim().toUpperCase();
    }

    public String getQuestionContent() {
        return content;
    }

    public String getCorrectAnswerKey() {
        return correctAnswerKey;
    }


    public boolean isAppropriateAnswer(String userAnswerKey) {
        return correctAnswerKey.equalsIgnoreCase(userAnswerKey.trim());
    }


    @Override
    public String toString() {
        return content + "\nCorrect: " + correctAnswerKey;
    }
}
