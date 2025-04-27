import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final String fileName = "rownania.txt";
    public static final Lock lockCzytanie = new ReentrantLock();
    public static final Lock lockPisanie = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        int LiczbaLini = 0;

        try {
            LiczbaLini = policzLinie(fileName);
        }
        catch (IOException e) {
            System.out.println("Nie można policzyć linii w pliku: " + e.getMessage());
            return;
        }

        for (int i = 0; i < LiczbaLini; i++) {
            int numerLinii = i;

            // Najpierw czytanie równania
            CzytanieRownania czytanie = new CzytanieRownania(numerLinii);
            try {
                String rownanie = executor.submit(czytanie).get();

                // Następnie obliczanie na podstawie odczytanego równania
                ObliczanieRownania obliczanie = new ObliczanieRownania(numerLinii, rownanie);
                executor.submit(obliczanie);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();



    }
    private static int policzLinie(String nazwaPliku) throws IOException {
        Scanner scanner = new Scanner(new File(nazwaPliku));
        int licznik = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            licznik++;
        }
        scanner.close();
        return licznik;
    }
}