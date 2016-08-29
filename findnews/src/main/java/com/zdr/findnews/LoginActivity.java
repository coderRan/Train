//package com.zdr.findnews;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.tencent.connect.UserInfo;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
//import com.zdr.findnews.utils.Constans;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import circleImageViewlib.CircleImageView;
//
//public class LoginActivity extends AppCompatActivity {
//
//    @BindView(R.id.iv_login_back)
//    ImageView ivLoginBack;
//    @BindView(R.id.civ_login_qq)
//    CircleImageView civLoginQq;
//
//    private Tencent mTencent;
//
//    private IUiListener loginListener;
//    private IUiListener userInfoiuiListener;
//    private String userName;
//    private String userHead;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.popup_window_login);
//        ButterKnife.bind(this);
//        init();
//
//    }
//
//    @OnClick(R.id.civ_login_qq)
//    public void qqlogin() {
//
//        mTencent.login(this, "all", loginListener);
//        //finish();
//    }
//
//
//    private void init() {
//        mTencent = Tencent.createInstance(Constans.APP_ID, this);
//        loginListener = new IUiListener() {
//            @Override
//            public void onComplete(Object o) {
//                //登录成功后调用的方法
//                JSONObject jo = (JSONObject) o;
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                Log.e("COMPLETE:", jo.toString());
//                //{"ret":0,"openid":"315D97308622FA6E38B58741779C1311","access_token":"786FD7DA7A688C591891F31F5480EDF7","pay_token":"112E5FE22B2CCF65AD740AA487B0CB87","expires_in":7776000,"pf":"desktop_m_qq-10000144-android-2002-","pfkey":"5f9dbb9fd0e32ffc33c46a7527fa3740","msg":"","login_cost":404,"query_authority_cost":192,"authority_cost":0}
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//
//                String openID;
//                try {
//                    openID = jo.getString("openid");
//                    String accessToken = jo.getString("access_token");
//                    String expires = jo.getString("expires_in");
//                    mTencent.setOpenId(openID);
//                    mTencent.setAccessToken(accessToken, expires);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//                //登录失败后调用的方法
//            }
//
//            @Override
//            public void onCancel() {
//                //取消登录后调用的方法
//            }
//        };
//
//        userInfoiuiListener = new IUiListener() {
//            @Override
//            public void onComplete(Object o) {
//                JSONObject jo = (JSONObject) o;
//                //{"ret":0,
//                // "msg":"",
//                // "is_lost":0,
//                // "nickname":"冬雷震震",
//                // "gender":"男",
//                // "province":"山东",
//                // "city":"菏泽",
//                // "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/222222\/315D97308622FA6E38B58741779C1311\/30",
//                // "figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/222222\/315D97308622FA6E38B58741779C1311\/50",
//                // "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/222222\/315D97308622FA6E38B58741779C1311\/100",
//                // "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/222222\/315D97308622FA6E38B58741779C1311\/40",
//                // "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/222222\/315D97308622FA6E38B58741779C1311\/100",
//                // "is_yellow_vip":"0",
//                // "vip":"0",
//                // "yellow_vip_level":"0",
//                // "level":"0",
//                // "is_yellow_year_vip":"0"}
//
//                Log.e("COMPLETE:", jo.toString());
//
//                try {
//                    userName = jo.getString("nickname");
//                    userHead = jo.getString("figureurl_qq_2");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        };
//    }
//
//    private void getUserInfo() {
//        UserInfo info = new UserInfo(this, mTencent.getQQToken());
//        info.getUserInfo(userInfoiuiListener);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
//        getUserInfo();
//        //finish();
//    }
//
//    @OnClick(R.id.iv_login_back)
//    public void back() {
//        finish();
//    }
//
//    @Override
//    public void finish() {
//        Intent intent = new Intent();
//        intent.putExtra("name", userName);
//        intent.putExtra("headUrl", userHead);
//        Log.e("HEADURL", userHead);
//        setResult(3,intent);
//        super.finish();
//        overridePendingTransition(R.anim.left_in, R.anim.left_out);
//    }
//}
