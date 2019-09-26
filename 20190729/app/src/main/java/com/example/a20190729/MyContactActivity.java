package com.example.a20190729;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);


        Button contactBtn = (Button)findViewById(R.id.contactBtn);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 보안관련 코드가 나온다.
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 마쉬멜로우 버전(6)버전보다 높은 경우
                    if(checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                            PackageManager.PERMISSION_GRANTED) {
                        // 이전에 허용한 적이 없는 경우
                        // 사용자의 허가를 얻어야 한다!
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                1111);
                    } else {
                        // 이전에 이미 허용버튼을 누른 경우
                        ContentResolver cr = getContentResolver();
                        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        Cursor c = cr.query(uri, null,
                                null,null,null);
                        String result = "";
                        while(c.moveToNext()) {
                            // 주소록에 있는 사용자 컬럼번호 ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                            result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            result += ", ";
                            result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            result += "\n";
                        }
                        final TextView tv = (TextView)findViewById(R.id.contactTv);
                        tv.setText(result);
                    }
                } else {
                    // 마쉬멜로우 버전(6)보다 낮은 경우
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1111) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 주소록 접근에 대한 권한요청 허용을 누르면
                // ContentResolver를 이용해서 주소록에 접근!!
                ContentResolver cr = getContentResolver();
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                Cursor c = cr.query(uri, null,
                        null,null,null);
                String result = "";
                while(c.moveToNext()) {
                    // 주소록에 있는 사용자 컬럼번호 ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    result += ", ";
                    result += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    result += "\n";
                }
                final TextView tv = (TextView)findViewById(R.id.contactTv);
                tv.setText(result);
            }
        }
    }
}
