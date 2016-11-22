package com.yw.musicplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.service.MainService;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.library.MusicPlayerView;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView musicTitle;
    TextView prev;
    TextView next;
    GLAudioVisualizationView glAudioVisualizationView;
    MusicPlayerView mpv;
    List<BaiduMHotList.SongListEntity> mAudioList = new ArrayList<>();
    int curPosition = 0;
    boolean isPaused = true;
    boolean isStoped = true;
    BaiduMHotList.SongListEntity songListEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music_player);
        glAudioVisualizationView = (GLAudioVisualizationView) findViewById(R.id.visualizer_view);
        glAudioVisualizationView.linkTo(0);
        curPosition = getIntent().getIntExtra("position", 0);
        mAudioList = (List<BaiduMHotList.SongListEntity>) getIntent().getSerializableExtra("list");
        if (mAudioList == null || mAudioList.isEmpty()) {
            BaiduMHotList.SongListEntity a = new BaiduMHotList.SongListEntity();
        }
        try {

        } catch (IndexOutOfBoundsException e) {

        }
        songListEntity = mAudioList.get(curPosition);
        App.mMainService.start(songListEntity);
        musicTitle = (TextView) findViewById(R.id.title);
        prev = (TextView) findViewById(R.id.prevBtn);
        next = (TextView) findViewById(R.id.nextBtn);
        updateInfo();


//        music/search
//        https://api.douban.com/v2/music/:id
        mpv = (MusicPlayerView) findViewById(R.id.mpv);

    }

    private void updateInfo() {
        musicTitle.setText(songListEntity.getTitle() + " \n" + (songListEntity.isLocal() ? songListEntity.getAudio().getArtist() : songListEntity.getArtist_name()));
    }

    private void player() {
        if (mpv.isRotating()) {
            App.mMainService.pause();
            mpv.setProgress(0);
        }
        final int curTime = MainService.mPlayer.getDuration() / 1000;
        mpv.setMax(curTime);
        mpv.setProgress(MainService.mPlayer.getCurrentPosition());
        App.mMainService.start(mAudioList.get(curPosition));
        updateInfo();
    }


    @Override
    public void onResume() {
        super.onResume();
        mpv.postDelayed(new Runnable() {
            @Override
            public void run() {
                initMediaView();
                glAudioVisualizationView.onResume();
            }
        }, 500);

    }

    private void initMediaView() {

        if (MainService.mPlayer.isPlaying()) {
            isStoped = false;
            isPaused = false;
            final int curTime = MainService.mPlayer.getDuration() / 1000;
            mpv.setMax(curTime);
            mpv.setProgress(MainService.mPlayer.getCurrentPosition());
            mpv.setAutoProgress(true);
            mpv.start();
        } else {
            if (MainService.mPlayer == null) {
                isStoped = true;
                isPaused = true;
                mpv.setProgress(0);
                mpv.setAutoProgress(true);
                mpv.stop();
            } else if (!MainService.mPlayer.isPlaying()) {
                final int curTime = MainService.mPlayer.getDuration() / 1000;
                mpv.setMax(curTime);
                mpv.setProgress(MainService.mPlayer.getCurrentPosition());
                mpv.setAutoProgress(true);
                mpv.stop();
            }
        }

        glAudioVisualizationView.linkTo(MainService.mPlayer);
        mpv.setCoverURL(songListEntity.isLocal() ? songListEntity.getAudio().getImagePath() : songListEntity.getPic_big());
        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpv.isRotating()) {
                    mpv.stop();
                    App.mMainService.pause();
                } else {
                    mpv.start();
                    App.mMainService.start(mAudioList.get(curPosition));
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPosition <= 0) {
                    curPosition = mAudioList.size() - 1;
                }
                curPosition--;
                player();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPosition >= mAudioList.size()) {
                    curPosition = 0;
                }
                curPosition++;
                player();
            }
        });
    }

    @Override
    public void onPause() {
        glAudioVisualizationView.onPause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        glAudioVisualizationView.release();
        super.onDestroy();
    }
}
