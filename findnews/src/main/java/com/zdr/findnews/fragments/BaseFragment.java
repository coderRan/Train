package com.zdr.findnews.fragments;

import android.support.v4.app.Fragment;

/**
 * Fragment基类，用于实现延时加载数据
 * Created by zdr on 16-8-23.
 */
public abstract class BaseFragment extends Fragment {
    //当前fragment是否可见
    protected static boolean isVisible = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            lazyInitData();
        }else {
            isVisible = false;
        }
    }
    public abstract void lazyInitData();
}
