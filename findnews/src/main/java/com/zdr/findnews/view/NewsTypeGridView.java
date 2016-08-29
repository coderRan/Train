package com.zdr.findnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *  添加新闻类型的自定义布局解决ScrollView与GridView的滑动冲突
 * Created by zdr on 16-8-16.
 */
public class NewsTypeGridView extends GridView{

    public NewsTypeGridView(Context context) {
        super(context);
    }

    public NewsTypeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsTypeGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, size);
    }
}
