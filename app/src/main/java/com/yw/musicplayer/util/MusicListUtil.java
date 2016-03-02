/*
 * @wengyiming
 */
package com.yw.musicplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Audio;

import com.yw.musicplayer.po.MusicInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

// TODO: Auto-generated Javadoc

/**
 * The Class MusicListUtil.
 */
public class MusicListUtil {
	
	/**
	 * Gets the music infos.
	 * 
	 * @param mcContext
	 *            the mc context
	 * @return the music infos
	 */

	public synchronized static ArrayList<MusicInfo> getMusicInfos(
			Context mcContext) {
		ArrayList<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
		/**
		 * 根据拼音来排列ListView里面的数据类
		 */
		PinyinComparator pinyinComparator;
		pinyinComparator = new PinyinComparator();
		CharacterParser characterParser;// 汉字转换成拼音的类
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		String[] columns = new String[] { Audio.Media.TITLE, Audio.Media.DATA,
				Audio.Media.ARTIST, Audio.Media.ALBUM, Audio.Media.SIZE,
				Audio.Media.DURATION };
		Cursor musiccursor = mcContext.getContentResolver().query(
				Audio.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
		int index = 0;
		DecimalFormat df = new DecimalFormat("0.00");// 保留两位小数的格式
		while (musiccursor.moveToNext()) {
			MusicInfo musicInfo = null;
			int filesize = Integer.parseInt(musiccursor.getString(4));
			if (filesize > 800000) {
				String str_size = musiccursor.getString(4);
				double double_size = Double.parseDouble(str_size) / 1000000;
				str_size = df.format(double_size) + "M";
				String totalTime = toTime(musiccursor.getInt(5));
				// 汉字转换成拼音
				String pinyin = characterParser.getSelling(musiccursor
						.getString(0));
				String sortString = pinyin.substring(0, 1).toUpperCase();
				musicInfo = new MusicInfo();
				musicInfo.setTitle(musiccursor.getString(0));
				musicInfo.setPath(musiccursor.getString(1));
				musicInfo.setArtist(musiccursor.getString(2));
				musicInfo.setAlbum(musiccursor.getString(3));
				musicInfo.setFilesize(str_size);
				musicInfo.setTotalTime(totalTime);
				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					musicInfo.setSortLetter(sortString.toUpperCase());
				} else {
					musicInfo.setSortLetter("#");
				}
				if (index == musiccursor.getCount()) {
					break;
				}
				index++;
				musicInfos.add(musicInfo);
			} else {
				continue;
			}

		}
		musiccursor.close();
		// 根据a-z进行排序源数据
		Collections.sort(musicInfos, pinyinComparator);
		return musicInfos;
	}

	/**
	 * To time.
	 * 
	 * @param time
	 *            the time
	 * @return the string
	 */
	private static String toTime(int time) {
		int minute = time / 1000 / 60;
		int s = time / 1000 % 60;
		String mm = null;
		String ss = null;
		if (minute < 10)
			mm = "0" + minute;
		else
			mm = minute + "";

		if (s < 10)
			ss = "0" + s;
		else
			ss = "" + s;
		return mm + ":" + ss;
	}

}
