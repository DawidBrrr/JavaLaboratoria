import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileUtil {
    private static final Path questionsFilePath = Path.of("src/data/bazaPytan.txt");
    private static final Path clientDB = Path.of("src/data/bazaOdpowiedzi.txt");
    private static final Path idFilePath = Path.of("src/data/id.txt");
    private static final Path resultFilePath = Path.of("src/data/wyniki.txt");
    private static final Lock clientsFileLock = new ReentrantLock();
    private static final Lock idsFileLock = new ReentrantLock();
    private static final Lock resultFileLock = new ReentrantLock();

    public static String loadFromFile(Path path) throws IOException {
        return Files.readString(path);
    }

    public static String loadQuestionFromFile() throws IOException {
        return loadFromFile(questionsFilePath);
    }

    public static void addAnswerToFile(String answer) throws IOException {
        String cleanAnswer = answer.strip();

        clientsFileLock.lock();
        try (BufferedWriter writer = Files.newBufferedWriter(
                clientDB,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            writer.write(cleanAnswer);
            writer.newLine();
        } finally {
            clientsFileLock.unlock();
        }
    }

    public static void addResultToFile(String result) throws IOException {
        String cleanAnswer = result.strip();

        resultFileLock.lock();
        try (BufferedWriter writer = Files.newBufferedWriter(
                resultFilePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            writer.write(cleanAnswer);
            writer.newLine();
        } finally {
            resultFileLock.unlock();
        }
    }


    public static int getNextID() throws IOException {
        idsFileLock.lock();
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
            idsFileLock.unlock();
        }
    }

}
