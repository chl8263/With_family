package com.example.user.with_family.ui.ChatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.user.with_family.R;
import com.example.user.with_family.util.Contact;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Note on 2017-11-28.
 */


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText input;
    private Button submit;
    private String getName;
    private RecyclerView chat_RecyclerView;
    private ArrayList<Chat_item> items;
    private Chat_Adapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("룸");
    private DatabaseReference room_users = myMainRoom.child("user_tree");
    private DatabaseReference chat_room = myMainRoom.child("chat_room");
    private DatabaseReference chat = myMainRoom.child("chat");
    private FloatingActionButton fab, fab1, fab2, fab3;
    private LinearLayout fabLayout1, fabLayout2, fabLayout3;
    private boolean isFABopen = false;
    private String roomName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);

        initFab();
        init();
        checkRoom();

        Log.e("myname", Contact.MyName);
        Log.e("yourname", getName);
    }

    private void checkRoom() {

        chat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    Log.e("zzzzzz", key);
                    if (key.contains(Contact.MyName) && key.contains(getName)) {
                        roomName = key;
                        Log.e("checkRoom_ roomname", roomName);
                        if (roomName.equals("")) {

                            roomName = getName + "," + Contact.MyName;
                            Log.e("없어서 만듬", roomName);
                            aaa();
                        }

                    }else{
                        Log.e("checkRoom_ roomname", roomName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat setformat = new SimpleDateFormat("HH:mm");
                String time = setformat.format(date);
                Chat_item item = new Chat_item(getName, input.getText().toString(), time);
                //checkRoom();
                //aaa();
                /*if (roomName.equals("")) {

                    roomName = getName + "," + Contact.MyName;
                    Log.e("없어서 만듬", roomName);
                    aaa();
                }*/
                chat.child(roomName).push().setValue(item);
                input.setText("");
                adapter.notifyDataSetChanged();
                chat_RecyclerView.getLayoutManager().scrollToPosition(chat_RecyclerView.getAdapter().getItemCount() - 1);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    private void initFab() {
        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABopen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

    }

    private void showFABMenu() {
        isFABopen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));

    }

    private void closeFABMenu() {
        isFABopen = false;
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABopen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private void aaa(){
        chat.child(roomName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat_item item = dataSnapshot.getValue(Chat_item.class);
                items.add(item);
                Log.e("들어옴roomName", roomName);
                Log.e("들어옴", "ㅅㅂ");
                if (item.getContent() != null) {
                    Log.e("내용", item.getContent());
                }else{
                    Log.e("내용", "null");
                }

                adapter.notifyDataSetChanged();
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
    private void init() {

        Intent intent = getIntent();
        getName = intent.getStringExtra("room_name");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        items = new ArrayList<Chat_item>();
        chat_RecyclerView = (RecyclerView) findViewById(R.id.chat_Recycler);
        chat_RecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Chat_Adapter(getApplicationContext(), items);

        chat_RecyclerView.setAdapter(adapter);
        chat_RecyclerView.scrollToPosition(adapter.getItemCount() - 1);

        input = (EditText) findViewById(R.id.input);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);


    }


}