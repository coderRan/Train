package com.zdr.findnews;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zdr.findnews.adapter.VPNewsAdapter;
import com.zdr.findnews.dbUtils.TypeDao;
import com.zdr.findnews.entity.NewsType;
import com.zdr.findnews.utils.Constans;
import com.zdr.findnews.utils.FragmentFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import circleImageViewlib.CircleImageView;

public class NewsMainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.drawer_layout_menu)
    DrawerLayout drawerMenu;
    @BindView(R.id.vp_news)
    ViewPager vpNews;
    @BindView(R.id.tl_newsType_tabs)
    TabLayout newsTypeTabs;
    @BindView(R.id.iv_add_newstype)
    ImageView ivAddNewstype;
    @BindView(R.id.tv_keep)
    TextView tvKeep;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.rl_header_top)
    RelativeLayout rlHeaderTop;
    @BindView(R.id.cir_header)
    CircleImageView cirHeader;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_settings)
    TextView tvSettings;


    private List<Fragment> fragmentList;
    private List<NewsType> newsTypeCheckedList;
    private VPNewsAdapter vpNewsAdapter;
    private TypeDao typeDao;

    private PopupWindow loginWindow;
    private IUiListener loginListener;
    private IUiListener userInfoiuiListener;
    private Tencent mTencent;
    private TextView tvCancelLogin;
    private CircleImageView cirLoginQq;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        sp = getSharedPreferences(Constans.SP_FILE, MODE_PRIVATE);
        editor = sp.edit();
        ButterKnife.bind(this);
        //设置Toolbar属性
        toolbarMain.setTitle("FindNews");
        toolbarMain.inflateMenu(R.menu.toolbar_menu);

        //绑定侧滑菜单
        ActionBarDrawerToggle barDrawerToggle =
                new ActionBarDrawerToggle(this, drawerMenu, toolbarMain,
                        R.string.app_name, R.string.app_name);
        barDrawerToggle.syncState();
        drawerMenu.addDrawerListener(barDrawerToggle);

        //设置ViewPage
        fragmentList = new ArrayList<>();
        newsTypeCheckedList = new ArrayList<>();
        typeDao = TypeDao.getTypeDao(this);

        initFragment();
        vpNewsAdapter = new VPNewsAdapter(getSupportFragmentManager(), fragmentList, newsTypeCheckedList);
        vpNews.setAdapter(vpNewsAdapter);

        //设置tablayout
        newsTypeTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        newsTypeTabs.setTabTextColors(Color.BLACK, Color.RED);
        newsTypeTabs.setupWithViewPager(vpNews);

        //修改关注的新闻类型
        ivAddNewstype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsMainActivity.this, AddNewsTypeActivity.class);

                startActivityForResult(intent, 1);

                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        File head = new File(getCacheDir(), "head.png");
        Bitmap bitmapHane = BitmapFactory.decodeFile(head.getAbsolutePath());
        cirHeader.setImageBitmap(bitmapHane);
        tvUserName.setText(sp.getString("userName",null));
    }

    public void initFragment() {
        newsTypeCheckedList.clear();
        fragmentList.clear();
        newsTypeCheckedList.addAll(typeDao.findCheckedType());
        for (int i = 0; i < newsTypeCheckedList.size(); i++) {
            Fragment recommendFragment =
                    FragmentFactory.getFragmentByType(newsTypeCheckedList.get(i).getFragmentType());

            if (recommendFragment != null) {
                Bundle bundle = new Bundle();
                bundle.putString("url", newsTypeCheckedList.get(i).getUrl());
                bundle.putString("type", newsTypeCheckedList.get(i).getTypeName());
                recommendFragment.setArguments(bundle);

                fragmentList.add(recommendFragment);
            }
        }
    }


    @OnClick(R.id.tv_keep)
    public void myKeep() {
        Intent intent = new Intent(NewsMainActivity.this, KeepActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @OnClick(R.id.tv_weather)
    public void weather() {
        Intent intent = new Intent(NewsMainActivity.this, WeatherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @OnClick(R.id.rl_header_top)
    public void login(View v) {

        //通过popupWindow弹出登录方式
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window_login, null, false);
        loginWindow = new PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        tvCancelLogin = (TextView) view.findViewById(R.id.tv_cancel_login);
        cirLoginQq = (CircleImageView) view.findViewById(R.id.civ_login_qq);
        //关闭登录窗口
        tvCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWindow.dismiss();
            }
        });
        cirLoginQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoginInfo();
                mTencent.login(NewsMainActivity.this, "all", loginListener);
                //getUserInfo();
            }
        });
        loginWindow.setFocusable(true);
        loginWindow.setOutsideTouchable(true);
        ColorDrawable colorDrawable = new ColorDrawable(0);
        loginWindow.setBackgroundDrawable(colorDrawable);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        loginWindow.setAnimationStyle(R.style.popuo_anim);
        loginWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, p.y - loginWindow.getHeight());


    }


    private void initLoginInfo() {
        mTencent = Tencent.createInstance(Constans.APP_ID, this);
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后调用的方法
                JSONObject jo = (JSONObject) o;
                Toast.makeText(NewsMainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Log.e("COMPLETE:", jo.toString());
                //{"ret":0,"openid":"315D97308622FA6E38B58741779C1311","access_token":"786FD7DA7A688C591891F31F5480EDF7","pay_token":"112E5FE22B2CCF65AD740AA487B0CB87","expires_in":7776000,"pf":"desktop_m_qq-10000144-android-2002-","pfkey":"5f9dbb9fd0e32ffc33c46a7527fa3740","msg":"","login_cost":404,"query_authority_cost":192,"authority_cost":0}

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
                //登录失败后调用的方法
                Toast.makeText(NewsMainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                //取消登录后调用的方法
            }
        };

        userInfoiuiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jo = (JSONObject) o;
                Log.e("COMPLETE:", jo.toString());
                try {
                    tvUserName.setText(jo.getString("nickname"));
                    editor.putString("userName", jo.getString("nickname"));
                    editor.commit();

                    Glide.with(NewsMainActivity.this).load(jo.getString("figureurl_qq_2"))
                            .asBitmap()
                            .placeholder(R.mipmap.pic_load_default)
                            .error(R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(new SimpleTarget<Bitmap>(){
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                    cirHeader.setImageBitmap(bitmap);
                                    File head  = new File(getCacheDir(),"head.png");

                                    //转换成In
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                    InputStream isBm = new ByteArrayInputStream(baos.toByteArray());

                                    OutputStream fos;
                                    try {
                                        fos = new FileOutputStream(head);
                                        byte[] buff = new byte[1024];
                                        int line = 0;
                                        while ((line = isBm.read(buff)) != -1) {
                                            fos.write(buff, 0, line);
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
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
    }
    private void getUserInfo() {
        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(userInfoiuiListener);
    }

    @OnClick(R.id.tv_settings)
    public void settings() {
        Intent intent = new Intent(NewsMainActivity.this, SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
                getUserInfo();
                loginWindow.dismiss();
            }
        }
        if (requestCode == 1 && resultCode == 1) {
            //修改新闻类型
            newsTypeCheckedList.clear();
            fragmentList.clear();
            initFragment();
            vpNewsAdapter.notifyDataSetChanged();
        }
    }



}
