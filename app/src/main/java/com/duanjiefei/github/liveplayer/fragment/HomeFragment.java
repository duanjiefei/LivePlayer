package com.duanjiefei.github.liveplayer.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.duanjiefei.github.liveplayer.R;
import com.duanjiefei.github.liveplayer.adapter.HomePicAdapter;
import com.duanjiefei.github.liveplayer.detail.DetailActivity;
import com.duanjiefei.github.liveplayer.favourite.FavouriteActivity;
import com.duanjiefei.github.liveplayer.history.HistoryActivity;
import com.duanjiefei.github.liveplayer.live.LiveActivity;
import com.duanjiefei.github.liveplayer.model.Channel;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

public class HomeFragment extends BaseFragment {

    private LoopViewPager mLoopViewPager;
    private CircleIndicator mIndicator;
    private HomePicAdapter mHomePicAdapter;
    private GridView mGridView;
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
        mGridView = bindViewID(R.id.gridView);
        mGridView.setAdapter(new ChannelAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 6://直播
                        LiveActivity.launch(getActivity());
                        break;
                    case 7://喜爱
                        FavouriteActivity.launch(getActivity());
                        break;
                    case 8://历史
                        HistoryActivity.launch(getActivity());
                        break;
                    default:
                        DetailActivity.launch(getActivity(),position+1);//position == channelID
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    class  ChannelAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return Channel.MAX_COUNT;
        }

        @Override
        public Channel getItem(int i) {
            return new Channel(i+1,getActivity());
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Channel channel = getItem(i);
            ViewHolder holder = null;
            if (view == null){
                view =  LayoutInflater.from(getActivity()).inflate(R.layout.home_grid_item,null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) view.findViewById(R.id.grid_img);
                holder.textView = (TextView) view.findViewById(R.id.grid_text);
                view.setTag(holder);
            }else{
                holder  = (ViewHolder) view.getTag();
            }

            holder.textView.setText(channel.getChannelName());
            int resID = -1;
            switch (channel.getChannelID()){
                case Channel.SHOW:
                    resID = R.drawable.ic_show;
                    break;
                case Channel.MOVIE:
                    resID = R.drawable.ic_movie;
                    break;
                case Channel.COMIC:
                    resID = R.drawable.ic_comic;
                    break;
                case Channel.DOCUMENTRY:
                    resID = R.drawable.ic_documentary;
                    break;
                case Channel.MUSIC:
                    resID = R.drawable.ic_music;
                    break;
                case Channel.VARIETY:
                    resID = R.drawable.ic_variety;
                    break;
                case Channel.LIVE:
                    resID = R.drawable.ic_live;
                    break;
                case Channel.FAVORITE:
                    resID = R.drawable.ic_bookmark;
                    break;
                case Channel.HISTORY:
                    resID  = R.drawable.ic_history;
                    break;

            }
            holder.imageView.setImageResource(resID);
            return view;
        }
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
