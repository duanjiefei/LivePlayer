package com.duanjiefei.github.liveplayer.api;

import com.duanjiefei.github.liveplayer.model.Channel;

public abstract class BaseSiteApi {
    protected abstract void onGetChannelAlbums(Channel channel, int pagNum, int pagSize,OnGetChannelAlbumListener listener);
}
