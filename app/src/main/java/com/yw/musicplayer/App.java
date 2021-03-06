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

import com.yw.musicplayer.domain.model.Audio;
import com.yw.musicplayer.domain.model.MusicData;
import com.yw.musicplayer.event.BasePlayEvent;
import com.yw.musicplayer.internal.di.components.ApplicationComponent;
import com.yw.musicplayer.internal.di.components.DaggerApplicationComponent;
import com.yw.musicplayer.internal.di.module.ApplicationModule;
import com.yw.musicplayer.util.MediaUtils;
import com.yw.musicplayer.view.MusicPlayService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：MVC仓储架构搞起
 * 创建人：wengyiming
 * 创建时间：16/3/2 下午10:50
 * 修改人：wengyiming
 * 修改时间：16/3/2 下午10:50
 * 修改备注：
 *
 * @author wengyiming
 */
public class App extends Application implements ServiceConnection, AudioManager.OnAudioFocusChangeListener {

    public static MusicPlayService mMainService;//音乐播放service
    private static List<Audio> audios = new ArrayList<>();
    private AudioManager mAudioManager;//音频管理实例
    private ApplicationComponent mApplicationComponent;

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        MediaUtils.init(this, new MediaUtils.Callback() {
            @Override
            public void onGetAudioListCallback(List<Audio> arrayList) {
                audios = arrayList;
                startMainService();
                bindMainService();
                mAudioManager = (AudioManager) getApp().getSystemService(Context.AUDIO_SERVICE);
            }
        });

        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Monospace-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        initInjector();
    }


    private void initInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static App getApp() {
        return app;
    }

    public static void updateAudios(List<Audio> audios) {
        App.audios = audios;
    }

    public static List<Audio> getAudios() {
        if (audios == null || audios.isEmpty()) {
            MediaUtils.init(getApp(), new MediaUtils.Callback() {
                @Override
                public void onGetAudioListCallback(List<Audio> arrayList) {
                    audios = arrayList;
                }
            });
        }
        return audios;
    }

    public void startMainService() {
        Intent it = new Intent(this, MusicPlayService.class);
        startService(it);
    }

    public void stopMainService() {
        Intent it = new Intent(this, MusicPlayService.class);
        stopService(it);
    }

    private void bindMainService() {
        Intent it = new Intent(this, MusicPlayService.class);
        this.bindService(it, this, Service.BIND_AUTO_CREATE);
    }

    private void unbindMainService() {
        this.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof MusicPlayService.ServiceBinder) {
            MusicPlayService.ServiceBinder binder = (MusicPlayService.ServiceBinder) service;
            EventBus.getDefault().post(new BasePlayEvent(BasePlayEvent.Opration.IDLE));
            mMainService = binder.getService();
            mMainService.setOnPlaybackListener(new MusicPlayService.OnPlaybackListener() {
                @Override
                public void onStateChanged(MusicData.BitrateEntity source, int state) {

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
