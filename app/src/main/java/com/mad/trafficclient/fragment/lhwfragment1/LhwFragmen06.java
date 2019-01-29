package com.mad.trafficclient.fragment.lhwfragment1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.mad.trafficclient.R;

public class LhwFragmen06 extends Fragment {
    private LineChart lc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.lhwfragment06, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        lc = (LineChart) inflate.findViewById(R.id.lc);
    }
}
