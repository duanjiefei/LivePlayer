package com.duanjiefei.github.liveplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.duanjiefei.github.liveplayer.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG = "GuideActivity";
    private static final String IS_FIRST = "is_first";

    private ViewPager mViewPager;
    private List<View> guideViews;
    private LinearLayout mDotLayout;
    private ImageView[] mDots;
    private int cPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDots();
    }

    private void initDots() {
        mDotLayout = findViewById(R.id.dots);
        mDots = new ImageView[guideViews.size()];
        for (int i=0;i<mDotLayout.getChildCount();i++){
            mDots[i] = (ImageView) mDotLayout.getChildAt(i);
            mDots[i].setEnabled(false);
        }
        cPosition = 0;
        mDots[0].setEnabled(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initData() {

        mPagerAdapter pagerAdapter = new mPagerAdapter(guideViews,this);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void initView() {
        mViewPager = bindViewID(R.id.guide_viewpager);
        guideViews = new ArrayList<>();
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        guideViews.add(layoutInflater.inflate(R.layout.layout_guide_one,null));
        guideViews.add(layoutInflater.inflate(R.layout.layout_guide_two,null));
        guideViews.add(layoutInflater.inflate(R.layout.layout_guide_three,null));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG,"onPageSelected"+position);
        mDots[cPosition].setEnabled(false);
        mDots[position].setEnabled(true);
        cPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class mPagerAdapter extends PagerAdapter{
        private List<View> viewList;
        private Context context;

        public mPagerAdapter(List<View> viewList, Context context) {
            this.viewList = viewList;
            this.context = context;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (viewList!=null){
                if(viewList.size()>0){
                    container.addView(viewList.get(position));

                    if (position == viewList.size()-1){
                        ImageView imageView = viewList.get(position).findViewById(R.id.ic_start);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startHomeActivity();
                                setGuide();
                            }
                        });
                    }

                    return viewList.get(position);
                }
            }
           return  null;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
           if(viewList!= null) {
               if (viewList.size() > 0) {
                   container.removeView(viewList.get(position));
               }
           }
        }

        @Override
        public int getCount() {
            if (viewList!=null){
                return viewList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private void setGuide() {
        getSharedPreferences("Config",MODE_PRIVATE).edit().putBoolean(IS_FIRST,false).commit();
    }

    private void startHomeActivity() {
        Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
