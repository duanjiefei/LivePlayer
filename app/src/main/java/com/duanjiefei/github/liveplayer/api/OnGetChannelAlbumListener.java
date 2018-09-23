package com.duanjiefei.github.liveplayer.api;

import com.duanjiefei.github.liveplayer.model.AlbumList;

public interface OnGetChannelAlbumListener {
    void OnGetChannelAlbumSuccess(AlbumList albumList);
    void OnGetChannelAlbumFailed(ErrorInfo errorInfo);
}


