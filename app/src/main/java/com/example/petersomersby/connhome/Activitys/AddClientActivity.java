package com.example.petersomersby.connhome.Activitys;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.R;

public class AddClientActivity extends AppCompatActivity {

    private EditText clientTitle;
    private EditText clientIpAddress;
    private RelativeLayout relativeLayout;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        mContext = getApplicationContext();
        clientTitle = (EditText) findViewById(R.id.input_clientTitle);
        clientIpAddress = (EditText) findViewById(R.id.input_clientIpAddress);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
    }

    public void saveClient(View view) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
        databaseAccess.open();

        ClientModel client = new ClientModel();
        client.setTitle(String.valueOf(clientTitle.getText()));
        client.setIp_address(String.valueOf(clientIpAddress.getText()));

        databaseAccess.insertClient(client);
        databaseAccess.close();
        finish();
    }
}
