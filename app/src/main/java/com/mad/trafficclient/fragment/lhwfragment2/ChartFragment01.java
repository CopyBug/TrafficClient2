package com.mad.trafficclient.fragment.lhwfragment2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.WeiguiCar;

public class ChartFragment01 extends Fragment {
    WeiguiCar weigui;
    private ProgressBar pb;
    private PieChart chartview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        weigui = (WeiguiCar) arguments.getSerializable("Weigui");
        View view = inflater.inflate(R.layout.lhwfragment02_1, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        pb = (ProgressBar) view.findViewById(R.id.pb);
        chartview = (PieChart) view.findViewById(R.id.chartview);
    }
}
