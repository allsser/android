package com.example.a20190729;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class DataFromActivity extends AppCompatActivity {

    private String selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_from);

        final ArrayList<String> list = new ArrayList<String>();
        list.add("수박");
        list.add("바나나");
        list.add("딸기");
        list.add("멜론");

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        // adapter가 필요
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, list);

        spinner.setAdapter(adapter);
        // spinner에서 item을 선택하는 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String)list.get(position);
                Log.i("selectedTest","선택된 과일 : " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button sendDataBtn = (Button)findViewById(R.id.sendMsgBtn);
        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭되면 현재 선택한 과일이름을 이전 Activity로 전달하고
                // 현재 Activity는 종료
                Intent resultIntent = new Intent();
                resultIntent.putExtra("DATA",selectedItem);
                setResult(5000, resultIntent);
                // 만약 finish();가 오류가 나면
                DataFromActivity.this.finish();
            }
        });
    }
}
