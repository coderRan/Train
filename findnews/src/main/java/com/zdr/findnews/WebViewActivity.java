package com.zdr.findnews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.connect.dataprovider.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zdr.findnews.dbUtils.RecommendNewsDao;
import com.zdr.findnews.entity.News;
import com.zdr.findnews.utils.Constans;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import circleImageViewlib.CircleImageView;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_web_back)
    ImageView ivWebBack;
    @BindView(R.id.web_top)
    RelativeLayout webTop;
    @BindView(R.id.iv_keep)
    ImageView ivKeep;
    @BindView(R.id.web_bottom)
    LinearLayout webBottom;
    @BindView(R.id.wb_item)
    WebView wbItem;
    @BindView(R.id.iv_share)
    ImageView ivShare;

    private boolean isKeep = false;
    private RecommendNewsDao newsDao;
    private News news;
    private News keepNews;
    IUiListener shareUiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        newsDao = RecommendNewsDao.getNewsDao(this);

        //设置进度条
        progressBar.setVisibility(View.GONE);
        wbItem.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wbItem.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        //与请求相关，更新进度条
        wbItem.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });
        wbItem.getSettings().setDefaultTextEncodingName("utf-8");
        wbItem.getSettings().setAppCacheEnabled(true);
        wbItem.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        news = (News) (getIntent().getExtras()).get("news");

        assert news != null;
        String url = news.getUrl();

        final List<News> newses = newsDao.findNewsByType(Constans.Database.KEEP_TYPE, url);
        isKeep = !newses.isEmpty();

        if (!TextUtils.isEmpty(url))
            wbItem.loadUrl(url);
        //是否已经收藏

        //设置收藏图标
        if (isKeep) {
            ivKeep.setImageResource(R.mipmap.detail_ion_collection_light);
            keepNews = newses.get(0);
        } else {
            ivKeep.setImageResource(R.mipmap.detail_ion_collection);
        }

        ivKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏监听
                if (isKeep) {
                    //取消收藏
                    newsDao.deleteByType(Constans.Database.KEEP_TYPE, keepNews.getUrl());
                    ivKeep.setImageResource(R.mipmap.detail_ion_collection);
                    isKeep = false;
                    Toast.makeText(WebViewActivity.this, "取消收藏！", Toast.LENGTH_SHORT).show();

                } else {
                    //收藏
                    keepNews = news;
                    News n = news.copySelf();
                    n.setNewsTypeName(Constans.Database.KEEP_TYPE);
                    newsDao.addNews(n);
                    ivKeep.setImageResource(R.mipmap.detail_ion_collection_light);
                    isKeep = true;
                    Toast.makeText(WebViewActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivWebBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void finish() {

        setResult(2, null);
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    @OnClick(R.id.iv_share)
    public void share(View v) {
        //实现qq共享功能
        View shareView = LayoutInflater.from(this)
                .inflate(R.layout.share_layout, null, false);
        final PopupWindow shareWindow = new PopupWindow(
                shareView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        Button cancel = (Button) shareView.findViewById(R.id.btn_cancel_share);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWindow.dismiss();
            }
        });
        CircleImageView shareQQ = (CircleImageView) shareView.findViewById(R.id.civ_share_qq);
        //开始共享
        shareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        shareWindow.setFocusable(true);
        shareWindow.setOutsideTouchable(true);
        ColorDrawable colorDrawable = new ColorDrawable(0);
        shareWindow.setBackgroundDrawable(colorDrawable);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point p = new Point();
        display.getSize(p);
        shareWindow.setAnimationStyle(R.style.popuo_anim);

        shareWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, p.y - shareView.getHeight());

    }

    private void share() {
        Tencent mTencent = Tencent.createInstance(Constans.APP_ID, this);
        shareUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.e("FX", "分享成功！");
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };

        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, news.getTitle());
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,news.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, news.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,news.getPicUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "FindNews");
        //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT);
        mTencent.shareToQQ(this, params, shareUiListener);

//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
//        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "Test：功能测试");//必填
//        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "功能测试，请自动忽略");//选填
//        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://blog.csdn.net/u013451048");//必填
//
//        mTencent.shareToQzone(this, params, shareUiListener);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, shareUiListener);
    }

}
