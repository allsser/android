package com.example.a20190729;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.CustomViewHolder> {

    private ArrayList<TitleData> mList = null;
    private Activity context = null;


    public TitleAdapter(Activity context, ArrayList<TitleData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;


        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textView_list_title);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list2, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.title.setText(mList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}