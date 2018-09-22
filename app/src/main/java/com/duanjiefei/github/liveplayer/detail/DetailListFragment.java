package com.duanjiefei.github.liveplayer.detail;

import android.os.Bundle;
import android.view.View;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.fragment.BaseFragment;

public class DetailListFragment extends BaseFragment {

    private static int mSiteID;
    private static int mChannelID;

    private static  final String SITE_ID = "site_id";
    private static  final String CHANNEL_ID = "channel_id";

    public DetailListFragment() {
    }

    public static DetailListFragment newInstance(int siteID, int channelID){
        DetailListFragment detailListFragment = new DetailListFragment();
        mSiteID = siteID;
        mChannelID = channelID;
        Bundle bundle = new Bundle();
        bundle.putInt(SITE_ID,siteID);
        bundle.putInt(CHANNEL_ID,channelID);
        detailListFragment.setArguments(bundle);
        return detailListFragment;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_detail_list;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected <T extends View> T bindViewID(int id) {
        return super.bindViewID(id);
    }
}
