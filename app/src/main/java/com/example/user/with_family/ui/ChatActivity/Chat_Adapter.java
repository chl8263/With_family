package com.example.user.with_family.ui.ChatActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.with_family.R;
import com.example.user.with_family.util.Contact;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by choi on 2017-12-05.
 */

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Chat_item> items;
    public final int VIEW_TYPE_YOU = 1;
    public final int VIEW_TYPE_ME = 0;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder_ME){
            ((ViewHolder_ME) holder).content.setText(items.get(position).getContent());
            ((ViewHolder_ME) holder).time.setText(items.get(position).getTime());
        }else {

           /* mStorageReference = mFirebaseStorage.getReferenceFromUrl(items.get(position).get);
            mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imgURL = uri.toString();
                    Glide.with(mcontext.getApplicationContext()).load(imgURL).into(viewHolder.userImg);
                }

            });*/

            ((ViewHolder_OTHER)holder).content.setText(items.get(position).getContent());
            ((ViewHolder_OTHER)holder).otherName.setText(items.get(position).getName());
            ((ViewHolder_OTHER)holder).time.setText(items.get(position).getTime());

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
