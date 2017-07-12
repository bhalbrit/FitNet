package de.edvschule_plattling.fitnet;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.edvschule_plattling.fitnet.klassen.Trainingsplan;

import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.TRAININGSPLAN_MAP;
import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.trainingsplaene;


public class Trainingsplan_erstellen extends AppCompatActivity {

    private EditText bez;
    private Trainingsplan mItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsplan_erstellen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bez = (EditText) findViewById(R.id.editText_bezeichnung);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        final Button button = (Button) findViewById(R.id.buttonspeichernplan);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Ueberprueft ob alle Felder einen Wert enthalten
                if (bez.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.fehler_alle_felder_asfuellen, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    //Speichert eine neuen Trainingsplan ein
                    mItem = new Trainingsplan(TRAININGSPLAN_MAP.size() + 1, bez.getText().toString());
                    trainingsplaene.add(mItem);
                    TRAININGSPLAN_MAP.put(String.valueOf(mItem.getId()), mItem);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
