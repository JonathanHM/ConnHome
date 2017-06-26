package com.example.petersomersby.connhome.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petersomersby.connhome.Activitys.EditClientActivity;
import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.R;

import java.util.List;

/**
 * Created by Peter Somersby on 07-06-2017.
 */

public class ClientAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ClientModel> mDataSource;

    public ClientAdapter(Context context, List<ClientModel> clients) {
        mContext = context;
        mDataSource = clients;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataSource.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.list_item_client, parent, false);

        // Get title element
        TextView titleTextView = (TextView) rowView.findViewById(R.id.client_list_title);

        // Get description element
        TextView ipAddressTextView = (TextView) rowView.findViewById(R.id.client_list_ipaddress);

        // Get edit button element
        Button editButton = (Button) rowView.findViewById(R.id.client_list_editButton);

        // Get thumbnail element
        ImageView deleteBtn = (ImageView) rowView.findViewById(R.id.client_list_deleteBtn);

        final ClientModel client = (ClientModel) getItem(position);

        titleTextView.setText(client.getTitle());
        ipAddressTextView.setText(client.getIp_address());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editDeviceIntent = new Intent(mContext, EditClientActivity.class);
                editDeviceIntent.putExtra("clientId", client.getId());
                mContext.startActivity(editDeviceIntent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View snackBarView = view;
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Are you sure you wanna delete Client?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                        databaseAccess.open();
                        databaseAccess.deleteClient(client.getId());
                        databaseAccess.close();
                        Snackbar.make(snackBarView, "Client " + client.getTitle() + " Deleted", Snackbar.LENGTH_LONG).show();
                        mDataSource.remove(client);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        return rowView;
    }
}
