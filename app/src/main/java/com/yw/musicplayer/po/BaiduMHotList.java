package com.yw.musicplayer.po;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/21 16:58
 * 修改人：wengyiming
 * 修改时间：2016/11/21 16:58
 * 修改备注：
 */

public class BaiduMHotList implements Serializable {


    /**
     * billboard_type : 1
     * billboard_no : 2020
     * update_date : 2016-11-21
     * billboard_songnum : 190
     * havemore : 1
     * name : 新歌榜
     * comment : 该榜单是根据百度音乐平台歌曲每日播放量自动生成的数据榜单，统计范围为近期发行的歌曲，每日更新一次
     * pic_s640 : http://c.hiphotos.baidu.com/ting/pic/item/f7246b600c33874495c4d089530fd9f9d62aa0c6.jpg
     * pic_s444 : http://d.hiphotos.baidu.com/ting/pic/item/78310a55b319ebc4845c84eb8026cffc1e17169f.jpg
     * pic_s260 : http://b.hiphotos.baidu.com/ting/pic/item/e850352ac65c1038cb0f3cb0b0119313b07e894b.jpg
     * pic_s210 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_c49310115801d43d42a98fdc357f6057.jpg
     * web_url : http://music.baidu.com/top/new
     */

    private BillboardEntity billboard;
    /**
     * song_list : [{"artist_id":"120870001","language":"英语","pic_big":"http://musicdata.baidu.com/data2/pic/38bfb279ede81f398e8ce486178d1397/275614033/275614033.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/4e3c79e86d037993a2a9b49df8905d87/275614036/275614036.jpg","country":"内地","area":"0","publishtime":"2016-11-07","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/c5e873c51a365a5947078d9d4e6ee705/277134939/277134939.lrc","copy_type":"1","hot":"272306","all_artist_ting_uid":"147308161","resource_type":"0","is_new":"1","rank_change":"1","rank":"1","all_artist_id":"120870001","style":"R&B","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":187,"has_mv_mobile":1,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"-1|-1\"}","song_id":"275614069","title":"July","ting_uid":"147308161","author":"吴亦凡","album_id":"275614071","album_title":"July","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":1,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"1","resource_type_ext":"1","mv_provider":"0100000000","artist_name":"吴亦凡"},{"artist_id":"274206178","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/c729ed388ccae2c85ea7eb1a951f1f9f/274912237/274912237.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/4f19b2f4335386205d8bea23f6ea4105/274912240/274912240.jpg","country":"内地","area":"0","publishtime":"2016-10-26","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/8bc825e85a7c0bd5347fb7057b063fd5/274913393/274913393.lrc","copy_type":"1","hot":"384147","all_artist_ting_uid":"239572469","resource_type":"0","is_new":"1","rank_change":"1","rank":"2","all_artist_id":"274206178","style":"影视原声","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":154,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"274912664","title":"我要你","ting_uid":"239572469","author":"任素汐","album_id":"274912674","album_title":"我要你","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"任素汐"},{"artist_id":"23428394","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/d2ffd0cb97d5cd16ec48454bf2160a87/276431797/276431797.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/5c36a72c6d3b844c56e6a02e23a50bf7/276431802/276431802.jpg","country":"内地","area":"0","publishtime":"2016-11-07","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/e4c5b26ea963daafd6f9f45b7c361558/275704377/275704377.lrc","copy_type":"1","hot":"116949","all_artist_ting_uid":"69112778","resource_type":"0","is_new":"1","rank_change":"1","rank":"3","all_artist_id":"23428394","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":275,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"259893685","title":"鼓楼先生 (正式版)","ting_uid":"69112778","author":"王梵瑞","album_id":"259893714","album_title":"鼓楼先生 (正式版)","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"王梵瑞"},{"artist_id":"5064","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/0dadd18a0cf164363491c304b63c9755/275349800/275349800.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/4189a879755f329a43b323ee546dc984/275349806/275349806.jpg","country":"内地","area":"0","publishtime":"2016-11-01","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/a33ec5bbaefd29ba94529b3dd2413a27/275350657/275350657.lrc","copy_type":"1","hot":"275078","all_artist_ting_uid":"1968","resource_type":"0","is_new":"1","rank_change":"-3","rank":"4","all_artist_id":"5064","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":257,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","song_id":"275350656","title":"天若有情","ting_uid":"1968","author":"A-Lin","album_id":"275350731","album_title":"天若有情","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"A-Lin"},{"artist_id":"89","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/f067ebd5da1f7a4cfed43fdb7d68f539/276865989/276865989.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/fc099dc984258608162326e87552b5ed/276865996/276865996.jpg","country":"内地","area":"0","publishtime":"2016-11-14","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/a0e07cf604482b1cf0a2e86f0a106ee0/276885636/276885636.lrc","copy_type":"1","hot":"78049","all_artist_ting_uid":"1078","resource_type":"0","is_new":"1","rank_change":"4","rank":"5","all_artist_id":"89","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":200,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"276867440","title":"刚好遇见你","ting_uid":"1078","author":"李玉刚","album_id":"276867491","album_title":"刚好遇见你","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"李玉刚"},{"artist_id":"444","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/07292d355a96f83d406871b6a7aaad2e/272951900/272951900.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/4b4102cfc621a8e3f54683be109f9499/272951909/272951909.jpg","country":"内地","area":"0","publishtime":"2016-10-10","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/140ce0e551282b7ea4b478521b75f376/273156819/273156819.lrc","copy_type":"1","hot":"670317","all_artist_ting_uid":"1273","resource_type":"0","is_new":"0","rank_change":"-1","rank":"6","all_artist_id":"444","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":271,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"272952711","title":"下完这场雨","ting_uid":"1273","author":"后弦","album_id":"272952776","album_title":"下完这场雨","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"后弦"},{"artist_id":"1665","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/057e1abc3b4bf363743dee5f5eb4cc02/276705215/276705215.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/dc0451c576fa48d19c146a937de626ef/276705220/276705220.jpg","country":"内地","area":"0","publishtime":"2016-11-10","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/22f2f2e19d1cad1d8a6821958bcf8e86/276720787/276720787.lrc","copy_type":"1","hot":"79411","all_artist_ting_uid":"2611","resource_type":"0","is_new":"1","rank_change":"1","rank":"7","all_artist_id":"1665","style":"影视原声","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","sound_effect":"0","file_duration":260,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"276703993","title":"爱","ting_uid":"2611","author":"刘惜君","album_id":"276705134","album_title":"咱们相爱吧 电视原声带","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"刘惜君"},{"artist_id":"141","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/f66c54247016d02ffc7a0d27d7769d12/277134999/277134999.png","pic_small":"http://musicdata.baidu.com/data2/pic/885ac04eb10036881a8e8610e5afbd3a/277135002/277135002.png","country":"内地","area":"0","publishtime":"2016-11-15","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/f147bcf186c57d9f9fe6fa8f64011ff2/277135741/277135741.lrc","copy_type":"1","hot":"65615","all_artist_ting_uid":"1107,1062","resource_type":"0","is_new":"1","rank_change":"4","rank":"8","all_artist_id":"141,68","style":"影视原声","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320","sound_effect":"0","file_duration":245,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"277135723","title":"缘分一道桥","ting_uid":"1107","author":"王力宏,谭维维","album_id":"277135200","album_title":"缘分一道桥","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"王力宏,谭维维"},{"artist_id":"23152118","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/3676fb667cd8f82b0fee70fdc67d2a74/276880628/276880628.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/28ece2378049a0ca636620b8dcf21fad/276880631/276880631.jpg","country":"内地","area":"0","publishtime":"2016-11-12","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/d83034e548338f5e4722fb354debddd7/276880854/276880854.lrc","copy_type":"1","hot":"83222","all_artist_ting_uid":"16541885","resource_type":"0","is_new":"1","rank_change":"-3","rank":"9","all_artist_id":"23152118","style":"影视原声","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320","sound_effect":"0","file_duration":176,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"276880634","title":"海洋之心","ting_uid":"16541885","author":"吉克隽逸","album_id":"276880614","album_title":"海洋之心","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"吉克隽逸"},{"artist_id":"13874366","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/41d16b8f170ca915a53a7f9166554093/274840546/274840546.jpg","pic_small":"http://musicdata.baidu.com/data2/pic/48202e0f7bd528bd629b82af6b2ef09d/274840549/274840549.jpg","country":"内地","area":"0","publishtime":"2016-10-27","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/0c963baf26ec66718e1ea1f4b4a4fd95/274939762/274939762.lrc","copy_type":"1","hot":"221149","all_artist_ting_uid":"90654808","resource_type":"0","is_new":"1","rank_change":"-3","rank":"10","all_artist_id":"13874366","style":"民谣","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320","sound_effect":"0","file_duration":329,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_id":"274841326","title":"成都","ting_uid":"90654808","author":"赵雷","album_id":"274841329","album_title":"成都","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"赵雷"}]
     * billboard : {"billboard_type":"1","billboard_no":"2020","update_date":"2016-11-21","billboard_songnum":"190","havemore":1,"name":"新歌榜","comment":"该榜单是根据百度音乐平台歌曲每日播放量自动生成的数据榜单，统计范围为近期发行的歌曲，每日更新一次","pic_s640":"http://c.hiphotos.baidu.com/ting/pic/item/f7246b600c33874495c4d089530fd9f9d62aa0c6.jpg","pic_s444":"http://d.hiphotos.baidu.com/ting/pic/item/78310a55b319ebc4845c84eb8026cffc1e17169f.jpg","pic_s260":"http://b.hiphotos.baidu.com/ting/pic/item/e850352ac65c1038cb0f3cb0b0119313b07e894b.jpg","pic_s210":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_c49310115801d43d42a98fdc357f6057.jpg","web_url":"http://music.baidu.com/top/new"}
     * error_code : 22000
     */

