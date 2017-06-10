package com.example.petersomersby.connhome.Network;

import android.os.AsyncTask;

/**
 * Created by Jonathan on 10-06-2017.
 */

public class SendOverNetwork extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            Networking networking = new Networking(4545, params[0]);
            networking.send(params[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String page) {

    }
}
