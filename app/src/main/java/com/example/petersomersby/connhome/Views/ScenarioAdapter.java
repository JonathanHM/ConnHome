package com.example.petersomersby.connhome.Views;

import android.app.AlertDialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.Network.SendOverNetwork;
import com.example.petersomersby.connhome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Peter Somersby on 31-05-2017.
 */

public class ScenarioAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ScenarioModel> mDataSource;

    public ScenarioAdapter(Context context, List<ScenarioModel> scenarios) {
        mContext = context;
        mDataSource = scenarios;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataSource.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.list_item_scenario, parent, false);

        // Get title element
        TextView titleTextView = (TextView) rowView.findViewById(R.id.scenario_list_title);

        // Get description element
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.scenario_list_description);

        // Get button element
        Button runButton = (Button) rowView.findViewById(R.id.scenario_list_buttonRun);

        // Get Delete Button Element
        ImageView deleteButton = (ImageView) rowView.findViewById(R.id.deleteScenarioBtn);

        final ScenarioModel scenario = (ScenarioModel) getItem(position);

        titleTextView.setText(scenario.getName());
        descriptionTextView.setText(scenario.getDescription());

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, List<Integer>> clients = new HashMap();
                List<Integer> pinNumbers;
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                databaseAccess.open();

                for (int deviceID :
                        scenario.getDevice_ids()) {
                    DeviceModel device = databaseAccess.getDevice(deviceID);
                    String client_ipAddress = databaseAccess.getClient(device.getClient_id()).getIp_address();
                    int pinNumber = device.getPinNumber();

                    if (!clients.containsKey(client_ipAddress)) {
                        pinNumbers = new ArrayList<>();
                        pinNumbers.add(pinNumber);
                        clients.put(client_ipAddress, pinNumbers);
                    } else {
                        pinNumbers = clients.get(client_ipAddress);
                        pinNumbers.add(pinNumber);
                    }
                }

                databaseAccess.close();

                for (String ipAddress :
                        clients.keySet()) {
                    List<Integer> pins = clients.get(ipAddress);
                    String toSend = pins.size() == 1 ? "S," : "M,";

                    for (int i = 0; i < pins.size(); i++) {
                        if (i == 0) {
                            toSend += pins.get(0);
                        } else {
                            toSend += "+" + pins.get(i);
                        }

                        if (i == pins.size() - 1) {
                            toSend += "+";
                        }
                    }

                    SendOverNetwork sendOverNetwork = new SendOverNetwork();
                    sendOverNetwork.execute(ipAddress, toSend);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View snackBarView = view;
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Are you sure you wanna delete Scenario?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                        databaseAccess.open();
                        databaseAccess.deleteScenario(scenario.getId());
                        databaseAccess.close();
                        Snackbar.make(snackBarView, "Scenario " + scenario.getName() + " Deleted", Snackbar.LENGTH_LONG).show();
                        mDataSource.remove(scenario);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        return rowView;
    }
}
