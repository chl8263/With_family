package com.example.user.with_family.ui.f_chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.with_family.R;
import com.example.user.with_family.ui.ChatActivity.ChatActivity;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-02.
 */

public class ChatRoom_adapter extends RecyclerView.Adapter<ChatRoom_adapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatRoom_item> items;

    public ChatRoom_adapter(Context context, ArrayList<ChatRoom_item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.char_room_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chatroom_name.setText(items.get(position).getName());
        holder.chatroom_content.setText(items.get(position).getContent());
        holder.chatroom_time.setText(items.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView chatroom_name;
        public TextView chatroom_content;
        public TextView chatroom_time;
        public LinearLayout chatroom_click;

        public ViewHolder(View itemView) {
            super(itemView);
            chatroom_name = (TextView) itemView.findViewById(R.id.chat_room_name);
            chatroom_content = (TextView) itemView.findViewById(R.id.chat_room_content);
            chatroom_time = (TextView) itemView.findViewById(R.id.chat_room_time);
            chatroom_click = (LinearLayout) itemView.findViewById(R.id.chatroom_click);
            chatroom_click.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.chatroom_click:
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        int position = getAdapterPosition();
                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                        view.getContext().startActivity(intent);
                        break;
                    }
            }
        }
    }
}
