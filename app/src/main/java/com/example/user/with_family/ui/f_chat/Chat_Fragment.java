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

import com.example.user.with_family.R;
import com.example.user.with_family.util.Room_UserDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by User on 2017-11-11.
 */

public class Chat_Fragment   extends Fragment {
    private RecyclerView chatRoom_recyclerView;
    private ChatRoom_adapter chatRoom_adapter;
    private ArrayList<ChatRoom_item> items;
    private TextView chatroom_isnull;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("hello");
    private DatabaseReference room_users = myMainRoom.child("user_tree");


    public Chat_Fragment(){

    }
    public static Chat_Fragment newInstance() {
        Bundle args = new Bundle();
        Chat_Fragment fragment = new Chat_Fragment();
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
        Init();
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
        chatRoom_adapter = new ChatRoom_adapter(getContext(),items);
        chatRoom_recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        chatRoom_recyclerView.setAdapter(chatRoom_adapter);
        //items.add(new ChatRoom_item("aa","aa","aa"));
        items.add(new ChatRoom_item("bb","aa","bb"));
        items.add(new ChatRoom_item("aa","aa","aa"));
        /*chatroom.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        });*/

    }
    private void makeChatRoom(){
        room_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Room_UserDAO dao = (Room_UserDAO) snapshot.getValue();
                    Log.e("sdsdsd",dao.getName());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*room_users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        })*/
    }
}






















