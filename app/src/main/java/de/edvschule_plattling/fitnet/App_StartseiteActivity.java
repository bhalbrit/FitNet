package de.edvschule_plattling.fitnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class App_StartseiteActivity extends AppCompatActivity {

    private Intent intent;
    private Button buttontraingsplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_startseite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getTitle());
        buttontraingsplan = (Button) findViewById(R.id.button);
        intent = new Intent(this, TrainingplanListActivity.class);


        //Startbutton für Traingsplan Activity
        buttontraingsplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


    }

}
