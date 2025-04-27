import ONP.ONP;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.FutureTask;

public class ObliczanieRownania extends FutureTask<String> {

    private final int numerLinii;
    private final String rownanie;

    public ObliczanieRownania(int numerLinii, String rownanie) {
        super(() -> {
            System.out.println("Obliczanie (linia " + numerLinii + ")");
            try {
                ONP onp = new ONP();
                String onpRownanie = onp.przeksztalcNaOnp(rownanie);
                String wynik = onp.obliczOnp(onpRownanie);
                return wynik;
            } catch (Exception e) {
                System.out.println("Błąd przy obliczaniu (linia " + numerLinii + "): " + e.getMessage());
                return "Błąd w równaniu";
            }
        });
        this.numerLinii = numerLinii;
        this.rownanie = rownanie;
    }

    @Override
    protected void done() {
        try {
            String wynik = get();
            dopiszWynikDoPliku(numerLinii, rownanie, wynik);
            System.out.println("Obliczono wynik (linia " + numerLinii + "): " + wynik);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dopiszWynikDoPliku(int lineNumber, String rownanie, String wynik) {
        Main.lockPisanie.lock(); // --- LOCK start ---
        try {
            File file = new File(Main.fileName);
            File tempFile = new File("temp_" + Main.fileName);

            Scanner scanner = new Scanner(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            int currentLine = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (currentLine == lineNumber) {
                    line = rownanie + " " + wynik;
                }
                writer.write(line);
                writer.newLine();
                currentLine++;
            }

            scanner.close();
            writer.close();

            if (!file.delete()) {
                System.out.println("Nie można usunąć starego pliku");
            }
            if (!tempFile.renameTo(file)) {
                System.out.println("Nie można zamienić plików");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Main.lockPisanie.unlock(); // --- LOCK end ---
        }
    }
}
