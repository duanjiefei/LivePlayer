package com.duanjiefei.github.liveplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duanjiefei.github.liveplayer.base.BaseActivity;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void initView() {
        setSupportActionBar();
        setActionBarIcon(R.drawable.ic_drawer_home);
        setTitle("主页");
    }
}
