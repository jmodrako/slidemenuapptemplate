package com.template.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: jbogacki
 * Date: 10.07.2013
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class SlidingPaneLayout extends android.support.v4.widget.SlidingPaneLayout {

    boolean enable = true;

    public SlidingPaneLayout(Context context) {
        super(context);
    }

    public SlidingPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setSlidingEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return enable ? super.onInterceptTouchEvent(ev) : false;

    }
}
