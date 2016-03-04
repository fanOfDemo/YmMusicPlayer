package com.yw.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.util.MediaUtils;

import java.io.IOException;
import java.util.List;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：16/3/2 下午10:51
 * 修改人：wengyiming
 * 修改时间：16/3/2 下午10:51
 * 修改备注：
 */
public class MainService extends Service {


    public static class ServiceBinder extends Binder {

        private MainService mService = null;

        public ServiceBinder(MainService service) {
            mService = service;
        }

        public MainService getService() {
            return mService;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAudioList = MediaUtils.getAudioList(this);
        return Service.START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    public static List<Audio> mAudioList;
    public static MediaPlayer mPlayer;
    private int mCurrentState;
    private Audio mCurrentAudio;

    //这个方法用来初始化我们的MediaPlayer  
    private void init() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            changeState(State.IDLE);
        } else {
            if (mCurrentState == State.IDLE || mCurrentState == State.INITIALIZED || mCurrentState == State.PREPARED ||
                    mCurrentState == State.STARTED || mCurrentState == State.PAUSED || mCurrentState == State.STOPPED ||
                    mCurrentState == State.COMPLETED || mCurrentState == State.ERROR) {
                mPlayer.reset();
                changeState(State.IDLE);        //注意状态更改的代码  
            }

        }
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(final MediaPlayer mp, final int what, final int extra) {
                return false;
            }
        });       //MainService 要实现这三个接口  
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                doStart();

            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {

            }
        });
    }

    private void doStart() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.start();
        changeState(State.STARTED);
    }

    public void pause() {
        mPlayer.pause();
        changeState(State.PAUSED);
    }

    public void stop() {
        mPlayer.stop();
        changeState(State.STOPPED);
    }

    public void continued() {
        if(!mPlayer.isPlaying()){
            mPlayer.start();
        }

    }

    public void start(Audio audio) {
        init();
        try {
            if (mCurrentState == State.IDLE) {
                mPlayer.setDataSource(this, Uri.parse(audio.getPath()));    //Valid Sates IDLE  
                mCurrentAudio = audio;
            }
            changeState(State.INITIALIZED);
            if (mCurrentState != State.ERROR) {
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);      //Invalid States ERROR  
            }
            if (mCurrentState == State.INITIALIZED || mCurrentState == State.STOPPED) {
                mPlayer.prepareAsync();//Valid Sates{Initialized, Stopped}  
                changeState(State.PREPARING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeState(int state) {
        mCurrentState = state;
        if (mPlaybackListener != null) {
            mPlaybackListener.onStateChanged(mCurrentAudio, mCurrentState);
        }
    }

    /*这里采用了setOnPlaybackListener的方法，如果有需要，也可以用一个List去保存一个Listener集合，只要在适当的时候进行释放，例如在Service的onDestroy方法中，去把这个List清空掉*/
    public void setOnPlaybackListener(OnPlaybackListener listener) {
        mPlaybackListener = listener;
    }

    private OnPlaybackListener mPlaybackListener;

    public static interface OnPlaybackListener {
        public void onStateChanged(Audio source, int state);
    }

    public static class State {
        public static final int
                IDLE = 0,
                INITIALIZED = 1,
                PREPARED = 2,
                PREPARING = 3,
                STARTED = 4,
                PAUSED = 5,
                STOPPED = 6,
                COMPLETED = 7,
                END = -1,
                ERROR = -2;
    }


}
