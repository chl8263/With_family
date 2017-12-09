package com.example.user.with_family.ui.f_home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.with_family.R;
import com.example.user.with_family.util.Contact;
import com.example.user.with_family.util.UserDAO;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;

    SharedPreferences sharedPreferences;            // 저장된 현재 접속자 정보
    FirebaseDatabase databaseRef;
    DatabaseReference userinfoRef;                  // 접속 유저 정보 가져오는 레퍼런스 - 유저 개인 이미지 갱신용
    DatabaseReference roominfoRef;                  // 접속 유저 정보 가져오는 레퍼런스 - 방 유저 이미지 갱신용

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FirebaseImageLoader mFirebaseIamgeLoader;

    private ProgressDialog mProgressDialog; // 사진이 저장되는 동안 프로그레스바를 띄워서 로딩중인걸 알게해줌

    private String login_user;

    private ImageView user_info_imgview;
    private ImageView user_back_imgview;
    private ImageView user_info_editview;
    private ImageView user_info_deleteview;

    private TextView user_name_textview;
    private TextView user_stats_textview;

    private String user_img_url;
    private int user_backimg_url;
    private String user_name;
    private String user_stats;
    private String user_id;
    private int user_position;

    private UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        databaseRef = FirebaseDatabase.getInstance();
        userinfoRef = databaseRef.getReference().child("register").child("user");
        roominfoRef = databaseRef.getReference().child("register").child("r_room");         // 해당 방에 있는 사용자들을 가져오는 레퍼런스

        sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);
        login_user = sharedPreferences.getString("myid", "01012345678");

        // 파이어베이스 로그인 유저데이터(나와 친구들)를 가져와서 저장하는 부분
        userinfoRef.child(login_user).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                dao = dataSnapshot.getValue(UserDAO.class);
                    /*removed();*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        Intent intent = getIntent();

        user_img_url = intent.getStringExtra("userurl");
        user_backimg_url = intent.getIntExtra("userbackurl", 0);
        user_name = intent.getStringExtra("username");
        user_stats = intent.getStringExtra("userstats");
        user_id = intent.getStringExtra("userid");
        user_position = intent.getIntExtra("position", 999);



        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReferenceFromUrl(user_img_url);
        System.out.println("user_img_url" + user_img_url);
        System.out.println("user_img_url22" + mStorageReference);

        mFirebaseIamgeLoader = new FirebaseImageLoader();

        mProgressDialog = new ProgressDialog(this);

        // 유저 동그라미 모양 메인 사진
        user_info_imgview = (ImageView)findViewById(R.id.user_info_userimgview);
        //Glide.with(getApplicationContext()).using(mFirebaseIamgeLoader).load(mStorageReference).into(user_info_imgview);

        mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgURL = uri.toString();
                Glide.with(getApplicationContext()).load(imgURL).into(user_info_imgview);
            }

        });
        user_info_imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity2.class);
                intent.putExtra("userurl", user_img_url);
                startActivity(intent);
            }
        });


        // 배경이미지 처리
        user_back_imgview = (ImageView)findViewById(R.id.user_background_imgview);
        user_back_imgview.setBackgroundResource(user_backimg_url);
        //Glide.with(getApplicationContext()).load(user_backimg_url).into(user_back_imgview);

        // 유저 이름
        user_name_textview = (TextView)findViewById(R.id.user_name_textview);
        user_name_textview.setText(user_name);

        // 유저 상태 - 얘는 빼야 될 수도 있음
        user_stats_textview = (TextView)findViewById(R.id.user_stats_textview);
        user_stats_textview.setText(user_stats);

        // 유저 편집 버튼
        user_info_editview = (ImageView)findViewById(R.id.user_info_edit);
        if(user_position!=999){
            user_info_editview.setVisibility(View.INVISIBLE);
        }
        user_info_editview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        // 친구 삭제 버튼
        user_info_deleteview = (ImageView)findViewById(R.id.user_info_delete);
        user_info_deleteview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fragment쪽 핸들러로 메세지를 보냄
                Bundle bundle = new Bundle();
                bundle.putString("id", user_id );
                bundle.putInt("position", user_position);
                Message msg = new Message();
                msg.setData(bundle);
                HomeFragment.mhandler.sendMessage(msg);
                finish();
            }
        });
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setMessage("사진을 변경중입니다....");
            mProgressDialog.show();

            Uri uri = data.getData();

            // 유저 아이디 +82102931293 에서 +는 자꾸 %2B 형태로 바뀜, 자바에서는 그냥 + 를 못읽어 들여서 앞에 \\을 붙여줘야함
            String str = user_id;
            String[] mobNum = str.split("\\+");
            str = mobNum[1];

            int a = (int)(Math.random()*100);
            String j = Integer.toString(a);
            System.out.println("유아엘 보내기전 : " + j);

            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child(str).child(j);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UserInfoActivity.this, "upload Done", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    // 데이터베이스도 갱신(스토리지에 있는 유저img 경로)
                    updateDao(dao, filepath.toString());
                    System.out.println("file" + filepath.toString());

                    Bundle bundle = new Bundle();
                    bundle.putString("url", filepath.toString() );
                    Message msg = new Message();
                    msg.setData(bundle);
                    HomeFragment.mhandler.sendMessage(msg);


                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserInfoActivity.this, "upload Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void updateDao(UserDAO currentDAO, String url){
        Map<String, Object> dataValues = new HashMap<>();

        dataValues.put("id", currentDAO.getId());
        dataValues.put("pw", currentDAO.getPw());
        dataValues.put("name", currentDAO.getName());
        dataValues.put("bir", currentDAO.getBir());
        dataValues.put("nick", currentDAO.getNick());
        dataValues.put("userimg", url);
        dataValues.put("friend1", currentDAO.getFriend1());
        dataValues.put("friend2", currentDAO.getFriend2());
        dataValues.put("friend3", currentDAO.getFriend3());
        dataValues.put("friend4", currentDAO.getFriend4());
        dataValues.put("room_name", currentDAO.getRoom_name());
        dataValues.put("check", currentDAO.getCheck());
        dataValues.put("temp_room", currentDAO.getTemp_room());
        dataValues.put("invite_id", currentDAO.getInvite_id());

        dataValues.put("ip", Contact.MyIp);
        dataValues.put("mainsendport", currentDAO.getMainsendport());
        dataValues.put("mainrecvport", currentDAO.getMainrecvport());
        dataValues.put("soundsendport", currentDAO.getSoundsendport());
        dataValues.put("soundrecvport", currentDAO.getSoundrecvport());

        refreshuserImg(currentDAO, url);

        //Ref2는 없어도 됨
        DatabaseReference dr = userinfoRef.child(dao.getId());
        dr.setValue(dataValues);
    }

    public void refreshuserImg(UserDAO currentDAO, String url){
        Map<String, Object> dataValues = new HashMap<>();

        dataValues.put("userimg", currentDAO.getUserimg());

        DatabaseReference dr = roominfoRef.child(currentDAO.getRoom_name()).child("userimg_tree").child(currentDAO.getName());
        dr.setValue(dataValues);
    }

}
