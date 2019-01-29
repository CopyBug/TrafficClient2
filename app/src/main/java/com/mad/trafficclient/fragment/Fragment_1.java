/**
 *
 */
package com.mad.trafficclient.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mad.trafficclient.R;
import com.mad.trafficclient.fragment.lhwfragment1.LhwFragmen01;
import com.mad.trafficclient.fragment.lhwfragment1.LhwFragmen02;
import com.mad.trafficclient.fragment.lhwfragment1.LhwFragmen03;
import com.mad.trafficclient.fragment.lhwfragment1.LhwFragmen04;
import com.mad.trafficclient.fragment.lhwfragment1.LhwFragmen05;
import com.mad.trafficclient.fragment.lhwfragment1.LhwFragmen06;
import com.mad.trafficclient.fragment.lhwfragment1.Myadapter;

import java.util.ArrayList;
import java.util.List;


public class Fragment_1 extends Fragment implements View.OnClickListener {
    private String TAG = "Mylogi";
    Thread thread1;
    private ViewPager vp;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Activity activity;
    private Button b6;
    private Button[] btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity=getActivity();
        View view = inflater
                .inflate(R.layout.fragment_layout01, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        vp = (ViewPager) view.findViewById(R.id.vp);
        vp.setOnClickListener(this);
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
        btn=new Button[]{b1,b2,b3,b4,b5,b6};
        initviewpager();
    }

    private void initviewpager() {
      final List<Fragment> fragments=new ArrayList<>();
        fragments.add(new LhwFragmen01());
        fragments.add(new LhwFragmen02());
        fragments.add(new LhwFragmen03());
        fragments.add(new LhwFragmen04());
        fragments.add(new LhwFragmen05());
        fragments.add(new LhwFragmen06());
        Myadapter myadapter=new Myadapter(getFragmentManager(),fragments);
        vp.setAdapter(myadapter);
         vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                        ChangeBtn(btn[position]);

             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                ChangeBtn(b1);
                break;
            case R.id.b2:
                ChangeBtn(b2);
                break;
            case R.id.b3:
                ChangeBtn(b3);
                break;
            case R.id.b4:
                ChangeBtn(b4);
                break;
            case R.id.b5:
                ChangeBtn(b5);
                break;
            case R.id.b6:
                ChangeBtn(b6);
                break;
        }
    }

    public void ChangeBtn(Button bn){
        bn.setBackgroundResource(R.drawable.yuan2);
        for (int i = 0; i <btn.length ; i++) {
            if(btn[i]==bn){
                continue;
            }
            btn[i].setBackgroundResource(R.drawable.yuan);
        }
    }
}
