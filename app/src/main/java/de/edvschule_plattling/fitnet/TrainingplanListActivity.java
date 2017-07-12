package de.edvschule_plattling.fitnet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.edvschule_plattling.fitnet.klassen.Trainingseinheit;
import de.edvschule_plattling.fitnet.klassen.Trainingsplaene;
import de.edvschule_plattling.fitnet.klassen.Trainingsplan;

import java.text.SimpleDateFormat;
import java.util.List;

import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.TRAININGSPLAN_MAP;
import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.UEBUNG_MAP;

public class TrainingplanListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Intent intent;
    private Intent intent2;
    private Intent intenttraining;
    private SimpleItemRecyclerViewAdapter.ViewHolder Vholder;
    AlertDialog.Builder builder;


    private SharedPreferences keyValues;
    private SharedPreferences.Editor keyValuesEditor;

    private final SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onResume() {
        super.onResume();
        View recyclerView = findViewById(R.id.trainingplan_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingplan_list);

        builder = new AlertDialog.Builder(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        intent = new Intent(this, Trainingsplan_erstellen.class);
        intent2 = new Intent(this, UebungListActivity.class);
        intenttraining = new Intent(this, TraingsplanTrainieren.class);

        //Button zur Traingsplan erstellen Activity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (findViewById(R.id.trainingplan_detail_container) != null) {
            mTwoPane = true;
        }

        //Lädt Pläne aus shared prefs
        Trainingsplaene.laden(getSharedPreferences("SharedUebungen", Context.MODE_PRIVATE));

        keyValues =getSharedPreferences("SharedUebungen", Context.MODE_PRIVATE);
        keyValuesEditor = keyValues.edit();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(Trainingsplaene.trainingsplaene));
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Trainingsplan> mValues;

        public SimpleItemRecyclerViewAdapter(List<Trainingsplan> items) {
            mValues = items;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trainingplan_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(mValues.get(position).getBezeichnung());
            Trainingseinheit latest=mValues.get(position).getLatestTraining();
            if(latest==null){
                holder.mLatest.setText(getString(R.string.bisher_unbenutzt));
            }else{
                holder.mLatest.setText(sdf.format(latest.getTrainingstag()));
            }



            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        startActivity(intent2);
                    } else {
                        //Prüfen, ob der Trainingsplan Übungen enthält
                        if (holder.mItem.getUebungen_keys().size() == 0) {
                            Toast toast = Toast.makeText(getApplicationContext(), R.string.uebung_hinzufügen, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {

                            builder.setTitle(R.string.start_training);

                            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            // Dialogbox erscheinen lassen mit 'JA', 'NEIN' auswahl
                                public void onClick(DialogInterface dialog, int which) {
                                    intenttraining.putExtra(TrainingsplanTrainierenFragment.ARG_ITEM_ID2, String.valueOf(holder.mItem.getId()));
                                    startActivity(intenttraining);

                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();


                        }
                    }
                }

            });

            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Vholder = holder;
                    registerForContextMenu(v);
                    openContextMenu(v);
                    unregisterForContextMenu(v);

                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final TextView mLatest;
            public Trainingsplan mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mLatest = (TextView) view.findViewById(R.id.latest);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Traingsplan löschen");
        menu.add(0, v.getId(), 0, "Traingsplan bearbeiten");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Traingsplan löschen")) {
            loeschetraingsplan();
        } else if (item.getTitle().equals("Traingsplan bearbeiten")) {
            bearbeiteplan();
        } else {
            return false;
        }
        return true;
    }

    public void loeschetraingsplan() {
        Trainingsplan trainingsplan = Vholder.mItem;
        Trainingsplaene.trainingsplaene.remove(trainingsplan);
       // Trainingsplaene.TRAININGSPLAN_MAP.remove(trainingsplan);
       // Toast.makeText(this, "Trainingsplan '" + trainingsplan.getBezeichnung() + " ' gelöscht", Toast.LENGTH_SHORT).show();
        Trainingsplaene.TRAININGSPLAN_MAP.remove(String.valueOf(trainingsplan.getId()));

        //Zugehörige Übungen Löschen
        for(String uebungBez:trainingsplan.getUebungen_keys()){
            Trainingsplaene.UEBUNG_MAP.remove(uebungBez);
        }
        Trainingsplaene.weggschreiben(UEBUNG_MAP,TRAININGSPLAN_MAP,keyValuesEditor,getApplicationContext());

        Toast.makeText(this,"Trainingsplan '" + trainingsplan.getBezeichnung() + " ' gelöscht", Toast.LENGTH_SHORT).show();
        onResume();

    }

    public void bearbeiteplan() {
        intent2.putExtra("nr", String.valueOf(Vholder.mItem.getId()));
        startActivity(intent2);
    }
}

