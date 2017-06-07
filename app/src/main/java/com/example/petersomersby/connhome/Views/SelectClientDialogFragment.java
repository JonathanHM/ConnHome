package com.example.petersomersby.connhome.Views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;

import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;

import java.util.List;

/**
 * Created by Peter Somersby on 07-06-2017.
 */

public class SelectClientDialogFragment extends DialogFragment {

    private SelectClientListener listener;

    public interface SelectClientListener {
        public void returnValue(ClientModel value);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        databaseAccess.open();

        final List<ClientModel> clients = databaseAccess.getClients();

        databaseAccess.close();

        final String[] clientTitles = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            clientTitles[i] = clients.get(i).getTitle();
        }

        builder.setTitle("Pick a client")
                .setItems(clientTitles, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.returnValue(clients.get(i));
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (SelectClientListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement SelectClientListener");
        }
    }
}
