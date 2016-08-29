package com.zdr.findnews.utils;

import android.support.v4.app.Fragment;

import com.zdr.findnews.fragments.RecommendFragment;
import com.zdr.findnews.fragments.TopNewsFragment;

/**
 * 根据NewsType中的fragment属性生成不同的fragment
 * Created by zdr on 16-8-21.
 */
public class FragmentFactory {

    public static Fragment getFragmentByType(int type){
        Fragment fragment;
        switch (type){
            case Constans.FragmentType.RECOMMEND:
                fragment =  new RecommendFragment();
                break;
            case Constans.FragmentType.TOPNEWS:
                fragment = new TopNewsFragment();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

}
