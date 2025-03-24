import java.util.Arrays;

public class Set<T extends Comparable<T>> {
    private T[] set;
    private int pojemnosc;
    private int rozmiar;

    public Set(int pojemnosc) {
        this.pojemnosc = pojemnosc;
        this.set = (T[]) new Comparable[pojemnosc];
        this.rozmiar = 0;
    }

    public void dodajElement(T element) throws Exception {
        if (rozmiar == pojemnosc) {
            throw new Exception("Zbiór jest pełny");
        }
        if (szukaj(element) != -1) {
            return; // Element już istnieje w zbiorze
        }

        // Wstawienie elementu w odpowiedniej pozycji
        int pozycja = 0;
        while (pozycja < rozmiar && set[pozycja].compareTo(element) < 0) {
            pozycja++;
        }
        System.arraycopy(set, pozycja, set, pozycja + 1, rozmiar - pozycja);
        set[pozycja] = element;
        rozmiar++;
    }

    public int szukaj(T element) {
        int lewy = 0;
        int prawy = rozmiar - 1;
        while (lewy <= prawy) {
            int srodkowy = (lewy + prawy) / 2;
            int porownanie = set[srodkowy].compareTo(element);
            if (porownanie == 0) {
                return srodkowy;
            } else if (porownanie < 0) {
                lewy = srodkowy + 1;
            } else {
                prawy = srodkowy - 1;
            }
        }
        return -1; // Element nie znaleziony
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rozmiar: ").append(rozmiar).append(", Pojemność: ").append(pojemnosc).append("\n");
        sb.append("Elementy: ");
        for (int i = 0; i < rozmiar; i++) {
            sb.append(set[i]).append(", ");
        }
        return sb.toString();
    }

    public void usunElement(T element) throws Exception {
        int pozycja = szukaj(element);
        if (pozycja == -1) {
            throw new Exception("Element nie istnieje w zbiorze");
        }
        System.arraycopy(set, pozycja + 1, set, pozycja, rozmiar - pozycja - 1);
        rozmiar--;
    }

    public Set<T> dodajElementy(Set<T> innyZbior) {
        Set<T> nowyZbior = new Set<>(pojemnosc + innyZbior.pojemnosc);
        try {
            for (int i = 0; i < rozmiar; i++) {
                nowyZbior.dodajElement(set[i]);
            }
            for (int i = 0; i < innyZbior.rozmiar; i++) {
                nowyZbior.dodajElement(innyZbior.set[i]);
            }
        } catch (Exception e) {
            System.out.println("Błąd podczas dodawania elementów: " + e.getMessage());
        }
        return nowyZbior;
    }

    public Set<T> odejmijElementy(Set<T> innyZbior) {
        Set<T> nowyZbior = new Set<>(pojemnosc);
        try {
            for (int i = 0; i < rozmiar; i++) {
                if (innyZbior.szukaj(set[i]) == -1) {
                    nowyZbior.dodajElement(set[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("Błąd podczas odejmowania elementów: " + e.getMessage());
        }
        return nowyZbior;
    }

    public Set<T> przeciecie(Set<T> innyZbior) {
        Set<T> nowyZbior = new Set<>(pojemnosc);
        try {
            for (int i = 0; i < rozmiar; i++) {
                if (innyZbior.szukaj(set[i]) != -1) {
                    nowyZbior.dodajElement(set[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("Błąd podczas przecięcia zbiorów: " + e.getMessage());
        }
        return nowyZbior;
    }
}