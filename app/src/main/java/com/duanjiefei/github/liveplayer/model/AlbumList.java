package com.duanjiefei.github.liveplayer.model;

import android.util.Log;

import java.util.ArrayList;

public class AlbumList extends ArrayList<Album> {

    private static final String TAG = AlbumList.class.getSimpleName();

    public void debug(){
        for (Album album:this){
            Log.d(TAG,"album == "+album.toString());
        }
    }
}
