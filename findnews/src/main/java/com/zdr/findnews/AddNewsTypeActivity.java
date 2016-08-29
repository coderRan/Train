package com.zdr.findnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zdr.findnews.adapter.NewsTypeAdapter;
import com.zdr.findnews.dbUtils.TypeDao;
import com.zdr.findnews.entity.NewsType;
import com.zdr.findnews.view.NewsTypeGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class AddNewsTypeActivity extends AppCompatActivity {

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.gv_type)
    NewsTypeGridView gvTypeChecked;
    @BindView(R.id.gv_type2)
    NewsTypeGridView gvTypeUnChecked;
    @BindView(R.id.iv_add_back)
    ImageView ivAddBack;

    private TypeDao typeDao;
    private NewsTypeAdapter checkedAdapter;
    private NewsTypeAdapter unCheckedAdapter;

    private ArrayList<NewsType> checkedList;
    private List<NewsType> unCheckedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_type);
        ButterKnife.bind(this);
        //设置gridView
        typeDao = TypeDao.getTypeDao(this);
        checkedList = (ArrayList<NewsType>) typeDao.findCheckedType();
        unCheckedList = typeDao.findUnCheckedType();

        checkedAdapter = new NewsTypeAdapter(checkedList, this);
        gvTypeChecked.setAdapter(checkedAdapter);

        unCheckedAdapter = new NewsTypeAdapter(unCheckedList, this);
        gvTypeUnChecked.setAdapter(unCheckedAdapter);

    }

    @OnClick(R.id.tv_edit)
    public void edit(View v) {
        checkedAdapter.setShowDelIc(!checkedAdapter.isShowDelIc());
        unCheckedAdapter.setShowDelIc(!unCheckedAdapter.isShowDelIc());

        checkedAdapter.notifyDataSetChanged();
        unCheckedAdapter.notifyDataSetChanged();
    }

    @OnItemClick(R.id.gv_type)
    public void checkedItemListener(int position,View v) {
        TextView tv = (TextView) v.findViewById(R.id.tv_grid_item);
        typeDao.upCheckedByName(tv.getText().toString(), NewsType.CHECKED_FALSE);

        unCheckedList.add(checkedList.remove(position));

        checkedAdapter.notifyDataSetChanged();
        unCheckedAdapter.notifyDataSetChanged();
    }

    @OnItemClick(R.id.gv_type2)
    public void unCheckedItemListener(int position,View v) {
        TextView tv = (TextView) v.findViewById(R.id.tv_grid_item);
        typeDao.upCheckedByName(tv.getText().toString(), NewsType.CHECKED_TRUE);

        checkedList.add(unCheckedList.remove(position));

        checkedAdapter.notifyDataSetChanged();
        unCheckedAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_add_back)
    public void back(View v) {
        finish();
    }

    @Override
    public void finish() {

        setResult(1,null);
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }


}
