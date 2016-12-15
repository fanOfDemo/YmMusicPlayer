package com.yw.musicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yw.musicplayer.event.BasePlayEvent;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.po.MusicData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    public static MediaPlayer mPlayer;
    private int mCurrentState;

    private MusicData.BitrateEntity bitrateEntity;
    private static BaiduMHotList baiduMHotList;
    private static int postion = 0;


    public static BaiduMHotList.SongListEntity getCurSong() {
        return baiduMHotList.getSong_list().get(postion);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnPlayEvent(BasePlayEvent event) {
        Log.e("OnPlayEvent", "event:" + event.curPosition + event.opration);
        if (event.opration == BasePlayEvent.Opration.IDLE) {
            init();
        } else {
            if (event.baiduMHotList != null) {
                baiduMHotList = event.baiduMHotList;
            }
            switch (event.opration) {
                case BasePlayEvent.Opration.IDLE:
                    init();
                    break;
                case BasePlayEvent.Opration.START:
                    String musicData = getMusicData();
                    if (musicData == null) {
                        Toast.makeText(this, "歌曲播放路径为空" + musicData, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    start(musicData);//如果是不同歌曲，则播放新的
                    break;
                case BasePlayEvent.Opration.PAUS:
                    pause();
                    break;
                case BasePlayEvent.Opration.NEXT:
                    doNext();
                    break;
                case BasePlayEvent.Opration.PREV:
                    doPrev();
                    break;
                default:
                    init();
                    break;
            }
        }
    }

    @Nullable
    private String getMusicData() {
        List<BaiduMHotList.SongListEntity> songListEntities = baiduMHotList.getSong_list();
        if (songListEntities == null || songListEntities.isEmpty()) {
            Toast.makeText(this, "播放列表为空", Toast.LENGTH_SHORT).show();
            return null;
        }
        BaiduMHotList.SongListEntity songListEntity = songListEntities.get(postion);
        if (songListEntity == null || songListEntity.getMusicData() == null) {
            Toast.makeText(this, "音乐数据不对", Toast.LENGTH_SHORT).show();
            return null;
        }
        MusicData musicData = songListEntity.getMusicData();
        if (musicData == null || musicData.getBitrate() == null) {
            Toast.makeText(this, "音乐文件出错", Toast.LENGTH_SHORT).show();
            return null;
        }
        MusicData.BitrateEntity bitrateEntity = musicData.getBitrate();
        if (bitrateEntity == null) {
            Toast.makeText(this, "音乐文件出错", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (TextUtils.isEmpty(bitrateEntity.getFile_link())) {
            Toast.makeText(this, "音乐文件url出错", Toast.LENGTH_SHORT).show();
            return null;
        }
        this.bitrateEntity = bitrateEntity;
        return bitrateEntity.getFile_link();
    }

    private void doPrev() {
        if (checkMusicList()) {
            return;
        }
        int totalSize = this.baiduMHotList.getSong_list().size();
        if (postion == 0) {
            postion = totalSize - 1;
        } else if (postion <= totalSize) {
            postion--;
        }
        String musicData = getMusicData();
        if (musicData == null) return;
        start(musicData);
    }

    private boolean checkMusicList() {
        List<BaiduMHotList.SongListEntity> songListEntities = baiduMHotList.getSong_list();
        if (songListEntities == null || songListEntities.isEmpty()) {
            Toast.makeText(this, "播放列表为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void doNext() {
        if (checkMusicList()) return;
        int totalSize = baiduMHotList.getSong_list().size();
        if (postion < totalSize - 1) {
            postion++;
        } else if (postion == totalSize - 1) {
            postion = 0;
        }
        String musicData = getMusicData();
        if (musicData == null) return;
        start(musicData);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }


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
        if (!mPlayer.isPlaying()) {
            mPlayer.start();
        }

    }

    public void start(String musicData) {
        init();
        try {
            if (mCurrentState == State.IDLE) {
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
                mPlayer.setDataSource(this, Uri.parse(musicData));    //Valid Sates IDLE
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
            mPlaybackListener.onStateChanged(bitrateEntity, mCurrentState);
        }
    }

    /*这里采用了setOnPlaybackListener的方法，如果有需要，也可以用一个List去保存一个Listener集合，只要在适当的时候进行释放，例如在Service的onDestroy方法中，去把这个List清空掉*/
    public void setOnPlaybackListener(OnPlaybackListener listener) {
        mPlaybackListener = listener;
    }

    private OnPlaybackListener mPlaybackListener;

    public static interface OnPlaybackListener {
        public void onStateChanged(MusicData.BitrateEntity source, int state);
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
                next = 8,
                prev = 9,
                END = -1,
                ERROR = -2;
    }


}
