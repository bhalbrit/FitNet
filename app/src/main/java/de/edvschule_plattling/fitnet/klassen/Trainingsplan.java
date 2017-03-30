package de.edvschule_plattling.fitnet.klassen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Trainingsplan {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Uebung> uebungen = new ArrayList<Uebung>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Uebung> TRAININGSPLAN_MAP = new HashMap<String, Uebung>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
          //  addItem(createTrainingsplan(i));
        }
    }

    private static void addItem(Uebung item) {
        uebungen.add(item);
        TRAININGSPLAN_MAP.put(String.valueOf(item.getId()), item);
    }

    private static Uebung createTrainingsplan(int position) {
        return new Uebung(position,"Bankdrücken","man drückt auf der Bank");
    }


    /**
     * A dummy item representing a piece of content.
     */

}
