package com.duanjiefei.github.liveplayer.api.sohu;

import com.google.gson.annotations.Expose;

public class ResponseResult {

    @Expose
    private long status;

    @Expose
    private String statusText;

    @Expose
    private Data data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
