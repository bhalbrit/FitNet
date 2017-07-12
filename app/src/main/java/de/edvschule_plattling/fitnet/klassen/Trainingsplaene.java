package de.edvschule_plattling.fitnet.klassen;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.edvschule_plattling.fitnet.R;

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


    public static void laden(SharedPreferences keyValues) {
        try {

            if (TRAININGSPLAN_MAP.isEmpty() && UEBUNG_MAP.isEmpty()) {
                //Lädt alle Übungen
                JSONArray jsonArray = new JSONArray(keyValues.getString("uebungen", "[]"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    String bez = jsonobject.getString("bezeichnung");
                    String besch = jsonobject.getString("beschreibung");
                    int id = jsonobject.getInt("id");
                    putInHashMap(new Uebung(id, bez, besch));

                }
                //Lädt alle Trainingsplaene
                JSONArray jsonArrayPlaene = new JSONArray(keyValues.getString("trainingsplaene", "[]"));
                for (int i = 0; i < jsonArrayPlaene.length(); i++) {
                    JSONObject jsonobject = jsonArrayPlaene.getJSONObject(i);
                    String bez = jsonobject.getString("bezeichnung");
                    int id = jsonobject.getInt("id");

                    List<String> uebIds = new ArrayList<>();
                    JSONArray jsonArrayUebungId = new JSONArray(jsonobject.getString("uebungen"));
                    for (int b = 0; b < jsonArrayUebungId.length(); b++) {
                        JSONObject jsonuebid = jsonArrayUebungId.getJSONObject(b);
                        String uebId = jsonuebid.getString("id");
                        uebIds.add(uebId);

                    }

                    List<Trainingseinheit> einheiten=new ArrayList<>();
                    JSONArray jsonArrayTrainingseinheiten = new JSONArray(jsonobject.getString("trainingseinheiten"));
                    for (int b = 0; b < jsonArrayTrainingseinheiten.length(); b++) {
                        JSONObject jsonEinheit = jsonArrayTrainingseinheiten.getJSONObject(b);
                       // int uebId = jsonEinheit.getInt("id");
                        Date trainingstag =new Date(jsonEinheit.getLong("tag"));

                        einheiten.add(new Trainingseinheit(trainingstag));
                    }


                    Trainingsplan zw = new Trainingsplan(id, bez, uebIds,einheiten);
                    //Testdaten
                  //  zw.addTrainingseinheit(new Trainingseinheit(10,new Date(2010,10,12)));
                  /// zw.addTrainingseinheit(new Trainingseinheit(10,new Date(2015-1900,10,12)));
                    putInHashMap(zw);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void putInHashMap(Uebung uebung) {
        //in Hash map für aktuellen Betrieb

        Trainingsplaene.UEBUNG_MAP.put(String.valueOf(uebung.getId()), uebung);

    }

    private static void putInHashMap(Trainingsplan plan) {
        //in Hash map für aktuellen Betrieb

        TRAININGSPLAN_MAP.put(String.valueOf(plan.getId()), plan);
        trainingsplaene.add(plan);
    }


    private static void addToTrainingsplan(Uebung uebung, Trainingsplan trainingsplan) {
        //in Hash map für aktuellen Betrieb
        trainingsplan.getUebungen_keys().add(String.valueOf(uebung.getId()));

    }



    public static void weggschreiben(Map<String,Uebung> curmap,Map<String,Trainingsplan> curmapPlaene, SharedPreferences.Editor keyValuesEditor, Context context){
        weggschreibenUebungen(curmap,keyValuesEditor,context);
        weggschreibenTrainingsplaene(curmapPlaene,keyValuesEditor,context);

    }

    private static void weggschreibenUebungen(Map<String, Uebung> curmap, SharedPreferences.Editor keyValuesEditor, Context context) {
        //speichert für nächstes mal mit hilfe von shared preferences
        try {

            JSONArray jsonArrUeb = new JSONArray();
            //alle Übungen in Json Array und in shared prefs speichern
            for (Map.Entry<String, Uebung> entry : curmap.entrySet()) {

                Uebung value = entry.getValue();
                //einzelnen Objekte als JSON-Objekt umwandeln
                JSONObject o = new JSONObject();
                o.put("bezeichnung", value.getBezeichnung());
                o.put("beschreibung", value.getBeschreibung());
                o.put("id", value.getId());
                jsonArrUeb.put(o);
            }

            keyValuesEditor.putString("uebungen", jsonArrUeb.toString());
            keyValuesEditor.commit();

        } catch (JSONException e) {
            Toast toast = Toast.makeText(context, R.string.fehler_abspeichern, Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    private static void weggschreibenTrainingsplaene(Map<String, Trainingsplan> curmap, SharedPreferences.Editor keyValuesEditor, Context context) {
        //speichert für nächstes mal mit hilfe von shared preferences
        try {

            JSONArray jsonArrTrainp = new JSONArray();

            //
            //alle Übungen in Json Array und in shared prefs speichern
            for (Map.Entry<String, Trainingsplan> entry : curmap.entrySet()) {
                JSONArray jsonArrUebId = new JSONArray();
                Trainingsplan value = entry.getValue();
                JSONArray jsonArrTrainingseinheiten=new JSONArray();

                for (String uebId : value.getUebungen_keys()) {
                    JSONObject c = new JSONObject();
                    c.put("id", uebId);
                    jsonArrUebId.put(c);
                }

                for(Trainingseinheit einheit:value.getTrainingseinheiten()){
                    JSONObject c = new JSONObject();
                   // c.put("id",einheit.getId());
                    c.put("tag",einheit.getTrainingstag().getTime());
                    jsonArrTrainingseinheiten.put(c);
                }

                JSONObject o = new JSONObject();
                o.put("bezeichnung",value.getBezeichnung());
                o.put("id",value.getId());
                o.put("uebungen",jsonArrUebId.toString());
                o.put("trainingseinheiten",jsonArrTrainingseinheiten.toString());
                jsonArrTrainp.put(o);

            }

            keyValuesEditor.putString("trainingsplaene", jsonArrTrainp.toString());
            keyValuesEditor.commit();

        } catch (JSONException e) {
            Toast toast = Toast.makeText(context, R.string.fehler_abspeichern, Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    /*
    Speichert Uebung ein
     */
    public static void einfuegen(Uebung uebung, SharedPreferences.Editor keyValuesEditor, Context context) {

        putInHashMap(uebung);

        weggschreibenUebungen(UEBUNG_MAP, keyValuesEditor, context);
    }

    /*
   Speichert Trainingsplan ein
    */
    public static void einfuegen(Trainingsplan plan, SharedPreferences.Editor keyValuesEditor, Context context) {
        //Speichert eine neuen Trainingsplan ein

        trainingsplaene.add(plan);
        putInHashMap(plan);

        weggschreibenTrainingsplaene(TRAININGSPLAN_MAP, keyValuesEditor, context);
    }

    /*
    Speichert Uebung ein und fügt sie dem Trainingsplan hinzu
    */
    public static void einfuegen(Uebung uebung, Trainingsplan trainingsplan, SharedPreferences.Editor keyValuesEditor, Context context) {

        putInHashMap(uebung);
        addToTrainingsplan(uebung, trainingsplan);
        weggschreiben(UEBUNG_MAP, TRAININGSPLAN_MAP, keyValuesEditor, context);
    }


}
