package com.duanjiefei.github.liveplayer.live;

import android.app.Activity;
import android.content.Intent;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;

public class LiveActivity  extends BaseActivity{


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_live;
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity,LiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
