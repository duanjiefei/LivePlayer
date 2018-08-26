package com.duanjiefei.github.liveplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duanjiefei.github.liveplayer.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private static final String IS_FIRST = "is_first";
    private static final int GO_HOME = 0;
    private static final int GO_GUIDE = 1;

    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
                    startHomeActivity();
                    break;
                case GO_GUIDE:
                    startGuideActivity();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }
    @Override
    protected void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Config",MODE_PRIVATE);
        boolean is_first =  sharedPreferences.getBoolean(IS_FIRST,true);
        Log.d(TAG,">>initData is_first=="+is_first);
        if (is_first){
            handler.sendEmptyMessageDelayed(GO_GUIDE,5000);
        }else {
            handler.sendEmptyMessageDelayed(GO_HOME,5000);
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startGuideActivity() {
        Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initView() {

    }
}
