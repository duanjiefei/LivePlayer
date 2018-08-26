package com.duanjiefei.github.liveplayer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initView();
        initData();
    }

    protected  <T extends View>  T bindViewID(int id){
        return (T) findViewById(id);
    }
    protected abstract void initData();

    protected abstract void initView();

    protected abstract int  getLayoutID();

    public void setSupportActionBar() {
        //mToolbar = findViewById()
    }
}
