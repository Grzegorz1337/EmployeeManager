package client;

public class clientInformation {
    private String imie;
    private String nazwisko;
    private String kontakt;

    public clientInformation(String imie, String nazwisko, String kontakt) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.kontakt = kontakt;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }
}
