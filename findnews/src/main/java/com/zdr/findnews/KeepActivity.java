package com.zdr.findnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zdr.findnews.adapter.RecommendAdapter;
import com.zdr.findnews.dbUtils.RecommendNewsDao;
import com.zdr.findnews.entity.News;
import com.zdr.findnews.utils.Constans;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeepActivity extends AppCompatActivity {


    @BindView(R.id.iv_keep_back)
    ImageView ivKeepBack;
    @BindView(R.id.ptrlv_keep)
    PullToRefreshListView ptrlvKeep;
    @BindView(R.id.tv_del)
    TextView tvDel;
    @BindView(R.id.iv_keep_del)
    ImageView ivKeepDel;


    private RecommendAdapter adapter;
    private RecommendNewsDao newsDao;
    private List<News> mData;
    private List<News> delKeepList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep);
        ButterKnife.bind(this);
        newsDao = RecommendNewsDao.getNewsDao(this);
        mData = newsDao.findAllNewsByType(Constans.Database.KEEP_TYPE);
        adapter = new RecommendAdapter(mData, this);
        ptrlvKeep.setAdapter(adapter);
        delKeepList = new ArrayList<>();

        ptrlvKeep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (adapter.isShowKeepDel()) {
                    return;
                }
                Intent intent = new Intent(KeepActivity.this, WebViewActivity.class);

                intent.putExtra("news", mData.get(position - 1));
                startActivityForResult(intent, 2);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });
        adapter.setDel(new RecommendAdapter.keepDel() {
            @Override
            public void del(int position) {
                if (mData.get(position).getIsKeepDel() == 0) {
                    mData.get(position).setIsKeepDel(1);
                    delKeepList.add(mData.get(position));

                } else {
                    mData.get(position).setIsKeepDel(0);
                    delKeepList.remove(mData.get(position));
                }
                adapter.notifyDataSetChanged();
            }
        });


    }

    @OnClick(R.id.iv_keep_back)
    public void back() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @OnClick(R.id.tv_del)
    public void showDel() {
        adapter.setShowKeepDel(!adapter.isShowKeepDel());
        if (adapter.isShowKeepDel()) {
            tvDel.setText("取消");
            for (int i = 0; i < mData.size(); i++) {
                mData.get(i).setIsKeepDel(0);
            }
            ivKeepDel.setVisibility(View.VISIBLE);

        } else {
            ivKeepDel.setVisibility(View.GONE);
            tvDel.setText("管理");
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_keep_del)
    public void delNews() {
        newsDao.deleteAll(delKeepList);
        mData.removeAll(delKeepList);
        adapter.setShowKeepDel(false);
        tvDel.setText("管理");
        ivKeepDel.setVisibility(View.GONE);
        delKeepList.clear();

        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == 2) {
            mData.clear();
            mData.addAll(newsDao.findAllNewsByType(Constans.Database.KEEP_TYPE));
            adapter.notifyDataSetChanged();

        }
    }


}
