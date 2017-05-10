package de.edvschule_plattling.fitnet.klassen;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Benedikt on 26.04.2017.
 */

public class Trainingsplaene {


    public static final List<Trainingsplan> trainingsplaene = new ArrayList<Trainingsplan>();

    public static final Map<String, Trainingsplan> TRAININGSPLAN_MAP = new HashMap<String, Trainingsplan>();

    public static final Map<String, Uebung> UEBUNG_MAP = new HashMap<String, Uebung>();

    @Nullable
    public static Trainingsplan getbyId(int id) {
        for (Trainingsplan t1 : trainingsplaene) {
            if (t1.getId() == id) {
                return t1;
            }
        }
        return null;

    }

}
