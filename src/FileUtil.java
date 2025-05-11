import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileUtil {
    private static final Path questionsFilePath = Path.of("src/data/questions.txt");
    private static final Path clientDB = Path.of("src/data/client_answer.txt");

    private static final Lock fileLock = new ReentrantLock();

    public static String loadFromFile(Path path) throws IOException {
        return Files.readString(path);
    }

    public static String loadQuestionFromFile() throws IOException {
        return loadFromFile(questionsFilePath);
    }

    public static void addAnswerToFile(String answer) throws IOException {
        String cleanAnswer = answer.strip();

        fileLock.lock();
        try (BufferedWriter writer = Files.newBufferedWriter(
                clientDB,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            writer.write(cleanAnswer);
            writer.newLine();
        } finally {
            fileLock.unlock();
        }
    }
}
