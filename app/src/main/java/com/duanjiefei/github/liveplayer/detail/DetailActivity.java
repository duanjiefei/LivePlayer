package com.duanjiefei.github.liveplayer.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.base.BaseActivity;
import com.duanjiefei.github.liveplayer.indiactor.CoolIndicatorLayout;
import com.duanjiefei.github.liveplayer.indiactor.IPagerIndicatorView;
import com.duanjiefei.github.liveplayer.indiactor.IPagerTitle;
import com.duanjiefei.github.liveplayer.indiactor.ViewPagerITitleView;
import com.duanjiefei.github.liveplayer.indiactor.ViewPagerIndicatorAdapter;
import com.duanjiefei.github.liveplayer.indiactor.ViewPagerIndicatorLayout;
import com.duanjiefei.github.liveplayer.indiactor.ViewPagerWrapper;
import com.duanjiefei.github.liveplayer.indiactor.ViewPaperIndicatorView;
import com.duanjiefei.github.liveplayer.model.Channel;
import com.duanjiefei.github.liveplayer.model.Site;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends BaseActivity{

    private static final String CHANNEL_ID = "channelID";

    private int mChannelID = 0;
    private ViewPager viewPager;

    String [] mSiteNames = new String[] {"搜狐视频","乐视视频"};
    private List<String> mDataSet = Arrays.asList(mSiteNames);


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
        CoolIndicatorLayout coolIndicatorLayout = bindViewID(R.id.viewpager_indicator);
        //组配indicator及title
        ViewPagerIndicatorLayout viewPagerIndicatorLayout = new ViewPagerIndicatorLayout(this);
        viewPagerIndicatorLayout.setAdapter(new ViewPagerIndicatorAdapter() {
            @Override
            public int getCount() {
                return mDataSet.size();
            }

            @Override
            public IPagerTitle getTitle(Context context, final int index) {
                ViewPagerITitleView viewPagerITitleView = new ViewPagerITitleView(context);
                viewPagerITitleView.setText(mDataSet.get(index));
                viewPagerITitleView.setNormalColor(Color.parseColor("#333333"));
                viewPagerITitleView.setSelectedColor(Color.parseColor("#e94220"));
                viewPagerITitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return viewPagerITitleView;
            }

            @Override
            public IPagerIndicatorView getIndicator(Context conext) {
                ViewPaperIndicatorView viewPaperIndicatorView = new ViewPaperIndicatorView(conext);
                viewPaperIndicatorView.setFillColor(Color.parseColor("#ebe4e3"));
                return viewPaperIndicatorView;
            }
        });

        coolIndicatorLayout.setPagerIndicatorLayout(viewPagerIndicatorLayout);
        ViewPagerWrapper.with(coolIndicatorLayout, viewPager).compose();
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
