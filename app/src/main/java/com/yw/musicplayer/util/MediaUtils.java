package com.yw.musicplayer.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.yw.musicplayer.domain.model.Ablums;
import com.yw.musicplayer.domain.model.Audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：16/3/2 下午10:42
 * 修改人：wengyiming
 * 修改时间：16/3/2 下午10:42
 * 修改备注：
 */

public class MediaUtils {

    public static List<Audio> audioList = new ArrayList<Audio>();

    public static final String[] AUDIO_KEYS = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TITLE_KEY,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST_KEY,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM_KEY,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.IS_RINGTONE,
            MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.IS_ALARM,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_NOTIFICATION,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.DATA
    };

    public static void init(final Context context, final Callback callback) {
        if (!audioList.isEmpty()) {
            callback.onGetAudioListCallback(audioList);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAudios(context);
                callback.onGetAudioListCallback(audioList);
            }
        }).start();
    }

    public interface Callback {
        void onGetAudioListCallback(List<Audio> arrayList);
    }

    private static void getAudios(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                AUDIO_KEYS,
                null,
                null,
                null);


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Bundle bundle = new Bundle();
            for (int i = 0; i < AUDIO_KEYS.length; i++) {
                final String key = AUDIO_KEYS[i];
                final int columnIndex = cursor.getColumnIndex(key);
                final int type = cursor.getType(columnIndex);
                switch (type) {
                    case Cursor.FIELD_TYPE_BLOB:
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        float floatValue = cursor.getFloat(columnIndex);
                        bundle.putFloat(key, floatValue);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        int intValue = cursor.getInt(columnIndex);
                        bundle.putInt(key, intValue);
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        String strValue = cursor.getString(columnIndex);
                        bundle.putString(key, strValue);
                        break;
                }
            }
            Audio audio = new Audio(bundle, context);
            audioList.add(audio);
        }
        cursor.close();
    }

    public static ArrayList<Ablums> getAblums() {
        ArrayList<Ablums> ablum = new ArrayList<Ablums>();
        HashMap<Audio, Integer> map = new HashMap<>();
        for (int i = 0; i < audioList.size(); i++) {
            if (null != map.get(i)) {
                map.put(audioList.get(i), map.get(audioList.get(i - 1)) + 1);
            } else {
                map.put(audioList.get(i), i);
            }
        }
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Audio key = (Audio) entry.getKey();
            Ablums ablums = new Ablums();
            ablums.setmAlbumId(key.getAlbumId());
            ablums.setmArtistId(key.getArtistId());
            ablums.addAudio(key);
            ablum.add(ablums);
            int value = Integer.parseInt(entry.getValue().toString());
        }
        return ablum;
    }


}
