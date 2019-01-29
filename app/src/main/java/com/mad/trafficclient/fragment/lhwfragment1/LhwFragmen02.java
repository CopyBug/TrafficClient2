package com.mad.trafficclient.fragment.lhwfragment1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LhwFragmen02 extends Fragment {
    private LineChart lc;
    public Thread thread;
    private Activity activity;
    private List<String> time = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();
    private Huanjin huanjin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        View inflate = inflater.inflate(R.layout.lhwfragment02, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        lc = (LineChart) inflate.findViewById(R.id.lc);
        SetChartWG();
    }

    public void SetChartWG() {
        XAxis xAxis = lc.getXAxis();
        xAxis.setLabelCount(20);
        xAxis.setLabelRotationAngle(90);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setAxisMaximum(19);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lc.setScaleEnabled(false);
        YAxis axisLeft = lc.getAxisRight();
        YAxis axisLeft1 = lc.getAxisLeft();
        axisLeft1.setSpaceTop(0);
        axisLeft1.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "℃";
            }
        });
        axisLeft.setEnabled(false);
        SetChartData();
    }

    public void SetChartData() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    //条件范围
                    try {
                        huanjin = HttpUtil.postRequest(Huanjin.class, "action/GetSenseByName.do ", "{\"SenseName\":\"humidity\", \"UserName\":\"user1\"} ");


                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                    if (time.size() == 20) {
                        time.remove(0);
                        entries.remove(0);
                        for (int i = 0; i < entries.size(); i++) {
                            entries.get(i).setX(i);
                        }
                    }
                    //横坐标
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    time.add(simpleDateFormat.format(new Date()));
                    //纵坐标
                   /*String serverinfo = huanjin.getServerinfo();
                   Ts ts=new Gson().fromJson(serverinfo,Ts.class);*/
                    Integer integer;
                   try{
                       integer = Integer.valueOf(huanjin.getHumidity());

                   }catch (Exception e){
                       integer=35;
                   }

                    entries.add(new Entry(entries.size(), integer));
                    LineDataSet lineDataSet = new LineDataSet(entries, "");
                    lineDataSet.setDrawValues(false);
                    final LineData lineData = new LineData(lineDataSet);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            XAxis xAxis = lc.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
                            xAxis.setAvoidFirstLastClipping(false);
                            lc.setData(lineData);
                            lc.notifyDataSetChanged();
                            lc.invalidate();
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        thread.interrupt();
                    }
                }


            }
        });
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }


}
