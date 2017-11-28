package com.example.user.with_family.ui.drawlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.with_family.R;

import java.util.ArrayList;

/**
 * Created by choi on 2017-11-25.
 */

public class DrawAdapter extends RecyclerView.Adapter<DrawAdapter.Viewholder> {
    private Context context;
    private ArrayList<DrawItem> items;
    public DrawAdapter(Context context , ArrayList<DrawItem> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.drawitem,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.textView.setText(items.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public TextView textView;
        public Viewholder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.dday_content);
        }

    }
}
