package com.mad.trafficclient.fragment.lhwfragment1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.Huanjin;
import com.mad.trafficclient.util.HttpUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LhwFragmen01 extends Fragment implements View.OnClickListener {
    private LineChart lc;
    private Activity activity;
    private Integer indext=0;
    LineDataSet lineDataSet;
    private List<String> list=new ArrayList<>();
   final List<Entry> entries=new ArrayList<>();
    private int y=2;
    private Button add;
    public Thread thread;
    private Huanjin huanjin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.lhwfragment01, container, false);
        activity = getActivity();
        initView(inflate);

        return inflate;
    }

    @SuppressLint("WrongConstant")
    private void initView(View inflate) {
        lc = (LineChart) inflate.findViewById(R.id.lc);
        LineData lineData=new LineData();
        lc.setData(lineData);
        YAxis axisLeft = lc.getAxisLeft();
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                BigDecimal bigDecimal=new BigDecimal(value);
                double v = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                return v+"℃";

            }
        });
        Description description=new Description();
        description.setText("ssss");
        description.setTextColor(Color.RED);


        Description description1 = lc.getDescription();
        lc.getAxisRight().setEnabled(false);

        XAxis xAxis = lc.getXAxis();
        float xOffset = xAxis.getXOffset();
        Log.i("xOffset", xOffset+"");
//        description.setXOffset( );

        lc.setDescription(description);
        Legend legend = lc.getLegend();
        legend.setEnabled(false);

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //设置X轴的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴的线宽度
        xAxis.setAxisLineWidth(5);
        //设置标签角度
        xAxis.setLabelRotationAngle(90);
        //设置20个X轴标签
        xAxis.setLabelCount(20);
        //设置X轴最多限度的点数
        xAxis.setAxisMaximum(19);
        //禁止话竖轴
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        AutoAddPoint();
        thread.start();
    }

    public synchronized void AutoAddPoint( ){

           thread= new Thread(new Runnable() {
               @Override
               public void run() {
                   final   int indext=0;

                   while (!Thread.currentThread().isInterrupted()){
                       try {
                            huanjin = HttpUtil.postRequest(Huanjin.class, "action/GetSenseByName.do", "{\"SenseName\":\"temperature\", \"UserName\":\"user1\"} ");
                           Gm(huanjin);
                       } catch (Exception e) {
                           e.printStackTrace();
                                break;
                       }


                   }

               }
           });

    }
public synchronized void Gm(final Huanjin huanjin){
   if(huanjin.getTemperature()!=null){
       activity.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               int indext=entries.size();
               if(entries.size()==20){
                   entries.remove(0);
                   for (int i = 0; i <entries.size() ; i++) {
                       Entry entry = entries.get(i);
                       entry.setX(entry.getX()-1);
                       entries.set(i,entry);
                   }
                   Log.i("sss", entries.get(0)+"");
                   list.remove(0);
                   indext=indext-1;
               }
               Integer integer = Integer.valueOf(huanjin.getTemperature());
               if(integer==null){
                   integer=35;
               }
               Entry entry=new Entry(indext,integer);

               entries.add(entry);
               LineDataSet lineDataSet=new LineDataSet(entries,"温度");
               SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
               String format = simpleDateFormat.format(new Date());
               list.add(format);
               XAxis xAxis = lc.getXAxis();
               xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
               xAxis.setAvoidFirstLastClipping(true);
               LineData lineData=new LineData(lineDataSet);
               lc.setData(lineData);
               Reflesh();


           }

       });
       try {
           Thread.sleep(3000);
       } catch (InterruptedException e) {
           e.printStackTrace();
           Thread.currentThread().interrupt();
       }
   }
}


    public LineDataSet lineDataSet(){
        LineDataSet lineDataSet=new LineDataSet(null,"");
        return  lineDataSet;
    }


    public void Reflesh() {

        lc.notifyDataSetChanged();
        lc.invalidate();
    }

    @Override
    public void onClick(View v) {

        //第一步：如何画一个空的坐标轴（X轴是自定义的,一共有20个点，但是空的）

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i("onViewStateRestored", "onViewStateRestored: ");
    }

}
