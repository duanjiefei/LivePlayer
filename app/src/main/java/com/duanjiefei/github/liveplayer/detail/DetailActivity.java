package com.duanjiefei.github.liveplayer.detail;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;
import com.duanjiefei.github.liveplayer.model.Channel;

public class DetailActivity extends BaseActivity{

    private static final String CHANNEL_ID = "channelID";

    private int channelID = 0;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent!=null){
            channelID = intent.getIntExtra(CHANNEL_ID,0);
        }
        Channel channel = new Channel(channelID,this);
        setSupportActionBar();
        setSupportArrowActionBar(true);//支持返回箭头的图标
        setTitle(channel.getChannelName());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();//处理返回图标的点击事件，将当前的activity finish掉
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_detail;
    }

    public static void launch(Activity activity,int channelID){
        Intent intent = new Intent(activity,DetailActivity.class);
        intent.putExtra(CHANNEL_ID,channelID);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }
}
