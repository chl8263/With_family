package com.example.user.with_family.ui.f_album;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.with_family.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.melnykov.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 2017-11-11.
 */

public class Album_Fragment   extends Fragment {

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference user_databaseReference;
    private DatabaseReference img_databaseReference;

    private long now ;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private String formatDate;
    private String imgDate="";

    private String room_name;

    private FloatingActionButton floatingActionButton;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog; // 사진이 저장되는 동안 프로그레스바를 띄워서 로딩중인걸 알게해줌

    private static List<String> date_DAOList = new ArrayList<>();     // 방에 들어가있는 유저 목록

    public Album_Fragment(){

    }
    public static Album_Fragment newInstance() {
        Bundle args = new Bundle();
        Album_Fragment fragment = new Album_Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(getContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        user_databaseReference = firebaseDatabase.getReference().child("register").child("user");
        img_databaseReference = firebaseDatabase.getReference().child("register").child("r_room");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 현재 방 이름 가져오는 것
        sharedPreferences = this.getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        room_name = sharedPreferences.getString("room_name", "room");

        // 현재 날짜 가져오는 것
        now = System.currentTimeMillis();
        date = new Date(now);
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        formatDate = simpleDateFormat.format(date);
        String[] nowDate = formatDate.split("/");

        for(int i=0; i<nowDate.length; i++){
            if(i==nowDate.length-1)
                imgDate+=nowDate[i];
            else
                imgDate+=nowDate[i]+"-";
        }


        // 방 안에 날짜 별로 리스트에 일단 저장
      /*  img_databaseReference.child(room_name).child("img_tree").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String date = dataSnapshot.getValue(String.class);
                    date_DAOList.add(date);
                }

                for(int i = 0; i<date_DAOList.size(); i++){
                    System.out.println("리스트 안의 값 : " + date_DAOList.get(i).toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/



        floatingActionButton = (FloatingActionButton)getActivity().findViewById(R.id.img_add_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            // 방 추가하는 버튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }

        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setMessage("사진을 업로드중입니다....");
            mProgressDialog.show();

            Uri uri = data.getData();

            int a = (int)(Math.random()*100);
            String j = Integer.toString(a);
            System.out.println("유아엘 보내기전 : " + j);

            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child(room_name).child(imgDate).child(j);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "upload Done", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    // 데이터베이스도 갱신(스토리지에 있는 유저img 경로)
                    //updateDao(dao, filepath.toString());
                    System.out.println("file" + filepath.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "upload Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}