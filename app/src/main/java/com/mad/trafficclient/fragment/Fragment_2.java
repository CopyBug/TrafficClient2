/**
 *
 */
package com.mad.trafficclient.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.mad.trafficclient.R;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment01;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment02;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment03;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment04;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment05;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment06;
import com.mad.trafficclient.fragment.lhwfragment2.ChartFragment07;
import com.mad.trafficclient.fragment.lhwfragment2.FragmentAdapter;
import com.mad.trafficclient.model.WeiguiCar;
import com.mad.trafficclient.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment_2 extends Fragment implements View.OnClickListener {
    List<Fragment> list = new ArrayList<>();
    private Thread thread;
    private WeiguiCar weiguiCar;
    private Activity activity;
    private ViewPager FragmentVp;
    FragmentAdapter fragmentAdapter;
    private ProgressBar pb;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    Button button[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater
                .inflate(R.layout.fragment_layout02, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        FragmentVp = (ViewPager) view.findViewById(R.id.FragmentVp);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), list);
        FragmentVp.setAdapter(fragmentAdapter);
        GetSameMsg();

        b1 = (Button) view.findViewById(R.id.b1);
        b1.setOnClickListener(this);
        b2 = (Button) view.findViewById(R.id.b2);
        b2.setOnClickListener(this);
        b3 = (Button) view.findViewById(R.id.b3);
        b3.setOnClickListener(this);
        b4 = (Button) view.findViewById(R.id.b4);
        b4.setOnClickListener(this);
        b5 = (Button) view.findViewById(R.id.b5);
        b5.setOnClickListener(this);
        b6 = (Button) view.findViewById(R.id.b6);
        b6.setOnClickListener(this);
        b7 = (Button) view.findViewById(R.id.b7);
        b7.setOnClickListener(this);
        button=new Button[]{b1, b2, b3, b4, b5, b6, b7};
        pb.animate();
    }

    public void GetSameMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WeiguiCar weiguiCar1 = HttpUtil.postRequest(WeiguiCar.class, "action/GetAllCarPeccancy.do ", "{\"UserName\":\"user1\"} ");
                    for (int i = 0; i <100 ; i++) {
                        pb.setProgress(i);
                        Thread.sleep(50);
                    }
                    SetFragment(weiguiCar1);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public synchronized void SetFragment(WeiguiCar weiguiCar) {
        if (weiguiCar != null) {
            pb.setProgress(100);
            list.add(new ChartFragment01());
            list.add(new ChartFragment02());
            list.add(new ChartFragment03());
            list.add(new ChartFragment04());
            list.add(new ChartFragment05());
            list.add(new ChartFragment06());
            list.add(new ChartFragment07());
            for (int i = 0; i < list.size(); i++) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Weigui", weiguiCar);
                list.get(i).setArguments(bundle);
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragmentAdapter.notifyDataSetChanged();

                    pb.setVisibility(View.GONE);
                    FragmentVp.setVisibility(View.VISIBLE);
                    FragmentVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            ChangeColorPage(button[position]);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                ChangeColorPage(b1);
                break;
            case R.id.b2:
                ChangeColorPage(b2);
                break;
            case R.id.b3:
                ChangeColorPage(b3);
                break;
            case R.id.b4:
                ChangeColorPage(b4);
                break;
            case R.id.b5:
                ChangeColorPage(b5);
                break;
            case R.id.b6:
                ChangeColorPage(b6);
                break;
            case R.id.b7:
                ChangeColorPage(b7);
                break;
        }
    }
        public void ChangeColorPage(Button bn){
            for (int i = 0; i <button.length ; i++) {
                if(bn==button[i]){
                    FragmentVp.setCurrentItem(i);
                    bn.setBackgroundResource(R.drawable.yuan2);
                    continue;
                }
                button[i].setBackgroundResource(R.drawable.yuan);

            }
        }
}
