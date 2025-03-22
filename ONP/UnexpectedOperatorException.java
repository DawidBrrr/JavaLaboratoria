public class UnexpectedOperatorException extends Exception {
    public UnexpectedOperatorException() {
        super("Nieobsługiwany operator");
    }
    public UnexpectedOperatorException(String message) {
        super(message);
    }
}
