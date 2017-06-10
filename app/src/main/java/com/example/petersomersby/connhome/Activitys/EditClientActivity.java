package com.example.petersomersby.connhome.Activitys;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.R;
import com.example.petersomersby.connhome.Views.DeviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditClientActivity extends AppCompatActivity {

    private EditText clientTitle;
    private EditText clientIpAddress;
    private ListView deviceListView;
    private DatabaseAccess databaseAccess;
    private int clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        clientTitle = (EditText) findViewById(R.id.input_clientTitle);
        clientIpAddress = (EditText) findViewById(R.id.input_clientIpAddress);
        deviceListView = (ListView) findViewById(R.id.deviceListView);
        Intent mIntent = getIntent();
        clientId = mIntent.getIntExtra("clientId", 0);

        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ClientModel client = databaseAccess.getClient(clientId);
        clientTitle.setText(client.getTitle());
        clientIpAddress.setText(client.getIp_address());
        List<DeviceModel> devices = databaseAccess.getDevicesForClient(clientId);
        databaseAccess.close();

        DeviceAdapter deviceAdapter = new DeviceAdapter(getApplicationContext(), devices);
        deviceListView.setAdapter(deviceAdapter);
    }

    public void saveChangesClient(View v) {
        databaseAccess.open();
        ClientModel client = new ClientModel();
        client.setIp_address(String.valueOf(clientIpAddress.getText()));
        client.setId(clientId);
        client.setTitle(String.valueOf(clientTitle.getText()));
        databaseAccess.updateClient(client);
        databaseAccess.close();
        finish();
    }
}
