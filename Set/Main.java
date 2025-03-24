public class Main {
    public static void main(String[] args) throws Exception {
        Set<Film> zbiorFilmow = new Set<>(5);
        zbiorFilmow.dodajElement(new Film("Shrek", 2001));
        zbiorFilmow.dodajElement(new Film("Shrek 2", 2002));
        zbiorFilmow.dodajElement(new Film("Shrek 3", 2003));

        System.out.println("Zbiór filmów: ");
        System.out.println(zbiorFilmow.toString());

        Set<Film> zbiorFilmow2 = new Set<>(5);
        zbiorFilmow2.dodajElement(new Film("ToyStory", 2008));
        zbiorFilmow2.dodajElement(new Film("ToyStory 2", 2010));

        Set<Film> zbiorPolaczony = zbiorFilmow.dodajElementy(zbiorFilmow2);
        System.out.println("Zbiór połączony:");
        System.out.println(zbiorPolaczony.toString());

        Set<Film> zbiorPrzeciecie = zbiorFilmow.przeciecie(zbiorFilmow2);
        System.out.println("Zbiór przecięcia:");
        System.out.println(zbiorPrzeciecie.toString());
    }
}