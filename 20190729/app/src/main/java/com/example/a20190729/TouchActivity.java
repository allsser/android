package com.example.a20190729;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class TouchActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, R.string.toastMsg, Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

}
