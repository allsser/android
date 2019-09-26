package com.example.choicetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    RadioGroup group;
    Button button;
    RadioButton button1, button2, button3;
    CheckBox check;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ILoveAnimal(최찬종)");

        group = (RadioGroup) findViewById(R.id.radioGroup);
        button1 = (RadioButton) findViewById(R.id.radioDolphin);
        button2 = (RadioButton) findViewById(R.id.radioGiraffe);
        button3 = (RadioButton) findViewById(R.id.radioTiger);
        button = (Button) findViewById(R.id.button);
        image = (ImageView) findViewById(R.id.imageButton);
        check = (CheckBox)findViewById(R.id.start);
        text = (TextView)findViewById(R.id.textView2);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(check.isChecked() == false){
                    text.setVisibility(View.INVISIBLE);
                    group.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    image.setVisibility(View.INVISIBLE);
                } else {
                    text.setVisibility(View.VISIBLE);
                    group.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    image.setVisibility(View.VISIBLE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radioDolphin:
                        image.setImageResource(R.drawable.dolphin);
                        break;

                    case R.id.radioGiraffe:
                        image.setImageResource(R.drawable.giraffe);
                        break;

                    case R.id.radioTiger:
                        image.setImageResource(R.drawable.tiger);
                        break;
                }
            }
        });
    }
}
