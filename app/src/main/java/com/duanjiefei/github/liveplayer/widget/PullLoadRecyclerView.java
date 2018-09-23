package com.duanjiefei.github.liveplayer.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duanjiefei.github.liveplayer.R;

public class PullLoadRecyclerView  extends LinearLayout{

    private final  String TAG = PullLoadRecyclerView.class.getSimpleName();

    private Context mContext;
    private SwipeRefreshLayout  swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View footView;
    private AnimationDrawable drawable;
    private boolean isRefresh = false;
    private boolean LoadMore = false;

    private OnPullLoadMoreListener mOnPullLoadMoreListener;



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
            public boolean onTouch(View v, MotionEvent event) {
                return  isRefresh || LoadMore;
            }
        });
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerViewOnScroll());

       footView =  view.findViewById(R.id.foot_view);
       ImageView foot_img = (ImageView) footView.findViewById(R.id.foot_img);
       foot_img.setBackgroundResource(R.drawable.imooc_loading);
       drawable = (AnimationDrawable) foot_img.getBackground();
       TextView foot_des = (TextView) foot_img.findViewById(R.id.foot_des);

       footView.setVisibility(View.GONE);

       this.addView(view);


    }

    class RecyclerViewOnScroll extends  RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Log.d(TAG,"onScrolled");
            int firstItem = 0;
            int lastITtem = 0;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int totalCount = layoutManager.getItemCount();
            if (layoutManager instanceof GridLayoutManager){
                layoutManager = (GridLayoutManager)layoutManager;
                firstItem = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                lastITtem = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                if (firstItem == 0 || firstItem == RecyclerView.NO_POSITION){
                    lastITtem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
            }
            // 什么时候加载更多？
            // 1 不是出于加载更多的状态
            // 2 lastITtem == totalCount-1 //最后一个可见的索引等于Adapter 中的item的子项的个数
            // 3 swipeRefreshLayout.isEnabled()  swipeRefreshLayout可用
            // 4 Refresh   不处于下拉刷新状态
            // 5 dx>0 || dy>0  向下或者向右下滑动
            if (!LoadMore
                    && lastITtem == totalCount-1
                    && swipeRefreshLayout.isEnabled()
                    && !isRefresh
                    && (dx>0 || dy>0)){

                swipeRefreshLayout.setEnabled(false);
                LoadMore = true;
                loadMoreData();
            }else {
                swipeRefreshLayout.setEnabled(true);
            }
        }
    }

    /**
     * 设置RecyclerView 的列数
     * @param count
     */
    public void setSpaceCount(int count){
        GridLayoutManager manager = new GridLayoutManager(mContext,count);
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }


    //通过外部为RecyclerView 设置Adapter
    public void setAdapter(RecyclerView.Adapter adapter){
        if (adapter!=null){
            recyclerView.setAdapter(adapter);
        }
    }

    class  RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            if (!isRefresh){
                isRefresh = true;
                refreshData();
            }
        }
    }

    /**
     * 设置刷新完毕
     */
    public void setRefreshCompleted(){
        isRefresh = false;
        setRefresh(false);
    }


    private void setRefresh(boolean refresh) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setLoadCompleted(){
        isRefresh = false;
        LoadMore = false;
        setRefresh(false);
        footView.animate().translationY(footView.getHeight()).
                setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(300).start();
    }

    public interface OnPullLoadMoreListener{
        void reFresh();
        void loadMore();
    }

    public void setOnPullLoadMoreListener(OnPullLoadMoreListener listener){
        mOnPullLoadMoreListener = listener;
    }

    private void refreshData() {
        if (mOnPullLoadMoreListener!= null){
            mOnPullLoadMoreListener.reFresh();
        }
    }

    private void loadMoreData() {
        if (mOnPullLoadMoreListener!=null){
            footView.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(350).
                    setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animator) {
                    footView.setVisibility(VISIBLE);
                    drawable.start();  //帧动画开始执行
                }
            }).start();
            invalidate();
            mOnPullLoadMoreListener.loadMore();
        }
    }

}
