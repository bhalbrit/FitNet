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

import de.edvschule_plattling.fitnet.klassen.Uebung;

import static de.edvschule_plattling.fitnet.klassen.Trainingsplan.TRAININGSPLAN_MAP;
import static de.edvschule_plattling.fitnet.klassen.Trainingsplan.uebungen;


public class Trainingsplan_erstellen extends AppCompatActivity {

       private EditText bez;
       private EditText besch;


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


        final Button button = (Button) findViewById(R.id.buttonspeichernplan);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(bez.getText().toString().equals("") || besch.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.fehler_alle_felder_asfuellen,Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Uebung u1 = new Uebung(TRAININGSPLAN_MAP.size() + 1, bez.getText().toString(), besch.getText().toString());
                    uebungen.add(u1);
                    TRAININGSPLAN_MAP.put(String.valueOf(u1.getId()), u1);
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
