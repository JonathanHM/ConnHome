package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.R;

import java.util.ArrayList;
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
        Switch typeSwitch = (Switch) rowView.findViewById(R.id.device_list_onOffSwitch);

        // Get thumbnail element
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.device_list_thumbnail);

        DeviceModel device = (DeviceModel) getItem(position);

        titleTextView.setText(device.getTitle());
        descriptionTextView.setText(device.getDescription());

        switch (device.getType()) {
            case DeviceModel.Type.LIGHT:
                typeSwitch.setText("On/Off");
            case DeviceModel.Type.DOORLOCK:
                typeSwitch.setText("Lock/Unlock");
            case DeviceModel.Type.GATE:
                typeSwitch.setText("Open/Close");
        }

        return rowView;
    }
}
