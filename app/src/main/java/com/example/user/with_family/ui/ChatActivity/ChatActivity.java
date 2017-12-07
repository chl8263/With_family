package com.example.user.with_family.ui.ChatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.with_family.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FloatingActionButton fab,fab1,fab2,fab3;
    private boolean isFABopen= false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);

        initFab();
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                long now  = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat setformat = new SimpleDateFormat("HH:mm");
                String time  = setformat.format(date);
                Chat_item item = new Chat_item(getName, input.getText().toString(),time);
                chat.child(getName).push().setValue(item);
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
    private void initFab(){
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABopen){
                    showFABMenu();
                }else {

                }
            }
        });

    }
    private void showFABMenu(){
        isFABopen=true;
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fab1.animate().translationY(-55);
        fab2.animate().translationY(-100);
        fab3.animate().translationY(-145);
    }
    private void closeFABMenu(){
        isFABopen=false;
        fab.animate().rotationBy(-180);
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABopen){
                    fab1.setVisibility(View.GONE);
                    fab2.setVisibility(View.GONE);
                    fab3.setVisibility(View.GONE);
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
    private void init() {

        Intent intent = getIntent();
        getName = intent.getStringExtra("room_name");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        items = new ArrayList<Chat_item>();
        chat_RecyclerView = (RecyclerView)findViewById(R.id.chat_Recycler);
        chat_RecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Chat_Adapter(getApplicationContext(),items);

        chat_RecyclerView.setAdapter(adapter);
        chat_RecyclerView.scrollToPosition(adapter.getItemCount()-1);

        input = (EditText) findViewById(R.id.input);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

        chat.child(getName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat_item item = dataSnapshot.getValue(Chat_item.class);
                items.add(item);
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




}