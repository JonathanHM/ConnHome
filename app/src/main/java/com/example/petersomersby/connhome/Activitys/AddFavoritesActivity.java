package com.example.petersomersby.connhome.Activitys;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.R;
import com.example.petersomersby.connhome.Views.SelectDeviceDialogFragment;
import com.example.petersomersby.connhome.Views.SelectScenarioDialogFragment;
import com.example.petersomersby.connhome.Views.SelectTypeDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddFavoritesActivity extends AppCompatActivity implements SelectDeviceDialogFragment.SelectDeviceListener, SelectScenarioDialogFragment.SelectScenarioListener {

    private EditText scenarios;
    private EditText devices;
    private List<Integer> device_ids = new ArrayList();
    private List<Integer> scenario_ids = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);

        scenarios = (EditText) findViewById(R.id.input_scenarios);
        devices = (EditText) findViewById(R.id.input_device);

        scenarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new SelectScenarioDialogFragment();
                dialog.show(getFragmentManager(), "SelectScenarioFragment");
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

    public void saveFavorites(View v) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        for (int device_id :
                device_ids) {
            databaseAccess.addDeviceToFavorites(device_id);
        }

        for (int scenario_id :
                scenario_ids) {
            databaseAccess.addScenarioToFavorites(scenario_id);
        }

        databaseAccess.close();
        finish();
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

    @Override
    public void onScenarioReturnValue(List<ScenarioModel> value) {
        scenario_ids.clear();
        String scenarioTitles = "";
        for (int i = 0; i < value.size(); i++) {
            if (i == 0) {
                scenarioTitles += value.get(i).getName();
            } else {
                scenarioTitles += ", " + value.get(i).getName();
            }
            scenario_ids.add(value.get(i).getId());
        }
        scenarios.setText(scenarioTitles);
    }
}
