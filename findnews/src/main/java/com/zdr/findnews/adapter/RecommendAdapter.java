package com.zdr.findnews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdr.findnews.R;

import com.zdr.findnews.dbUtils.RecommendNewsDao;
import com.zdr.findnews.entity.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适配RecommendFragment
 * Created by zdr on 16-8-24.
 */
public class RecommendAdapter extends BaseAdapter {
    private List<News> mData;
    private Context mContext;

    private boolean showKeepDel = false;
    private keepDel del;
    public RecommendAdapter(List<News> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;

    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.news_item_1_pic, null, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mData.get(position).getPicUrl())
                .placeholder(R.mipmap.pic_load_default)
                .error(R.mipmap.pic_load_default)
                .crossFade(1000).into(holder.ivNewsImg);

        holder.tvNewsTitle.setText(mData.get(position).getTitle());
        holder.tvNewsTime.setText(mData.get(position).getCtime());
        holder.ivNewsItemDel.setImageResource(R.mipmap.list_icon_operation_nor);
        holder.tvNewsComm.setText("25625");
        if(mData.get(position).getIsKeepDel() == 0){
            holder.ivKeepDel.setImageResource(R.mipmap.collection_check_box);
        }else {
            holder.ivKeepDel.setImageResource(R.mipmap.collection_checked);
        }

        if (showKeepDel) {
            //设置删除监听，将待删除的数据放入delKeepList
            holder.ivKeepDel.setVisibility(View.VISIBLE);


            holder.ivKeepDel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                   del.del(position);
                }
            });

        } else {
            holder.ivKeepDel.setVisibility(View.GONE);
        }

        return convertView;
    }

    public boolean isShowKeepDel() {
        return showKeepDel;
    }

    public void setShowKeepDel(boolean showKeepDel) {

        this.showKeepDel = showKeepDel;
    }

    static class ViewHolder {
        @BindView(R.id.iv_newsImg)
        ImageView ivNewsImg;
        @BindView(R.id.tv_newsTitle)
        TextView tvNewsTitle;
        @BindView(R.id.iv_keep_del)
        ImageView ivKeepDel;
        @BindView(R.id.tv_newsSrc)
        TextView tvNewsSrc;
        @BindView(R.id.tv_newsTime)
        TextView tvNewsTime;
        @BindView(R.id.iv_news_item_Del)
        ImageView ivNewsItemDel;
        @BindView(R.id.tv_newsComm)
        TextView tvNewsComm;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public interface keepDel{
        void del(int position);
    }

    public void setDel(keepDel del) {
        this.del = del;
    }
}