    private int error_code;
    /**
     * artist_id : 120870001
     * language : 英语
     * pic_big : http://musicdata.baidu.com/data2/pic/38bfb279ede81f398e8ce486178d1397/275614033/275614033.jpg
     * pic_small : http://musicdata.baidu.com/data2/pic/4e3c79e86d037993a2a9b49df8905d87/275614036/275614036.jpg
     * country : 内地
     * area : 0
     * publishtime : 2016-11-07
     * album_no : 1
     * lrclink : http://musicdata.baidu.com/data2/lrc/c5e873c51a365a5947078d9d4e6ee705/277134939/277134939.lrc
     * copy_type : 1
     * hot : 272306
     * all_artist_ting_uid : 147308161
     * resource_type : 0
     * is_new : 1
     * rank_change : 1
     * rank : 1
     * all_artist_id : 120870001
     * style : R&B
     * del_status : 0
     * relate_status : 0
     * toneid : 0
     * all_rate : 64,128,256,320,flac
     * sound_effect : 0
     * file_duration : 187
     * has_mv_mobile : 1
     * versions :
     * bitrate_fee : {"0":"0|0","1":"-1|-1"}
     * song_id : 275614069
     * title : July
     * ting_uid : 147308161
     * author : 吴亦凡
     * album_id : 275614071
     * album_title : July
     * is_first_publish : 0
     * havehigh : 2
     * charge : 0
     * has_mv : 1
     * learn : 0
     * song_source : web
     * piao_id : 0
     * korean_bb_song : 1
     * resource_type_ext : 1
     * mv_provider : 0100000000
     * artist_name : 吴亦凡
     */

