package com.example.a20190809_myresolverapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView)findViewById(R.id.tv);
        Button btn = (Button)findViewById(R.id.selectBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getContentResolver();
                Uri uri = Uri.parse("content://com.test.data/Member");
                Log.i("Test",uri.toString());
                Cursor c = cr.query(uri, null, null,
                        null, null);

                Log.i("Test2",c.toString());
                String result = "";
                while (c.moveToNext()) {
                    result += c.getString(0);
                    result += ", ";
                    result += c.getInt(1);
                    result += "\n";
                }
                tv.setText(result);
            }
        });
    }
}
