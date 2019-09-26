package com.example.buttonlayout_java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams parambtn = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        parambtn.setMargins(0, 0, 0, 0);

        LinearLayout.LayoutParams paramL = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        paramL.setMargins(0, 100, 90, -45);

        LinearLayout.LayoutParams paramR = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        paramR.setMargins(90, -50, 0, 100);

        Button Btn_1 = new Button(this);
        Btn_1.setText("버튼1");
        container.addView(Btn_1, parambtn);

        Button Btn_2 = new Button(this);
        Btn_2.setText("버튼2");
        container.addView(Btn_2, parambtn);



        Button Btn_3 = new Button(this);
        Btn_3.setText("버튼3");
        container.addView(Btn_3, paramL);

        Button Btn_4 = new Button(this);
        Btn_4.setText("버튼4");
        container.addView(Btn_4, paramR);


        Button Btn_5 = new Button(this);
        Btn_5.setText("버튼5");
        container.addView(Btn_5, parambtn);

        Button Btn_6 = new Button(this);
        Btn_6.setText("버튼6");
        container.addView(Btn_6, parambtn);

        setContentView(container);
    }
}
