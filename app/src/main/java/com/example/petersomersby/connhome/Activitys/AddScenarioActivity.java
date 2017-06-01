package com.example.petersomersby.connhome.Activitys;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.R;
import com.example.petersomersby.connhome.Views.SelectDeviceDialogFragment;
import com.example.petersomersby.connhome.Views.SelectTypeDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddScenarioActivity extends AppCompatActivity implements SelectDeviceDialogFragment.SelectDeviceListener {

    private EditText scenarioName;
    private EditText scenarioDescription;
    private EditText devices;
    private Context mContext;
    private Button saveButton;
    private List<Integer> device_ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scenario);

        mContext = getApplicationContext();
        scenarioName = (EditText) findViewById(R.id.input_scenarioName);
        scenarioDescription = (EditText) findViewById(R.id.input_scenarioDescription);
        devices = (EditText) findViewById(R.id.input_scenarioDevices);
        saveButton = (Button) findViewById(R.id.saveButton);
        device_ids = new ArrayList();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                databaseAccess.open();
                ScenarioModel scenario = new ScenarioModel();
                scenario.setName(String.valueOf(scenarioName.getText()));
                scenario.setDescription(String.valueOf(scenarioDescription.getText()));
                scenario.setDevice_ids(device_ids);
                databaseAccess.insertScenario(scenario);
                databaseAccess.close();
                finish();
            }
        });

        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new SelectDeviceDialogFragment();
                dialog.show(getFragmentManager(), "SelectDeviceFragment");
            }
        });
    }

    @Override
    public void onReturnValue(List<DeviceModel> value) {
        device_ids.clear();
        String deviceTitles = "";
        boolean isFirst = true;
        for (DeviceModel device :
                value) {
            if (isFirst) {
                deviceTitles += device.getTitle();
                isFirst = false;
            } else {
                deviceTitles += ", " + device.getTitle();
            }
            device_ids.add(device.getId());
        }
        devices.setText(deviceTitles);
    }
}