/*
 * @wengyiming
 */
package com.yw.musicplayer.util;


import com.yw.musicplayer.po.MusicInfo;

import java.util.Comparator;

// TODO: Auto-generated Javadoc

/**
 * The Class PinyinComparator.
 */
public class PinyinComparator implements Comparator<MusicInfo> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(MusicInfo o1, MusicInfo o2) {
		//这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
		if (o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")) {
			return 1;
		} else {
			return (o1.getSortLetter()).compareTo(o2.getSortLetter());
		}
	}
}
