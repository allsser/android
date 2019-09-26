package com.example.a20190729;

import android.app.DatePickerDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

class SearchMoiveTitleRunnabl implements Runnable {
    private String date;
    private Handler handler;

    SearchMoiveTitleRunnabl(Handler handler, String date) {
        this.handler = handler;
        this.date = date;
    }

    @Override
    public void run() {
        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f7f8b222c3c9cb2f69c19a771cfbcc55&targetDt=" + date;

        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();

            BufferedReader br =
                    new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input="";
            StringBuffer sb = new StringBuffer();
            while ((input = br.readLine()) != null) {
                sb.append(input);
            }
            Log.i("TEST3:",sb.toString());
            ObjectMapper mapper = new ObjectMapper();
            Log.i("TEST4:",mapper.toString());
            // Jackson library를 이용해여 JSON문자열을 Stirng[] 형태로 변환

            String[] resultArr = mapper.readValue(sb.toString(),String[].class);
            Log.i("TEST5:",resultArr.toString());



            Bundle bundle = new Bundle();
            bundle.putStringArray("MOVIEARRAY",resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

        } catch (Exception e) {
            Log.i("DATAError", e.toString());
        }
    }
}

public class MovieActivity extends AppCompatActivity {

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private ListView ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        this.InitializeView();
        this.InitializeListener();

        final ListView lv = (ListView)findViewById(R.id.lv);

//        final Handler handler = new Handler() {
//            // handeler에게 message가 전달되면 아래의 method가 callback된다.
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Bundle bundle = msg.getData();
//                String[] result = bundle.getStringArray("MOVIEARRAY");
//
//                // adapter라는 객체는 데이터를 가져다가 view에 그리는 역할을 담당
//                ArrayAdapter adapter = new ArrayAdapter(MovieActivity.this,
//                        android.R.layout.simple_list_item_1, result);
//
//                lv.setAdapter(adapter);
//
//
//            }
//        };
    }



    private void InitializeView() {
        textView_Date = (TextView)findViewById(R.id.textView_date);
        ListView  = (ListView)findViewById(R.id.lv);
    }

    private void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String test = (year+""+"0"+(monthOfYear+1)+""+"0"+dayOfMonth);
                // 월이랑 일에 0값 추가
                Log.i("TEST : ",test.toString());
                textView_Date.setText(year + "년" + (monthOfYear+1) + "월" + dayOfMonth + "일");
                SearchMoiveTitleRunnabl runnable =
                        new SearchMoiveTitleRunnabl(handler, test.toString()) ;
                Thread t = new Thread(runnable);
                t.start();
            }

            Handler handler = new Handler() {
                // handeler에게 message가 전달되면 아래의 method가 callback된다.
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Bundle bundle = msg.getData();
                    String[] result = bundle.getStringArray("MOVIEARRAY");

                    // adapter라는 객체는 데이터를 가져다가 view에 그리는 역할을 담당
                    ArrayAdapter adapter = new ArrayAdapter(MovieActivity.this,
                            android.R.layout.simple_list_item_1, result);
                    ListView.setAdapter(adapter);
                }
            };


        };
    }

    public void OnClickHandler(View view) {
        Calendar cal = new GregorianCalendar();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod,
                mYear, mMonth, mDay);
        dialog.show();
    }
}
