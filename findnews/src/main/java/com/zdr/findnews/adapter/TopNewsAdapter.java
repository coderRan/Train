package com.zdr.findnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdr.findnews.R;
import com.zdr.findnews.entity.TopNews;
import com.zdr.findnews.fragments.TopNewsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适配TopNewsFragment,item:news_item_1_pic.xml
 * Created by zdr on 16-8-21.
 */
public class TopNewsAdapter extends BaseAdapter {
    private List<TopNews> mData;
    private Context mContext;

    public TopNewsAdapter(List<TopNews> mData, Context mContext) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.news_item_1_pic, null, false);

            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mData.get(position).getImg())
                .placeholder(R.mipmap.pic_load_failed)
                .error(R.mipmap.pic_load_failed)
                .crossFade(1000).into(holder.ivNewsImg);
        holder.tvNewsTitle.setText(mData.get(position).getTitle());
        holder.tvNewsSrc.setText(mData.get(position).getAuthor());
        //holder.tvNewsTime.setText(mData.get(position).getTime());
        holder.ivNewsItemDel.setImageResource(R.mipmap.list_icon_operation_nor);
        holder.tvNewsComm.setText("12546");

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_newsImg)
        ImageView ivNewsImg;
        @BindView(R.id.tv_newsTitle)
        TextView tvNewsTitle;
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
}
