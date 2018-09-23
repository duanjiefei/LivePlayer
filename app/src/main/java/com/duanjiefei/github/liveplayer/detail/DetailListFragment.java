package com.duanjiefei.github.liveplayer.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.api.ErrorInfo;
import com.duanjiefei.github.liveplayer.api.OnGetChannelAlbumListener;
import com.duanjiefei.github.liveplayer.api.SiteApi;
import com.duanjiefei.github.liveplayer.fragment.BaseFragment;
import com.duanjiefei.github.liveplayer.model.Album;
import com.duanjiefei.github.liveplayer.model.AlbumList;
import com.duanjiefei.github.liveplayer.model.Site;
import com.duanjiefei.github.liveplayer.utils.ImageUtiils;
import com.duanjiefei.github.liveplayer.widget.PullLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailListFragment extends BaseFragment {

    private static final  String TAG = DetailListFragment.class.getSimpleName();
    private PullLoadRecyclerView pullLoadRecyclerView;
    private TextView tv_empty;
    private MyAdapter adapter;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int mColumns;

    private int pageNo;
    private int pageSize = 30;

    private static  final String SITE_ID = "site_id";
    private static  final String CHANNEL_ID = "channel_id";

    private int mSiteId;
    private int mChannelId;

    public DetailListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null ) {
            mSiteId = getArguments().getInt(SITE_ID);
            mChannelId = getArguments().getInt(CHANNEL_ID);
        }
        pageNo = 0;
        adapter = new MyAdapter(getActivity());
        loadMoreData();
        if (mSiteId == Site.LETV){
            mColumns = 2;
            adapter.setColumns(mColumns);
        }else if (mSiteId == Site.SOHU){
            mColumns = 3;
            adapter.setColumns(mColumns);
        }
    }

    public static DetailListFragment newInstance(int siteID, int channelID){
        DetailListFragment detailListFragment = new DetailListFragment();
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
        Log.d(TAG,"initView  mColumns == "+mColumns);
       pullLoadRecyclerView =  bindViewID(R.id.fragment_recycler_view);
       pullLoadRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
       pullLoadRecyclerView.setAdapter(adapter);
       pullLoadRecyclerView.setSpaceCount(mColumns);
       tv_empty = bindViewID(R.id.tv_empty);
       tv_empty.setText(getActivity().getResources().getText(R.string.load_more_text));
       tv_empty.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    class  PullLoadMoreListener implements PullLoadRecyclerView.OnPullLoadMoreListener{
        @Override
        public void reFresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reFreshData();
                    pullLoadRecyclerView.setRefreshCompleted();
                }
            },2000);
        }

        @Override
        public void loadMore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadMoreData();
                    pullLoadRecyclerView.setLoadCompleted();
                }
            },4000);
        }
    }

    /**
     * 下拉加载更多
     */
    private void loadMoreData() {
        pageNo ++;
        SiteApi.onGetChannelAlbums(getActivity(),pageNo,pageSize,mSiteId,mChannelId,new OnGetChannelAlbumListener(){
            @Override
            public void OnGetChannelAlbumSuccess(AlbumList albumList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_empty.setVisibility(View.GONE);
                    }
                });
               // albumList.debug();
                if (albumList!=null){
                    for(Album album :albumList){
                        adapter.setData(album);
                    }
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void OnGetChannelAlbumFailed(ErrorInfo errorInfo) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_empty.setText(getActivity().getResources().getString(R.string.data_failed_tip));
                    }
                });
            }
        });
    }

    /**
     * 下拉刷新数据
     */
    private void reFreshData() {
        pageNo = 0;
        adapter = null;
        adapter = new MyAdapter(getActivity());
        loadMoreData(); //第一次加载数据
        if (mSiteId == Site.LETV) { // 乐视下相关频道2列
            mColumns = 2;
            adapter.setColumns(mColumns);
        } else {
            mColumns = 3;
            adapter.setColumns(mColumns);
        }
        pullLoadRecyclerView.setAdapter(adapter);
        Toast.makeText(getActivity(), "已加载到最新数据", Toast.LENGTH_LONG).show();

    }

    class MyAdapter extends RecyclerView.Adapter{

        private List<Album> albums = new ArrayList<>();
        private Context cxt;
        private int mClms;

        public MyAdapter(Context context) {
            cxt = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view  = ((Activity)cxt).getLayoutInflater().inflate(R.layout.detailist_item,null);
            ItemViewHolder holder = new ItemViewHolder(view);
            view.setTag(holder);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (albums.size() == 0) {
                return;
            }

            Album album = albums.get(position);

            if (holder instanceof  ItemViewHolder){
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.albumName.setText(album.getTitle());
                if (album.getTip().isEmpty()) {
                    itemViewHolder.albumTip.setVisibility(View.GONE);
                } else {
                    itemViewHolder.albumTip.setText(album.getTip());
                }
                Point point = null;
                //重新计算宽高
                if (mClms == 2) {
                    point = ImageUtiils.getHorPostSize(cxt, mClms);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(point.x, point.y);
                    itemViewHolder.albumPoster.setLayoutParams(params);
                } else {
                    point = ImageUtiils.getVerPostSize(cxt, mClms);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(point.x, point.y);
                    itemViewHolder.albumPoster.setLayoutParams(params);
                }

                if (album.getVerImgUrl() != null) {
                    ImageUtiils.disPlayImage(itemViewHolder.albumPoster, album.getVerImgUrl(), point.x, point.y);
                } else if (album.getHorImgUrl() != null){
                    ImageUtiils.disPlayImage(itemViewHolder.albumPoster, album.getHorImgUrl(), point.x, point.y);
                } else {
                    //TOD 默认图
                }
            }


        }

        @Override
        public int getItemCount() {
            if (albums!=null){
                return albums.size();
            }
            return 0;
        }


        //显示列数
        public void setColumns(int columns){
            mClms = columns;
        }
        public void setData(Album album) {
            albums.add(album);
        }

        class ItemViewHolder extends  RecyclerView.ViewHolder{
            private LinearLayout resultContainer;
            private ImageView albumPoster;
            private TextView albumName;
            private TextView albumTip;

            public ItemViewHolder(View itemView) {
                super(itemView);
                resultContainer = (LinearLayout) itemView.findViewById(R.id.album_container);
                albumPoster = (ImageView) itemView.findViewById(R.id.iv_album_poster);
                albumTip = (TextView) itemView.findViewById(R.id.tv_album_tip);
                albumName = (TextView) itemView.findViewById(R.id.tv_album_name);
            }
        }
    }

    @Override
    protected <T extends View> T bindViewID(int id) {
        return super.bindViewID(id);
    }
}
