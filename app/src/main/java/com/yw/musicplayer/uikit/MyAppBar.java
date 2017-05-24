package com.yw.musicplayer.uikit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.yw.musicplayer.R;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * 项目名称：ViewStudyDemo
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/12/5 18:08
 * 修改人：wengyiming
 * 修改时间：2016/12/5 18:08
 * 修改备注：
 */

public class MyAppBar extends FrameLayout {

    private Paint paint;
    private Path path;
    private float currentX;
    private float currentY;
    @BindColor(R.color.mp_theme_dark_blue_colorPrimary)
    int UC_COLOR;
    @BindDimen(R.dimen.max_drag)
    int MAX_DRAG;

    public MyAppBar(Context context) {
        super(context, null);
    }

    public MyAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(UC_COLOR);
        path = new Path();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                currentX = event.getRawX();
//                float rawY = event.getRawY();
//                currentY = rawY;
//                currentY = rawY >= 200 ? 200 : rawY;
//                Log.e(TAG, "onTouchEvent: " + currentX + ";;;" + currentY);
//                targetView.setTranslationY(currentY*0.5f);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                currentX = 0;
//                currentY = 0;
//                targetView.setTranslationY(0);
//                invalidate();
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(0, getMeasuredHeight());
        path.quadTo(currentX, currentY + getMeasuredHeight(), getWidth(), getMeasuredHeight());
        canvas.drawPath(path, paint);
    }


    public void onDispatch(float dx, float dy) {
        currentY = dy > MAX_DRAG ? MAX_DRAG : dy;
        currentX = dx;
        if (dy > 0) {
            invalidate();
        }
    }


    public void onDispatchUp() {
        currentY = 0;
        invalidate();
    }
}
