package com.yw.musicplayer.data.network.api;


import com.yw.musicplayer.domain.model.BaiduMHotList;
import com.yw.musicplayer.domain.model.MusicData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/21 16:44
 * 修改人：wengyiming
 * 修改时间：2016/11/21 16:44
 * 修改备注：
 */

public interface MusicApi {
//    https://mrasong.com/a/baidu-mp3-api

    /**
     *
     *
     * 百度music web版全接口
     http://tingapi.ting.baidu.com/v1/restserver/ting
     获取方式：GET
     参数：
     format:  json|xml
     callback:
     from:  webapp_music
     method:
     //获取列表
     baidu.ting.billboard.billList  {type:1,size:10, offset:0}
     type: //1、新歌榜，2、热歌榜，
     11、摇滚榜，12、爵士，16、流行
     21、欧美金曲榜，22、经典老歌榜，23、情歌对唱榜，24、影视金曲榜，25、网络歌曲榜
     size: 10 //返回条目数量
     offset: 0 //获取偏移

     //貌似是推广，无用
     baidu.ting.adv.showlist  { _: (new Date)}
     _: //时间戳

     //搜索
     baidu.ting.search.catalogSug  { query: keyword }
     query: '' //搜索关键字

     //播放
     baidu.ting.song.play  {songid: id}
     baidu.ting.song.playAAC  {songid: id}

     //LRC歌词
     baidu.ting.song.lry  {songid: id}

     //推荐列表
     baidu.ting.song.getRecommandSongList  {song_id: id, num: 5 }
     num: //返回条目数量

     //下载
     baidu.ting.song.downWeb  {songid: id, bit:"24, 64, 128, 192, 256, 320, flac", _t: (new Date())}
     songid: //歌曲id
     bit: //码率
     _t: //时间戳

     //获取歌手信息
     baidu.ting.artist.getInfo  { tinguid: id }
     tinguid: //歌手ting id

     //获取歌手歌曲列表
     baidu.ting.artist.getSongList  { tinguid: id, limits:6, use_cluster:1, order:2}
     tinguid: //歌手ting id
     limits: //返回条目数量

     */


    //    type = 1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
    //http://tingapi.ting.baidu.com/v1/restserver/ting?format=json%E6%88%96xml&calback=&from=webapp_music&method=baidu.ting.billboard.billList&size=10&offset=0
    @GET("v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&offset=0")
    Observable<BaiduMHotList> getList(@Query("size") int size, @Query("type") int type, @Query("offset") int offset);


    //    method=baidu.ting.song.playAAC&songid=877578
    @GET("v1/restserver/ting?method=baidu.ting.song.playAAC")
    Observable<MusicData> play(@Query("songid") String songid);


    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
