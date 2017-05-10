package de.edvschule_plattling.fitnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

/**
 * An activity representing a single uebungen_keys detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link UebungListActivity}.
 */
public class UebungDetailActivity extends AppCompatActivity {


    private Intent intent;


    @Override
    protected void onResume() {
        super.onResume();

        //neus Fragment erstellen
        Bundle arguments = new Bundle();
        arguments.putString(UebungDetailFragment.ARG_ITEM_ID,
                getIntent().getStringExtra(UebungDetailFragment.ARG_ITEM_ID));
        UebungDetailFragment fragment = new UebungDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.uebung_detail_container, fragment)
                .commit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebung_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        intent = new Intent(this, Uebung_erstellen.class);

        //Button für Übung bearbeiten Activity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = getIntent();
                String tid = intent2.getStringExtra("nr");
                intent.putExtra("nr", tid);
                intent.putExtra(UebungDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(UebungDetailFragment.ARG_ITEM_ID));
                startActivity(intent);

            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


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
