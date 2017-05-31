package com.example.petersomersby.connhome.Views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.petersomersby.connhome.R;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class Tab3Scenarios extends Fragment {

    private RecyclerView scenarioRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3scenarios, container, false);

        scenarioRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTab3);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        scenarioRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        return rootView;
    }
}
