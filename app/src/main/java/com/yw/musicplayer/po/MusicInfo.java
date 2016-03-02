/*
 * @wengyiming
 */
package com.yw.musicplayer.po;


// TODO: Auto-generated Javadoc
/**
 * The Class MusicInfo.
 */
public class MusicInfo implements Comparable<MusicInfo> {
	
	/** The title. */
	private String title;
	
	/** The sort letter. */
	private String sortLetter;
	
	/** The path. */
	private String path;
	
	/** The filesize. */
	private String filesize;
	
	/** The album. */
	private String album;
	
	/** The artist. */
	private String artist;
	
	/** The total time. */
	private String totalTime;

	/**
	 * Instantiates a new music info.
	 * 
	 * @param title
	 *            the title
	 * @param path
	 *            the path
	 * @param artist
	 *            the artist
	 * @param album
	 *            the album
	 * @param filesize
	 *            the filesize
	 * @param totalTime
	 *            the total time
	 */
	public MusicInfo(String title, String path, String artist, String album,
					 String filesize, String totalTime) {
		this.title = title;
		this.path = path;
		this.artist = artist;
		this.album = album;
		this.filesize = filesize;
		this.totalTime = totalTime;
	}
	
	/**
	 * Instantiates a new music info.
	 */
	public MusicInfo(){
		
	}


	/**
	 * Gets the sort letter.
	 * 
	 * @return the sort letter
	 */
	public String getSortLetter() {
		return sortLetter;
	}




	/**
	 * Sets the sort letter.
	 * 
	 * @param sortLetter
	 *            the new sort letter
	 */
	public void setSortLetter(String sortLetter) {
		this.sortLetter = sortLetter;
	}




	/**
	 * Gets the total time.
	 * 
	 * @return the total time
	 */
	public String getTotalTime() {
		return totalTime;
	}

	/**
	 * Sets the total time.
	 * 
	 * @param totalTime
	 *            the new total time
	 */
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the path.
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the filesize.
	 * 
	 * @return the filesize
	 */
	public String getFilesize() {
		return filesize;
	}

	/**
	 * Gets the album.
	 * 
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * Gets the artist.
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	
	
	/**
	 * Sets the comparetypeoftitle.
	 * 
	 * @param sortType
	 *            the new comparetypeoftitle
	 */
	public  void setComparetypeoftitle(int sortType) {
//		this.sortTyoe = sortType;
	}

	/**
	 * Sets the comparetypeoffilesize.
	 */
	public  void setComparetypeoffilesize() {
	}

	/**
	 * Sets the comparetypeofartist.
	 */
	public  void setComparetypeofartist() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(MusicInfo another) {
			return this.title.compareTo(another.title);
	}




	/**
	 * Gets the sort letters.
	 * 
	 * @return the sort letters
	 */
	public CharSequence getSortLetters() {
		return sortLetter;
	}




	/**
	 * Sets the path.
	 * 
	 * @param path
	 *            the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Sets the filesize.
	 * 
	 * @param filesize
	 *            the new filesize
	 */
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	/**
	 * Sets the album.
	 * 
	 * @param album
	 *            the new album
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * Sets the artist.
	 * 
	 * @param artist
	 *            the new artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public  void setTitle(String title) {
		this.title=title;
	}

}
