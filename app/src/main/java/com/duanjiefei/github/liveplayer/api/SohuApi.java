package com.duanjiefei.github.liveplayer.api;

import android.util.Log;

import com.duanjiefei.github.liveplayer.api.sohu.ResponseAblum;
import com.duanjiefei.github.liveplayer.api.sohu.ResponseResult;
import com.duanjiefei.github.liveplayer.application.AppManager;
import com.duanjiefei.github.liveplayer.model.Album;
import com.duanjiefei.github.liveplayer.model.AlbumList;
import com.duanjiefei.github.liveplayer.model.Channel;
import com.duanjiefei.github.liveplayer.model.Site;
import com.duanjiefei.github.liveplayer.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SohuApi extends BaseSiteApi {


    private static final String TAG = SohuApi.class.getSimpleName();
    private static final int SOHU_CHANNELID_MOVIE = 1; //搜狐电影频道ID
    private static final int SOHU_CHANNELID_SERIES = 2; //搜狐电视剧频道ID
    private static final int SOHU_CHANNELID_VARIETY = 7; //搜狐综艺频道ID
    private static final int SOHU_CHANNELID_DOCUMENTRY = 8; //搜狐纪录片频道ID
    private static final int SOHU_CHANNELID_COMIC = 16; //搜狐动漫频道ID
    private static final int SOHU_CHANNELID_MUSIC = 24; //搜狐音乐频道ID

    private final static String API_CHANNEL_ALBUM_FORMAT = "http://api.tv.sohu.com/v4/search/channel.json" +
            "?cid=%s&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&" +
            "sver=6.2.0&sysver=4.4.2&partner=47&page=%s&page_size=%s";
    @Override
    protected void onGetChannelAlbums(Channel channel, int pagNum, int pagSize, OnGetChannelAlbumListener listener) {
        String url  =  getChannelAlbumUrl(channel,pagNum,pagSize);
        doGetHttpRequest(url,listener);//发起网络请求
    }

    private void doGetHttpRequest(final String url, final OnGetChannelAlbumListener listener) {
        Log.d(TAG,"doGetHttpRequest"+ url);
        OkHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null ){
                    ErrorInfo errorInfo = buildErrorInfo(url,"doGetHttpRequest",e,ErrorInfo.ERROR_TYPE_URL);
                    listener.OnGetChannelAlbumFailed(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()){
                    ErrorInfo errorInfo = buildErrorInfo(url,"doGetHttpRequest",null,ErrorInfo.ERROR_TYPE_HTTP);
                    listener.OnGetChannelAlbumFailed(errorInfo);
                    return;
                }

                //Log.d(TAG,"response.body().toString() == "+response.body().string());
                if (response.isSuccessful()){
                    ResponseResult result = AppManager.getGson().fromJson(response.body().string(),ResponseResult.class);
                    AlbumList albumList = toConverAlbumList(result);

                    if (albumList!=null){
                        if (albumList.size()>0 && listener != null){
                            listener.OnGetChannelAlbumSuccess(albumList);
                        }
                    }else {
                        ErrorInfo errorInfo = buildErrorInfo(url,"doGetHttpRequest",null,ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                        listener.OnGetChannelAlbumFailed(errorInfo);
                    }
                }


            }
        });
    }

    /**
     * 将服务端返回的数据转化为 客户端需要的数据AlbumList
     * @param result
     * @return
     */
    private AlbumList toConverAlbumList(ResponseResult result) {
        if (result.getData().getAblumList().size()>0){
            AlbumList albumList = new AlbumList();
            for (ResponseAblum responseAblum :result.getData().getAblumList()){
                Album album  = new Album(Site.SOHU);
                album.setAlbumDesc(responseAblum.getTvDesc());
                album.setAlbumId(responseAblum.getAlbumId());
                album.setHorImgUrl(responseAblum.getHorHighPic());
                album.setMainActor(responseAblum.getMainActor());
                album.setTip(responseAblum.getTip());
                album.setTitle(responseAblum.getAlbumName());
                album.setVerImgUrl(responseAblum.getVerHighPic());
                album.setDirector(responseAblum.getDirector());
                albumList.add(album);
            }
            return albumList;
        }
        return null;
    }

    private ErrorInfo buildErrorInfo(String url, String doGetHttpRequest, IOException e, int errorTypeUrl) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setClassName(TAG);
        errorInfo.setErrorException(e.getMessage());
        errorInfo.setFunctionName(doGetHttpRequest);
        errorInfo.setUrl(url);
        errorInfo.setType(errorTypeUrl);
        return  errorInfo;
    }

    private String getChannelAlbumUrl(Channel channel, int pagNum, int pagSize) {
        String url = String.format(API_CHANNEL_ALBUM_FORMAT,transFormChannelID(channel),pagNum,pagSize);
        return  url;
    }

    private int transFormChannelID(Channel channel) {
        int channelId = -1;//-1 无效值
        switch (channel.getChannelID()) {
            case Channel.SHOW:
                channelId = SOHU_CHANNELID_SERIES;
                break;
            case Channel.MOVIE:
                channelId = SOHU_CHANNELID_MOVIE;
                break;
            case Channel.COMIC:
                channelId = SOHU_CHANNELID_COMIC;
                break;
            case Channel.MUSIC:
                channelId = SOHU_CHANNELID_MUSIC;
                break;
            case Channel.DOCUMENTRY:
                channelId = SOHU_CHANNELID_DOCUMENTRY;
                break;
            case Channel.VARIETY:
                channelId = SOHU_CHANNELID_VARIETY;
                break;
        }
        return channelId;
    }
}
