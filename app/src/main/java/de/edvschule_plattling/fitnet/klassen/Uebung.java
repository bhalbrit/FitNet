package de.edvschule_plattling.fitnet.klassen;

/**
 * Created by Benedikt on 25.03.2017.
 */

public class Uebung {

    private int id;
    private String bezeichnung;
    private String beschreibung;


    public Uebung(int id, String bezeichnung, String beschreibung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        return "Uebung{" +
                "id=" + id +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }
}
