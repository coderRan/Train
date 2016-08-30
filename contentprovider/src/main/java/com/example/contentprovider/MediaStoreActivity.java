package com.example.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaStoreActivity extends AppCompatActivity {

    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_store);
        ButterKnife.bind(this);
    }

    public void read(View view) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        ContentResolver resolver = getContentResolver();

        Cursor cursor = resolver.query(uri, new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA}
                ,MediaStore.Audio.Media.DURATION+">60000",null, null);
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()){
            sb.append(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            sb.append("\n");
            sb.append(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            sb.append("\n");
        }
        cursor.close();
        tvShow.setText(sb.toString());
    }
}
