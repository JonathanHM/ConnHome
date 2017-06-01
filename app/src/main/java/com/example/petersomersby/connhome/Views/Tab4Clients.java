package com.example.petersomersby.connhome.Views;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.petersomersby.connhome.Network.Networking;
import com.example.petersomersby.connhome.R;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class Tab4Clients extends Fragment {

    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4clients, container, false);

        button = (Button) rootView.findViewById(R.id.sendButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendOverNetwork obj = new SendOverNetwork();
                obj.execute();
            }
        });

        return rootView;
    }

    public class SendOverNetwork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Networking networking = new Networking(9000, "192.168.1.172");
                networking.send("Hva så din gamle luder");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String page) {

        }
    }
}
