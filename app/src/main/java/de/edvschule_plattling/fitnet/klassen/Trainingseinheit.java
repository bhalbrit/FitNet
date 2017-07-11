package de.edvschule_plattling.fitnet.klassen;

import java.util.Date;

/**
 * Created by Markus on 21.06.2017.
 */

public class Trainingseinheit {
    private int id;

    private Date trainingstag;

    //evtl später noch Map um Gewichte ect. pro Übung zu speichern


    public Trainingseinheit(int id, Date trainingstag) {
        this.id = id;
        this.trainingstag = trainingstag;
    }

    public int getId() {
        return id;
    }


    public Date getTrainingstag() {
        return trainingstag;
    }

    public void setTrainingstag(Date trainingstag) {
        this.trainingstag = trainingstag;
    }
}
