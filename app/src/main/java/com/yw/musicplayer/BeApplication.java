package com.yw.musicplayer;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.service.MainService;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：16/3/2 下午10:50
 * 修改人：wengyiming
 * 修改时间：16/3/2 下午10:50
 * 修改备注：
 */
public class BeApplication extends Application implements ServiceConnection, AudioManager.OnAudioFocusChangeListener {

    public static MainService mMainService;

    private AudioManager mAudioManager;

    @Override
    public void onCreate() {
        super.onCreate();
        startMainService();
        bindMainService();
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void startMainService() {
        Intent it = new Intent(this, MainService.class);
        startService(it);
    }

    public void stopMainService() {
        Intent it = new Intent(this, MainService.class);
        stopService(it);
    }

    private void bindMainService() {
        Intent it = new Intent(this, MainService.class);
        this.bindService(it, this, Service.BIND_AUTO_CREATE);
    }

    private void unbindMainService() {
        this.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof MainService.ServiceBinder) {
            MainService.ServiceBinder binder = (MainService.ServiceBinder) service;
            mMainService = binder.getService();
            mMainService.setOnPlaybackListener(new MainService.OnPlaybackListener() {
                @Override
                public void onStateChanged(final Audio source, final int state) {

                }
            });
//            mMainService.registerServiceCallback(mPlayManager);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Toast.makeText(this, "onServiceDisconnected name=" + name, Toast.LENGTH_LONG).show();
    }

    // 以下是进行申请焦点的两个方法，
    private int requestAudioFocus() {
        //Toast.makeText(mContext, "requestAudioFocus", Toast.LENGTH_SHORT).show();  
        return mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    private int releaseAudioFocus() {
        //Toast.makeText(mContext, "releaseAudioFocus excuted", Toast.LENGTH_SHORT).show();  
        return mAudioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(final int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
                releaseAudioFocus();
                mMainService.stop();
                Log.i("Michael onAudioFocusChange", "AUDIOFOCUS_LOSS:" + focusChange);
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
//                mService.recoverVolume();  
                /*if (mService.getState() == MichaelService.State.PAUSED) { 
                    mService.resume(); 
                }*/
                Log.i("Michael onAudioFocusChange", "AUDIOFOCUS_GAIN:" + focusChange);
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                mMainService.pause();
                Log.i("Michael onAudioFocusChange", "AUDIOFOCUS_LOSS_TRANSIENT:" + focusChange);
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
//                mService.lowerVolume();
                Log.i("Michael onAudioFocusChange", "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:" + focusChange);
                break;
            default:
                Log.i("Michael onAudioFocusChange", "default:" + focusChange);
                break;
        }

    }
}
