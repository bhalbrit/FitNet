package de.edvschule_plattling.fitnet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.edvschule_plattling.fitnet.klassen.Trainingseinheit;
import de.edvschule_plattling.fitnet.klassen.Trainingsplaene;
import de.edvschule_plattling.fitnet.klassen.Trainingsplan;
import de.edvschule_plattling.fitnet.klassen.Uebung;

import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.TRAININGSPLAN_MAP;
import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.UEBUNG_MAP;

public class TraingsplanTrainieren extends AppCompatActivity {

    private Trainingsplan mItem;
    private int zaehler = 0;
    private List<Uebung> uebungen = new LinkedList<Uebung>();

    private SharedPreferences keyValues;
    private SharedPreferences.Editor keyValuesEditor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle arguments = new Bundle();
            TrainingsplanTrainierenFragment fragment = new TrainingsplanTrainierenFragment();

            switch (item.getItemId()) {
                case R.id.navigation_weiter:

                    if (zaehler != uebungen.size() - 1) {
                        zaehler++;
                        //Dem Fragment die parameter mitgeben
                        arguments.putString("bez", uebungen.get(zaehler).getBezeichnung());
                        arguments.putString("besch", uebungen.get(zaehler).getBeschreibung());
                        //Feststellen ob es die letzte Übung ist
                        if (zaehler == uebungen.size() - 1) {
                            arguments.putBoolean("letztes", true);
                        } else {
                            arguments.putBoolean("letztes", false);
                        }

                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenttraining, fragment)
                                .commit();

                    }

                    return true;
                case R.id.navigation_zurueck:

                    if (zaehler != 0) {
                        if (zaehler != 0) {
                            zaehler--;
                        }
                        arguments.putString("bez", uebungen.get(zaehler).getBezeichnung());
                        arguments.putString("besch", uebungen.get(zaehler).getBeschreibung());
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.contenttraining, fragment)
                                .commit();
                    }
                    return true;
            }
            return false;
        }

    };




    public void datumWegschreiben(){
        keyValues = getSharedPreferences("SharedUebungen", Context.MODE_PRIVATE);
        keyValuesEditor = keyValues.edit();
        mItem.addTrainingseinheit(new Trainingseinheit(new Date()));
        Trainingsplaene.weggschreiben(UEBUNG_MAP, TRAININGSPLAN_MAP, keyValuesEditor, getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traingsplan_trainieren);


        mItem = Trainingsplaene.TRAININGSPLAN_MAP.get(getIntent().getStringExtra(TrainingsplanTrainierenFragment.ARG_ITEM_ID2));

        //Alle Übungen für den jeweiligen Trainingsplan finden
        for (String key : mItem.getUebungen_keys()) {
            if (UEBUNG_MAP.containsKey(key)) {
                uebungen.add(UEBUNG_MAP.get(key));
            }
        }

        //Die erste Übung gleich als Fragment laden
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Bundle arguments = new Bundle();
        TrainingsplanTrainierenFragment fragment = new TrainingsplanTrainierenFragment();
        arguments.putString("bez", uebungen.get(0).getBezeichnung());
        arguments.putString("besch", uebungen.get(0).getBeschreibung());
        if (uebungen.size() == 1) {
            arguments.putBoolean("letztes", true);
        } else {
            arguments.putBoolean("letztes", false);
        }
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenttraining, fragment)
                .commit();
    }


}


