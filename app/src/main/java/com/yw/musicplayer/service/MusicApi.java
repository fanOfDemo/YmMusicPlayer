package com.yw.musicplayer.service;

import com.yw.musicplayer.po.BaiduMHotList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

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

//    type = 1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
    //http://tingapi.ting.baidu.com/v1/restserver/ting?format=json%E6%88%96xml&calback=&from=webapp_music&method=baidu.ting.billboard.billList&size=10&offset=0
    @GET("v1/restserver/ting?format=json%E6%88%96xml&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=1&offset=0")
    Observable<BaiduMHotList> login(@Query("size")int size,@Query("type") int type);
}
