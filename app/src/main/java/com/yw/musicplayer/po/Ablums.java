package com.yw.musicplayer.po;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/16 17:38
 * 修改人：wengyiming
 * 修改时间：2016/11/16 17:38
 * 修改备注：
 */
public class Ablums {
    private int mId,
            mArtistId,
            mAlbumId;

    private List<Audio> audios = new ArrayList<>();

    public Ablums() {
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public void addAudio(Audio audios) {
        this.audios.add(audios);
    }

    public void setAudios(List<Audio> audios) {
        this.audios = audios;
    }

    public int getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(int mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public int getmArtistId() {
        return mArtistId;
    }

    public void setmArtistId(int mArtistId) {
        this.mArtistId = mArtistId;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
