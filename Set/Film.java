class Film implements Comparable<Film> {
    private String tytul;
    private int rokPremiery;

    public Film(String tytul, int rokPremiery) {
        this.tytul = tytul;
        this.rokPremiery = rokPremiery;
    }

    @Override
    public int compareTo(Film o) {
        if (this.rokPremiery == o.rokPremiery) {
            return this.tytul.compareTo(o.tytul);
        }
        return Integer.compare(this.rokPremiery, o.rokPremiery);
    }

    @Override
    public String toString() {
        return "Film{" +
                "tytul='" + tytul + '\'' +
                ", rokPremiery=" + rokPremiery +
                '}';
    }
}