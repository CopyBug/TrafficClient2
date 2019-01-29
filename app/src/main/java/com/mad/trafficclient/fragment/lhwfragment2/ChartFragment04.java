package com.mad.trafficclient.fragment.lhwfragment2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.WeiguiCar;
import com.mad.trafficclient.util.LHWuntil;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment04 extends Fragment {
    WeiguiCar weigui;
    private ProgressBar pb;
    private BarChart chartview;
    private Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        weigui = (WeiguiCar) arguments.getSerializable("Weigui");
        View view = inflater.inflate(R.layout.lhwfragment02_4, container, false);

        initView(view);
        return view;

    }


    private void initView(View view) {
        String[] msg={"90后","80后","70后","60后","50后"};
        pb = (ProgressBar) view.findViewById(R.id.pb);
        chartview = (BarChart) view.findViewById(R.id.chartview);
        chartview.getAxisRight().setEnabled(false);
        YAxis axisLeft = chartview.getAxisLeft();
        axisLeft.setDrawGridLines(true);
        axisLeft.setAxisMinimum(0f);
        XAxis xAxis = chartview.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MyXFormatter(msg));
        xAxis.setLabelCount(5);
        xAxis.setTextSize(30);
        xAxis.setTextColor(Color.BLACK);
        Legend legend = chartview.getLegend();
        legend.setEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        chartview.animateXY(1500,1500);
        chartview.setExtraOffsets(0,0,0,20);
        chartview.getDescription().setEnabled(false);
        chartview.setFitBars(true);
        BarData barData = SetDate();
        chartview.setScaleEnabled(false);

        chartview.setData(barData);
        chartview.invalidate();
        LHWuntil.OpenView(pb,chartview);
    }
    public BarData SetDate(){
        List<WeiguiCar.ROWSDETAILBean> rows_detail = weigui.getROWS_DETAIL();
        double a=50;
        double b=100;
        double c=150;
        double d=145;
        double e=190;
        double Count=a+b+c+d+e;
       /* float one[] ={LHWuntil.CountPrecent(Count,a),
                LHWuntil.CountPrecent(Count,b),
                LHWuntil.CountPrecent(Count,c),
                LHWuntil.CountPrecent(Count,d),
                LHWuntil.CountPrecent(Count,e)};*/
        final float one[] ={(float) a,(float)b,(float)c,(float)d,(float)e};
        final float onepre[] ={LHWuntil.CountPrecent(Count,a),
                LHWuntil.CountPrecent(Count,b),
                LHWuntil.CountPrecent(Count,c),
                LHWuntil.CountPrecent(Count,d),
                LHWuntil.CountPrecent(Count,e)};
        Log.i("CountPrecent", LHWuntil.CountPrecent(Count,a)+"");
        double a2=20;
        double b2=80;
        double c2=110;
        double d2=45;
        double e2=140;
        double Count2=a2+b2+c2+d2+e2;
        /*float one2[] ={LHWuntil.CountPrecent(Count2,a2),
                LHWuntil.CountPrecent(Count2,b2),
                LHWuntil.CountPrecent(Count2,c2),
                LHWuntil.CountPrecent(Count2,d2),
                LHWuntil.CountPrecent(Count2,e2)};*/
      /*  for (int i = 0; i <rows_detail.size() ; i++) {
            for (int j = 0; j <rows_detail.size() ; j++) {
                if(rows_detail.get(i).getCarnumber().equals(rows_detail.get(j).getCarnumber())){
                    rows_detail.remove(j);
                }
                //发送请求
            }*/
        float one2[] ={(float) a2,(float)b2,(float)c2,(float)d2,(float)e2};

        List<BarEntry> list=new ArrayList<>();
        List<BarEntry> list2=new ArrayList<>();
        for (int i = 0; i <one.length ; i++) {
            list.add(new BarEntry((float) i,one[i]));
            list2.add(new BarEntry((float) i,one2[i]));
        }
        BarDataSet barDataSet=new BarDataSet(list,"有违章");
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                for (int i = 0; i <one.length ; i++) {
                    if(value==one[i]){
                        return onepre[i]+"%";

                    }
                }
                return "";
            }
        });
        barDataSet.setColors(Color.parseColor("#FFFF8833"));
        BarDataSet barDataSet2=new BarDataSet(list2,"无违章");
        barDataSet2.setDrawValues(false);
        barDataSet2.setColors(Color.parseColor("#FF00D4B8"));
        List<IBarDataSet> barDataSets=new ArrayList<>();
        barDataSets.add(barDataSet);
        barDataSets.add(barDataSet2);
        BarData barData=new BarData(barDataSets);

        return barData;
        }


}
