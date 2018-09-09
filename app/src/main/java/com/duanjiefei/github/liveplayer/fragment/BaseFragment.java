package com.duanjiefei.github.liveplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment{
    protected  View viewContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            viewContainer = getActivity().getLayoutInflater().inflate(getLayoutID(),container,false);
            initView();
            initData();
            return viewContainer;
    }

    protected abstract int getLayoutID();
    protected abstract void initView();
    protected abstract void initData();

    protected <T extends View> T bindViewID(int id){
        return (T) viewContainer.findViewById(id);
    }
}
