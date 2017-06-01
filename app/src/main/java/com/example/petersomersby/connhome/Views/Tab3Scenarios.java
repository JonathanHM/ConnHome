package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.petersomersby.connhome.Activitys.AddDeviceActivity;
import com.example.petersomersby.connhome.Activitys.AddScenarioActivity;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.R;

import java.util.List;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class Tab3Scenarios extends Fragment {

    private FloatingActionButton fab;
    private Context mContext;
    private ListView scenarioListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3scenarios, container, false);

        mContext = getActivity().getApplicationContext();
        fab = (FloatingActionButton) rootView.findViewById(R.id.addScenarioTab3);
        scenarioListView = (ListView) rootView.findViewById(R.id.scenarioListTab3);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
        databaseAccess.open();
        List<ScenarioModel> scenarios = databaseAccess.getScenarios();
        databaseAccess.close();

        ScenarioAdapter adapter = new ScenarioAdapter(mContext, scenarios);
        scenarioListView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addScenarioScreen = new Intent(mContext, AddScenarioActivity.class);
                startActivity(addScenarioScreen);
            }
        });

        return rootView;
    }
}
