package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.ClientModel;
import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
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

        // Get thumbnail element
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.client_list_thumbnail);

        ClientModel client = (ClientModel) getItem(position);

        titleTextView.setText(client.getTitle());
        ipAddressTextView.setText(client.getIp_address());

        return rowView;
    }
}
