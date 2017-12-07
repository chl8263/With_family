package com.example.user.with_family.ui.f_chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.with_family.Interfaces.ChatRoom_Refresh_callback;
import com.example.user.with_family.R;
import com.example.user.with_family.util.Contact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by User on 2017-11-11.
 */

public class ChatRoom_Fragment extends Fragment {
    private RecyclerView chatRoom_recyclerView;
    private ChatRoom_adapter chatRoom_adapter;
    private ArrayList<ChatRoom_item> items;
    private TextView chatroom_isnull;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("룸");
    private DatabaseReference room_users = myMainRoom.child("user_tree");
    private DatabaseReference chat_room = myMainRoom.child("chat_room");


    public ChatRoom_Fragment(){

    }
    public static ChatRoom_Fragment newInstance() {
        Bundle args = new Bundle();
        ChatRoom_Fragment fragment = new ChatRoom_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        chatroom_isnull = (TextView)view.findViewById(R.id.chatroom_isnull);
        chatRoom_recyclerView = (RecyclerView)view.findViewById(R.id.chatroom_recycleview);
        items = new ArrayList<ChatRoom_item>();

        if(items.size()==0){
            chatroom_isnull.setVisibility(view.VISIBLE);
        }else {
            chatroom_isnull.setVisibility(view.INVISIBLE);
        }
        Init();
        makeChatRoom();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    private void Init(){
        chatRoom_adapter = new ChatRoom_adapter(getContext(),items,callback);
        chatRoom_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        chatRoom_recyclerView.setAdapter(chatRoom_adapter);
    }

    private void makeChatRoom(){

        room_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> a = new ArrayList<String>();
                ArrayList<String> b = new ArrayList<String>();
                String all="";
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String name = (String) snapshot.child("name").getValue();
                    Log.e("sdsdsd",name);
                    a.add(name);
                }
                for(int i=0;i<a.size()-1;i++){
                    b.add(a.get(0)+","+a.get(i+1));
                }
                b.add(Contact.MyMainRoom);
                for(int i=0;i<b.size()-1;i++){
                    items.add(new ChatRoom_item(b.get(i),"aa","aa"));
                }

                chatRoom_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private ChatRoom_Refresh_callback callback = new ChatRoom_Refresh_callback() {
        @Override
        public void refresh(ChatRoom_item o, int position) {
            items.get(position).setContent(o.getContent());
            items.get(position).setTime(o.getTime());
            chatRoom_adapter.notifyDataSetChanged();
        }

    };
}






















