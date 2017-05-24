package com.yw.musicplayer.view.business.detail;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yw.musicplayer.R;
import com.yw.musicplayer.domain.model.BaiduMHotList;
import com.yw.musicplayer.event.BasePlayEvent;
import com.yw.musicplayer.uiframework.base.BaseActivity;
import com.yw.musicplayer.uikit.LyricView;
import com.yw.musicplayer.util.LyricManager;
import com.yw.musicplayer.view.MusicPlayService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import okhttp3.ResponseBody;

public class MusicPlayerActivity extends BaseActivity implements LyricManager.OnProgressChangedListener {
    @Bind(R.id.title)
    TextView musicTitle;
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
        lastPosition = MusicPlayService.postion;
        lyricManager = LyricManager.getInstance(this);
        lyricManager.setOnProgressChangedListener(this);
        lyricManager.setNormalTextColor(R.color.white);
        lyricManager.setSelectedTextColor(R.color.bt_accent);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (MusicPlayService.mPlayer != null && MusicPlayService.mPlayer.isPlaying()) {
                lyricManager.setCurrentTimeMillis(MusicPlayService.mPlayer.getCurrentPosition());
                if (runnable != null) {
                    if (musicTitle != null) {
                        musicTitle.postDelayed(runnable,1000);
                    }
                }
            }
        }
    };

    private void updateInfo() {
        if (MusicPlayService.postion != lastPosition || lastPosition == 0) {
            final int curTime = MusicPlayService.mPlayer.getDuration()/1000;
            lyricManager.setCurrentTimeMillis(MusicPlayService.mPlayer.getCurrentPosition());
            musicTitle.setText(songListEntity.getTitle() + " \n" + (songListEntity.isLocal() ? songListEntity.getAudio().getArtist() : songListEntity.getArtist_name()));
            downloadLyrc();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initMediaView();
        updateInfo();
    }

    private void initMediaView() {
        final int curTime = MusicPlayService.mPlayer.getDuration() / 1000;
        if (MusicPlayService.mPlayer.isPlaying()) {
        } else {
        }

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
        songListEntity = MusicPlayService.getCurSong();
        updateInfo();
    }


    private void downloadLyrc() {
        final File futureStudioIconFile = new File(getLyrcSDPath());
        Log.e("tag", futureStudioIconFile.getAbsolutePath());
        if (futureStudioIconFile.exists()) {
            try {
                lyricManager.setNormalTextColor(R.color.colorAccent);
                lyricManager.setSelectedTextColor(R.color.colorAccent);
                lyricManager.setLyricFile(futureStudioIconFile);
                lyricManager.setCurrentTimeMillis(MusicPlayService.mPlayer.getCurrentPosition());
            } catch (IOException e) {

            }
        } else {
            TextView textView = new TextView(this);
            textView.setText("加载中");
            lyricView.setEmptyView(textView);
            lyricManager.setCurrentTimeMillis(MusicPlayService.mPlayer.getCurrentPosition());

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
