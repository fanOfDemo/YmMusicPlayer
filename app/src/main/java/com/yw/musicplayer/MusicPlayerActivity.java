package com.yw.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.yw.musicplayer.base.BaseActivity;
import com.yw.musicplayer.event.BasePlayEvent;
import com.yw.musicplayer.po.BaiduMHotList;
import com.yw.musicplayer.service.ApiService;
import com.yw.musicplayer.service.MainService;
import com.yw.musicplayer.service.MusicApi;
import com.yw.musicplayer.util.LyricManager;
import com.yw.musicplayer.view.widget.LyricView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import co.mobiwise.library.MusicPlayerView;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MusicPlayerActivity extends BaseActivity implements LyricManager.OnProgressChangedListener {
    @Bind(R.id.visualizer_view)
    GLAudioVisualizationView glAudioVisualizationView;
    @Bind(R.id.title)
    TextView musicTitle;
    @Bind(R.id.mpv)
    MusicPlayerView mpv;
    @Bind(R.id.lyrcview)
    LyricView lyricView;
    @Bind(R.id.prevBtn)
    TextView prev;
    @Bind(R.id.nextBtn)
    TextView next;


    private BaiduMHotList.SongListEntity songListEntity;
    private LyricManager lyricManager;
    private int lineNumber = -1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_music_player;
    }

    @Override
    protected String getTag() {
        return "MusicPlayerActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songListEntity = (BaiduMHotList.SongListEntity) getIntent().getSerializableExtra("songListEntity");
        glAudioVisualizationView.linkTo(0);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (MainService.mPlayer != null && MainService.mPlayer.isPlaying()) {
                lyricManager.setCurrentTimeMillis(MainService.mPlayer.getCurrentPosition());
                if (runnable != null)
                    mpv.post(runnable);
            }
        }
    };


    private void updateInfo() {
        lyricView.setCurrentPosition(MainService.mPlayer.getCurrentPosition());
        musicTitle.setText(songListEntity.getTitle() + " \n" + (songListEntity.isLocal() ? songListEntity.getAudio().getArtist() : songListEntity.getArtist_name()));
        mpv.setCoverURL(songListEntity.isLocal() ? songListEntity.getAudio().getImagePath() : songListEntity.getPic_big());
        mpv.postDelayed(runnable, 1000);
        downloadLyrc();
    }


    @Override
    public void onResume() {
        super.onResume();
        lyricManager = LyricManager.getInstance(this);
        lyricManager.setOnProgressChangedListener(this);
        mpv.postDelayed(new Runnable() {
            @Override
            public void run() {
                initMediaView();
                glAudioVisualizationView.onResume();
                updateInfo();

            }
        }, 500);
    }

    private void initMediaView() {
        if (MainService.mPlayer.isPlaying()) {
            final int curTime = MainService.mPlayer.getDuration() / 1000;
            mpv.setMax(curTime <= 0 ? 100 : curTime);
            mpv.setProgress(MainService.mPlayer.getCurrentPosition());
            mpv.setAutoProgress(true);
            mpv.start();
        } else {
            if (MainService.mPlayer == null) {
                mpv.setProgress(0);
                mpv.setAutoProgress(true);
                mpv.stop();
            } else if (!MainService.mPlayer.isPlaying()) {
                final int curTime = MainService.mPlayer.getDuration() / 1000;
                mpv.setMax(curTime <= 0 ? 100 : curTime);
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
                    postEvent(BasePlayEvent.Opration.START);
                } else {
                    mpv.start();
                    postEvent(BasePlayEvent.Opration.START);
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEvent(BasePlayEvent.Opration.PREV);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEvent(BasePlayEvent.Opration.NEXT);
            }
        });
    }

    private void postEvent(int opration) {
        EventBus.getDefault().post(new BasePlayEvent(opration));
        songListEntity = MainService.getCurSong();
        updateInfo();
    }

    protected CompositeSubscription subscription = new CompositeSubscription();

    private void downloadLyrc() {
        final File futureStudioIconFile = new File(getLyrcSDPath());
        Log.e("tag", futureStudioIconFile.getAbsolutePath());
        if (futureStudioIconFile.exists()) {
            try {
                lyricManager.setNormalTextColor(R.color.colorAccent);
                lyricManager.setSelectedTextColor(R.color.colorAccent);
                lyricManager.setLyricFile(futureStudioIconFile);
            } catch (IOException e) {

            }
        } else {
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
