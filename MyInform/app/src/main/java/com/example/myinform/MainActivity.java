package com.example.myinform;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        TextView name = new TextView(this);
        name.setText("최 찬 종");
        name.setTextColor(Color.RED);
        name.setTextSize(25);
        container.addView(name);

        TextView stuID = new TextView(this);
        stuID.setText("2015305505");
        stuID.setTextColor(Color.BLUE);
        stuID.setTextSize(16);
        container.addView(stuID);

        TextView num = new TextView(this);
        num.setText("010-1234-5678");
        num.setTextColor(Color.GREEN);
        num.setTextSize(16);
        container.addView(num);

        setContentView(container);
    }
}
