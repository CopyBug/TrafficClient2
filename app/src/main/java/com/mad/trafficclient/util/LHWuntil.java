package com.mad.trafficclient.util;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mad.trafficclient.model.WeiguiCar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LHWuntil {
    public static double GetSmallshu(double xiaoshu){
        BigDecimal bigDecimal=new BigDecimal(xiaoshu);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
    public static float CountPrecent(double amount,double beichu){
        if(beichu==0){
            return 0;
        }
        return (float) GetSmallshu(beichu*100/amount);
    }

    public static List<Integer> SetColors(int ... color){
        List<Integer> colors=new ArrayList<>();
        for (int i = 0; i <color.length; i++) {
            colors.add(color[i]);
        }
        return colors;
    }
    public static void OpenView(ProgressBar bar,View view){
        bar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }
    public void GetSameMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WeiguiCar weiguiCar1 = HttpUtil.postRequest(WeiguiCar.class, "action/GetAllCarPeccancy.do ", "{\"UserName\":\"user1\"} ");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public static  void PrintMsg(Object object){
        Log.i("LHWERROW", object+"");
    }
}
