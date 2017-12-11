package com.example.user.with_family.ui.ChatActivity;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.with_family.R;
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
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by choi on 2017-12-05.
 */

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Chat_item> items;
    public final int VIEW_TYPE_YOU = 1;
    public final int VIEW_TYPE_ME = 0;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("룸");
    private DatabaseReference roomimg = firebaseDatabase.getReference("register").child("r_room").child("룸").child("userimg_tree");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference reference;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder_ME){
            ((ViewHolder_ME) holder).content.setText(items.get(position).getContent());
            ((ViewHolder_ME) holder).time.setText(items.get(position).getTime());
        }else {

            ((ViewHolder_OTHER)holder).content.setText(items.get(position).getContent());
            ((ViewHolder_OTHER)holder).otherName.setText(items.get(position).getName());
            ((ViewHolder_OTHER)holder).time.setText(items.get(position).getTime());
            roomimg.child(items.get(position).getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String a = (String) dataSnapshot.child("userimg").getValue();
//                Log.e("aaaaa",dataSnapshot.child("userimg").toString());
                    if(dataSnapshot.getValue()!=null){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String uri  = dataSnapshot.child("userimg").getValue(String.class);
                        Log.e("check",uri);
                        reference = storage.getReferenceFromUrl(uri);
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                try {

                                    Picasso.with(context).load(url).transform(new Circle()).into(((ViewHolder_OTHER) holder).imageView);
                                }catch (RejectedExecutionException e){
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
            //((ViewHolder_OTHER)holder).imageView.

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder_ME extends RecyclerView.ViewHolder{
        public TextView content;
        public TextView time;

        public ViewHolder_ME(View itemView) {
            super(itemView);
            content=(TextView)itemView.findViewById(R.id.content);
            time = (TextView)itemView.findViewById(R.id.time);
        }
    }
    public class ViewHolder_OTHER extends RecyclerView.ViewHolder {
        public TextView content;
        public TextView otherName;
        public ImageView imageView;
        public TextView time;
        public ViewHolder_OTHER(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.chat_other_img);
            content = (TextView) itemView.findViewById(R.id.content);
            otherName = (TextView)itemView.findViewById(R.id.otherName);
            time = (TextView)itemView.findViewById(R.id.time);
        }
    }}
