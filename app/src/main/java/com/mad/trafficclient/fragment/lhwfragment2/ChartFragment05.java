package com.mad.trafficclient.fragment.lhwfragment2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.WeiguiCar;
import com.mad.trafficclient.util.LHWuntil;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment05 extends Fragment {
    WeiguiCar weigui;
    private ProgressBar pb;
    private BarChart chartview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        weigui = (WeiguiCar) arguments.getSerializable("Weigui");
        View view = inflater.inflate(R.layout.lhwfragment02_5, container, false);
        initView(view);
        return view;

    }


    private void initView(View view) {
        String[] msg={"女","男"};
        pb = (ProgressBar) view.findViewById(R.id.pb);
        chartview = (BarChart) view.findViewById(R.id.chartview);
        chartview.getAxisRight().setEnabled(false);
        YAxis axisLeft =chartview.getAxisLeft();
        axisLeft.setSpaceMin(1000);
        axisLeft.setAxisMinimum(13000);
        XAxis xAxis = chartview.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(2);
        xAxis.setValueFormatter(new MyXFormatter(msg));
        chartview.setData(SetMsg());
        Legend legend = chartview.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        chartview.getDescription().setEnabled(false);
        chartview.setFitBars(true);
        LHWuntil.OpenView(pb,chartview);
    }

    private BarData SetMsg() {
        float nv1=18800;
        float nv2=15800;
        float nan1=18000;
        float nan2=15700;
      final   double Amount= nv1+nan1+nv2+nan2;
        List<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(0,nv1));
        barEntries.add(new BarEntry(1,nan1));
        List<BarEntry> barEntries2=new ArrayList<>();
        barEntries2.add(new BarEntry(0,nv2));
        barEntries2.add(new BarEntry(1,nan2));
        BarDataSet barDataSet=new BarDataSet(barEntries,"有违规");
        barDataSet.setColors(Color.parseColor("#FFFF8833"));
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return LHWuntil.CountPrecent(Amount,(double) value)+"%";
            }
        });
        BarDataSet barDataSet2=new BarDataSet(barEntries2,"无违规");
        barDataSet2.setColors(Color.GREEN);
        barDataSet2.setDrawValues(false);
        List<IBarDataSet> barDataSets=new ArrayList<>();
        barDataSets.add(barDataSet);
        barDataSets.add(barDataSet2);
        BarData barData=new BarData(barDataSets);
        return  barData;
    }
}
