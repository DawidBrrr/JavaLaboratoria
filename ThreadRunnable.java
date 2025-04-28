import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadRunnable {

    public static void main(String[] args) {
        MenedzerZadan menedzerZadan = new MenedzerZadan();
        Scanner scanner = new Scanner(System.in);
        String komenda;

        System.out.println("Witaj w Menedżerze zadań!");
        System.out.println("Wybierz komende: dodaj, lista, stan [id], anuluj [id], wynik [id], wyjdz");

        while (true) {
            System.out.print("> ");
            komenda = scanner.nextLine();
            String[] polecenia = komenda.trim().split("\\s+");
            String ujednolicenie = polecenia[0].toLowerCase();

            switch (ujednolicenie) {
                case "dodaj":
                    menedzerZadan.dodajZadanie();
                    break;
                case "lista":
                    menedzerZadan.wyswietlListe();
                    break;
                case "stan":
                    if (polecenia.length > 1) {
                        try {
                            int id = Integer.parseInt(polecenia[1]);
                            menedzerZadan.getStan(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Błędne ID zadania.");
                        }
                    } else {
                        System.out.println("Podaj ID zadania.");
                    }
                    break;
                case "anuluj":
                    if (polecenia.length > 1) {
                        try {
                            int id = Integer.parseInt(polecenia[1]);
                            menedzerZadan.anulujZadanie(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Błędne ID zadania.");
                        }
                    } else {
                        System.out.println("Podaj ID zadania.");
                    }
                    break;
                case "wynik":
                    if (polecenia.length > 1) {
                        try {
                            int id = Integer.parseInt(polecenia[1]);
                            menedzerZadan.pokazWynik(id);
                        } catch (NumberFormatException e) {
                            System.out.println("Błędne ID zadania.");
                        }
                    } else {
                        System.out.println("Podaj ID zadania.");
                    }
                    break;
                case "wyjdz":
                    System.out.println("Zamykanie programu...");
                    menedzerZadan.zamknij();
                    scanner.close();
                    return;
                default:
                    System.out.println("Nieznana komenda.");
                    break;
            }
        }
    }
}

class Zadanie implements Runnable {
    private int id;
    private Thread watek;
    private String wynik;
    private AtomicBoolean anulowane = new AtomicBoolean(false);
    private AtomicBoolean zakonczone = new AtomicBoolean(false);

    public Zadanie(int id) {
        this.id = id;
        this.watek = new Thread(this);
    }

    public void uruchom() {
        watek.start();
    }

    public void anuluj() {
        anulowane.set(true);
        watek.interrupt();
    }

    public boolean czyZakonczone() {
        return zakonczone.get();
    }

    public String pobierzWynik() {
        return wynik;
    }

    public Thread.State pobierzStanWatku() {
        return watek.getState();
    }

    @Override
    public void run() {  // zadaniem naszych wątków jest pomnożenie 5 kolejnych liczb przez siebie
        try {
            int iloczyn = 1;
            for (int i = 1; i <= 5; i++) {
                if (anulowane.get()) {
                    wynik = "Zadanie anulowane.";
                    return;
                }
                iloczyn *= i;
                Thread.sleep(1000);
            }
            wynik = "Wynik: " + iloczyn;
            zakonczone.set(true);
        } catch (InterruptedException e) {
            wynik = "Błąd: zadanie anulowane";
        }
    }

    public int getId() {
        return id;
    }
}

class MenedzerZadan {
    private List<Zadanie> zadania = new ArrayList<>();
    private int nastepneId = 0;

    public void dodajZadanie() {
        Zadanie zadanie = new Zadanie(nastepneId++);
        zadania.add(zadanie);
        zadanie.uruchom();
        System.out.println("Dodano zadanie o ID: " + zadanie.getId());
    }

    public void wyswietlListe() {
        if (zadania.isEmpty()) {
            System.out.println("Brak zadań.");
            return;
        }
        for (Zadanie zadanie : zadania) {
            System.out.println("ID: " + zadanie.getId() + ", Stan: " + zadanie.pobierzStanWatku());
        }
    }

    public void getStan(int id) {
        Zadanie zadanie = znajdzPoId(id);
        if (zadanie != null) {
            System.out.println("Zadanie ID " + id + " - Stan: " + zadanie.pobierzStanWatku());
        } else {
            System.out.println("Nie znaleziono zadania o ID: " + id);
        }
    }

    public void anulujZadanie(int id) {
        Zadanie zadanie = znajdzPoId(id);
        if (zadanie != null) {
            zadanie.anuluj();
            System.out.println("Anulowano zadanie " + id);
        } else {
            System.out.println("Nie znaleziono zadania " + id);
        }
    }

    public void pokazWynik(int id) {
        Zadanie zadanie = znajdzPoId(id);
        if (zadanie != null) {
            String wynik = zadanie.pobierzWynik();
            if (wynik != null) {
                System.out.println("Wynik zadania ID " + id + ": " + wynik);
            } else {
                System.out.println("Zadanie jeszcze się nie zakończyło.");
            }
        } else {
            System.out.println("Nie znaleziono zadania o ID: " + id);
        }
    }

    private Zadanie znajdzPoId(int id) {
        for (Zadanie zadanie : zadania) {
            if (zadanie.getId() == id) {
                return zadanie;
            }
        }
        return null;
    }

    public void zamknij() {
        for (Zadanie zadanie : zadania) {
            if (!zadanie.czyZakonczone()) {
                zadanie.anuluj();
            }
        }
    }
}
