package com.zdr.findnews.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zdr.findnews.R;
import com.zdr.findnews.WebViewActivity;
import com.zdr.findnews.adapter.RecommendAdapter;
import com.zdr.findnews.dbUtils.RecommendNewsDao;
import com.zdr.findnews.entity.News;
import com.zdr.findnews.entity.NewsJSON;

import com.zdr.findnews.netUtils.Callback;
import com.zdr.findnews.netUtils.VolleyManager;
import com.zdr.findnews.utils.Constans;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 推荐界面,适配大部分新闻界面
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseFragment {

    @BindView(R.id.ptrlv_recommend)
    PullToRefreshListView ptrlvRecommend;

    private View v;
    private List<News> mData;
    private RecommendAdapter adapter;
    private String url;
    private String newsTypeName;
    private int page = 1;
    private RecommendNewsDao newsDao;
    private boolean isPrepared = false;
    private boolean isFirst = true;
    public RecommendFragment() {
        // Required empty public constructor
    }
    //实现延时加载
    @Override
    public void lazyInitData() {
        if (isPrepared && isVisible && isFirst) {
            mData.clear();
            mData.addAll(newsDao.findAllNewsByType(newsTypeName));
            adapter.notifyDataSetChanged();
            ptrlvRecommend.onRefreshComplete();
            loadlatest();
            isFirst = false;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
        newsTypeName = getArguments().getString("type");
        newsDao = RecommendNewsDao.getNewsDao(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //如果当前页面不为空，将不再创建
        if(v != null){
            return v;
        }
        v = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, v);
        //设置ptrListView
        mData = new ArrayList<>();

        adapter = new RecommendAdapter(mData, getActivity());
        ptrlvRecommend.setAdapter(adapter);
        ptrlvRecommend.setMode(PullToRefreshBase.Mode.BOTH);

        //设置ptrlv刷新监听
        ptrlvRecommend.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新监听
                loadlatest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上啦刷新监听
                loadeMore();
            }
        });

        ptrlvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);

                intent.putExtra("news", mData.get(position-1));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
        isPrepared = true;
        lazyInitData();
        return v;
    }

    /**
     * 下拉刷新加载数据
     */
    private void loadlatest() {
        page = 1;
        VolleyManager.get(url+page)
                .setMethod(Request.Method.GET)
                .setCallbacl(new RecommendLatestCallback())
                .buile()
                .addHeader("apikey", Constans.API_KEY)
                .start();
    }



    private class RecommendLatestCallback implements Callback{

        @Override
        public void onSuccess(String respone) {
            mData.clear();
            //初始化mData
            initmData(Constans.GSON.fromJson(respone, NewsJSON.class));
            //添加进数据库
            newsDao.deleteByType(newsTypeName);
            newsDao.addAllNews(mData);

            adapter.notifyDataSetChanged();
            ptrlvRecommend.onRefreshComplete();
        }

        @Override
        public void onError(String err) {
            Log.e("err", err);
            mData.clear();
            mData.addAll(newsDao.findAllNewsByType(newsTypeName));

            adapter.notifyDataSetChanged();
            ptrlvRecommend.onRefreshComplete();

        }
    }

    /**
     * 上拉刷新加载更多数据
     */
    private void loadeMore() {
        page++;
        VolleyManager.get(url+page)
                .setMethod(Request.Method.GET)
                .setCallbacl(new RecommendMoreCallback())
                .buile()
                .addHeader("apikey", Constans.API_KEY)
                .start();
    }

    private class RecommendMoreCallback implements Callback{

        @Override
        public void onSuccess(String respone) {
            Log.e("JSON", respone);

            //初始化mData
            initmData(Constans.GSON.fromJson(respone, NewsJSON.class));

            adapter.notifyDataSetChanged();
            ptrlvRecommend.onRefreshComplete();
        }

        @Override
        public void onError(String err) {
            Log.e("err", err);

            adapter.notifyDataSetChanged();
            ptrlvRecommend.onRefreshComplete();

        }
    }

    /**
     * 利用Json数据初始化当前fragment中的Listview数据
     */
    private void initmData(NewsJSON newsJson) {

        for (News n:newsJson.getNewsList()) {
            n.setNewsTypeName(newsTypeName);
            mData.add(n);
        }
    }

}
