import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CzytanieRownania implements Callable<String> {

    private final int numerLinii;

    public CzytanieRownania(int numerLinii) {

        this.numerLinii = numerLinii;
    }

    @Override
    public String call() throws Exception {
        Main.lockCzytanie.lock(); // --- LOCK start ---
        System.out.println("Czytanie (linia " + numerLinii + ")");
        try {
            Scanner scanner = new Scanner(new File(Main.fileName));
            int currentLine = 0;
            String rownanie = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (currentLine == numerLinii) {
                    rownanie = line;
                    break;
                }
                currentLine++;
            }
            scanner.close();

            if (rownanie == null) {
                throw new Exception("Nie znaleziono linii: " + numerLinii);
            }

            System.out.println("Odczytano (linia "+numerLinii +"): " + rownanie);
            return rownanie;
        } finally {
            Main.lockCzytanie.unlock(); // --- LOCK end ---
        }
    }
}
