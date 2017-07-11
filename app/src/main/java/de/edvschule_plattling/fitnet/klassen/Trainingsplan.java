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

    private List<String> uebungen_keys = new ArrayList<>();

    private List<Trainingseinheit> trainingseinheiten=new ArrayList<>();

    public Trainingsplan(int id, String bezeichnung) {
        this(id, bezeichnung, new ArrayList<String>() );
    }

    public Trainingsplan(int id, String bezeichnung,List<String> uebungenKeys) {
        this(id, bezeichnung,uebungenKeys, new ArrayList<Trainingseinheit>() );
    }

    public Trainingsplan(int id, String bezeichnung,List<String> uebungenKeys, List<Trainingseinheit> pTrainingseinheiten) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.uebungen_keys=uebungenKeys;
        this.trainingseinheiten=pTrainingseinheiten;
    }
    /*
    Liefert die Trainingseinheit wann das letzte mal trainiert wurde
     */
    public Trainingseinheit getLatestTraining(){
        if(!trainingseinheiten.isEmpty()){
            Trainingseinheit latest=trainingseinheiten.get(0);
            for (Trainingseinheit current:trainingseinheiten) {
                    if(current.getTrainingstag().after(latest.getTrainingstag())){
                        latest=current;
                    }
            }
            return latest;
        }
        return null;

    }

    public void addTrainingseinheit(Trainingseinheit trainingseinheiten) {
        this.trainingseinheiten.add(trainingseinheiten);
    }

    public List<Trainingseinheit> getTrainingseinheiten() {
        return trainingseinheiten;
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

