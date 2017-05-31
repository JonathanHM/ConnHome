package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.R;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class Tab2Devices extends Fragment {

    private ListView deviceListView;
    private FloatingActionButton addDeviceButton;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab2devices, container, false);
        deviceListView = (ListView) rootView.findViewById(R.id.deviceListViewTab2);
        addDeviceButton = (FloatingActionButton) rootView.findViewById(R.id.addDeviceTab2);
        mContext = getActivity().getApplicationContext();

        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
                databaseAccess.open();
                List<DeviceModel> devices = databaseAccess.getDevices();
                databaseAccess.close();
                DeviceAdapter adapter = new DeviceAdapter(mContext, devices);
                deviceListView.setAdapter(adapter);
            }
        });

        return rootView;
    }
}
