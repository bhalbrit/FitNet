package de.edvschule_plattling.fitnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.edvschule_plattling.fitnet.klassen.Trainingsplaene;
import de.edvschule_plattling.fitnet.klassen.Trainingsplan;
import de.edvschule_plattling.fitnet.klassen.Uebung;


public class Uebung_erstellen extends AppCompatActivity {

    private EditText bez;
    private EditText besch;
    private Uebung mItem;
    private boolean aenderung = false;
    private Trainingsplan trainingsplan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebung_erstellen);
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
        String nr = intent.getStringExtra("nr");
        trainingsplan = Trainingsplaene.getbyId(Integer.parseInt(nr));
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(UebungDetailFragment.ARG_ITEM_ID)) {
                String item_id =
                extras.getString(UebungDetailFragment.ARG_ITEM_ID);
                mItem = Trainingsplaene.UEBUNG_MAP.get(item_id);
                bez.setText(mItem.getBezeichnung());
                besch.setText(mItem.getBeschreibung());
                aenderung = true;
                setTitle(R.string.titel_uebung_bearbeiten);

            }
        }

        final Button button = (Button) findViewById(R.id.buttonspeichernuebung);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Ueberprueft ob alle Felder einen Wert enthalten
                if (bez.getText().toString().equals("") || besch.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.fehler_alle_felder_asfuellen, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    if (!aenderung) {
                        //Speichert eine neue Uebung ein
                        mItem = new Uebung(Trainingsplaene.UEBUNG_MAP.size() + 1, bez.getText().toString(), besch.getText().toString());
                        trainingsplan.uebungen_keys.add(String.valueOf(mItem.getId()));
                        Trainingsplaene.UEBUNG_MAP.put(String.valueOf(mItem.getId()), mItem);

                    } else {
                        //Aktualisiert eine vorhandene Uebung
                        mItem.setBeschreibung(besch.getText().toString());
                        mItem.setBezeichnung(bez.getText().toString());

                    }

                    finish();


                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
