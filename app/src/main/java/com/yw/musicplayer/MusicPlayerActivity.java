package com.yw.musicplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.yw.musicplayer.po.Audio;
import com.yw.musicplayer.service.MainService;

import java.util.List;

import co.mobiwise.library.MusicPlayerView;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView musicTitle;
    TextView prev;
    TextView next;
    GLAudioVisualizationView glAudioVisualizationView;
    MusicPlayerView mpv;
    List<Audio> mAudioList;
    int curPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        mAudioList = (List<Audio>) getIntent().getSerializableExtra("AudioList");
        if (mAudioList == null || mAudioList.isEmpty()) {
            onBackPressed();
        }
        musicTitle = (TextView) findViewById(R.id.title);
        prev = (TextView) findViewById(R.id.prev);
        next = (TextView) findViewById(R.id.next);
        musicTitle.setText(mAudioList.get(curPosition).getTitle() + " " + mAudioList.get(curPosition).getArtist());
        mpv = (MusicPlayerView) findViewById(R.id.mpv);
        glAudioVisualizationView = (GLAudioVisualizationView) findViewById(R.id.visualizer_view);


        mpv.setCoverURL("http://pic16.nipic.com/20110928/7745445_045524550001_2.jpg");
        BeApplication.mMainService.start(mAudioList.get(curPosition));
        mpv.start();
        glAudioVisualizationView.linkTo(MainService.mPlayer);

        final int curTime = MainService.mPlayer.getDuration() / 1000;
        mpv.setMax(curTime);
        mpv.setProgress(MainService.mPlayer.getCurrentPosition());
        mpv.setAutoProgress(true);


        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player();
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

    private void player() {
        if (mpv.isRotating()) {
            mpv.stop();
            BeApplication.mMainService.pause();
        }
        mpv.start();
        BeApplication.mMainService.start(mAudioList.get(curPosition));
        musicTitle.setText(mAudioList.get(curPosition).getTitle() + " " + mAudioList.get(curPosition).getArtist());
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
