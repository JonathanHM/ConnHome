package com.example.petersomersby.connhome.Views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 10-06-2017.
 */

public class SelectScenarioDialogFragment extends DialogFragment {

    private SelectScenarioListener listener;

    public interface SelectScenarioListener {
        void onScenarioReturnValue(List<ScenarioModel> value);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        databaseAccess.open();
        final List<ScenarioModel> scenarios = databaseAccess.getScenarios();
        databaseAccess.close();
        final String[] scenarioTitles = new String[scenarios.size()];

        for (int i = 0; i < scenarios.size(); i++) {
            scenarioTitles[i] = scenarios.get(i).getName();
        }

        final List<ScenarioModel> mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select scenarios to use")
                .setMultiChoiceItems(scenarioTitles, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(scenarios.get(i));
                        } else if (mSelectedItems.contains(scenarios.get(i))) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(scenarios.get(i));
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onScenarioReturnValue(mSelectedItems);
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
            listener = (SelectScenarioListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement SelectScenarioListener");
        }
    }
}
