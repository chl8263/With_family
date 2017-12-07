package com.example.user.with_family.ui.ChatActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.with_family.R;
import com.example.user.with_family.util.Contact;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-05.
 */

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Chat_item> items;
    public final int VIEW_TYPE_YOU = 1;
    public final int VIEW_TYPE_ME = 0;

    public Chat_Adapter(Context context, ArrayList<Chat_item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_me, parent, false);
            return new ViewHolder_ME(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_other, parent, false);
            return new ViewHolder_OTHER(view);
        }    }
    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getName().equals(Contact.MyName)) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_YOU;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder_ME){
            ((ViewHolder_ME) holder).content.setText(items.get(position).getContent());
        }else {
            ((ViewHolder_OTHER)holder).content.setText(items.get(position).getContent());
            ((ViewHolder_OTHER)holder).otherName.setText(items.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder_ME extends RecyclerView.ViewHolder{
        private TextView content;
        public ViewHolder_ME(View itemView) {
            super(itemView);
            content=(TextView)itemView.findViewById(R.id.content);
        }
    }
    public class ViewHolder_OTHER extends RecyclerView.ViewHolder {
        public TextView content;
        public TextView otherName;
        public ViewHolder_OTHER(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            otherName = (TextView)itemView.findViewById(R.id.otherName);
        }
    }}
