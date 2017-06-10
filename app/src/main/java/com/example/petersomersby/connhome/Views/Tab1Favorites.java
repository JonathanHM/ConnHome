package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.petersomersby.connhome.Activitys.AddFavoritesActivity;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.Network.Networking;
import com.example.petersomersby.connhome.Network.SendOverNetwork;
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

    private ListView scenarioListView;
    private ListView deviceListView;
    private DatabaseAccess databaseAccess;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1favorites, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabtab1);
        scenarioListView = (ListView) rootView.findViewById(R.id.scenario_list);
        deviceListView = (ListView) rootView.findViewById(R.id.device_list);
        databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());

        databaseAccess.open();
        Pair<List<DeviceModel>, List<ScenarioModel>> lists = databaseAccess.getFavorites();
        databaseAccess.close();

        ScenarioAdapter scenarioAdapter = new ScenarioAdapter(getContext(), lists.second);
        DeviceAdapter deviceAdapter = new DeviceAdapter(getContext(), lists.first);
        scenarioListView.setAdapter(scenarioAdapter);
        deviceListView.setAdapter(deviceAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getContext(), AddFavoritesActivity.class);
                startActivity(mIntent);
            }
        });
        return rootView;
    }
}
