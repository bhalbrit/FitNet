package de.edvschule_plattling.fitnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import de.edvschule_plattling.fitnet.klassen.Trainingsplan;
import de.edvschule_plattling.fitnet.klassen.Uebung;

import static de.edvschule_plattling.fitnet.klassen.Trainingsplan.TRAININGSPLAN_MAP;
import static de.edvschule_plattling.fitnet.klassen.Trainingsplan.uebungen;


public class Trainingsplan_erstellen extends AppCompatActivity {

       private EditText bez;
       private EditText besch;
       private Uebung mItem;
    private boolean aenderung=false;



    //Kommentar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsplan_erstellen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bez = (EditText) findViewById(R.id.editText_bezeichnung);
        besch = (EditText) findViewById(R.id.editText_beschreibung);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Lädt werte aus HashMap und trägt sie in Textfelder ein
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(TrainingsplanDetailFragment.ARG_ITEM_ID)) {
                String item_id =
                        extras.getString(TrainingsplanDetailFragment.ARG_ITEM_ID);

                mItem = Trainingsplan.TRAININGSPLAN_MAP.get(item_id);
                bez.setText(mItem.getBezeichnung());
                besch.setText(mItem.getBeschreibung());
                aenderung=true;

            }
        }

        final Button button = (Button) findViewById(R.id.buttonspeichernplan);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Ueberprueft ob alle Felder einen Wert enthalten
                if(bez.getText().toString().equals("") || besch.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.fehler_alle_felder_asfuellen,Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    if(!aenderung){
                        //Speichert eine neue Uebung ein
                        mItem = new Uebung(TRAININGSPLAN_MAP.size() + 1, bez.getText().toString(), besch.getText().toString());
                        uebungen.add(mItem);
                        TRAININGSPLAN_MAP.put(String.valueOf(mItem.getId()), mItem);

                    }else{
                        //Aktualisiert eine vorhandene Uebung
                        mItem.setBeschreibung(besch.getText().toString());
                        mItem.setBezeichnung(bez.getText().toString());
                        //Map<String, Uebung>
                       //         TRAININGSPLAN_MAP2 = new HashMap<String, Uebung>();
                       // TRAININGSPLAN_MAP2=TRAININGSPLAN_MAP;



                    }
                    finish();


                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
