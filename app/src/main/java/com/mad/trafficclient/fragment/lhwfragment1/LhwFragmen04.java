package com.mad.trafficclient.fragment.lhwfragment1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.mad.trafficclient.R;
import com.mad.trafficclient.model.Huanjin;
import com.mad.trafficclient.util.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LhwFragmen04 extends Fragment {

    private Huanjin huanjin;
    private BarChart bc;
    private Thread thread;
    private List<String> time = new ArrayList<>();
    private List<BarEntry> barEntries = new ArrayList<>();
    private Activity activity;
    private TextView chazhi;
    private TextView shuzi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.lhwfragment04, container, false);
        activity = getActivity();
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        bc = (BarChart) inflate.findViewById(R.id.bc);
        XAxis xAxis = bc.getXAxis();
        xAxis.setLabelCount(20);
        xAxis.setAxisMaximum(20);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(false);
        bc.getAxisRight().setEnabled(false);
        YAxis axisLeft = bc.getAxisLeft();
        Description description = new Description();
        description.setText("S");
        bc.setDescription(description);
        SetBarchart();
        chazhi = (TextView) inflate.findViewById(R.id.chazhi);
        shuzi = (TextView) inflate.findViewById(R.id.shuzi);
    }

    public void SetBarchart() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {

                    try {
                        huanjin = HttpUtil.postRequest(Huanjin.class, "action/GetSenseByName.do ", "{\"SenseName\":\"co2\", \"UserName\":\"user1\"} ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (time.size() == 20) {
                        time.remove(0);
                        barEntries.remove(0);
                        for (int i = 0; i < barEntries.size(); i++) {
                            barEntries.get(i).setX(i);
                        }
                    }

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
                    String format = simpleDateFormat.format(new Date());
                    BarData barData = bc.getBarData();
                    time.add(format);
                    barEntries.add(new BarEntry(barEntries.size(), Integer.valueOf(huanjin.getCo2())));
                    final BarDataSet barDataSet = new BarDataSet(barEntries, "");
                    final float yMax = barDataSet.getYMax();
                    barDataSet.setColor(Color.GRAY);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BarData barData = new BarData(barDataSet);
                            bc.setData(barData);
                            XAxis xAxis = bc.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
                            shuzi.setText(yMax+"");
                            bc.notifyDataSetChanged();
                            bc.invalidate();
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
