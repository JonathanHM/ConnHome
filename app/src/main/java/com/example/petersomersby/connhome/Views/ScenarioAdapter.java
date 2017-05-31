package com.example.petersomersby.connhome.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petersomersby.connhome.Models.DeviceModel;
import com.example.petersomersby.connhome.Models.ScenarioModel;
import com.example.petersomersby.connhome.R;

import java.util.List;

/**
 * Created by Peter Somersby on 31-05-2017.
 */

public class ScenarioAdapter extends RecyclerView.Adapter<ScenarioAdapter.ViewHolder> {

    private Context mContext;
    private List<ScenarioModel> mDataSource;

    public ScenarioAdapter(Context context, List<ScenarioModel> scenarios) {
        this.mContext = context;
        this.mDataSource = scenarios;
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ScenarioModel scenario = mDataSource.get(position);
        holder.placeName.setText(scenario.getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
        }
    }
}
