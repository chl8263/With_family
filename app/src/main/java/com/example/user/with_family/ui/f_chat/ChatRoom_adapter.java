package com.example.user.with_family.ui.f_chat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.with_family.Interfaces.ChatRoom_Refresh_callback;
import com.example.user.with_family.R;
import com.example.user.with_family.ui.ChatActivity.ChatActivity;
import com.example.user.with_family.ui.ChatActivity.Chat_item;
import com.example.user.with_family.ui.Circleimage.Circle;
import com.example.user.with_family.util.Contact;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
    private DatabaseReference roomimg = firebaseDatabase.getReference("register").child("r_room").child("룸").child("userimg_tree");
    private DatabaseReference chatroom = myMainRoom.child("chat_room");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.chatroom_name.setText(items.get(position).getName());
        holder.chatroom_content.setText(items.get(position).getContent());
        holder.chatroom_time.setText(items.get(position).getTime());
        //reference = storage.getReferenceFromUrl(items.get(position).getImgpath());
        //Glide.with(context).using(new FirebaseImageLoader()).load(reference).into(holder.chat_room_img);
        roomimg.child(items.get(position).getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = (String) dataSnapshot.child("userimg").getValue();
//                Log.e("aaaaa",dataSnapshot.child("userimg").toString());
                if(dataSnapshot.getValue()!=null){

                    String uri  = dataSnapshot.child("userimg").getValue(String.class);
                    Log.e("check",uri);
                    reference = storage.getReferenceFromUrl(uri);

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Picasso.with(context).load(url).transform(new Circle()).into((holder).chat_room_img);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //holder.chat_room_img.setImageResource(items.g);
        String roomName = Contact.MyName+","+items.get(position).getName();
        String roomName2 = items.get(position).getName()+","+Contact.MyName;
        Log.e("asdadsadsdas",items.get(position).getName()+","+Contact.MyName);
        if(items.get(position).getName().equals(Contact.MyMainRoom)){
            chatroom.child(items.get(position).getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Chat_item chat_item = dataSnapshot.getValue(Chat_item.class);
                    callback.refresh(chat_item,position);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            chatroom.child(items.get(position).getName()+","+Contact.MyName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Chat_item chat_item = dataSnapshot.getValue(Chat_item.class);
                    callback.refresh(chat_item,position);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView chatroom_name;
        public TextView chatroom_content;
        public TextView chatroom_time;
        public ImageView chat_room_img;
        public LinearLayout chatroom_click;

        public ViewHolder(View itemView) {
            super(itemView);
            chatroom_name = (TextView) itemView.findViewById(R.id.chat_room_name);
            chatroom_content = (TextView) itemView.findViewById(R.id.chat_room_content);
            chatroom_time = (TextView) itemView.findViewById(R.id.chat_room_time);
            chat_room_img = (ImageView)itemView.findViewById(R.id.chat_room_img);
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
