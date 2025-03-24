
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.util.*;

class Klient implements Serializable {
    private String nazwisko;
    private String imie;
    private String email;
    private String telefon;
    private Seans seans;
    private List<Miejsce> miejsca;

    public Klient(String nazwisko, String imie, String email, String telefon) {
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.email = email;
        this.telefon = telefon;
        this.miejsca = new ArrayList<>();
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public Seans getSeans() {
        return seans;
    }

    public List<Miejsce> getMiejsca() {
        return miejsca;
    }

    public void rezerwujSeans(Seans seans) {
        this.seans = seans;
    }

    public void dodajMiejsce(Miejsce miejsce) {
        miejsca.add(miejsce);
    }

    public void anulujRezerwacje() {
        if (seans != null) {
            for (Miejsce miejsce : miejsca) {
                seans.anulujMiejsce(miejsce.rzad(), miejsce.numer());
            }
            miejsca.clear();
            seans = null;
        }
    }

    @Override
    public String toString() {
        return "Klient{" +
                "nazwisko='" + nazwisko + '\'' +
                ", imie='" + imie + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", seans=" + (seans != null ? seans.getTytul() : "Brak") +
                ", miejsca=" + miejsca +
                '}';
    }
}

class Seans implements Serializable {
    private String tytul;
    private String data;
    private String godzina;
    private int ograniczeniaWiekowe;
    private HashMap<Character, HashMap<Integer, Boolean>> miejsca;

    public Seans(String tytul, String data, String godzina, int ograniczeniaWiekowe) {
        this.tytul = tytul;
        this.data = data;
        this.godzina = godzina;
        this.ograniczeniaWiekowe = ograniczeniaWiekowe;
        this.miejsca = new HashMap<>();
        inicjalizujMiejsca();
    }

    public String getTytul() {
        return tytul;
    }

    public String getData() {
        return data;
    }

    public String getGodzina() {
        return godzina;
    }

    public int getOgraniczeniaWiekowe() {
        return ograniczeniaWiekowe;
    }

    public int iloscMiejsc(boolean typ) {
        return (int) miejsca.values().stream()
                .flatMap(m -> m.values().stream())
                .filter(miejsce -> miejsce == typ)
                .count();
    }

    private void inicjalizujMiejsca() {
        for (char rzad = 'A'; rzad <= 'J'; rzad++) {
            HashMap<Integer, Boolean> rzadMiejsc = new HashMap<>();
            for (int miejsce = 1; miejsce <= 10; miejsce++) {
                rzadMiejsc.put(miejsce, false);
            }
            miejsca.put(rzad, rzadMiejsc);
        }
    }

    public boolean rezerwujMiejsce(char rzad, int miejsce) {
        return miejsca.containsKey(rzad) && miejsca.get(rzad).computeIfPresent(miejsce, (k, v) -> v ? v : true);
    }

    public void anulujMiejsce(char rzad, int numer) {
        if (miejsca.containsKey(rzad)) {
            miejsca.get(rzad).put(numer, false);
        }
    }

    @Override
    public String toString() {
        return "Seans{" +
                "tytul='" + tytul + '\'' +
                ", data='" + data + '\'' +
                ", godzina='" + godzina + '\'' +
                ", ograniczeniaWiekowe=" + ograniczeniaWiekowe +
                '}';
    }
}

record Miejsce(char rzad, int numer) implements Serializable {
}

public class Kino {
    private static final String PLIK_DANYCH_XML = "dane_kina.xml";
    private static final String PLIK_DANYCH_DAT = "dane_kina.dat";
    private List<Klient> klienci;
    private List<Seans> seanse;
    private transient XStream xstream;

    public Kino() {
        klienci = new ArrayList<>();
        seanse = new ArrayList<>();
        xstream = new XStream(new DomDriver());
        xstream.allowTypes(new Class[]{Kino.class, Klient.class, Seans.class, Miejsce.class});
    }

    public void dodajSeans(Seans seans) {
        seanse.add(seans);
    }

    public void dodajKlienta(Klient klient) {
        klienci.add(klient);
    }


    public void zapiszDaneXML() {
        try (FileWriter writer = new FileWriter(PLIK_DANYCH_XML)) {
            writer.write(xstream.toXML(this));
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku XML");
            e.printStackTrace();
        }
    }

    public void wczytajDaneXML() {
        try (FileReader reader = new FileReader(PLIK_DANYCH_XML)) {
            Kino kino = (Kino) xstream.fromXML(reader);
            this.klienci = kino.klienci;
            this.seanse = kino.seanse;
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku XML");
            e.printStackTrace();
        }
    }

    public void zapiszDaneDAT() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLIK_DANYCH_DAT))) {
            oos.writeObject(klienci);
            oos.writeObject(seanse);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku DAT");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void wczytajDaneDAT() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PLIK_DANYCH_DAT))) {
            klienci = (List<Klient>) ois.readObject();
            seanse = (List<Seans>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd odczytu pliku DAT");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Kino{" +
                "klienci=" + klienci +
                ", seanse=" + seanse +
                '}';
    }

    public static void main(String[] args) {
        Kino kino = new Kino();
        Seans seans1 = new Seans("Minecraft", "2025-03-29", "18:00", 12);
        kino.dodajSeans(seans1);
        Klient klient1 = new Klient("Kowalski", "Jan", "jan@example.com", "123456789");
        klient1.rezerwujSeans(seans1);
        if (seans1.rezerwujMiejsce('A', 5)) {
            klient1.dodajMiejsce(new Miejsce('A', 5));
        }
        kino.dodajKlienta(klient1);

        System.out.println("\nPrzed anulowaniem rezerwacji:");
        System.out.println(klient1);
        System.out.println(kino);

        klient1.anulujRezerwacje();
        System.out.println("\nPo anulowaniu rezerwacji:");
        System.out.println(klient1);
        System.out.println(kino);

        kino.zapiszDaneXML();
        kino.zapiszDaneDAT();

        Kino deserialized = new Kino();
        deserialized.wczytajDaneDAT();
        deserialized.wczytajDaneXML();
        System.out.println("\nPo deserializacji:");
        System.out.println(deserialized);
    }
}
