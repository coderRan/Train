package com.example.contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvShow;
    public static final int REQUEST_READ_CONTACTS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShow = (TextView) findViewById(R.id.tv_show);
    }
    public void read(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS);
            if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_READ_CONTACTS);
            }
        }
    }
    public void readContract() {
        //ContentProvider Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //内容访问者
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, null);
        StringBuilder sb = new StringBuilder();
        if (cursor != null) {
            while (cursor.moveToNext()) {
               String  cid = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
               String  name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Cursor phones = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{cid}, null);
                while (phones.moveToNext()){
                    String phone = phones.getString(
                            phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    sb.append(name + ":" + phone+"\n");
                }
                phones.close();
            }
            cursor.close();
        }
        tvShow.setText(sb.toString());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContract();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
