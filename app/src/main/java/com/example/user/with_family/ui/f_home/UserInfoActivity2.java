package com.example.user.with_family.ui.f_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.with_family.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class UserInfoActivity2 extends AppCompatActivity {
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseImageLoader mFirebaseIamgeLoader;
    private ImageView user_info_imgview;
    private String user_img_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);

        Intent intent = getIntent();
        user_img_url = intent.getStringExtra("userurl");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReferenceFromUrl(user_img_url);
        mFirebaseIamgeLoader = new FirebaseImageLoader();

        System.out.println("유저 url : " + user_img_url);


        // 유저 모양 메인 사진
        user_info_imgview = (ImageView)findViewById(R.id.user_info2_userimgview);
        Glide.with(getApplicationContext()).using(mFirebaseIamgeLoader).load(mStorageReference).into(user_info_imgview);

    }
}
