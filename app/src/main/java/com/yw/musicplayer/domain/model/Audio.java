package com.yw.musicplayer.domain.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.Serializable;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：16/3/2 下午10:41
 * 修改人：wengyiming
 * 修改时间：16/3/2 下午10:41
 * 修改备注：
 */
public class Audio implements Serializable {
    private String imagePath;
    private Uri imageUri;
    private String mTitle;
    private String mTitleKey;
    private String mArtist;
    private String mArtistKey;
    private String mComposer;
    private String mAlbum;
    private String mAlbumKey;
    private String mDisplayName;
    private String mMimeType;
    private String mPath;

    private int mId,
            mArtistId,
            mAlbumId,
            mYear,
            mTrack;

    private int mDuration = 0,
            mSize = 0;

    private boolean isRingtone = false,
            isPodcast = false,
            isAlarm = false,
            isMusic = false,
            isNotification = false;

    public Audio() {
    }

    public Audio(Bundle bundle, Context context) {
        mId = bundle.getInt(MediaStore.Audio.Media._ID);
        mTitle = bundle.getString(MediaStore.Audio.Media.TITLE);
        mTitleKey = bundle.getString(MediaStore.Audio.Media.TITLE_KEY);
        mArtist = bundle.getString(MediaStore.Audio.Media.ARTIST);
        mArtistKey = bundle.getString(MediaStore.Audio.Media.ARTIST_KEY);
        mComposer = bundle.getString(MediaStore.Audio.Media.COMPOSER);
        mAlbum = bundle.getString(MediaStore.Audio.Media.ALBUM);
        mAlbumKey = bundle.getString(MediaStore.Audio.Media.ALBUM_KEY);
        mDisplayName = bundle.getString(MediaStore.Audio.Media.DISPLAY_NAME);
        mYear = bundle.getInt(MediaStore.Audio.Media.YEAR);
        mMimeType = bundle.getString(MediaStore.Audio.Media.MIME_TYPE);
        mPath = bundle.getString(MediaStore.Audio.Media.DATA);

        mArtistId = bundle.getInt(MediaStore.Audio.Media.ARTIST_ID);
        mAlbumId = bundle.getInt(MediaStore.Audio.Media.ALBUM_ID);
        mTrack = bundle.getInt(MediaStore.Audio.Media.TRACK);
        mDuration = bundle.getInt(MediaStore.Audio.Media.DURATION);
        mSize = bundle.getInt(MediaStore.Audio.Media.SIZE);
        isRingtone = bundle.getInt(MediaStore.Audio.Media.IS_RINGTONE) == 1;
        isPodcast = bundle.getInt(MediaStore.Audio.Media.IS_PODCAST) == 1;
        isAlarm = bundle.getInt(MediaStore.Audio.Media.IS_ALARM) == 1;
        isMusic = bundle.getInt(MediaStore.Audio.Media.IS_MUSIC) == 1;
        isNotification = bundle.getInt(MediaStore.Audio.Media.IS_NOTIFICATION) == 1;

        setImage(context, mAlbumId);
    }


    private void setImage(Context context, int albumId) {
        ContentResolver musicResolve = context.getContentResolver();
        Cursor cursor = musicResolve.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{String.valueOf(albumId)},
                null);
        if (cursor != null && cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            if (!TextUtils.isEmpty(path)) {
                Uri uri = Uri.fromFile(new File(path));
                this.imagePath = path;
                this.imageUri = uri;
            }
            cursor.close();
        }

    }


    public int getId() {
        return mId;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public int getDuration() {
        return mDuration;
    }

    public int getSize() {
        return mSize;
    }

    public boolean isRingtone() {
        return isRingtone;
    }

    public boolean isPodcast() {
        return isPodcast;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTitleKey() {
        return mTitleKey;
    }

    public String getArtist() {
        return mArtist;
    }

    public int getArtistId() {
        return mArtistId;
    }

    public String getArtistKey() {
        return mArtistKey;
    }

    public String getComposer() {
        return mComposer;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public String getAlbumKey() {
        return mAlbumKey;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public int getYear() {
        return mYear;
    }

    public int getTrack() {
        return mTrack;
    }

    public String getPath() {
        return mPath;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}