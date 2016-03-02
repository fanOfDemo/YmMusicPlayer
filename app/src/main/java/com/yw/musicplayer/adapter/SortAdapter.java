/*
 * @wengyiming
 */
package com.yw.musicplayer.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yw.musicplayer.R;
import com.yw.musicplayer.po.MusicInfo;

import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class SortAdapter.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer{
	
	/** The list. */
	private List<MusicInfo> list = null;
	
	/** The m context. */
	private Context mContext;
	
	/**
	 * Instantiates a new sort adapter.
	 * 
	 * @param mContext
	 *            the m context
	 * @param list
	 *            the list
	 */
	public SortAdapter(Context mContext, List<MusicInfo> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * Update list view.
	 * 
	 * @param list
	 *            the list
	 */
	public void updateListView(List<MusicInfo> list){
		this.list = list;
		notifyDataSetChanged();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		return this.list.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		return list.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final MusicInfo mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.musiclist_row, null);
			viewHolder.tvletter = (TextView) view
					.findViewById(R.id.tvletter);
			viewHolder.music_name = (TextView) view
					.findViewById(R.id.music_name);
			viewHolder.music_artist = (TextView) view
					.findViewById(R.id.music_artist);
			viewHolder.music_filesize = (TextView) view
					.findViewById(R.id.music_filesize);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvletter.setVisibility(View.VISIBLE);
			viewHolder.tvletter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvletter.setVisibility(View.INVISIBLE);
		}
	
		viewHolder.tvletter.setText(this.list.get(position).getSortLetters());
		viewHolder.music_name.setText(this.list.get(position).getTitle());
		viewHolder.music_artist.setText(this.list.get(position).getArtist());
		viewHolder.music_filesize.setText(this.list.get(position).getFilesize());
		
		return view;

	}
	


	/**
	 * The Class ViewHolder.
	 */
	final static class ViewHolder {
		
		/** The music_name. */
		TextView music_name;
		
		/** The music_artist. */
		TextView music_artist;
		
		/** The music_filesize. */
		TextView music_filesize;
		
		/** The tvletter. */
		TextView tvletter;
	}


	/* (non-Javadoc)
	 * @see android.widget.SectionIndexer#getSectionForPosition(int)
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/* (non-Javadoc)
	 * @see android.widget.SectionIndexer#getPositionForSection(int)
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = (String) list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	

	/* (non-Javadoc)
	 * @see android.widget.SectionIndexer#getSections()
	 */
	@Override
	public Object[] getSections() {
		return null;
	}
}
