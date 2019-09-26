package com.example.a20190729;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);

        Button implicitBtn = (Button)findViewById(R.id.implicitBtn);

        implicitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 새로운 Activity를 호출!!!
                // Implicit Intent를 이용해서 Activity를 호출
                Intent i = new Intent();
                i.setAction("MY_ACTION");
                i.addCategory("MY_CATEGORY");
                i.putExtra("DATA", "소리없는 아우성!!");
                startActivity(i);
            }
        });

        Button dialBtn = (Button)findViewById(R.id.dialBtn);

        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전화걸기 Activity를 호출!!
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:01077266542"));
                startActivity(i);
            }
        });

        Button browserBtn = (Button)findViewById(R.id.browserBtn);

        browserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // browser를 이용해서 특정 URL로 접속!!
                Uri uri = Uri.parse("http://www.naver.com");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        Button mapBtn = (Button)findViewById(R.id.mapBtn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 지도를 표현할 activity를 실행
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.KAKAOMAPActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        Button callBtn = (Button)findViewById(R.id.callBtn);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 사용자의 안드로이드 버전이 6버전 보다 작은가?
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 추가적인 보안해제가 필요
                    // 현재 앱에 대해 사용자 권한 중 전화걸기 기능이 설정되어 있는가?
                    int result = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if( result == PackageManager.PERMISSION_DENIED) {
                        // 전화걸기 기능에 대한 보안이 설정 안되있다.
                        // 한번이라도 전화걸기에 대한 권한 설정을 거부한적이 있는가?
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            //거부한적이 있다! 다시 dialog를 띄워서 물어봐야 한다,
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(IntentTestActivity.this);
                            builder.setTitle("권한필요!");
                            builder.setMessage("전화걸기 기능이 필요해요. 수락할까요?");
                            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                                10000);
                                    }
                                }
                            });
                            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                    10000);
                        }
                    } else {
                        // 전화걸기 기능에 대한 보안이 설정되어 있다.
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:01077266542"));
                        startActivity(i);
                    }
                } else {
                    // 이전 안드로이드 버전이기 때문에 간단한 설정으로 바로 실행
                    // 직접 calling하는 activity 호출
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:01077266542"));
                    startActivity(i);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requsetCode,@NonNull String[] permission, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requsetCode, permission, grantResult);
        if(requsetCode == 1000) {
            if(grantResult[0] == PackageManager.PERMISSION_DENIED) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:01077266542"));
                startActivity(i);
            }
        }
    }
}
