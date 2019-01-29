package com.mad.trafficclient.fragment.lhwfragment2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.WeiguiCar;
import com.mad.trafficclient.util.LHWuntil;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment02 extends Fragment {
    WeiguiCar weigui;
    private ProgressBar pb;
    private PieChart chartview;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity=getActivity();
        Bundle arguments = getArguments();
        weigui = (WeiguiCar) arguments.getSerializable("Weigui");
        View view = inflater.inflate(R.layout.lhwfragment02_2, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        pb = (ProgressBar) view.findViewById(R.id.pb);
        chartview = (PieChart) view.findViewById(R.id.chartview);
    }

}
