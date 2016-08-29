package com.zdr.findnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zdr.findnews.entity.NewsType;

import java.util.List;

/**
 * 主界面中ViewPage的适配器，每个item是一个fragment
 * Created by zdr on 16-8-19.
 */
public class VPNewsAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragment;
    private List<NewsType> mNewsType;

    public VPNewsAdapter(FragmentManager fm, List<Fragment> mFragment, List<NewsType> mNewsType) {
        super(fm);
        this.mFragment = mFragment;
        this.mNewsType = mNewsType;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragment.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragment.size();
    }

    //将viewpage与tablayout关联起来，设置tab的title需要重写下面两个方法
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return this.mNewsType.get(position).getTypeName();
    }

}
