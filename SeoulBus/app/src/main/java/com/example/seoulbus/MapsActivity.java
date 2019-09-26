package com.example.seoulbus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ///// (1) Bus Route ID /////
        //노선ID 추출을 위한 공공 DB API의 URL
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
        //노선ID 추출을 위한 공공 DB API 키
        String serviceKey = "EhMJl0%2BmI6mHGK4e1vJD4NLoAKf%2Fvt0tHpCtWrAYnOEDTFEc1M%2Fn3OIL1lSEba%2FYDK%2BGcgRwK4L499wKgMIGbw%3D%3D";
        serviceKey = URLEncoder.encode(serviceKey);
        //노선버스의 노선번호
        String strSrch = "2115";
        //공공 DB API 호출을 의한 URL
        String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&strSrch="+strSrch;

        //URL에 해당하는 문서 추출을 위한 객체 생성
        DownloadWebpageTask1 task1 = new DownloadWebpageTask1();
        //문서 추출 객체 생성
        task1.execute(strUrl);
    }

    private class DownloadWebpageTask1 extends AsyncTask<String, Void, String> {

        @Override
        //downloadUrl 메소드 호출하여 문서 추출
        protected String doInBackground(String... urls) {
            try {
                return (String)downloadUrl((String)urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        //문서 추출 결과의 출력
        protected void onPostExecute(String result) {

            String headerCd = "";
            String busRouteId = "100100598";
            String busRouteNm = "";


            boolean bSet_headerCd = false;
            boolean bSet_busRouteId = false;
            boolean bSet_busRouteNm = false;

            ///// (1) Bus Route ID /////
            try {
                //XmlPull Parser를 만들기 위한 XmlPullParserFactor 객체 생성
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                //XmlPullParserFactor에 위해 생성될 parser를 만들 때, XML name space 지원 여부를 설정함
                factory.setNamespaceAware(true);
                //Xml Pull Parser 객체 생성
                XmlPullParser xpp = factory.newPullParser();

                //데이터 리소스(result)에 대한 Input Stream 설정
                xpp.setInput(new StringReader(result));
                //parser가 현재 가리키고 있는 이벤트 타입(START_TAG, END_TAG, TEXT 등)을 반환함
                int eventType = xpp.getEventType();

                //현재 이벤트 타입이 END_DOCUMENT가 될 때까지 처리를 반복
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    }
                    //이벤트 타입이 START_TAG인 경우
                    else if(eventType == XmlPullParser.START_TAG) {
                        //태그 이름 추출
                        String tag_name = xpp.getName();
                        //태그 이름이 <headerCd>인 경우
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        if (tag_name.equals("busRouteId"))
                            bSet_busRouteId = true;
                        if (tag_name.equals("busRouteNm"))
                            bSet_busRouteNm = true;
                    }
                    //이벤트 타입이 TEXT인 경우
                    else if(eventType == XmlPullParser.TEXT) {
                        if (bSet_headerCd) {
                            //시작 태그(< >)와 마침 태그(< / >) 사이에 있는 데이터 추출
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }

                        //headerCd 태그의 값이 "0"인 경우
                        if (headerCd.equals("0")) {
                            if (bSet_busRouteId) {
                                //노선버스 ID 추출
                                busRouteId = xpp.getText();
                                bSet_busRouteId = false;
                            }
                            if (bSet_busRouteNm) {
                                busRouteNm = xpp.getText();
                                bSet_busRouteNm = false;
                            }
                        }
                    }
                    //이벤트 타입이 END_TAG인 경우
                    else if(eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    //parser를 다음 이벤트 타입으로 이동 후, 인식한 타입을 반환
                    eventType = xpp.next();
                }
            } catch (Exception e) {
            }

            ///// (2) Bus Position /////
            //버스위치 추출을 위한 공공 DB API의 URL
            String serviceUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";
            //버스위치 추출을 위한 공공 DB API 키
            String serviceKey = "EhMJl0%2BmI6mHGK4e1vJD4NLoAKf%2Fvt0tHpCtWrAYnOEDTFEc1M%2Fn3OIL1lSEba%2FYDK%2BGcgRwK4L499wKgMIGbw%3D%3D";
            //String busRouteId = "100100598"; 노선ID "2115"의 버스경로 ID (위에서 정의해 주었다.)
            //공공 DB API 호출을 위한 URL(버스경로ID 포함)
            String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&busRouteId="+busRouteId;

            ///// (3) Bus Station /////
            //버스정류소위치 추출을 위한 공공 DB API의 URL
            String StationserviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
            //버스정류소위치 추출을 위한 공공 DB API 키
            String StationserviceKey = "EhMJl0%2BmI6mHGK4e1vJD4NLoAKf%2Fvt0tHpCtWrAYnOEDTFEc1M%2Fn3OIL1lSEba%2FYDK%2BGcgRwK4L499wKgMIGbw%3D%3D";
            //String busRouteId = "100100598"; 노선ID "2115"의 버스경로 ID (위에서 정의해 주었다.)
            //공공 DB API 호출을 위한 URL(버스경로ID 포함)
            String StationstrUrl = StationserviceUrl+"?ServiceKey="+StationserviceKey+"&busRouteId="+busRouteId;

            //URL에 해당하는 문서 추출을 위한 객체 생성
            DownloadWebpageTask2 task2 = new DownloadWebpageTask2();
            task2.execute(strUrl);

            DownloadWebpageTask3 task3 = new DownloadWebpageTask3();
            task3.execute(StationstrUrl);
        }

        //URL에 해당하는 웹문서 다운로드
        private String downloadUrl(String myurl) throws IOException {

            //HTTP로 URL 접속을 의한 객체 생성
            HttpURLConnection conn = null;
            try {
                //URL 객체 생성
                URL url = new URL(myurl);
                //URL 객체를 이용한 HTTP 연결
                conn = (HttpURLConnection) url.openConnection();
                //버퍼에 문서 다운로드
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                //버퍼 내용을 UTF-8 문서 형식으로 변환
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                //버퍼 내용을 행 단위로 읽어 문자 변수에 저장
                while((line = bufreader.readLine()) != null) {
                    page += line;
                }

                //추출한 웹문서의 문서 내용을 반환
                return page;
            } finally {
                //HTTP 연결 해제
                conn.disconnect();
            }
        }
    }


    private class DownloadWebpageTask2 extends DownloadWebpageTask1 {

        protected void onPostExecute(String result) {

            String headerCd = "";
            String plainNo = "";
            String gpsX = "";
            String gpsY = "";

            boolean bSet_headerCd = false;
            boolean bSet_gpsX = false;
            boolean bSet_gpsY = false;
            boolean bSet_plainNo = false;

            ///// (2) Bus Positions
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                int count = 0;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if(eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        //시작 태그가 <gpsX>인 경우(버스위치의 위도)
                        if (tag_name.equals("gpsX"))
                            bSet_gpsX = true;
                        //시작 태그가 <gpsY>인 경우(버스위치의 경도)
                        if (tag_name.equals("gpsY"))
                            bSet_gpsY = true;
                        //시작 태그가 <plainNo>인 경우(버스번호)
                        if (tag_name.equals("plainNo"))
                            bSet_plainNo = true;
                    } else if(eventType == XmlPullParser.TEXT) {
                        if (bSet_headerCd) {
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }

                        if (headerCd.equals("0")) {
                            //시작 태그가 <gpsX>인 경우(버스위치의 위도)
                            if (bSet_gpsX) {
                                count++;

                                //<gpsX>와 </gpsX> 사이에 있는 데이터 추출
                                gpsX = xpp.getText();
                                bSet_gpsX = false;
                            }
                            //시작 태그가 <gpsY>인 경우(버스위치의 경도)
                            if (bSet_gpsY) {
                                //<gpsY>와 </gpsY> 사이에 있는 데이터 추출
                                gpsY = xpp.getText();
                                bSet_gpsY = false;
                            }
                            //시작 태그가 <plainNo>인 경우(차량번호)
                            if (bSet_plainNo) {
                                //시작 태그스 <plainNo>인 경우, 버스번호 추출
                                plainNo = xpp.getText();
                                bSet_plainNo = false;

                                displayBus(gpsX, gpsY, plainNo);
                            }
                        }
                    } else if(eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
            }
        }

        private void displayBus(String gpsX, String gpsY, String plainNo) {

            double latitude;
            double longitude;
            LatLng LOC;

            //버스위치의 위도와 경도에 대한객체 생성
            latitude = Double.parseDouble(gpsY);
            longitude = Double.parseDouble(gpsX);
            LOC = new LatLng(latitude, longitude);
            //버스위치에 대한 커스텀 아이콘(bus.png)설정
            Marker mk1 = mMap.addMarker(new MarkerOptions()
                    .position(LOC)
                    .title(plainNo)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
            //버스위치를 중심으루 지도 출력
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
            //지도의 줌 레벨(숫자가 높을 수록 크게 출력)
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        }
    }

    private class DownloadWebpageTask3 extends DownloadWebpageTask1 {

        protected void onPostExecute(String result) {

            String headerCd = "";
            String stationNm = "";
            String gpsX = "";
            String gpsY = "";

            boolean bSet_headerCd = false;
            boolean bSet_gpsX = false;
            boolean bSet_gpsY = false;
            boolean bSet_stationNm = false;

            ///// (3) Bus Station
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                int count = 0;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if (eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        //시작 태그가 <gpsX>인 경우(버스정류장의 위도)
                        if (tag_name.equals("gpsX"))
                            bSet_gpsX = true;
                        //시작 태그가 <gpsY>인 경우(버스정류장의 경도)
                        if (tag_name.equals("gpsY"))
                            bSet_gpsY = true;
                        //시작 태그가 <stationNm>인 경우(정류장 이름)
                        if (tag_name.equals("stationNm"))
                            bSet_stationNm = true;
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (bSet_headerCd) {
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }

                        if (headerCd.equals("0")) {
                            //시작 태그가 <gpsX>인 경우(버스정류장의 위도)
                            if (bSet_gpsX) {
                                count++;

                                //<gpsX>와 </gpsX> 사이에 있는 데이터 추출
                                gpsX = xpp.getText();
                                bSet_gpsX = false;
                            }
                            //시작 태그가 <gpsY>인 경우(버스정류장의 경도)
                            if (bSet_gpsY) {
                                //<gpsY>와 </gpsY> 사이에 있는 데이터 추출
                                gpsY = xpp.getText();
                                bSet_gpsY = false;
                            }
                            //시작 태그가 <stationNm>인 경우(정류소 이름)
                            if (bSet_stationNm) {
                                //시작 태그스 <station>인 경우, 버스정류소 이름 추출
                                stationNm = xpp.getText();
                                bSet_stationNm = false;

                                displayBusStatoin(gpsX, gpsY, stationNm);
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
            }
        }


        private void displayBusStatoin(String gpsX, String gpsY, String stationNm) {

            double latitude;
            double longitude;
            LatLng LOC;

            //버스정류소위치의 위도와 경도에 대한객체 생성
            latitude = Double.parseDouble(gpsY);
            longitude = Double.parseDouble(gpsX);
            LOC = new LatLng(latitude, longitude);
            //버스정류소에 대한 커스텀 아이콘(station.png)설정
            Marker mk2 = mMap.addMarker(new MarkerOptions()
                    .position(LOC)
                    .title(stationNm)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.station)));
        }
    }
}