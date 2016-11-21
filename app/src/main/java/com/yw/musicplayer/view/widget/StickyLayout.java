package com.yw.musicplayer.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.yw.musicplayer.R;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/21 15:15
 * 修改人：wengyiming
 * 修改时间：2016/11/21 15:15
 * 修改备注：
 */

public class StickyLayout extends LinearLayout implements NestedScrollingParent {
    private View topView;
    private View tabView;
    private ViewPager viewPager;

    private int topViewHeiht = 0;
    private int alpha = 0;

    private OverScroller scroller;
    private VelocityTracker mVelocityTracker;

    public StickyLayout(Context context) {
        super(context);
        init(context);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("NewApi")
    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        scroller = new OverScroller(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.id_top_view);
        tabView = findViewById(R.id.id_tab_view);
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - tabView.getMeasuredHeight();//计算viewpager的高度，默认屏幕高度，移动时并未隐藏tabview，tabview会占用一部分高度，会导致viewpager显示不全，所以此处减去tabview的高度
        setMeasuredDimension(getMeasuredWidth(), topView.getMeasuredHeight() + tabView.getMeasuredHeight() + viewPager.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topViewHeiht = topView.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //这个不返回true的话，嵌套滑动机制完全失效
        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //在topView可以展示出来，或者可以隐藏的时候
        if ((dy > 0 && getScrollY() < topViewHeiht) || (dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1))) {
            scrollBy(0, dy);
            alpha = dy / topViewHeiht;
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //这里需要判断，当topview不可见之后，不需要处理滑动冲突，返回false，避免recyclerview滑动时跟随当前布局的滑动，导致丧失滑动的流程性
        if (getScrollY() >= topViewHeiht) return false;//不代理子布局的滑动事件
        scroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, 0, topViewHeiht);
        updateBackGround(topView, (alpha) * 255);
        invalidate();
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > topViewHeiht) {
            y = topViewHeiht;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void updateBackGround(View view, int alpha) {
        Log.e("alpha:", String.valueOf(alpha));
        if (view != null) {
            ColorDrawable background = (ColorDrawable) view.getBackground();
            if (background != null) {
                background.setAlpha(alpha);
            }
        }
    }
}