    private List<SongListEntity> song_list;

    public BillboardEntity getBillboard() {
        return billboard;
    }

    public void setBillboard(BillboardEntity billboard) {
        this.billboard = billboard;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<SongListEntity> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<SongListEntity> song_list) {
        this.song_list = song_list;
    }

    public static class BillboardEntity implements Serializable {
        private String billboard_type;
        private String billboard_no;
        private String update_date;
        private String billboard_songnum;
        private int havemore;
        private String name;
        private String comment;
        private String pic_s640;
        private String pic_s444;
        private String pic_s260;
        private String pic_s210;
        private String web_url;

        public String getBillboard_type() {
            return billboard_type;
        }

        public void setBillboard_type(String billboard_type) {
            this.billboard_type = billboard_type;
        }

        public String getBillboard_no() {
            return billboard_no;
        }

        public void setBillboard_no(String billboard_no) {
            this.billboard_no = billboard_no;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getBillboard_songnum() {
            return billboard_songnum;
        }

        public void setBillboard_songnum(String billboard_songnum) {
            this.billboard_songnum = billboard_songnum;
        }

        public int getHavemore() {
            return havemore;
        }

        public void setHavemore(int havemore) {
            this.havemore = havemore;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPic_s640() {
            return pic_s640;
        }

        public void setPic_s640(String pic_s640) {
            this.pic_s640 = pic_s640;
        }

        public String getPic_s444() {
            return pic_s444;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public String getPic_s260() {
            return pic_s260;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public String getPic_s210() {
            return pic_s210;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }

    public static class SongListEntity implements Serializable {


        private String artist_id;
        private String language;
        private String pic_big;
        private String pic_small;
        private String country;
        private String area;
        private String publishtime;
        private String album_no;
        private String lrclink;
        private String copy_type;
        private String hot;
        private String all_artist_ting_uid;
        private String resource_type;
        private String is_new;
        private String rank_change;
        private String rank;
        private String all_artist_id;
        private String style;
        private String del_status;
        private String relate_status;
        private String toneid;
        private String all_rate;
        private String sound_effect;
        private int file_duration;
        private int has_mv_mobile;
        private String versions;
        private String bitrate_fee;
        private String song_id;
        private String title;
        private String ting_uid;
        private String author;
        private String album_id;
        private String album_title;
        private int is_first_publish;
        private int havehigh;
        private int charge;
        private int has_mv;
        private int learn;
        private String song_source;
        private String piao_id;
        private String korean_bb_song;
        private String resource_type_ext;
        private String mv_provider;
        private String artist_name;


        private Audio audio;

        public Audio getAudio() {
            return audio;
        }

        public void setAudio(Audio audio) {
            this.audio = audio;
        }

        public boolean isLocal() {
            return audio != null;
        }


        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getAlbum_no() {
            return album_no;
        }

        public void setAlbum_no(String album_no) {
            this.album_no = album_no;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getRank_change() {
            return rank_change;
        }

        public void setRank_change(String rank_change) {
            this.rank_change = rank_change;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getDel_status() {
            return del_status;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }

        public String getRelate_status() {
            return relate_status;
        }

        public void setRelate_status(String relate_status) {
            this.relate_status = relate_status;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public String getSound_effect() {
            return sound_effect;
        }

        public void setSound_effect(String sound_effect) {
            this.sound_effect = sound_effect;
        }

        public int getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public int getIs_first_publish() {
            return is_first_publish;
        }

        public void setIs_first_publish(int is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public String getSong_source() {
            return song_source;
        }

        public void setSong_source(String song_source) {
            this.song_source = song_source;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getKorean_bb_song() {
            return korean_bb_song;
        }

        public void setKorean_bb_song(String korean_bb_song) {
            this.korean_bb_song = korean_bb_song;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getMv_provider() {
            return mv_provider;
        }

        public void setMv_provider(String mv_provider) {
            this.mv_provider = mv_provider;
        }

        public String getArtist_name() {
            return artist_name;
        }

        public void setArtist_name(String artist_name) {
            this.artist_name = artist_name;
        }


        private MusicData musicData;

        public MusicData getMusicData() {
            return musicData;
        }

        public void setMusicData(MusicData musicData) {
            this.musicData = musicData;
        }

    }


}
