package com.example.xmlpullparser;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    final int STEP_NONE = 0 ;
    final int STEP_NO = 1 ;
    final int STEP_NAME = 2 ;
    final int STEP_SALARY = 3 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager am = getResources().getAssets() ;
        InputStream is = null ;


        try {
            int no = 0 ;
            String name = null ;
            String salary = null ;

            is = am.open("employee.xml") ;

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser() ;

            parser.setInput(is, "UTF-8") ;

            int eventType = parser.getEventType() ;
            int step = STEP_NONE ;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String startTag = parser.getName() ;
                    if (startTag.equals("id")) {
                        step = STEP_NO ;
                    } else if (startTag.equals("name")) {
                        step = STEP_NAME ;
                    } else if (startTag.equals("salary")) {
                        step = STEP_SALARY ;
                    } else {
                        step = STEP_NONE ;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = parser.getName() ;
                    if ((endTag.equals("id") && step != STEP_NO) ||
                            (endTag.equals("name") && step != STEP_NAME) ||
                            (endTag.equals("salary") && step != STEP_SALARY))
                    {

                    }
                    step = STEP_NONE ;
                } else if (eventType == XmlPullParser.TEXT) {
                    String text = parser.getText() ;
                    if (step == STEP_NO) {
                        try {
                            no = Integer.parseInt(text);
                        } catch (Exception e) {
                            no = 0 ;
                        }
                    } else if (step == STEP_NAME) {
                        name = text ;
                    } else if (step == STEP_SALARY) {
                        salary = text ;
                    }
                }

                eventType = parser.next();
            }

            if (no == 0 || name == null || salary == null) {
                // ERROR : XML is invalid.
            } else {

                EditText editTextNo = (EditText) findViewById(R.id.no1);
                editTextNo.setText(Integer.toString(no));

                EditText editTextName = (EditText) findViewById(R.id.name1);
                editTextName.setText(name);

                EditText editTextPhone = (EditText) findViewById(R.id.salary1);
                editTextPhone.setText(salary);

                EditText editTextNo2 = (EditText) findViewById(R.id.no2);
                editTextNo2.setText(Integer.toString(no));

                EditText editTextName2 = (EditText) findViewById(R.id.name2);
                editTextName2.setText(name);

                EditText editTextPhone3= (EditText) findViewById(R.id.salary2);
                editTextPhone3.setText(salary);
            }
        } catch (Exception e) {
            e.printStackTrace() ;
        }
    }
}

