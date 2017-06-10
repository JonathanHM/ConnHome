package com.example.petersomersby.connhome.Views;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.petersomersby.connhome.Activitys.AddClientActivity;
import com.example.petersomersby.connhome.Activitys.AddScenarioActivity;
import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Network.Networking;
import com.example.petersomersby.connhome.R;

import java.util.List;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class Tab4Clients extends Fragment {

    private FloatingActionButton fab;
    private Context mContext;
    private ListView clientListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4clients, container, false);

        mContext = getActivity().getApplicationContext();
        clientListView = (ListView) rootView.findViewById(R.id.clientListTab4);
        fab = (FloatingActionButton) rootView.findViewById(R.id.addClientTab4);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
        databaseAccess.open();

        List<ClientModel> clients = databaseAccess.getClients();

        databaseAccess.close();

        ClientAdapter adapter = new ClientAdapter(getContext(), clients);
        clientListView.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addClientScreen = new Intent(mContext, AddClientActivity.class);
                startActivity(addClientScreen);
            }
        });

        return rootView;
    }
}
