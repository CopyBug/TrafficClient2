package com.mad.trafficclient.util;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MySlidingPaneLayout extends SlidingPaneLayout {
    public MySlidingPaneLayout(Context context) {
        super(context);
    }

    public MySlidingPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)){
            case MotionEvent.ACTION_MOVE:
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
