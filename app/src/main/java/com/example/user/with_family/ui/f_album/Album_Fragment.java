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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.with_family.R;
import com.example.user.with_family.util.AlbumDAO;
import com.example.user.with_family.util.AlbumDAO2;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.melnykov.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 2017-11-11.
 */

public class Album_Fragment   extends Fragment {

    private  int count=0;

    private SharedPreferences sharedPreferences;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference user_databaseReference;
    private DatabaseReference img_databaseReference;
    private DatabaseReference temp_databaseReference;

    private FirebaseStorage firebaseStorage;
    private StorageReference mStorageReference ;

    private long now ;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private String formatDate;
    private String imgDate="";

    private String room_name;
    private String random_imgname;
    private String hide_date;

    private FloatingActionButton floatingActionButton;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog; // 사진이 저장되는 동안 프로그레스바를 띄워서 로딩중인걸 알게해줌

    private int date_dao_count=0;
    private int albumdaocount=0;

    private StaggeredGridLayoutManager mStgaggeredGridLayoutManager;
    private RecyclerView album_recyclerView;
    Album_Adapter album_adapter;
    Album_Adapter2 album_adapter2;

    private  List<AlbumDAO2> date_DAOList = new ArrayList<>();     // 방에 들어가있는 날짜 목록
    private  List<String> img_DAOList = new ArrayList<>();      // 방에 들어가있는 날짜에 속한 그림 목록
    public static List<AlbumDAO> albumDAOList = new ArrayList<>();      // 방안의 앨범 이미지들 저장해놓을 리스트

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
        firebaseStorage = FirebaseStorage.getInstance();

        user_databaseReference = firebaseDatabase.getReference().child("register").child("user");
        img_databaseReference = firebaseDatabase.getReference().child("register").child("r_room");
        temp_databaseReference = firebaseDatabase.getReference().child("register").child("r_room");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container,false);
        album_recyclerView = (RecyclerView)view.findViewById(R.id.album_recycleview);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStgaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        album_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        album_adapter2 = new Album_Adapter2(getView().getContext());
        album_recyclerView.setAdapter(album_adapter2);

        // 현재 방 이름 가져오는 것
        sharedPreferences = this.getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        room_name = sharedPreferences.getString("room_name", "room");
        // 스토리지 경로도 그룹(방)이름에 맞게 초기화 설정
        mStorageReference = firebaseStorage.getReferenceFromUrl("gs://ahntanwithfamily.appspot.com").child(room_name);

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
        temp_databaseReference = temp_databaseReference.child(room_name).child("img_tree");
        img_databaseReference = temp_databaseReference;
        img_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CurrentListRemove(img_DAOList);
                //CurrentListRemove(albumDAOList);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String load_date = snapshot.getKey().toString();
                    System.out.println("날짜 무슨 값이니 : " + load_date);
                    for(int i=0; i<date_DAOList.size(); i++){
                        if (date_DAOList.get(i).getDate().equals(load_date)) {
                            date_dao_count++;
                        }
                    }
                    if(date_dao_count==0) {
                        date_DAOList.add(new AlbumDAO2(load_date, room_name));
                    }
                    date_dao_count=0;
                }
                for(int i=0; i<date_DAOList.size(); i++){
                    load_img(date_DAOList.get(i).getDate(), 1);
                }


                for(int i = 0; i<date_DAOList.size(); i++){
                    System.out.println("리스트 안의 들어간 날짜 : " + date_DAOList.get(i).toString());
                }

                //write();
                //write();
                //hide_date="";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







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

            int n = (int)(Math.random()*100);
            random_imgname = Integer.toString(n);
            System.out.println("유아엘 보내기전 : " + random_imgname);

            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child(room_name).child(imgDate).child(random_imgname);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    /*removed();
                    albumDAOList.clear();
                    date_DAOList.clear();*/
                    System.out.println("앨범사이즈 : "  + albumDAOList.size() + " 데이터 사이즈 : " + date_DAOList.size());

                    Toast.makeText(getContext(), "upload Done", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    // 데이터베이스도 갱신(스토리지에 있는 유저img 경로)
                    //updateDao(dao, filepath.toString());
                    System.out.println("file" + filepath.toString());
                    removed();
                    albumDAOList.clear();
                    date_DAOList.clear();
                    updateDao(imgDate, random_imgname);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "upload Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    // 방 안에있는 img_tree안에 이미지 경로 저장
    public void updateDao(String date, String url){


        Map<String, Object> dataValues = new HashMap<>();

        dataValues.put("temp", url);

        System.out.println("파이어베이스에 들어갈 경로 : " + url);

        //Ref2는 없어도 됨
        DatabaseReference dr = img_databaseReference.child(url);
        dr.setValue(dataValues);

     /*   removed();
        albumDAOList.clear();
        date_DAOList.clear();*/
        hide_date = "999";
    }



    public void write(){

        if(count==date_DAOList.size()) {
            removed();
            for (int i = 0; i < date_DAOList.size(); i++) {
                System.out.println("앨범사이즈 : " + date_DAOList.size());
                album_adapter2.addItem(date_DAOList.get(i));
                album_adapter2.notifyDataSetChanged();
            }
            count=0;
        }

        System.out.println("프래그먼트에서의 크기 : " + albumDAOList.size());
    }


    public void removed(){
        album_adapter2.removeItem();
    }

    // 현재 albumDAOList 초기화
    public void CurrentListRemove(List arrayList){
        int size = arrayList.size();
        for(int i=0; i<size; i++) {
            arrayList.remove(0);
        }
    }


    // 해당 날짜에 속한 이미지 값들 리스트에 저장
    public void load_img(String dates, int cnt) {

        System.out.println("넘어온 dates : " + dates);
        hide_date = dates;
        System.out.println("템프데이트 : " + hide_date);
        img_databaseReference = temp_databaseReference.child(hide_date);
        img_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CurrentListRemove(albumDAOList);
                if (!hide_date.equals("999")){
                    System.out.println("로드이미지 포문안2 : " + dataSnapshot.getKey().toString() + "로드이미지 템프값 : " + hide_date);
                String temp_date = dataSnapshot.getKey().toString(); // 현재 찾고있는 날짜
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String load_image = snapshot.getKey().toString();                   // 날짜에 속해있는 이미지들 키값 가져오는 부분임

                    for(int k=0; k<albumDAOList.size(); k++){
                        if(albumDAOList.get(k).getImg_ref().equals(room_name + "/" + temp_date + "/" + load_image)){
                            albumdaocount++;
                        }
                    }
                    if(albumdaocount==0){
                        albumDAOList.add(new AlbumDAO(temp_date, room_name + "/" + temp_date + "/" + load_image));              // 앨범 DAO에 날짜와 이미지 값들 저장
                    }
                    albumdaocount=0;
                    System.out.println("데이터 왜들어가 : " + albumDAOList.get(0).getDate());
                }

                for (int i = 0; i < albumDAOList.size(); i++) {
                    System.out.println("리스트 안의 날짜 : " + albumDAOList.get(i).getDate());
                    System.out.println("리스트 안의 이미지 : " + albumDAOList.get(i).getImg_ref());
                }
                count++;
                write();

            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        }


}
