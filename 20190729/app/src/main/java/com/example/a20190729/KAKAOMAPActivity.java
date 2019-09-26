package com.example.a20190729;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class KAKAOMAPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaomap);

        MapView map = new MapView(this);
        ViewGroup group = (ViewGroup)findViewById(R.id.map);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.501289,127.039574);
        map.setMapCenterPoint(mapPoint,true);
        group.addView(map);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("멀티캠퍼스");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        map.addPOIItem(marker);
    }
}
