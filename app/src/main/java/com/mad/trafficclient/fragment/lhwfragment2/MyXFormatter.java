package com.mad.trafficclient.fragment.lhwfragment2;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class MyXFormatter implements IAxisValueFormatter {
    private String[] msg;

    public MyXFormatter(String[] msg) {
        this.msg = msg;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if((int) value>=0&&(int)value<msg.length){
            return msg[(int)value];
        }else {
            return "";
        }
    }
}
