package com.example.a20190729;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MessageExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_example);

        final TextView tv = (TextView)findViewById(R.id.tv);
        final TextView uId = (TextView)findViewById(R.id.userId);
        final EditText et = (EditText)findViewById(R.id.editText);
        Button sendBtn = (Button)findViewById(R.id.sendBtn);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.append(uId.getText() + ">>" + et.getText() + "\n");
                // 스크롤을 해야하는지 판단해서
                tv.scrollTo(0,100);
            }
        });
    }
}
