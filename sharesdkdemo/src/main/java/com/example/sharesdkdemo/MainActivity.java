package com.example.sharesdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {
    //shareSDK网站给出的appkey
    private static String APP_KEY = "169a1284e721a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this, APP_KEY);
    }

    public void shareSDK(View view) {
        shareToQQByShareSDK();
    }

    private void shareToQQByShareSDK() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://blog.csdn.net/u013451048");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("功能测试，请自动忽略");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        //oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("功能测试：qq空间评论");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("功能测试：app name");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.csdn.net/");
        // 启动分享GUI
        oks.show(this);
    }
}
