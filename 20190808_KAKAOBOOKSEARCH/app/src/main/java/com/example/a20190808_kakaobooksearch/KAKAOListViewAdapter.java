package com.example.a20190808_kakaobooksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class KAKAOListViewAdapter extends BaseAdapter{

    private List<KAKAOBookVO> list = new ArrayList<KAKAOBookVO>();

    public void addList(KAKAOBookVO vo) {list.add(vo); }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        // 출력할 View 객체를 생성
        if(view == null) {
            LayoutInflater inflater =
                    (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            // 생성된 View객체에 XML Layout을 설정.
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }
        // 출력할 View Component의 reference를 획득.
        ImageView iv = (ImageView)view.findViewById(R.id.customIv);
        TextView tv1 = (TextView)view.findViewById(R.id.customTv1);
        TextView tv2 = (TextView)view.findViewById(R.id.customTv2);

        KAKAOBookVO vo = list.get(i);  // 화면에 출력한 데이터를 가지고 온다.


        iv.setImageDrawable(vo.getDrawable());
        tv1.setText(vo.getTitle());
        tv2.setText(vo.getPrice());

        return view;
    }
}
