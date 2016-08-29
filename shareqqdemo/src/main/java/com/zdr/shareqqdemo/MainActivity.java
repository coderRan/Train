package com.zdr.shareqqdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Tencent mTencent;
    //appid
    private static String APP_ID = "1105578953";
    //获取权限列表。所有权限为 all
    private static String SCOPE = "all";
    //回调接口
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private IUiListener shareListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initQqLogin();
    }
    //初始化QQ登录分享的需要的资源
    private void initQqLogin(){
        mTencent =  Tencent.createInstance(APP_ID, this);
        //创建QQ登录回调接口
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后回调该方法
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                //设置openid，如果不设置这一步的话无法获取用户信息
                JSONObject jo = (JSONObject) o;
                String openID;
                try {
                    openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.e("LoginError:", uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
                Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        };
        //获取用户信息的回调接口
        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                try {
                    JSONObject info = (JSONObject) o;
                    Log.e("JO:", jo.toString());

                    String nickName = info.getString("nickname");
                    Toast.makeText(MainActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
        //qq分享的回调接口
        shareListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //分享成功后回调
                Toast.makeText(MainActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError uiError) {
                //分享失败后回调
            }

            @Override
            public void onCancel() {
                //取消分享后回调
            }
        };
    }
    //qq登录功能
    public void qqLogin(View view) {

        mTencent.login(this, SCOPE, loginListener);
    }
    //获取用户信息
    private void getUserInfo(){
        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(userInfoListener);
    }
    //qq分享
    public void qqShare(View view) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "qq第三方登录");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"android实现qq第三方登录，并获取用户信息");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://blog.csdn.net/u013451048/article/details/52352810");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
        mTencent.shareToQQ(this, params, shareListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //官方文档上面的是错误的
//        if(requestCode == Constants.REQUEST_API) {
//            if(resultCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
        //resultCode 是log出来的，官方文档里给的那个属性是没有的
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                getUserInfo();
            }
        }
    }
}
