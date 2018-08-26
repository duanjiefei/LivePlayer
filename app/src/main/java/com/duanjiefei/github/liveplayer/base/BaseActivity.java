package com.duanjiefei.github.liveplayer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.duanjiefei.github.liveplayer.R;

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

    protected void setSupportActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar!=null){
            setSupportActionBar(mToolbar);
        }
    }

    protected void setActionBarIcon(int resID){
        if (mToolbar!=null){
            mToolbar.setNavigationIcon(resID);
        }
    }
}
