package com.mad.trafficclient.fragment.lhwfragment2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.WeiguiCar;
import com.mad.trafficclient.util.LHWuntil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartFragment06 extends Fragment {
    WeiguiCar weigui;
    private ProgressBar pb;
    private BarChart chartview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        weigui = (WeiguiCar) arguments.getSerializable("Weigui");
        View view = inflater.inflate(R.layout.lhwfragment02_6, container, false);

        initView(view);
        return view;

    }


    private void initView(View view) {
        String [] msg=new String[12];
        String pinjie=":00:01";
        String pinjie2=":00:00";
        String news="";
        int j=0;
        int i = 0;
        for (i = 0,j=0; i <24 ; i+=2,j++) {
            news=i+pinjie+"-"+(i+2)+pinjie2;
            msg[j]=news;
        }
        pb = (ProgressBar) view.findViewById(R.id.pb);
        chartview = (BarChart) view.findViewById(R.id.chartview);
        chartview.getAxisRight().setEnabled(false);
        YAxis axisLeft = chartview.getAxisLeft();
        axisLeft.setAxisMinimum(0);
        axisLeft.setValueFormatter(new PercentFormatter());
        XAxis xAxis = chartview.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new MyXFormatter(msg));
        xAxis.setLabelRotationAngle(45);
        xAxis.setTextSize(10);
        chartview.getDescription().setEnabled(false);
        chartview.getLegend().setEnabled(false);
        chartview.setData(Setmsg());
        chartview.setFitBars(true);
        chartview.invalidate();
        LHWuntil.OpenView(pb,chartview);
    }

    private BarData Setmsg() {
        List<Integer> MyColor=new ArrayList<>();
        String Colors[]={"#FFB6C1","#8B008B","#483D8B","#00FFFF","#F5FFFA","#7FFF00","#FFFF00","#FFA500","#FFA07A","#A52A2A","#F0E68C","#008B8B"};
        double Count=weigui.getROWS_DETAIL().size();
        Float date[] =new Float[12];
        for (int i = 0; i <date.length ; i++) {
            MyColor.add(Color.parseColor(Colors[i]));
            date[i]= Float.valueOf(0);
        }
        List<WeiguiCar.ROWSDETAILBean> rows_detail = weigui.getROWS_DETAIL();
        for (int i = 0; i <rows_detail.size() ; i++) {
            String pdatetime = rows_detail.get(i).getPdatetime();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date parse = simpleDateFormat.parse(pdatetime);
                int hours = parse.getHours();
                if(hours<2){
                    date[0]+=1;
                    continue;
                }else  if(hours<4){
                    date[1]+=1;
                    continue;
                }else  if(hours<6){
                    date[2]+=1;
                    continue;
                }else  if(hours<8){
                    date[3]+=1;
                    continue;
                }
                else  if(hours<10){
                    date[4]+=1;
                    continue;
                }
                else  if(hours<12){
                    date[5]+=1;
                    continue;
                }
                else  if(hours<14){
                    date[6]+=1;
                    continue;
                }else  if(hours<16){
                    date[7]+=1;
                    continue;
                }else  if(hours<18){
                    date[8]+=1;
                    continue;
                }else  if(hours<20){
                    date[9]+=1;
                    continue;
                }else  if(hours<22){
                    date[10]+=1;
                    continue;
                }else  if(hours<24){
                    date[11]+=1;
                    continue;
                }
            }catch (Exception e){
                LHWuntil.PrintMsg(e);
            }
            }
            List<BarEntry> barEntries=new ArrayList<>();

        for (int i = 0; i <date.length ; i++) {


            barEntries.add(new BarEntry(i,LHWuntil.CountPrecent(Count,date[i])));
        }

        BarDataSet barDataSet=new BarDataSet(barEntries,"");
        barDataSet.setColors(MyColor);
        barDataSet.setValueFormatter(new PercentFormatter());
        BarData barData=new BarData(barDataSet);
        return barData;




    }
}
