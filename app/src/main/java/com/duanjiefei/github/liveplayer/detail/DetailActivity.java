package com.duanjiefei.github.liveplayer.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;
import com.duanjiefei.github.liveplayer.model.Channel;
import com.duanjiefei.github.liveplayer.model.Site;

import java.util.HashMap;

public class DetailActivity extends BaseActivity{

    private static final String CHANNEL_ID = "channelID";

    private int mChannelID = 0;
    private ViewPager viewPager;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent!=null){
            mChannelID = intent.getIntExtra(CHANNEL_ID,0);
        }
        Channel channel = new Channel(mChannelID,this);
        setSupportActionBar();
        setSupportArrowActionBar(true);//支持返回箭头的图标
        setTitle(channel.getChannelName());

        viewPager  = bindViewID(R.id.pager_detail);
        viewPager.setAdapter(new SitePagerAdapter(getSupportFragmentManager(),this,mChannelID));

    }

     class  SitePagerAdapter extends FragmentPagerAdapter{
        private  Context mContext;
        private  int ChannelID;
        private HashMap<Integer,DetailListFragment> mPagerMap;

        public SitePagerAdapter(FragmentManager fm, Context context,int channelID) {
            super(fm);
            ChannelID = channelID;
            mContext = context;
            mPagerMap = new HashMap<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj =  super.instantiateItem(container, position);
            if (obj instanceof  DetailListFragment){
                mPagerMap.put(position, (DetailListFragment) obj);
            }
            return obj;
        }

         @Override
         public void destroyItem(ViewGroup container, int position, Object object) {
             super.destroyItem(container, position, object);
             mPagerMap.remove(position);
         }

         @Override
         public int getCount() {
             return Site.MAX_COUNT;
         }

         @Override
        public Fragment getItem(int position) {
            return DetailListFragment.newInstance(position+1,ChannelID);
        }
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
