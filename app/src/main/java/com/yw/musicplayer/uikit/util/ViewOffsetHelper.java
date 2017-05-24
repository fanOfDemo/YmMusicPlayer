/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yw.musicplayer.uikit.util;

import android.support.v4.view.ViewCompat;
import android.util.Property;
import android.view.View;


/**
 * Utility helper for moving a {@link View} around using
 * {@link View#offsetLeftAndRight(int)} and
 * {@link View#offsetTopAndBottom(int)}.
 * <p>
 *  Allows the setting of absolute offsets (similar to translationX/Y), in addition to relative
 * offsets. Reapplies offsets after a layout pass (as long as you call {@link #onViewLayout()}).
 * <p>
 * Adapted from the mDesign support library.
 */
public class ViewOffsetHelper {

    /**
     * Animatable property
     */
    public static final Property<ViewOffsetHelper, Integer> OFFSET_Y =
            AnimUtils.createIntProperty(
                    new AnimUtils.IntProp<ViewOffsetHelper>("topAndBottomOffset") {
                @Override
                public void set(ViewOffsetHelper viewOffsetHelper, int offset) {
                    viewOffsetHelper.setTopAndBottomOffset(offset);
                }

                @Override
                public int get(ViewOffsetHelper viewOffsetHelper) {
                    return viewOffsetHelper.getTopAndBottomOffset();
                }
            });

    private final View mView;

    private int mLayoutTop;//top
    private int mLayoutLeft;
    private int mOffsetTop;//top移动的绝对值
    private int mOffsetLeft;

    public ViewOffsetHelper(View view) {
        mView = view;
    }

    public void onViewLayout() {
        // Grab the intended top & left
        mLayoutTop = mView.getTop();
        mLayoutLeft = mView.getLeft();

        // And offset it as needed
        updateOffsets();
    }

    /**
     * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view by
     * an absolute amount.
     *
     * @param absoluteOffset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setTopAndBottomOffset(int absoluteOffset) {
        if (mOffsetTop != absoluteOffset) {
            mOffsetTop = absoluteOffset;
            updateOffsets();
            return true;
        }
        return false;
    }

    /**
     * Set the top and bottom offset for this {@link ViewOffsetHelper}'s view by
     * an relative amount.
     */
    public void offsetTopAndBottom(int relativeOffset) {
        mOffsetTop += relativeOffset;
        updateOffsets();
    }

    /**
     * Set the left and right offset for this {@link ViewOffsetHelper}'s view by
     * an absolute amount.
     *
     * @param absoluteOffset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setLeftAndRightOffset(int absoluteOffset) {
        if (mOffsetLeft != absoluteOffset) {
            mOffsetLeft = absoluteOffset;
            updateOffsets();
            return true;
        }
        return false;
    }

    /**
     * Set the left and right offset for this {@link ViewOffsetHelper}'s view by
     * an relative amount.
     */
    public void offsetLeftAndRight(int relativeOffset) {
        mOffsetLeft += relativeOffset;
        updateOffsets();
    }

    public int getTopAndBottomOffset() {
        return mOffsetTop;
    }

    public int getLeftAndRightOffset() {
        return mOffsetLeft;
    }

    /**
     * Notify this helper that a change to the view's offsets has occurred outside of this class.
     */
    public void resyncOffsets() {
        mOffsetTop = mView.getTop() - mLayoutTop;
        mOffsetLeft = mView.getLeft() - mLayoutLeft;
    }

    private void updateOffsets() {
        ViewCompat.offsetTopAndBottom(mView, mOffsetTop - (mView.getTop() - mLayoutTop));
        ViewCompat.offsetLeftAndRight(mView, mOffsetLeft - (mView.getLeft() - mLayoutLeft));
    }

}
