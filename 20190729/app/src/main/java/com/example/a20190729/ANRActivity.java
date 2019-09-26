package com.example.a20190729;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

class MySum implements Runnable {
    private TextView tv;

    MySum(TextView tv) {
        this.tv = tv;
    }
    @Override
    public void run() {
        // Thread가 실해이 되면 수행되는 코드를 여기에 작성
        long sum = 0;
        for(long i=0; i<100000000L; i++) {
            sum += i;
        }
        tv.setText("총합은 : " + sum);
    }
}

public class ANRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        final TextView tv = (TextView)findViewById(R.id.countTv);
        Button countBtn = (Button)findViewById(R.id.countBtn);
        Button toastBtn = (Button)findViewById(R.id.toastBtn);

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Thread를 파생시켜야 한다.
                MySum mySum = new MySum(tv); // Runnale interface를 구현한 객체

                Thread t = new Thread(mySum); // Thread를 생성

                t.start(); // non-blocking method
                           // 새로운 실행흐름을 만들어 낼 수 있다.
            }
        });

        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ANRActivity.this,
                        "Toast가 실행되요!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
