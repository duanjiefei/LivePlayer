package com.duanjiefei.github.liveplayer.fragment;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.adapter.HomePicAdapter;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

public class HomeFragment extends BaseFragment {

    private LoopViewPager mLoopViewPager;
    private CircleIndicator mIndicator;
    private HomePicAdapter mHomePicAdapter;
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mLoopViewPager = bindViewID(R.id.looperviewpager);
        mIndicator = bindViewID(R.id.indicator);
        mHomePicAdapter = new HomePicAdapter(getActivity());
        mLoopViewPager.setAdapter(mHomePicAdapter);
        mLoopViewPager.setLooperPic(true);//循环滚动
        mIndicator.setViewPager(mLoopViewPager);
    }

    @Override
    protected void initData() {

    }
}
