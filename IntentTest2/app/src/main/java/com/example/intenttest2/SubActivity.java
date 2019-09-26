package com.example.intenttest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity);

        Intent inIntent = getIntent();
        String calc = (inIntent.getStringExtra("Calc"));
        int calValue = 0;

        if(calc.equals("+")) {
            calValue = inIntent.getIntExtra("Num1", 0)
                    + inIntent.getIntExtra("Num2", 0);
        } else if (calc.equals("-")){
            calValue = inIntent.getIntExtra("Num1", 0)
                    - inIntent.getIntExtra("Num2", 0);
        } else if (calc.equals("*")){
            calValue = inIntent.getIntExtra("Num1", 0)
                    * inIntent.getIntExtra("Num2", 0);
        } else {
            calValue = inIntent.getIntExtra("Num1",0)
                    / inIntent.getIntExtra("Num2",0);
        }

        final int retValue = calValue;

        Button rebutton = (Button) findViewById(R.id.rebutton);
        rebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent(getApplicationContext(),
                        MainActivity.class);
                outIntent.putExtra("Hap", retValue);
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

    }
}
