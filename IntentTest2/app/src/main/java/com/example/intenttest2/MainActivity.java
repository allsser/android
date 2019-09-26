package com.example.intenttest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText text1 = (EditText)findViewById(R.id.text1);
        final EditText text2 = (EditText)findViewById(R.id.text2);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.Radio);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SubActivity.class);

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.add:
                        intent.putExtra("Calc", "+");
                        break;
                    case R.id.sub:
                        intent.putExtra("Calc", "-");
                        break;
                    case R.id.Mul:
                        intent.putExtra("Calc", "*");
                        break;
                    case R.id.div:
                        intent.putExtra("Calc", "/");
                        break;
                        default:
                            break;
                }
                intent.putExtra("Num1",
                        Integer.parseInt(text1.getText().toString()));
                intent.putExtra("Num2",
                        Integer.parseInt(text2.getText().toString()));
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {
            int hap = data.getIntExtra("Hap", 0);
            Toast.makeText(getApplicationContext(), "결과:" + hap,
                    Toast.LENGTH_LONG).show();
        }
    }
}
