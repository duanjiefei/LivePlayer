package com.duanjiefei.github.liveplayer.api;

import com.duanjiefei.github.liveplayer.model.Album;
import com.duanjiefei.github.liveplayer.model.AlbumList;
import com.duanjiefei.github.liveplayer.model.Channel;
import com.duanjiefei.github.liveplayer.model.Site;
import com.duanjiefei.github.liveplayer.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LetvApi  extends BaseSiteApi{

    private static final String TAG = LetvApi.class.getSimpleName();
    private static final int LETV_CHANNELID_MOVIE = 1; //乐视电影频道ID
    private static final int LETV_CHANNELID_SERIES = 2; //乐视电视剧频道ID
    private static final int LETV_CHANNELID_VARIETY = 11; //乐视综艺频道ID
    private static final int LETV_CHANNELID_DOCUMENTRY = 16; //乐视纪录片频道ID
    private static final int LETV_CHANNELID_COMIC = 5; //乐视动漫频道ID
    private static final int LETV_CHANNELID_MUSIC = 9; //乐视音乐频道ID
    private static final int BITSTREAM_SUPER = 100;
    private static final int BITSTREAM_NORMAL = 101;
    private static final int BITSTREAM_HIGH = 102;


    //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/listalbum/act/index/src/1/cg/2/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/1/ps/30/pcode/010110263/version/5.6.2.mindex.html
    private final static String ALBUM_LIST_URL_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_DOCUMENTARY_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/3/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_SHOW_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    @Override
    protected void onGetChannelAlbums(Channel channel, int pageNo, int pageSize,OnGetChannelAlbumListener listener) {
        String url =  getChannelAlbumUrl(channel,pageNo, pageSize);
        doGetChannelAlbumsByUrl(url, listener);
    }

    private String getChannelAlbumUrl(Channel channel, int pageNo, int pageSize) {
        if (channel.getChannelID() == Channel.DOCUMENTRY) {
            return String.format(ALBUM_LIST_URL_DOCUMENTARY_FORMAT, conVertChannleId(channel), pageNo, pageSize);
        } else if (channel.getChannelID() == Channel.DOCUMENTRY) {
            return String.format(ALBUM_LIST_URL_SHOW_FORMAT, conVertChannleId(channel), pageNo, pageSize);
        }
        return String.format(ALBUM_LIST_URL_FORMAT, conVertChannleId(channel), pageNo, pageSize);
    }


    private int conVertChannleId(Channel channel) {
        int channelId = -1;//-1 无效值
        switch (channel.getChannelID()) {
            case Channel.SHOW:
                channelId = LETV_CHANNELID_SERIES;
                break;
            case Channel.MOVIE:
                channelId = LETV_CHANNELID_MOVIE;
                break;
            case Channel.COMIC:
                channelId = LETV_CHANNELID_COMIC;
                break;
            case Channel.MUSIC:
                channelId = LETV_CHANNELID_MUSIC;
                break;
            case Channel.DOCUMENTRY:
                channelId = LETV_CHANNELID_DOCUMENTRY;
                break;
            case Channel.VARIETY:
                channelId = LETV_CHANNELID_VARIETY;
                break;
        }
        return channelId;
    }

    private void doGetChannelAlbumsByUrl(final String url, final OnGetChannelAlbumListener listener) {
        OkHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo info  = buildErrorInfo(url, "doGetChannelAlbumsByUrl", e, ErrorInfo.ERROR_TYPE_URL);
                    listener.OnGetChannelAlbumFailed(info);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo info  = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_HTTP);
                    listener.OnGetChannelAlbumFailed(info);
                    return;
                }
                String json = response.body().string();
                try {
                    JSONObject resultJson = new JSONObject(json);
                    JSONObject bodyJson = resultJson.optJSONObject("body");
                    if (bodyJson.optInt("album_count") > 0) {
                        AlbumList list = new AlbumList();
                        JSONArray albumListJosn = bodyJson.optJSONArray("album_list");
                        for (int i = 0; i< albumListJosn.length(); i++) {
                            Album album = new Album(Site.LETV);
                            JSONObject albumJson = albumListJosn.getJSONObject(i);
                            album.setAlbumId(albumJson.getString("aid"));
                            album.setAlbumDesc(albumJson.getString("subname"));
                            album.setTitle(albumJson.getString("name"));
                            album.setTip(albumJson.getString("subname"));
                            JSONObject jsonImage = albumJson.getJSONObject("images");
                            //读取【400*300】字符
                            //String imageurl = StringEscapeUtils.unescapeJava(jsonImage.getString("400*300"));
                            String imageurl = jsonImage.getString("400*300");
                            album.setHorImgUrl(imageurl);
                            list.add(album);
                        }
                        if (list != null) {
                            if (list.size() > 0 && listener != null) {
                                listener.OnGetChannelAlbumSuccess(list);
                            }
                        } else {
                            ErrorInfo info  = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                            listener.OnGetChannelAlbumFailed(info);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ErrorInfo info  = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_PARSE_JSON);
                    listener.OnGetChannelAlbumFailed(info);
                }
            }
        });
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

}
