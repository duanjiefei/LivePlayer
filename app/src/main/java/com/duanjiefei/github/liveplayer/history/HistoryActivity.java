package com.duanjiefei.github.liveplayer.history;

import android.app.Activity;
import android.content.Intent;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;

public class HistoryActivity extends BaseActivity{


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_his;
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity,HistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
