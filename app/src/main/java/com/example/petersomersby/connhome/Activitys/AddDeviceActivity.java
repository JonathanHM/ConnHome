package com.example.petersomersby.connhome.Activitys;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.R;
import com.example.petersomersby.connhome.Views.SelectTypeDialogFragment;

public class AddDeviceActivity extends AppCompatActivity implements SelectTypeDialogFragment.SelectTypeListener {

    private EditText deviceTitle;
    private EditText deviceDescription;
    private EditText devicePinNumber;
    private EditText deviceType;
    private Context mContext;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        deviceTitle = (EditText) findViewById(R.id.input_deviceTitle);
        deviceDescription = (EditText) findViewById(R.id.input_deviceDescription);
        devicePinNumber = (EditText) findViewById(R.id.input_devicePinNumber);
        deviceType = (EditText) findViewById(R.id.input_deviceType);
        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                databaseAccess.open();
                DeviceModel device = new DeviceModel();
                device.setTitle(deviceTitle.getText().toString());
                device.setDescription(deviceDescription.getText().toString());
                device.setPinNumber(Integer.parseInt(devicePinNumber.getText().toString()));
                device.setClient_id(0);

                switch (deviceType.getText().toString()) {
                    case "Light":
                        device.setType(DeviceModel.Type.LIGHT);
                        break;
                    case "Gate":
                        device.setType(DeviceModel.Type.GATE);
                        break;
                    case "Door Lock":
                        device.setType(DeviceModel.Type.DOORLOCK);
                        break;
                }

                databaseAccess.insertDevice(device);
                databaseAccess.close();
                finish();
            }
        });

        deviceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new SelectTypeDialogFragment();
                dialog.show(getFragmentManager(), "SelectTypeFragment");
            }
        });
    }

    @Override
    public void onReturnValue(String value) {
        deviceType.setText(value);
    }
}
