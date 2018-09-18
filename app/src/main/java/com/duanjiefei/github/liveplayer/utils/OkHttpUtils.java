package com.duanjiefei.github.liveplayer.utils;

import com.duanjiefei.github.liveplayer.application.AppManager;

import okhttp3.Callback;
import okhttp3.Request;

public class OkHttpUtils {

    private static final String TAG = "okHttp";

    public static Request BuildRequest(String url) {
        if (AppManager.isNetWorkAvailable()) {
            Request request = new Request.Builder()
                    .tag(TAG)
                    .url(url)
                    .build();
            return request;
        }
        return null;
    }

    public static void execute(String url, Callback callback) {
        Request request = BuildRequest(url);
        execute(request, callback);
    }

    public static void execute(Request request, Callback callback) {
        AppManager.getHttpClient().newCall(request).enqueue(callback);
    }

}
