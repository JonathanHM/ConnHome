package com.example.petersomersby.connhome.Views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.petersomersby.connhome.Activitys.AddScenarioActivity;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Somersby on 31-05-2017.
 */

public class SelectDeviceDialogFragment extends DialogFragment {

    private SelectDeviceListener listener;

    public interface SelectDeviceListener {
        void onReturnValue(List<DeviceModel> value);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        databaseAccess.open();
        final List<DeviceModel> devices = databaseAccess.getDevices();
        databaseAccess.close();
        final String[] deviceTitles = new String[devices.size()];

        int i = 0;

        for (DeviceModel device :
                devices) {
            deviceTitles[i] = device.getTitle();
            i++;
        }

        final List<DeviceModel> mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select devices to use")
                .setMultiChoiceItems(deviceTitles, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(devices.get(i));
                        } else if (mSelectedItems.contains(devices.get(i))) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(devices.get(i));
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onReturnValue(mSelectedItems);
                        if (getFragmentManager() != null ) dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
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
            listener = (SelectDeviceListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement EditNameDialogListener");
        }
    }
}
