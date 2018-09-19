package com.duanjiefei.github.liveplayer.live;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;

public class LiveActivity  extends BaseActivity{

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_live;
    }
    @Override
    protected void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        setSupportActionBar();
        setSupportArrowActionBar(true);
        setTitle(R.string.channel_live);

        recyclerView = bindViewID(R.id.ry_live);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        gridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        LiveItemAdapter liveAdapter = new LiveItemAdapter(this);
        recyclerView.setAdapter(liveAdapter);
    }

    public static void launch(Activity activity){
        Intent intent = new Intent(activity,LiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
