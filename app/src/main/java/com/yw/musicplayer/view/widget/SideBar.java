/*
 * @wengyiming
 */
package com.yw.musicplayer.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yw.musicplayer.R;


// TODO: Auto-generated Javadoc

/**
 * The Class SideBar.
 */
public class SideBar extends View {

    private Context mContext;
    // 触摸事件
    /**
     * The on touching letter changed listener.
     */
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    /**
     * The b.
     */
    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    /**
     * The choose.
     */
    private int choose = -1;// 选中

    /**
     * The paint.
     */
    private Paint paint = new Paint();

    /**
     * The m text dialog.
     */
    private TextView mTextDialog;

    /**
     * Sets the text view.
     *
     * @param mTextDialog the new text view
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    /**
     * Instantiates a new side bar.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    /**
     * Instantiates a new side bar.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    /**
     * Instantiates a new side bar.
     *
     * @param context the context
     */
    public SideBar(Context context) {
        super(context);
        this.mContext = context;
    }


    /* (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / b.length;// 获取每一个字母的高度

        for (int i = 0; i < b.length; i++) {
            paint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            // paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            // 选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }

    /* (non-Javadoc)
     * @see android.view.View#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * Sets the on touching letter changed listener.
     *
     * @param onTouchingLetterChangedListener the new on touching letter changed listener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * The listener interface for receiving onTouchingLetterChanged events. The
     * class that is interested in processing a onTouchingLetterChanged event
     * implements this interface, and the object created with that class is
     * registered with a component using the component's
     * <code>addOnTouchingLetterChangedListener<code> method. When
     * the onTouchingLetterChanged event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnTouchingLetterChangedListener OnTouchingLetterChangedListener
     */
    public interface OnTouchingLetterChangedListener {

        /**
         * On touching letter changed.
         *
         * @param s the s
         */
        public void onTouchingLetterChanged(String s);
    }

}
