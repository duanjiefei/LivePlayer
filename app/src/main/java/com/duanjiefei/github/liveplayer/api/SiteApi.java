package com.duanjiefei.github.liveplayer.api;

import android.content.Context;

import com.duanjiefei.github.liveplayer.model.Channel;
import com.duanjiefei.github.liveplayer.model.Site;

public class SiteApi {
    public static void onGetChannelAlbums(Context context, int pagNum, int pagSize, int siteID, int channelID,OnGetChannelAlbumListener listener){
        switch (siteID){
            case Site.LETV:
                new LetvApi().onGetChannelAlbums(new Channel(channelID,context),pagNum, pagSize,listener);
                break;
            case Site.SOHU:
                new SohuApi().onGetChannelAlbums(new Channel(channelID,context),pagNum, pagSize,listener);
                break;
        }
    }
}
