package com.example.petersomersby.connhome.Views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.petersomersby.connhome.R;

/**
 * Created by Peter Somersby on 31-05-2017.
 */

public class SelectTypeDialogFragment extends DialogFragment {

    public interface SelectTypeListener {
        public void onReturnValue(String value);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] types = getResources().getStringArray(R.array.types_array);

        builder.setTitle("Pick a type")
                .setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SelectTypeListener listener = (SelectTypeListener) getActivity();
                        listener.onReturnValue(types[i]);
                    }
                });
        return builder.create();
    }
}
