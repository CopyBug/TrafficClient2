package com.mad.trafficclient.fragment.lhwfragment2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.WeiguiCar;
import com.mad.trafficclient.util.LHWuntil;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment03 extends Fragment {
    WeiguiCar weigui;
    private ProgressBar pb;
    private HorizontalBarChart chartview;
    private Activity activity;
    private int xcount=3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity=getActivity();
        Bundle arguments = getArguments();
        weigui = (WeiguiCar) arguments.getSerializable("Weigui");
        View view = inflater.inflate(R.layout.lhwfragment02_3, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final String[] msg={"1-2条违章","3-5条违章","5条违章以上"};
        pb = (ProgressBar) view.findViewById(R.id.pb);
        chartview = (HorizontalBarChart) view.findViewById(R.id.chartview);
       // chartview.setDrawValueAboveBar(true);
       // chartview.getDescription().setEnabled(false);
        //chartview.setNoDataText("空数据");
        //11111111111111
        //chartview.setPinchZoom(false);
        //chartview.setDrawGridBackground(false);
        chartview.getAxisRight().setEnabled(false);
        XAxis xAxis = chartview.getXAxis();
        xAxis.setValueFormatter(new MyXFormatter(msg));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
      //  xAxis.setGranularity(1f);
        //xAxis.setCenterAxisLabels(true);
       // xAxis.setAxisMinimum(0f);
        //xAxis.setAxisMaximum(100);
        xAxis.setLabelCount(xcount);
        YAxis axisLeft = chartview.getAxisLeft();
        Setchartview();
        axisLeft.setDrawGridLines(true);
        axisLeft.setDrawAxisLine(true);
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value+"%";
            }
        });
        //靠最右
        axisLeft.setAxisMinimum(0f);
        //适中
        chartview.setFitBars(true);
        chartview.animateXY(2000,2000);
        chartview.getLegend().setEnabled(false);
        chartview.getDescription().setEnabled(false);

    }
    public void Setchartview(){
        final String[] msg={"1-2条违章","3-5条违章","5条违章以上"};
        float spaceForBar = 1f;       //每个数据条实际占的宽度
        List<WeiguiCar.ROWSDETAILBean> rows_detail = weigui.getROWS_DETAIL();
        double one=0;
        double two=0;
        double three=0;
        for (int i = 0; i <rows_detail.size() ; i++) {
            int j=0,cishu=0;
            for ( j=0,cishu=0;j <rows_detail.size(); j++) {
                if(rows_detail.get(i).getCarnumber().equals(rows_detail.get(j).getCarnumber())){
                    cishu+=1;
                }
                if(cishu>5){
                    break;
                }
            }
            if(cishu>0){
                if(cishu>=1&&cishu<=2){
                    one+=1;
                }else if(cishu>=3&&cishu<=5){
                    two+=1;
                }else {
                    three+=1;
                }
            }
        }

        double amount=rows_detail.size();
        float v1 = LHWuntil.CountPrecent(amount, one);
        float v2 = LHWuntil.CountPrecent(amount, two);
        float v3 = LHWuntil.CountPrecent(amount, three);

        List<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(0,v1,"1-2条违章"));
        barEntries.add(new BarEntry(1,v2,"3-5条违章"));
        barEntries.add(new BarEntry(2,v3,"5条违章以上"));
        List<Integer> list = LHWuntil.SetColors(Color.GREEN, Color.BLUE, Color.RED);
        BarDataSet barDataSet=new BarDataSet(barEntries,"");
        barDataSet.setColors(list);
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value+"%";
            }
        });
        barDataSet.setValueTextSize(15);
        final BarData barData=new BarData(barDataSet);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chartview.setData(barData);
                XAxis xAxis = chartview.getXAxis();
                xAxis.setValueFormatter(new MyXFormatter(msg));
                LHWuntil.OpenView(pb,chartview);

            }
        });

    }

}
