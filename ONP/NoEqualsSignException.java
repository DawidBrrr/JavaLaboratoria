public class NoEqualsSignException extends Exception {
    public NoEqualsSignException() {
        super("Brak znaku = w wyrażeniu");
    }
    public NoEqualsSignException(String message) {
        super(message);
    }
}
