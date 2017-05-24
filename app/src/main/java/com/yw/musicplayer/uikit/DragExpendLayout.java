package com.yw.musicplayer.uikit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yw.musicplayer.uikit.util.AnimUtils;
import com.yw.musicplayer.uikit.util.ViewOffsetHelper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static android.content.ContentValues.TAG;

/**
 * 项目名称：ViewStudyDemo
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/12/6 11:36
 * 修改人：wengyiming
 * 修改时间：2016/12/6 11:36
 * 修改备注：
 */

public class DragExpendLayout extends FrameLayout {
    private View sheet;// 可被拖动的view
    private ViewDragHelper sheetDragHelper;//拖动帮助类
    private ViewOffsetHelper sheetOffsetHelper;//子view的偏移量帮助类

    private static final int MIN_SETTLE_VELOCITY = 6000; // px/s  这是自定义的一个滑动速度，每秒6000px
    private final int MIN_FLING_VELOCITY;//系统的滑动惯性速度
    private final int MIN_DRAG_DISTANCE = 200;//最小可拖拉的距离

    private int tabHeight;//当前拖拽view的上面的tab的view的高度
    private int dismissOffset;//当前拖动view距离顶部的距离，上面预留的高度就是headerview的高度

    private int sheetExpandedTop;//记录可拖动的子view，此值是累加上tab之后的高度
    private int sheetBottom;//实际可用的屏幕高度，也是子view的右下点的高度
    private int currentTop;//记录可拖动的子view的左上 右下的上的值
    private int currentTop2;//这是个临时变量，用来计算动画执行后子view的高度距离的变化产生的相对偏移量，以回调出去

    private boolean initialHeightChecked = false;//初始化高度的标志位，默认高度为检查
    private boolean hasInteractedWithSheet = false;
    private boolean settling = false;//是否正在执行复位的动画
    private boolean canUp;//当下拉的距离超过默认值MIN_DRAG_DISTANCE，则复位到MIN_DRAG_DISTANCE
    private boolean reverse = true;
    private float currentX;//记录触摸的view的点的x坐标以回调出去，供下拉时触发三阶赛贝尔曲线以绘制下拉的波浪

    private List<Callbacks> callbacks;

