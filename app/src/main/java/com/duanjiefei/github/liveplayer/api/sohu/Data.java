package com.duanjiefei.github.liveplayer.api.sohu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @Expose
    private int count;

    @SerializedName("videos")
    @Expose
    private List<ResponseAblum> ablumList;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResponseAblum> getAblumList() {
        return ablumList;
    }

    public void setAblumList(List<ResponseAblum> ablumList) {
        this.ablumList = ablumList;
    }
}
