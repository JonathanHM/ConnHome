package com.example.petersomersby.connhome.Views;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.R;

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
                databaseAccess.deleteDevice(6);
                databaseAccess.close();
                text.setText("Inserted device");
            }
        });
        return rootView;
    }
}
