package com.example.a20190729;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
class MyCounter1 implements Runnable {

    private Handler handler;

    MyCounter1(Handler handler) {
        this.handler = handler;
    }
    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            try {
                Thread.sleep(1000);
                // UI Thread에 message를 이용해서 message를 전달
                Bundle bundle = new Bundle();
                bundle.putString("COUNT", i + "");
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            } catch (Exception e) {
            }
        }
    }
}
public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        final TextView tv1 = (TextView)findViewById(R.id.counterTv1);
        Button startBtn = (Button)findViewById(R.id.startBtn1);

        final Handler handler = new Handler() {
            // message를 받는 순간 아래 method가 호출된다.
            @Override
            public void handleMessage(@NonNull  Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                tv1.setText(b.getString("COUNT"));
            }
        };

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCounter1 counter = new MyCounter1(handler);
                Thread t= new Thread(counter);
                t.start();
            }
        });
    }
}
