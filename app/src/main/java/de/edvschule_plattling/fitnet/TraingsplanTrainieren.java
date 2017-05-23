package de.edvschule_plattling.fitnet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class TraingsplanTrainieren extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_weiter:
                    Bundle arguments = new Bundle();
                    arguments.putString(UebungDetailFragment.ARG_ITEM_ID,
                            getIntent().getStringExtra(UebungDetailFragment.ARG_ITEM_ID));
                    TrainingsplanTrainierenFragment fragment = new TrainingsplanTrainierenFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenttraining, fragment)
                            .commit();
                    return true;
                case R.id.navigation_zurueck:
                   // mTextMessage.setText(R.string.title_last);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traingsplan_trainieren);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
