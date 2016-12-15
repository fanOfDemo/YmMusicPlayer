package com.yw.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import co.mobiwise.library.MusicPlayerView;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MusicPlayerActivity extends BaseActivity implements LyricManager.OnProgressChangedListener {
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
    private int lastPosition = 0;

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
        lastPosition = MainService.postion;
        lyricManager = LyricManager.getInstance(this);
        lyricManager.setOnProgressChangedListener(this);
        lyricManager.setNormalTextColor(R.color.white);
        lyricManager.setSelectedTextColor(R.color.bt_accent);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (MainService.mPlayer != null && MainService.mPlayer.isPlaying()) {
                lyricManager.setCurrentTimeMillis(MainService.mPlayer.getCurrentPosition());
                if (runnable != null) {
                    if (musicTitle != null) {
                        musicTitle.postDelayed(runnable,1000);
                    }
                }
            }
        }
    };

    private void updateInfo() {
        if (MainService.postion != lastPosition || lastPosition == 0) {
            final int curTime = MainService.mPlayer.getDuration()/1000;
            lyricManager.setCurrentTimeMillis(MainService.mPlayer.getCurrentPosition());
            musicTitle.setText(songListEntity.getTitle() + " \n" + (songListEntity.isLocal() ? songListEntity.getAudio().getArtist() : songListEntity.getArtist_name()));
            mpv.setCoverURL(songListEntity.isLocal() ? songListEntity.getAudio().getImagePath() : songListEntity.getPic_big());
            mpv.postDelayed(runnable, 500);
            mpv.setMax(curTime <= 0 ? 100 : curTime);
            downloadLyrc();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mpv.postDelayed(new Runnable() {
            @Override
            public void run() {
                initMediaView();
                updateInfo();
            }
        }, 500);
    }

    private void initMediaView() {
        final int curTime = MainService.mPlayer.getDuration() / 1000;
        if (MainService.mPlayer.isPlaying()) {
            mpv.setMax(curTime <= 0 ? 100 : curTime);
            mpv.setAutoProgress(true);
            mpv.start();
        } else {
            if (MainService.mPlayer == null) {
                mpv.setProgress(0);
                mpv.setAutoProgress(true);
                mpv.stop();
            } else if (!MainService.mPlayer.isPlaying()) {
                mpv.setMax(curTime <= 0 ? 100 : curTime);
                mpv.setAutoProgress(true);
                mpv.stop();
            }
        }

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
                lyricManager.setCurrentTimeMillis(MainService.mPlayer.getCurrentPosition());
            } catch (IOException e) {

            }
        } else {
            TextView textView = new TextView(this);
            textView.setText("加载中");
            lyricView.setEmptyView(textView);
            lyricManager.setCurrentTimeMillis(MainService.mPlayer.getCurrentPosition());
            subscription.add(ApiService.getInstance().createApi(MusicApi.class).downloadFileWithDynamicUrlSync(songListEntity.getLrclink())
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<ResponseBody, Boolean>() {
                        @Override
                        public Boolean call(ResponseBody responseBody) {
                            return responseBody != null && writeResponseBodyToDisk(responseBody);

                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void call(Boolean responseBody) {
                            if (responseBody) {
                                Log.e("", futureStudioIconFile.getAbsolutePath());
                                try {
                                    lyricManager.setLyricFile(futureStudioIconFile);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
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
    public void onProgressChanged(String singleLine, boolean refresh) {

    }

    @Override
    public void onProgressChanged(SpannableStringBuilder stringBuilder, int lineNumber, boolean refresh) {
        if (this.lineNumber != lineNumber || refresh) {
            this.lineNumber = lineNumber;
            lyricView.setText(stringBuilder);
            lyricView.setCurrentPosition(lineNumber);
        }
    }
}
