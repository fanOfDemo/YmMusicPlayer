package com.yw.musicplayer.po;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/12/2 10:32
 * 修改人：wengyiming
 * 修改时间：2016/12/2 10:32
 * 修改备注：
 */

public class LyricInfo {

    private List<LineInfo> lines;
    private String artist;
    private String title;
    private String album;
    private long offset;

    public List<LineInfo> getLines() {
        if(lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(List<LineInfo> lines) {
        this.lines = lines;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public static class LineInfo {
        private String content;
        private long start;
        private long end;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

    }

}
