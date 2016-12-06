package com.yw.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.service.ApiService;
import com.yw.musicplayer.service.MainService;
import com.yw.musicplayer.service.MusicApi;
import com.yw.musicplayer.util.LyricManager;
import com.yw.musicplayer.view.widget.LyricView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import co.mobiwise.library.MusicPlayerView;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MusicPlayerActivity extends AppCompatActivity implements LyricManager.OnProgressChangedListener {
    private TextView musicTitle;
    private TextView prev;
    private TextView next;
    private LyricView lyricView;
    private GLAudioVisualizationView glAudioVisualizationView;
    private MusicPlayerView mpv;
    private List<BaiduMHotList.SongListEntity> mAudioList = new ArrayList<>();
    private int curPosition = 0;
    private boolean isPaused = true;
    private boolean isStoped = true;
    private BaiduMHotList.SongListEntity songListEntity;
    private LyricManager lyricManager;
    private int lineNumber = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        lyricView = (LyricView) findViewById(R.id.lyrcview);
        glAudioVisualizationView = (GLAudioVisualizationView) findViewById(R.id.visualizer_view);
        glAudioVisualizationView.linkTo(0);
        curPosition = getIntent().getIntExtra("position", 0);
        mAudioList = (List<BaiduMHotList.SongListEntity>) getIntent().getSerializableExtra("list");
        if (mAudioList == null || mAudioList.isEmpty()) {
            BaiduMHotList.SongListEntity a = new BaiduMHotList.SongListEntity();
        }
        lyricManager = LyricManager.getInstance(this);
        lyricManager.setOnProgressChangedListener(this);
        songListEntity = mAudioList.get(curPosition);
        App.mMainService.start(songListEntity);
        lyricView.setCurrentPosition(MainService.mPlayer.getCurrentPosition());
        musicTitle = (TextView) findViewById(R.id.title);
        prev = (TextView) findViewById(R.id.prevBtn);
        next = (TextView) findViewById(R.id.nextBtn);
        mpv = (MusicPlayerView) findViewById(R.id.mpv);


        updateInfo();


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (MainService.mPlayer != null && MainService.mPlayer.isPlaying()) {
                lyricManager.setCurrentTimeMillis(MainService.mPlayer.getCurrentPosition());
                mpv.post(runnable);
            }
        }
    };


    private void updateInfo() {
        musicTitle.setText(songListEntity.getTitle() + " \n" + (songListEntity.isLocal() ? songListEntity.getAudio().getArtist() : songListEntity.getArtist_name()));
        mpv.postDelayed(runnable, 1000);
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
        subscription = new CompositeSubscription();
        downloadLyrc();
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

    protected CompositeSubscription subscription = new CompositeSubscription();

    private void downloadLyrc() {
        final File futureStudioIconFile = new File(getLyrcSDPath());
        Log.e("tag", futureStudioIconFile.getAbsolutePath());
        try {
            lyricManager.setNormalTextColor(R.color.colorAccent);
            lyricManager.setSelectedTextColor(R.color.colorAccent);
            lyricManager.setLyricFile(futureStudioIconFile);
        } catch (IOException e) {
            e.printStackTrace();
            subscription.add(ApiService.getInstance().createApi(MusicApi.class).downloadFileWithDynamicUrlSync(songListEntity.getLrclink())
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ResponseBody>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void call(ResponseBody t) {
                            if (t == null) return;
                            boolean writtenToDisk = writeResponseBodyToDisk(t);
                            if (writtenToDisk) {
                                try {
                                    Log.e("", futureStudioIconFile.getAbsolutePath());
                                    lyricManager.setLyricFile(futureStudioIconFile);

                                } catch (IOException e1) {
                                }
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                        }
                    }));
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = new File(getLyrcSDPath());

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.createNewFile();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @NonNull
    private String getLyrcSDPath() {
        return Environment.getExternalStorageDirectory() + File.separator + songListEntity.getTitle() + ".lrc";
    }


    @Override
    public void onPause() {
        glAudioVisualizationView.onPause();
        super.onPause();
        subscription.unsubscribe();
    }


    @Override
    protected void onDestroy() {
        glAudioVisualizationView.release();
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(String singleLine, boolean refresh) {

    }

    @Override
    public void onProgressChanged(SpannableStringBuilder stringBuilder, int lineNumber, boolean refresh) {
        if (this.lineNumber != lineNumber || refresh) {
            this.lineNumber = lineNumber;
            Log.e("歌词", String.valueOf(stringBuilder));
            lyricView.setText(stringBuilder);
            lyricView.setCurrentPosition(lineNumber);
        }
    }
}
