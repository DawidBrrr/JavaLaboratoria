class Produkt implements Comparable<Produkt> {
    private String nazwa;
    private double cena;

    public Produkt(String nazwa, double cena) {
        this.nazwa = nazwa;
        this.cena = cena;
    }

    @Override
    public int compareTo(Produkt o) {
        if (this.cena == o.cena) {
            return this.nazwa.compareTo(o.nazwa);
        }
        return Double.compare(this.cena, o.cena);
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                '}';
    }
}
