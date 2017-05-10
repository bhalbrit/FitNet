package de.edvschule_plattling.fitnet.klassen;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class Trainingsplan {

    private int id;

    private String bezeichnung;

    public List<String> uebungen_keys = new ArrayList<>();


    public Trainingsplan(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }


    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }

    public List<String> getUebungen_keys() {
        return uebungen_keys;
    }


    public void setId(int id) {
        this.id = id;
    }
}