    private OnLayoutChangeListener sheetLayout = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            sheetExpandedTop = top + tabHeight;//view初始化时会执行一次，此时top为0，sheetExpandedTop的默认值为tabHeight
            sheetBottom = bottom;//1731
            currentTop = top;//0
            sheetOffsetHelper.onViewLayout();//第一次记录结果全都是0
            if (!initialHeightChecked) {
                applySheetInitialHeightOffset(false, -1);
                initialHeightChecked = true;
            } else if (!hasInteractedWithSheet
                    && (oldBottom - oldTop) != (bottom - top)) { /* sheet height changed */
                /* if the sheet content's height changes before the user has interacted with it
                   then consider this still in the 'initial' state and apply the height constraint,
                   but in this case, animate to it */
                applySheetInitialHeightOffset(true, oldTop - sheetExpandedTop);
            }
        }
    };

    private void applySheetInitialHeightOffset(boolean animateChange, int previousOffset) {
        //第一次调用该方法事，sheet.getTop()=0，minimumGap=603，需要做的偏移量就是603，第一次animateChange为false，会执行sheetOffsetHelper.setTopAndBottomOffset(603)，最后的效果就是子view被往上平移603个像素
        final int minimumGap = sheet.getMeasuredWidth() / 16 * 9;//基于16比9的宽高比计算出tab的最低高度，在三星s6edg 上计算结果是603
        if (sheet.getTop() < minimumGap) {//如果子view距离上面的距离小于这个高度，则将子view的高度拖到这个高度的位置上，比如第一次初始化时sheet.getTop()=0，
            final int offset = minimumGap - sheet.getTop();//计算离正常的位置的偏移量，这个偏移量就是我们要复位的距离
            if (animateChange) {
                animateSettle(previousOffset, offset, 0);
            } else {
                sheetOffsetHelper.setTopAndBottomOffset(offset);//将子view移动往下调整这么多的偏移量
            }
        }
    }


    private void animateSettle(int targetOffset, float initialVelocity) {
        animateSettle(sheetOffsetHelper.getTopAndBottomOffset(), targetOffset, initialVelocity);
    }

    /**
     * 当前拖拽view最主要的方法，分别在三个地方触发，
     * 第一次触发在onlayoutChanged中触发，用于设置初始位置，
     * 第二次触发在拖动结束后释放手时，根据已经拖动的距离和拖动的速度判断
     *
     * @param initialOffset
     * @param targetOffset
     * @param initialVelocity
     */
    private void animateSettle(int initialOffset, final int targetOffset, float initialVelocity) {
        if (settling) return;
        if (sheetOffsetHelper.getTopAndBottomOffset() == targetOffset) {
            if (targetOffset >= dismissOffset) {//如果要复位的偏移量超出了headerview的高度
                dispatchDismissCallback();//
            }
            return;
        }

        settling = true;
        final boolean dismissing = targetOffset == dismissOffset;//如果需要被移动的偏移量等于登录子view距离顶部的高度，代表
        final long duration = computeSettleDuration(initialVelocity, dismissing);//根据高度和
        final ObjectAnimator settleAnim = ObjectAnimator.ofInt(sheetOffsetHelper,
                ViewOffsetHelper.OFFSET_Y,
                initialOffset,
                targetOffset);
        settleAnim.setDuration(duration);
        settleAnim.setInterpolator(getSettleInterpolator(dismissing, initialVelocity));
        settleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchPositionChangedCallback();
                if (dismissing) {
                    dispatchDismissCallback();
                }
                settling = false;
            }
        });
        if (callbacks != null && !callbacks.isEmpty()) {
            settleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedFraction() > 0f) {
                        dispatchPositionChangedCallback();
                    }
                }
            });
        }
        settleAnim.start();
    }

    public void dismiss() {
        reverse = true;
        animateSettle(dismissOffset, 0);
    }

    private void dispatchDismissCallback() {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (Callbacks callback : callbacks) {
                callback.onSheetNarrowed();
            }
        }
    }

    private void dispatchPositionChangedCallback() {
        if (currentTop2 == 0) {
            currentTop2 = sheet.getTop();
        }
        int dy = sheet.getTop() - currentTop2;
        currentTop2 = sheet.getTop();
        if (callbacks != null && !callbacks.isEmpty()) {
            for (Callbacks callback : callbacks) {
                callback.onSheetPositionChanged(sheet.getTop(), currentX, dy, reverse);
                if (isExpanded()) {
                    if (sheetDragHelper != null) {
                        sheetDragHelper.cancel();
                    }
                    callback.onSheetExpanded();
                }
            }
        }
    }

    public boolean isExpanded() {
        return sheet.getTop() == sheetExpandedTop;
    }

    /**
     * Provides the appropriate interpolator for the settle animation depending upon:
     * – If dismissing then exit at full speed i.e. linearly otherwise decelerate
     * – If have initial velocity then respect it (i.e. start linearly) otherwise accelerate into
     * the animation.
     */
    private TimeInterpolator getSettleInterpolator(boolean dismissing, float initialVelocity) {
        if (initialVelocity != 0) {
            if (dismissing) {
                return AnimUtils.getLinearInterpolator();
            } else {
                return AnimUtils.getLinearOutSlowInInterpolator(getContext());
            }
        } else {
            if (dismissing) {
                return AnimUtils.getFastOutLinearInInterpolator(getContext());
            } else {
                return AnimUtils.getFastOutSlowInInterpolator(getContext());
            }
        }
    }

    /**
     * Calculate the duration of the settle animation based on the gesture velocity
     * and how far it has to go.
     * 根据滑动手势的速度和要滑动的高度计算需要花费的时间，返回时间以ms为单位
     */
    private long computeSettleDuration(final float velocity, final boolean dismissing) {
        // enforce a min velocity to prevent too slow settling
        final float clampedVelocity = Math.max(MIN_SETTLE_VELOCITY, Math.abs(velocity));
        final int settleDistance = Math.abs(dismissing
                ? sheetBottom - sheet.getTop()
                : sheet.getTop() - sheetExpandedTop);
        // velocity is in px/s but we want duration in ms thus * 1000
        return (long) (settleDistance * 1000 / clampedVelocity);
    }

    private ViewDragHelper.Callback dragHelperCallbacks = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == sheet && !isExpanded();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //此处返回的
            //随着子view网上推动，top值越来越小，直到比sheetExpandedTop的值小，意味着子view即将接触到屏幕最高点，此时返回子view的竖直方向的移动高度就是sheetExpandedTop高度，也就是tab的高度
            return Math.max(top, sheetExpandedTop);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return sheet.getLeft();
        }

        /**
         * Return the magnitude of a draggable child view's vertical range of motion in pixels.
         * This method should return 0 for views that cannot move vertically.
         * 返回可拖动的子view的竖直方向的可滑动范围，以像素为单位的int值，如果希望竖直方向不可滑动返回0
         * @param child Child view to check
         * @return range of vertical motion in pixels
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return sheetBottom - sheetExpandedTop;//子view的底部的绝对高度减去 子view的top+tab的高度
        }

        @Override
        public void onViewPositionChanged(View child, int left, int top, int dx, int dy) {
            // notify the offset helper that the sheets offsets have been changed externally
            reverse = false;
            sheetOffsetHelper.resyncOffsets();//刷新子view的位置信息，这里只是刷新了子view的左上点的坐标
            dispatchPositionChangedCallback();//将位移变化回调出去
            canUp = Math.abs(top - dismissOffset) > MIN_DRAG_DISTANCE;//如果拖拉点的top坐标的值减去headerView的绝对高度的绝对值大于最小滑动距离，意味着可以往上复位到指定的距离
        }

        @Override
        public void onViewReleased(View releasedChild, float velocityX, float velocityY) {
            // dismiss on downward fling, otherwise settle back to expanded position
            boolean expand = canUp || Math.abs(velocityY) > MIN_FLING_VELOCITY;//如果滑动的速度大于系统的最小速度或者滑动的距离超出我们设定的值，则返回可自动复位
            reverse = false;
            animateSettle(expand ? sheetExpandedTop : dismissOffset, velocityY);
        }
    };

    public DragExpendLayout(Context context) {
        this(context, null, 0);
    }

    public DragExpendLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragExpendLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        MIN_FLING_VELOCITY = viewConfiguration.getScaledMinimumFlingVelocity();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (sheet != null) {
            throw new UnsupportedOperationException("DragExpendLayout must only have 1 child view");
        }
        sheet = child;//重写addview的方法，将layout的子布局自定添加到当前布局
        sheetOffsetHelper = new ViewOffsetHelper(sheet);//创建子view拖动帮助类
        sheet.addOnLayoutChangeListener(sheetLayout);//设置可拖动的子布局的布局变化监听器
        // force the sheet contents to be gravity bottom. This ain't a top sheet.
        ((LayoutParams) params).gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        super.addView(child, index, params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        currentX = ev.getRawX();
        sheetDragHelper.processTouchEvent(ev);
        return sheetDragHelper.getCapturedView() != null || super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (sheetDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (sheet != null) {
            sheetDragHelper = ViewDragHelper.create((ViewGroup) sheet.getParent(), dragHelperCallbacks);
            sheetDragHelper.captureChildView(sheet, 1);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        currentX = ev.getRawX();
        Log.e(TAG, "BottomSheet onInterceptTouchEvent: " + currentX);
        if (isExpanded()) {
            sheetDragHelper.cancel();
            return false;
        }
        hasInteractedWithSheet = true;

        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            sheetDragHelper.cancel();
            return false;
        }
        return getVisibility() == VISIBLE && sheetDragHelper.isViewUnder(this, (int) ev.getX(), (int) ev.getY())
                && (sheetDragHelper.shouldInterceptTouchEvent(ev));
    }


    public void setDismissOffset(int dismissOffset) {
        this.dismissOffset = currentTop + dismissOffset;
    }


    public void setTabHeight(int tabHeight) {
        if (this.tabHeight != tabHeight) {
            this.tabHeight = tabHeight;
            sheetExpandedTop = currentTop + tabHeight;
        }
    }

    /**
     * Callbacks for responding to interactions with the bottom sheet.
     * 状态回调，
     */
    public static abstract class Callbacks {
        //在子view回复正常时回调
        public void onSheetNarrowed() {

        }

        //在子view展开时回调
        public void onSheetExpanded() {
        }

        //在子view被拖动时回调
        public void onSheetPositionChanged(int sheetTop, float currentX, int dy, boolean userInteracted) {
        }
    }

    public void registerCallback(Callbacks callback) {
        if (callbacks == null) {
            callbacks = new CopyOnWriteArrayList<>();
        }
        callbacks.add(callback);
    }

    public void unregisterCallback(Callbacks callback) {
        if (callbacks != null && !callbacks.isEmpty()) {
            callbacks.remove(callback);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DEFAULT, super.onSaveInstanceState());
        bundle.putBoolean(KEY_EXPAND, isExpanded());
        bundle.putInt(KEY_DISMISS_OFFSET, dismissOffset);
        bundle.putInt(KEY_TOP, sheetExpandedTop);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(KEY_DEFAULT));
            dismissOffset = bundle.getInt(KEY_DISMISS_OFFSET);
            sheetExpandedTop = bundle.getInt(KEY_TOP);
            Log.e(TAG, "onRestoreInstanceState: 保存了top::" + sheetExpandedTop);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private static final String KEY_DEFAULT = "key_default";
    private static final String KEY_EXPAND = "key_expand";
    private static final String KEY_DISMISS_OFFSET = "key_dismissoffset";
    private static final String KEY_TOP = "key_top";
}
