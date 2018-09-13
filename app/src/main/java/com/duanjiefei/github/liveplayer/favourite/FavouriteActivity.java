package com.duanjiefei.github.liveplayer.favourite;

import android.app.Activity;
import android.content.Intent;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;

public class FavouriteActivity extends BaseActivity{


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fav;
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity,FavouriteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
