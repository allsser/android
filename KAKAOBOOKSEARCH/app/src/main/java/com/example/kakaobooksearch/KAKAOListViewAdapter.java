package com.example.kakaobooksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class KAKAOListViewAdapter extends BaseAdapter {

    private ArrayList<KAKAOBookVO> list = new ArrayList<KAKAOBookVO>();
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

        if(view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlistview_item,viewGroup,false);
        }

        ImageView bookimg = view.findViewById(R.id.bookImg);
        TextView author = view.findViewById(R.id.authorTextView);
        TextView title = view.findViewById(R.id.titleTextView);
        TextView price =  view.findViewById(R.id.priceTextView);

        KAKAOBookVO vo = list.get(i);

        try {
            Bitmap bmp = BitmapFactory.decodeByteArray(vo.getThumbnailImg(),
                    0,vo.getThumbnailImg().length);
            bookimg.setImageBitmap(bmp);

            StringBuilder sb = new StringBuilder();
            for(String s : vo.getAuthors()) {
                sb.append(s);
                sb.append(",");
            }
            author.setText(sb.toString());
            title.setText(vo.getTitle());
            price.setText(vo.getPrice());

        } catch(Exception e) {
            Log.i("KAKAOBOOKLog",e.toString());
        }
        return view;
    }

    public void addList(KAKAOBookVO vo) {
        list.add(vo);
    }
}
