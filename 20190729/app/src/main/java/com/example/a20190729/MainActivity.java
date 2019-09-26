package com.example.a20190729;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("mytest","onCreate() 호출");

        // btn1 : Event Source
        Button btn1 = (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.LinearLayoutExampleActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn2 : Message Event Source
        Button btn2 = (Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.MessageExampleActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn3 : Image Event Source
        Button btn3 = (Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.ImageActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn4 : Image Event Source
        Button btn4 = (Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.TouchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn5 : Activity간의 데이터 전달
        Button btn5 = (Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(MainActivity.this);
                // AlertDialog를 생성
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Activity에 데이터 전달");
                dialog.setMessage("전달할 내용을 입력하세요.");
                dialog.setView(et);
                dialog.setPositiveButton("Activity 호출",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 다은 Activity를 호출하는 code
                                Intent i = new Intent();
                                ComponentName cname = new ComponentName("com.example.a20190729",
                                        "com.example.a20190729.SecondActivity");
                                i.setComponent(cname);
                                // putExtra를 사용해서 데이터 사용
                                i.putExtra("sendMsg", et.getText().toString());
                                i.putExtra("anotherMsg", "다른 데이터");
                                startActivity(i);
;                            }
                        });
                dialog.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소버튼을 눌렸을때 수행할 코드 작성.
                            }
                        });
                dialog.show();
            }
        });

        // btn6 : Image Event Source
        Button btn6 = (Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.DataFromActivity");
                i.setComponent(cname);
                startActivityForResult(i, 3000);
            }
        });

        // btn7 : ANR
        Button btn7 = (Button)findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.ANRActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn8 : counter app (실행에러)
        Button btn8 = (Button)findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.NoCounterActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn9 : counter app (Handler)
        Button btn9 = (Button)findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.CounterActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn10 : MySQL에 있는 도서 DB 불러오기 (강사님)
        Button btn10 = (Button)findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.BookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn11 : MySQL에 있는 도서 DB 불러오기 (나)
        Button btn11 = (Button)findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.Mybook");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn12 : MySQL 연결 TEST
        Button btn12 = (Button)findViewById(R.id.btn12);
        btn12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.DBtest");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn13 : MySQL TEST 출력
        Button btn13 = (Button)findViewById(R.id.btn13);
        btn13.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.DBtestCheck");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn14 : 영화순위
        Button btn14 = (Button)findViewById(R.id.btn14);
        btn14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.MovieActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn15 : 도서검색(CUSTOM)
        Button btn15 = (Button)findViewById(R.id.btn15);
        btn15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.CustomBookSearchActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn16 : 묵시적 인텐트
        Button btn16 = (Button)findViewById(R.id.btn16);
        btn16.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 명시적 인텐트(Explicit Intent)
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.IntentTestActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        // btn17 : Broadcast Message
        Button btn17 = (Button)findViewById(R.id.btn17);
        btn17.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Broadcasting
                Intent i = new Intent();
                // 일반적으로 System에서 발생하는 broadcasting은 그 종류에 따라
                // 사용하는 Action이 정해져 있다.
                i.setAction("MY_BROADCAST");
                i.putExtra("broadcastMSG", "메시지가 전파된다.");
                sendBroadcast(i);
            }
        });

        // btn18 : start broadcast
        Button btn18 = (Button)findViewById(R.id.btn18);
        btn18.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("START_BROADCAST_ACTIVITY");
                startActivity(i);
            }
        });

        // btn19 : start broadcast
        Button btn19 = (Button)findViewById(R.id.btn19);
        btn19.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("START_DATABASE_ACTIVITY");
                startActivity(i);
            }
        });

        // btn20 : 주소록 가져오기
        Button btn20 = (Button)findViewById(R.id.btn20);
        btn20.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction("Conteact_ACTIVITY");
                startActivity(i);
            }
        });

        // btn21 : KAKAOMAP
        Button btn21 = (Button)findViewById(R.id.btn21);
        btn21.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.a20190729",
                        "com.example.a20190729.KAKAOMAPActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3000 && resultCode == 5000) {
            String result = data.getExtras().getString("DATA");
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
    protected void onStart(){
        super.onStart();
        Log.i("mytest","onStart() 호출");
    }
    protected void onResume(){
        super.onResume();
        Log.i("mytest","onResume() 호출");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("mytest", "onPause() 호출");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("mytest", "onStop() 호출");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("mytest", "onRestart() 호출");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("mytest", "onDestroy() 호출");
    }
}
