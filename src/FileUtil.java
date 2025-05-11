import java.io.BufferedReader;
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
    private static final Path idFilePath = Path.of("src/data/id.txt");
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
    public static int getNextID() throws IOException {
    fileLock.lock();
    try {
        int currentId = 0;

        if (Files.exists(idFilePath)) {
            try (BufferedReader reader = Files.newBufferedReader(idFilePath)) {
                String line = reader.readLine();
                if (line != null && line.matches("\\d+")) {
                    currentId = Integer.parseInt(line);
                }
            }
        }

        int newId = currentId + 1;

        try (BufferedWriter writer = Files.newBufferedWriter(
                idFilePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(String.valueOf(newId));
        }

        return currentId;
    } finally {
        fileLock.unlock();
    }
}

}
