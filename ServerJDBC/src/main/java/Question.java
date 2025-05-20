
public class Question {
    private final String content;
    private final String correctAnswerKey;
    private final String correctAnswerText;

    public Question(String content, String correctAnswerKey, String correctAnswerText) {
        this.content = content.trim();
        this.correctAnswerKey = correctAnswerKey.trim().toUpperCase();
        this.correctAnswerText = correctAnswerText;
    }

    public String getQuestionContent() {
        return content;
    }

    public String getCorrectAnswerKey() {
        return correctAnswerKey;
    }


    public boolean isAppropriateAnswerByKey(String userAnswerKey) {
        return correctAnswerKey.equalsIgnoreCase(userAnswerKey.trim());
    }
     public boolean isAppropriateAnswerByText(String userAnswerKey) {
        return correctAnswerText.equalsIgnoreCase(userAnswerKey.trim());
    }

    public String getCorrectAnswerText() {
        return correctAnswerText;
    }
     @Override
    public String toString() {
        return "Pytanie: " + content + "\n" +
               "Poprawna odpowiedź: " + correctAnswerKey + ") " + correctAnswerText;
    }
}
