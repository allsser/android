package com.example.a20190808_kakaobooksearch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class KAKAOBookSearchService extends Service {

    // inner class 형태로 Thread를 생성하기 위한 Runnable interface를
    // 구현한 class를 정의.
    private class BookSearchRunnable implements Runnable {
        private String keyword;

        BookSearchRunnable(String keyword) {
            this.keyword = keyword;
        }
        @Override
        public void run() {
            // 전달된 keyword를 이용해서 network 처리(API 처리)
            String url = "https://dapi.kakao.com/v3/search/book?target=title";
            url = url + "&query=" + keyword;
            String myKey = "ad99328dab1f2b5ca4cf62241960d4bf";
            try {
                URL urlOb = new URL(url);
                HttpURLConnection con = (HttpURLConnection)urlOb.openConnection();
                // request 방식을 지정
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", "KakaoAK " + myKey);
                // 정상적으로 설정을 하면 API 호출이 성공하면 결과를 받아 올 수 있다.
                // 연결통로(stream)를 통해서 결과를 문자열로 얻어낸다.
                // 기본적인 Stream을 BufferedReader형태로 생성.
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                );
                String line = null;
                StringBuffer sb = new StringBuffer();
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // 데이터를 다 읽어들였으니 통로(Stream)을 닫는다.
                br.close();
                Log.i("KAKAOBOOKLog", sb.toString());
                // 데이터가 JSON형태로 정상적으로 출력되면 외부 API 호출 성공
                // Jackson library를 이용해서 JSON데이터를 처리
                // 현재 { documents : [] } 상태, 내가 필요한 형태는 []
                ObjectMapper mapper = new ObjectMapper();
                // Map에서 뽑아낼수 있게 만든 형태
                Map<String, Object> map =
                        mapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {});

                // 책에 있는 배열을 키값을 사용해 뽑아낼 수 있다.
                Object obj = map.get("documents");

                // 해당 Object를 다시 JSON형태로 바꾼다.
                String resultJsonData = mapper.writeValueAsString(obj);
                Log.i("KAKAOLog",resultJsonData);
                // 결과적으로 우리가 얻은 데이터의 형태는
                // [{책 1권의 데이터}, {책 1권의 데이터}, {책 1권의 데이터}, ....]
                // 책 1권의 데이터를 객체화 => KAKAOBookVO class를 이용
                // 책 여러권의 데이터는 ArrayList로 표현
                // 책 1권의 데이터는 key와 value으 쌍으로 표현되고 있다.
                ArrayList<KAKAOBookVO> myObject =
                        mapper.readValue(resultJsonData, new TypeReference<ArrayList<KAKAOBookVO>>(){});

                // JSON -> 객체화
                // 정상적으로 객체화 되었는지 확인
                for(KAKAOBookVO book : myObject) {
                    Log.i("KAKAOLog", book.getTitle());
                }
                // 이미지데이터를 처리하기 위한 추가 코드
                // 책 표지 데이터는 문자열 URL로 되어 있는데 해당 URL에 접속해서
                // byte[] 형태의 데이터로 추출해서,
                // byte[]을 저장


                // 정상적으로 객체화 되었으면 intent에 해당 데이터를 붙여서
                // Activity에게 전달해야 한다.
                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                // 만약 Activity가 메모리에 존재하면 새로 생성하지 않고 기존 Activity를 이용.
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // 전달할 데이터를 intent에 붙인다.
                // parcelable interface를 구현한 객체를 붙이기 위하서
                // method를 다른 method로 교체
                i.putParcelableArrayListExtra("resultData",myObject);

                // Activity에게 데이터를 전달.
                startActivity(i);

            } catch (Exception e) {
                Log.i("KAKAOLog", e.toString());
            }
        }
    }

    public KAKAOBookSearchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // 서비스 객체가 만들어지는 시점에 1회 호출
        // 사용할 resource를 준비하는 과정.
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // onCreat()후에 자동적을 호출되며
        // startService()에 의해서 호출된다.
        // 실제 로직처리는 onStartCommand에서 진행
        Log.i("KAKAOBOOKLog","onStartCommand 호출!!");
        // 전달된 키워드를 이용해서 외부 네트워크 접속을 위한
        // Thread를 하나 생성해야 한다.
        String keyword = intent.getExtras().getString("searchKeyword");
        // Thread를 만들기 위한 Runnable 객체부터 생성
        BookSearchRunnable runnable = new BookSearchRunnable(keyword);
        Thread t = new Thread(runnable);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 서비스 객체가 메모리상에서 삭제될 때 1번 호출
        // 사용한 resource를 정리하는 과정.
        super.onDestroy();
    }
}
