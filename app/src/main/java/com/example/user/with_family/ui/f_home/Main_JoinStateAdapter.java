package com.example.user.with_family.ui.f_home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.with_family.R;
import com.example.user.with_family.util.Contacts;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by 안탄 on 2017-11-19.
 */

public class Main_JoinStateAdapter extends RecyclerView.Adapter<Main_JoinStateAdapter.UnknownViewHolder>{


    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseImageLoader mFirebaseImageLoader;
    private static final int request_code = 0;
    private int userinfo_position;  // 현재 버튼눌린 유저목록 위치
    Context mcontext;
    ArrayList<Contacts> arrayList;
    public Bitmap bmp;


    public Main_JoinStateAdapter(Context context){
        this.mcontext = context;
        arrayList = new ArrayList<>();
        mFirebaseImageLoader = new FirebaseImageLoader();
        mFirebaseStorage = FirebaseStorage.getInstance();

    }

    public class UnknownViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView userImg;
        public TextView username;
        public TextView userstats;


        // 뷰홀더 -> UI(?)부분에 해당되는 것들을 뷰에 미리 붙여서 고정
        public UnknownViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            userImg = (ImageView)itemView.findViewById(R.id.user_img);

            username = (TextView)itemView.findViewById(R.id.user_name);
            userstats = (TextView)itemView.findViewById(R.id.user_stat);

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent intent = new Intent(context, ChatActivity.class);
                    context.startActivity(intent);*/
                }
            });
        }

        @Override
        public void onClick(View view){
            int position = getLayoutPosition();

            Context context2 = view.getContext();
            // 프로필 눌렀을시 나오는 화면 구현
            Intent intent = new Intent(context2, UserInfoActivity.class);
            intent.putExtra("id", arrayList.get(position).getId());
            intent.putExtra("position", position);
            intent.putExtra("userurl", arrayList.get(position).getUrl().toString());
            intent.putExtra("username", arrayList.get(position).getName());
            intent.putExtra("userstats", arrayList.get(position).getStats());
            intent.putExtra("userbackurl", arrayList.get(position).getBackurl());
            intent.putExtra("userid", arrayList.get(position).getId());
            ((Activity)context2).startActivityForResult(intent, request_code);
        }

    }

    // 포지션값 가져오는 부분
    @Override
    public int getItemCount(){

       return arrayList.size();
    }

    public void addItem(Contacts contacts){
        arrayList.add(contacts);
        notifyDataSetChanged();
    }

    @Override //아이템을 위한 뷰를 만들어서 뷰홀더에 넣어서 리턴
    public Main_JoinStateAdapter.UnknownViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contatctview = inflater.inflate(R.layout.fragment_main_home_state_item, parent, false);
        UnknownViewHolder viewHolder = new UnknownViewHolder(contatctview);
        return viewHolder;
    }


    @Override   // 뷰홀더의 뷰에 position에 해당되는 데이터를 넣음 = 데이터 처리하는곳
    public void onBindViewHolder(final Main_JoinStateAdapter.UnknownViewHolder viewHolder, int position){
         mStorageReference = mFirebaseStorage.getReferenceFromUrl(arrayList.get(position).getUrl());
         mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgURL = uri.toString();
                Glide.with(mcontext.getApplicationContext()).load(imgURL).into(viewHolder.userImg);
            }

        });

        viewHolder.username.setText(arrayList.get(position).getName());
        viewHolder.userstats.setText(arrayList.get(position).getStats());

    }

    public void removeItem(){
        int size = arrayList.size();
        for(int i=0; i<size; i++) {
            arrayList.remove(0);
            notifyItemRemoved(0);
        }
    }





}
