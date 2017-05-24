package com.yw.musicplayer.uikit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/12/6 17:43
 * 修改人：wengyiming
 * 修改时间：2016/12/6 17:43
 * 修改备注：
 */

public class MyViewPager extends ViewPager {
    private boolean isFirst = true;
    private boolean scrollble;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //scrollble false 直接返回。如果scrollble 为true 那么还要判断 super.onInterceptTouchEvent(ev)的返回。
        Log.e("TAG", "onInterceptTouchEvent: ....");
        return scrollble && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("TAG", "onTouchEvent: ScrollAbleViewPager有事件了！！！");
        //如果子View不消费相关的事件，那么又会走到`onTouchEvent`,那么是不可滑动的话，直接就返回 true(!scrollble) ,其他情况那么直接就返回 super.onTouchEvent(ev).
        return !scrollble || super.onTouchEvent(ev);
    }


    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollable(boolean scrollble) {
        this.scrollble = scrollble;
    }

//    @Override
//    public void detachViewFromParent(View child) {
//        super.detachViewFromParent(child);
//    }

}
