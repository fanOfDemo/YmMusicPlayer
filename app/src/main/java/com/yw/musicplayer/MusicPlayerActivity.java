package com.yw.musicplayer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cleveroad.audiovisualization.AudioVisualization;
import com.yw.musicplayer.service.MainService;

import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

public class MusicPlayerActivity extends AppCompatActivity {

    private AudioVisualization audioVisualization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        audioVisualization = (AudioVisualization) findViewById(R.id.visualizer_view);
        audioVisualization.linkTo(MainService.mPlayer);


        InteractivePlayerView ipv = (InteractivePlayerView) findViewById(R.id.ipv);
        ipv.setProgressEmptyColor(Color.GRAY);
        ipv.setProgressEmptyColor(Color.BLACK);
        ipv.setMax(MainService.mPlayer.getDuration()); // music duration in seconds.    
        ipv.setProgress(MainService.mPlayer.getCurrentPosition());
        ipv.setCoverURL("http://img.kejixun.com/2013/1101/20131101024212971.jpg");
        ipv.setProgressLoadedColor(Color.RED);
        
        ipv.start();
        ipv.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(int id) {
                switch (id) {
                    case 1:
                        BeApplication.mMainService.pause();
                        //Called when 1. action is clicked.
                        break;
                    case 2:
                        BeApplication.mMainService.pause();
                        //Called when 2. action is clicked.
                        break;
                    case 3:
                        //Called when 3. action is clicked.
                        break;
                    default:
                        break;
                }
            }
        });
      
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioVisualization.onResume();
    }

    @Override
    public void onPause() {
        audioVisualization.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        audioVisualization.release();
        super.onDestroy();
        
    }
}
