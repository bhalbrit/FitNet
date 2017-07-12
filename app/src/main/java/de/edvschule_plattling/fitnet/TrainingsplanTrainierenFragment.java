package de.edvschule_plattling.fitnet;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.edvschule_plattling.fitnet.klassen.Trainingsplaene;
import de.edvschule_plattling.fitnet.klassen.Trainingsplan;
import de.edvschule_plattling.fitnet.klassen.Uebung;

import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.TRAININGSPLAN_MAP;
import static de.edvschule_plattling.fitnet.klassen.Trainingsplaene.trainingsplaene;


public class TrainingsplanTrainierenFragment extends Fragment {


    public static final String ARG_ITEM_ID2 = "item_id";

    private String bezeichnung;
    private String beschreibung;
    private boolean btnanzeigen;
    private Activity activity;


    public TrainingsplanTrainierenFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Die Paramter des Arguments auslesen
        bezeichnung = getArguments().getString("bez").toString();
        beschreibung = getArguments().getString("besch").toString();
        btnanzeigen = getArguments().getBoolean("letztes");

        activity = this.getActivity();

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(bezeichnung);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trainingsplan_trainieren, container, false);

        //Textview befüllen
        if (beschreibung != null) {
            ((TextView) rootView.findViewById(R.id.trainingsplan_uebung_bez)).setText(beschreibung);
        }
        // beenden-button anzeigen, wenn es die letze Übungs ist.
        if (btnanzeigen == true) {
            rootView.findViewById(R.id.buttonbeenden).setVisibility(View.VISIBLE);
            final Button button = (Button) rootView.findViewById(R.id.buttonbeenden);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //TODO datum

                    activity.finish();

                }
            });
        }
        return rootView;
    }
}

