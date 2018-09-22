package com.duanjiefei.github.liveplayer.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duanjiefei.github.liveplayer.R;

public class PullLoadRecyclerView  extends LinearLayout{

    private Context mContext;
    private SwipeRefreshLayout  swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View footView;
    private AnimationDrawable drawable;
    private boolean Refresh;
    private boolean LoadMore;



    public PullLoadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PullLoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext  = context;
        View view =  LayoutInflater.from(mContext).inflate(R.layout.pullload_recyclerview_layout,null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_dark,android.R.color.holo_green_dark,android.R.color.holo_blue_dark);


        recyclerView = (RecyclerView) view.findViewById(R.id.pull_load_recyclerView);
        recyclerView.setHasFixedSize(true);//设置RecyclerView 为固定大小
        recyclerView.setItemAnimator(new DefaultItemAnimator());//为RecyclerView 设置默认动画
        recyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return Refresh || LoadMore;
            }
        });

       footView =  view.findViewById(R.id.foot_view);
       ImageView foot_img = (ImageView) footView.findViewById(R.id.foot_img);
       foot_img.setBackgroundResource(R.drawable.imooc_loading);
       drawable = (AnimationDrawable) foot_img.getBackground();
       TextView foot_des = (TextView) foot_img.findViewById(R.id.foot_des);

       footView.setVisibility(View.GONE);

       this.addView(view);


    }

    class  RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            if (!Refresh){
                Refresh = true;
                refresData();
            }
        }
    }

    private void refresData() {
    }

}
