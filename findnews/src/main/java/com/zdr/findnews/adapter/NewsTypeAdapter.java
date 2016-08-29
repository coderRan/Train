package com.zdr.findnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdr.findnews.R;
import com.zdr.findnews.entity.NewsType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 适配在添加新闻类型时使用的GridView
 * Created by zdr on 16-8-22.
 */
public class NewsTypeAdapter extends BaseAdapter {
    private List<NewsType> mData;
    private Context mContext;

    private boolean showDelIc = false;
    public NewsTypeAdapter(List<NewsType> mData, Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_type_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvGridItem.setText(mData.get(position).getTypeName());
        holder.ivGridDel.setImageResource(R.mipmap.closeicon_download_leftdrawer);
        if(showDelIc){
            holder.ivGridDel.setVisibility(View.VISIBLE);
        }
        else {
            holder.ivGridDel.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_grid_item)
        TextView tvGridItem;
        @BindView(R.id.iv_grid_del)
        ImageView ivGridDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public boolean isShowDelIc() {
        return showDelIc;
    }

    public void setShowDelIc(boolean showDelIc) {
        this.showDelIc = showDelIc;
    }
}
