package com.duanjiefei.github.liveplayer.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

public class AppManager  extends Application{

    private static OkHttpClient httpClient;
    private static Context mContext;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        httpClient = new OkHttpClient();
        mContext = this;
        gson = new Gson();
    }

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }

    public static Gson getGson() {
        return gson;
    }

    /**
     * 判断当前网络状态是否可用
     * @return
     */
    public static boolean isNetWorkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return  networkInfo!=null && networkInfo.isConnected();
    }

    /**
     * 判断wifi 是否可用
     * @return
     */
    public static boolean isNetWorkWifiAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo!=null){
            NetworkInfo.State state = networkInfo.getState();
            if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING){
                return true;
            }
            else {
                return false;
            }
        }
        return  false;
    }
}
