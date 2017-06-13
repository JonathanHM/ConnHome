package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DatabaseAccess;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Network.Networking;
import com.example.petersomersby.connhome.Network.SendOverNetwork;
import com.example.petersomersby.connhome.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Peter Somersby on 31-05-2017.
 */

public class DeviceAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<DeviceModel> mDataSource;


    public DeviceAdapter(Context context, List<DeviceModel> devices) {
        mContext = context;
        mDataSource = devices;
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
        View rowView = mInflater.inflate(R.layout.list_item_device, parent, false);

        // Get title element
        TextView titleTextView = (TextView) rowView.findViewById(R.id.device_list_title);

        // Get description element
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.device_list_description);

        // Get switch element
        final Switch typeSwitch = (Switch) rowView.findViewById(R.id.device_list_onOffSwitch);

        // Get thumbnail element
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.device_list_thumbnail);

        // Get deleteBtn
        ImageView deleteBtn = (ImageView) rowView.findViewById(R.id.deleteDeviceBtn);

        final DeviceModel device = (DeviceModel) getItem(position);

        titleTextView.setText(device.getTitle());
        descriptionTextView.setText(device.getDescription());

        switch (device.getType()) {
            case DeviceModel.Type.LIGHT:
                typeSwitch.setText("OFF/ON");
                Picasso.with(mContext).load(R.drawable.ic_lightbulb).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
                break;
            case DeviceModel.Type.DOORLOCK:
                typeSwitch.setText("LOCK/UNLOCK");
                break;
            case DeviceModel.Type.GATE:
                typeSwitch.setText("CLOSE/OPEN");
                break;
        }

        typeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendOverNetwork sendOverNetwork = new SendOverNetwork();
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                databaseAccess.open();
                String toSend = "S," + device.getPinNumber();
                ClientModel clientModel = databaseAccess.getClient(device.getClient_id());
                sendOverNetwork.execute(clientModel.getIp_address(), toSend);
                ReceiveOverNetwork receiveOverNetwork = new ReceiveOverNetwork();
                receiveOverNetwork.execute();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                databaseAccess.open();
                databaseAccess.deleteDevice(device.getId());
                databaseAccess.close();
                Snackbar.make(view, "Device " + device.getTitle() + " Deleted", Snackbar.LENGTH_LONG).show();
                mDataSource.remove(device);
                notifyDataSetChanged();
            }
        });

        return rowView;
    }

    public class ReceiveOverNetwork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String statusString = "";
            try {
                Networking networking = new Networking(4545, "");
                statusString = networking.receive();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return statusString;
        }

        protected void onPostExecute(String statusString) {

        }
    }

}
