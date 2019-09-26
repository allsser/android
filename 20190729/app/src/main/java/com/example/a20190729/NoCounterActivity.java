package com.example.a20190729;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
class MyCounter implements Runnable {
    private TextView tv;

    MyCounter(TextView tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            try {
                Thread.sleep(1000);
                tv.setText("count : " + i);
            } catch (Exception e) {

            }
        }
    }
}
public class NoCounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_counter);

        final TextView tv = (TextView)findViewById(R.id.counterTv);
        Button startBtn = (Button)findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thread를 하나 생성해서 1초마다 TextView에 카운트를 출력
//                Thread t = new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        // 1초마다 카운트를 증가시켜서 TextView에 출력
////                        for(int i=0; i<10; i++) {
////                            try {
////                                Thread.sleep(1000);
////                                tv.setText("count : " + i);
////                            } catch (Exception e) {
////
////                            }
////                        }
////                    }
////                });
////                t.start();

                // 외부에서 Thread를 만들었다 보통 팅김
                MyCounter counter = new MyCounter(tv);
                Thread t= new Thread(counter);
                t.start();
            }
        });
    }
}
