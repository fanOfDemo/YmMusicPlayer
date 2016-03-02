package com.yw.musicplayer.util;

import android.media.MediaPlayer;

/**
 * 项目名称：MyViewPagerActivity
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：15/12/28 下午11:57
 * 修改人：wengyiming
 * 修改时间：15/12/28 下午11:57
 * 修改备注：
 */
public class MyPlayer {
    public static MediaPlayer mPlayer;
    private static MyPlayer ourInstance = new MyPlayer();

    public static MyPlayer getInstance() {
        return ourInstance;
    }

    private MyPlayer() {

    }

    /**
     * Gets the mediaplayer.
     *
     * @return the mediaplayer
     */
    public synchronized MediaPlayer getMediaplayer() {
        mPlayer = new MediaPlayer();
        return mPlayer;

    }

    ;
}
