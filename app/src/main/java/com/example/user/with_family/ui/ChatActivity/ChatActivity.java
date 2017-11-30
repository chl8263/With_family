package com.example.user.with_family.ui.ChatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.with_family.R;

/**
 * Created by Note on 2017-11-28.
 */


public class ChatActivity {/*extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private TextView chat_activity_setName;
    private ImageView chat_activity_back;
    private Button button;
    private String getName;
    private RecyclerView chatActivity_recyclerView;
    private ChatActivity_RecyclerView_Adapter chatActivity_recyclerView_adapter;
    private List<ChatActivity_RecyclerView_Item> chatActivity_recyclerView_items;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //firebase 접속
    private DatabaseReference ChatRoomReference = firebaseDatabase.getReference("ChatRoom");
    private DatabaseReference databaseReference = firebaseDatabase.getReference();  //firebase json tree 접근
    private DatabaseReference chat = firebaseDatabase.getReference("chat");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);

        InitStatusbar_and_Actionbar();
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                long now  = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat setformat = new SimpleDateFormat("HH:mm");
                String time  = setformat.format(date);
                ChatActivity_RecyclerView_Item item = new ChatActivity_RecyclerView_Item(getName, editText.getText().toString(),time,1);
                databaseReference.child("chat").child(getName).push().setValue(item);
                editText.setText("");
                ChatRoomReference.child(getName).setValue(item);
                *//* 스크롤 맨 밑으로 내리기, 어댑터 갱신하기 *//*
                chatActivity_recyclerView_adapter.notifyDataSetChanged();
                chatActivity_recyclerView.getLayoutManager().scrollToPosition(chatActivity_recyclerView.getAdapter().getItemCount() - 1);
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

    private void init() {
        chatActivity_recyclerView_items = new ArrayList<ChatActivity_RecyclerView_Item>();
        chatActivity_recyclerView = (RecyclerView) findViewById(R.id.chat_activity_RecyclerView);
        chatActivity_recyclerView_adapter = new ChatActivity_RecyclerView_Adapter(getApplicationContext(), chatActivity_recyclerView_items);
        //chatActivity_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        chatActivity_recyclerView.setLayoutManager(linearLayoutManager);
        chatActivity_recyclerView.setAdapter(chatActivity_recyclerView_adapter);

        chatActivity_recyclerView.scrollToPosition(chatActivity_recyclerView_adapter.getItemCount()-1);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);

        databaseReference.child("chat").child(getName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatActivity_RecyclerView_Item item = dataSnapshot.getValue(ChatActivity_RecyclerView_Item.class);
                chatActivity_recyclerView_items.add(item);
                chatActivity_recyclerView_adapter.notifyDataSetChanged();
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


    private void InitStatusbar_and_Actionbar() {
        Intent intent = getIntent();
        getName = intent.getStringExtra("name");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getName);

        actionBar.setBackgroundDrawable(getDrawable(R.color.colorPrimary));
        actionBar.show();

        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        } else getWindow().setStatusBarColor(Color.parseColor("#000"));
    }
*/

}