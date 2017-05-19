package de.edvschule_plattling.fitnet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import de.edvschule_plattling.fitnet.klassen.Trainingsplaene;
import de.edvschule_plattling.fitnet.klassen.Trainingsplan;
import de.edvschule_plattling.fitnet.klassen.Uebung;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Trainingsplaene. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link UebungDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class UebungListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Intent intent;
    private Trainingsplan trainingsplan;


    @Override
    protected void onResume() {
        super.onResume();

        View recyclerView = findViewById(R.id.uebung_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebung_list);


        Intent intent2 = getIntent();

        String nr = intent2.getStringExtra("nr");

        trainingsplan = Trainingsplaene.getbyId(Integer.parseInt(nr));

        setTitle(trainingsplan.getBezeichnung());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        intent = new Intent(this, Uebung_erstellen.class);
        intent.putExtra("nr", String.valueOf(trainingsplan.getId()));

        //button für Übung erstellen Activity
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

        //akt: test
        // View recyclerView = findViewById(R.id.uebung_list);
        // assert recyclerView != null;
        // setupRecyclerView((RecyclerView) recyclerView);


        if (findViewById(R.id.uebung_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(trainingsplan.getUebungen_keys()));
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


        private List<Uebung> mValues = new ArrayList<>();

        public SimpleItemRecyclerViewAdapter(List<String> items) {
            //Aus shared prefs laden

            //Übungen filtern und schauen, welche Übungen zu diesem Traingsplan gehören
            for (String item : items) {
                if (Trainingsplaene.UEBUNG_MAP.containsKey(item)) {
                    mValues.add(Trainingsplaene.UEBUNG_MAP.get(item));
                }

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.uebung_list_content, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(mValues.get(position).getBezeichnung());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(UebungDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                        UebungDetailFragment fragment = new UebungDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.uebung_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();

                        Intent intent = new Intent(context, UebungDetailActivity.class);
                        intent.putExtra(UebungDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
                        intent.putExtra("nr", String.valueOf(trainingsplan.getId()));
                        context.startActivity(intent);
                    }
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
            public Uebung mItem;

            public ViewHolder(View view) {//Kommentar
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
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
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
