package com.example.petersomersby.connhome.Views;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class Tab1Favorites extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1favorites, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabtab1);
        final TextView text = (TextView) rootView.findViewById(R.id.section_labeltab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
                databaseAccess.open();
                ScenarioModel scenarioModel = new ScenarioModel();
                scenarioModel.setDescription("day and night");
                scenarioModel.setName("datatatea");
                List<Integer> list = new ArrayList<Integer>();
                list.add(1);
                list.add(2);
                list.add(3);
                scenarioModel.setDevice_ids(list);
                databaseAccess.insertScenario(scenarioModel);
                databaseAccess.close();
                text.setText("Inserted scenario");
            }
        });
        return rootView;
    }
}
