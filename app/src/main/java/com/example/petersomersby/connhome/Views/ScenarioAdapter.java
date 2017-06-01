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
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Somersby on 31-05-2017.
 */

public class ScenarioAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ScenarioModel> mDataSource;

    public ScenarioAdapter(Context context, List<ScenarioModel> scenarios) {
        mContext = context;
        mDataSource = scenarios;
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
        View rowView = mInflater.inflate(R.layout.list_item_scenario, parent, false);

        // Get title element
        TextView titleTextView = (TextView) rowView.findViewById(R.id.scenario_list_title);

        // Get description element
        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.scenario_list_description);

        // Get thumbnail element
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.scenario_list_thumbnail);

        ScenarioModel device = (ScenarioModel) getItem(position);

        titleTextView.setText(device.getName());
        descriptionTextView.setText(device.getDescription());

        return rowView;
    }
}
