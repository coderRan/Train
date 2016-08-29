package com.zdr.findnews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zdr.findnews.R;
import com.zdr.findnews.adapter.TopNewsAdapter;
import com.zdr.findnews.entity.News;
import com.zdr.findnews.entity.NewsJSON;
import com.zdr.findnews.entity.TopNews;
import com.zdr.findnews.entity.TopNewsJSON;
import com.zdr.findnews.netUtils.Callback;
import com.zdr.findnews.netUtils.VolleyManager;
import com.zdr.findnews.utils.Constans;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 头条新闻界面适配多个界面
 * A simple {@link Fragment} subclass.
 */
public class TopNewsFragment extends Fragment {
    @BindView(R.id.ptrlv_topnews)
    PullToRefreshListView ptrlvTopnews;

    private List<TopNews> mData;
    private View view;
    private String url;
    private int page = 1;
    private TopNewsAdapter adapter;

    public TopNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            return view;
        }
        view = inflater.inflate(R.layout.fragment_top_news, container, false);
        ButterKnife.bind(this, view);
        //设置adapter
        mData = new ArrayList<>();
        adapter = new TopNewsAdapter(mData, getActivity());
        ptrlvTopnews.setAdapter(adapter);
        ptrlvTopnews.setMode(PullToRefreshBase.Mode.BOTH);

        //设置下拉刷新监听
        ptrlvTopnews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadLatest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });
        return view;
    }

    /**
     * 下拉刷新加载数据
     */
    private void loadLatest() {
        page = 1;
        VolleyManager.get(url+page)
                .setMethod(Request.Method.GET)
                .setCallbacl(new TopNewsLatestCallback())
                .buile()
                .addHeader("apikey", Constans.API_KEY)
                .start();
    }

    /**
     * 上拉刷新加载更多数据
     */
    private void loadMore() {
        page++;
        VolleyManager.get(url+page)
                .setMethod(Request.Method.GET)
                .setCallbacl(new TopNewsMoreCallback())
                .buile()
                .addHeader("apikey", Constans.API_KEY)
                .start();
    }
    private class TopNewsLatestCallback implements Callback{
        @Override
        public void onSuccess(String respone) {
            Log.e("JSON", respone);
            mData.clear();

            String s = null;
            try {
                s = new String(respone.getBytes(), "utf-8");
                Log.e("JSON", s);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //初始化mData
            initmData(Constans.GSON.fromJson(respone, TopNewsJSON.class));
            adapter.notifyDataSetChanged();
            ptrlvTopnews.onRefreshComplete();
        }

        @Override
        public void onError(String err) {

        }
    }

    private class TopNewsMoreCallback implements Callback{
        @Override
        public void onSuccess(String respone) {

            //初始化mData
            initmData(Constans.GSON.fromJson(respone, TopNewsJSON.class));
            Log.e("JSON", respone);
            adapter.notifyDataSetChanged();
            ptrlvTopnews.onRefreshComplete();
        }

        @Override
        public void onError(String err) {

        }
    }
    /**
     * 利用Json数据初始化当前fragment中的Listview数据
     */
    private void initmData(TopNewsJSON  topNewsJson) {

        for (TopNews n:topNewsJson.getData().getArticle()) {
            mData.add(n);
        }
    }

}
