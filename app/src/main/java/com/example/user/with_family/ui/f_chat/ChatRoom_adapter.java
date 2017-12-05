package com.example.user.with_family.ui.f_chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.with_family.Interfaces.ChatRoom_Refresh_callback;
import com.example.user.with_family.R;
import com.example.user.with_family.ui.ChatActivity.ChatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-02.
 */

public class ChatRoom_adapter extends RecyclerView.Adapter<ChatRoom_adapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatRoom_item> items;
    private ChatRoom_Refresh_callback callback;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("룸");
    private DatabaseReference chat_room = myMainRoom.child("chat_room");
    public ChatRoom_adapter(Context context, ArrayList<ChatRoom_item> items,ChatRoom_Refresh_callback callback) {
        this.context = context;
        this.items = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.char_room_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.chatroom_name.setText(items.get(position).getName());
        holder.chatroom_content.setText(items.get(position).getContent());
        holder.chatroom_time.setText(items.get(position).getTime());

        chat_room.child(items.get(position).getName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatRoom_item chatRoom_item = dataSnapshot.getValue(ChatRoom_item.class);
                callback.refresh(chatRoom_item,position);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                        intent.putExtra("room_name",items.get(position).getName());
                        view.getContext().startActivity(intent);
                        break;
                    }
            }
        }
    }
}