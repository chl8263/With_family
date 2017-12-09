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
import com.example.user.with_family.ui.ChatActivity.Chat_item;
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

public class ChatRoom_Fragment extends Fragment{
    private RecyclerView chatRoom_recyclerView;
    private ChatRoom_adapter chatRoom_adapter;
    private ArrayList<ChatRoom_item> items;
    private TextView chatroom_isnull;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("룸");
    private DatabaseReference userimg_tree = firebaseDatabase.getReference("register").child("r_room").child("룸").child("userimg_tree");
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

 /*   @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        //chatroom_isnull = (TextView)view.findViewById(R.id.chatroom_isnull);
        chatRoom_recyclerView = (RecyclerView)view.findViewById(R.id.chatroom_recycleview);
        items = new ArrayList<ChatRoom_item>();

       /* IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.refresh_chaatroom);
        getContext().registerReceiver(receiver, intentFilter);*/
        Init();

        makeChatRoom();
        /*if(items.size()==0){
            chatroom_isnull.setVisibility(view.VISIBLE);
        }else {
            chatroom_isnull.setVisibility(view.INVISIBLE);
        }*/
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
                for(int i=0;i<a.size();i++){
                    b.add(a.get(i));
                }
                b.add(Contact.MyMainRoom);
                Log.e("asd",Contact.MyName);
                for(int i=0;i<b.size();i++){
                    if(!b.get(i).equals(Contact.MyName)){
                        items.add(new ChatRoom_item(b.get(i),"",""));
                    }
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
        public void refresh(Chat_item o, int position) {
            Log.e("qqqqqqqqqqq","qqqqqqqqqqqq");
           try{
                items.get(position).setContent(o.getContent());
                items.get(position).setTime(o.getTime());
                chatRoom_adapter.notifyDataSetChanged();
            }catch (NullPointerException e){}
        }

    };

    /*private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            items.clear();
            if(intent.getAction().equals(Contact.refresh_chaatroom)){
               String name = intent.getStringExtra("name");
               String content = intent.getStringExtra("content");
               String time = intent.getStringExtra("time");
               for(int i=0;i<items.size();i++){
                   if(items.get(i).getName().equals(name)){
                       items.get(i).setTime(time);
                       items.get(i).setContent(content);
                   }
               }
            }
        }
    };*/
}






















