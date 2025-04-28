import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class SluchaczeFutureTask {

    public static void main(String[] args) {
        // Tworzymy wykonawcę z stałą pulą wątków, u nas 2
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // przykład: liczenie iloczynu 5 kolejnych liczb (1, 2, 3, 4, 5)
        Callable<Integer> iloczynZadanie = () -> {
            int iloczyn = 1;
            for (int i = 1; i <= 5; i++) {
                iloczyn *= i;
            }
            return iloczyn;
        };

        // przykład: liczenie pola trójkąta (podstawa = 5, wysokość = 4)
        Callable<Double> poleTrojkataZadanie = () -> {
            double podstawa = 5;
            double wysokosc = 4;
            return 0.5 * podstawa * wysokosc;
        };

        // tworzymy instancję naszego mechanizmu z możliwością dodawania słuchaczy


        FutureTaskSluchacze<Integer> IloczynSluchacze = new FutureTaskSluchacze<>(iloczynZadanie);
        IloczynSluchacze.dodajSluchacza(future -> {
            try {
                Integer wynik = future.get(); // Odczytanie wyniku
                System.out.println("Iloczyn liczb 1, 2, 3, 4, 5 wynosi: " + wynik);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        FutureTaskSluchacze<Double> PoleSluchacze = new FutureTaskSluchacze<>(poleTrojkataZadanie);
        PoleSluchacze.dodajSluchacza(future -> {
            try {
                Double wynik = future.get(); // Odczytanie wyniku
                System.out.println("Pole trójkąta o podstawie 5 i wysokości 4 wynosi: " + wynik);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Uruchamiamy zadania w tle
        executor.submit(IloczynSluchacze);
        executor.submit(PoleSluchacze);

        // Zatrzymujemy wykonawcę
        executor.shutdown();
    }

    //klasa umożliwiająca dodawanie słuchaczy
    public static class FutureTaskSluchacze<T> extends FutureTask<T> {

        private final List<Sluchacz<T>> sluchacze = new ArrayList<>();

        public FutureTaskSluchacze(Callable<T> zadanie) {
            super(zadanie);
        }

        //metoda dodająca słuchacza
        public void dodajSluchacza(Sluchacz<T> sluchacz) {
            sluchacze.add(sluchacz);
        }

        //metoda wywołująca wszystkich słuchaczy po zakończeniu zadania
        @Override
        protected void done() {
            for (Sluchacz<T> sluchacz : sluchacze) {
                sluchacz.koniec(this);
            }
        }
    }

    //interfejs słuchacza, który będzie reagował na zakończenie zadania
    public interface Sluchacz<T> {
        void koniec(Future<T> future);
    }
}
