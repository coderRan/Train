package com.zdr.notification;

import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.graphics.BitmapFactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;


public class MainActivity extends AppCompatActivity {
    NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void send(View view) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle("这是标题");
        builder.setContentText("这是内容");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentInfo("内容信息");
        builder.setOngoing(true);
        //builder.setAutoCancel(true);
        //设置震动
        builder.setVibrate(new long[]{200, 100, 200, 100, 300,300,200,300});
        //设置声音
        //builder.setSound();
        //设置意图
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        nm.notify(1,builder.build());
    }

    public void send2(View view) {
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("正在下载。。。");
        builder.setContentText("App名字");

        builder.setOngoing(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    builder.setProgress(100, i, false);
                    nm.notify(2, builder.build());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        builder.setContentTitle("下载完成");
        builder.setProgress(0, 0, true);
        nm.notify(2, builder.build());
    }

    public void send3(View view) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //展开后的图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setTicker("这是bigPicture");
        builder.setContentTitle("Title");
        builder.setContentText("bigPicture");

        BigPictureStyle bigPictureStyle = new BigPictureStyle();
        bigPictureStyle.bigPicture(
                BitmapFactory.decodeResource(getResources(), R.mipmap.image)
        );
        builder.setStyle(bigPictureStyle);
        nm.notify(3, builder.build());
    }

    public void send4(View view) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentTitle("自定义");
        //定义布局
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notifiction_layout);
        remoteViews.setTextViewText(R.id.tv_title,"这是自定义的title");
        remoteViews.setImageViewResource(R.id.iv_img, R.mipmap.ic_launcher);
        //设置button监听事件
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                new Intent(this, this.getClass()), PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn,pendingIntent);
        builder.setContent(remoteViews);
        nm.notify(4, builder.build());
    }
}
