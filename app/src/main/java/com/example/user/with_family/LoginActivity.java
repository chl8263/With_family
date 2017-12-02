package com.example.user.with_family;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.with_family.util.UserDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharededitor;
    FirebaseDatabase databaseRef;
    DatabaseReference userinfoRef;                  // 유저 정보 가져오는 레퍼런스

    private TelephonyManager telephonyManager;
    private EditText id_edittext;
    private EditText pw_edittext;
    private Button login_btn;
    private Button login_register;

    private static List<UserDAO> userDAOList = new ArrayList<>();      // 유저 정보들 저장해놓을 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);
        sharededitor = sharedPreferences.edit();
        databaseRef = FirebaseDatabase.getInstance();
        userinfoRef = databaseRef.getReference().child("register").child("user");
        //data_allRef = database.getReference().child("register");

        telephonyManager = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);      // 전화번호 가져오는 서비스
        id_edittext = (EditText) findViewById(R.id.login_edit_id_text);
        pw_edittext = (EditText) findViewById(R.id.login_edit_pw_text);
        login_btn = (Button) findViewById(R.id.login_loginBtn);
        login_register = (Button) findViewById(R.id.login_register);

        // 외부저장소, 내부저장소 읽기 쓰기 권한 받아오기
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 466);
        }

        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
            }, 466);
        }

        //전화번호 가져오기위한 권한, 권한이 없다면 권한승인 여부를 띄움
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 466);
        }

        //권한이 이미 있다면 바로 id 부분에 ID값(=전화번호)을 띄움
        else {
            // OnClick리스너는 2번을 눌러야 되서 Focus가 잡히면 자동으로 id=전화번호 가져오게함
            id_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    // sharedpreference에 전화번호 저장
<<<<<<< HEAD
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    sharededitor.putString("myid", telephonyManager.getLine1Number());
                    sharededitor.commit();
=======
                   sharededitor.putString("myid", telephonyManager.getLine1Number());
                    //sharededitor.commit();
>>>>>>> origin/master

                    id_edittext.setText(telephonyManager.getLine1Number());
                    Toast.makeText(getApplicationContext(), telephonyManager.getLine1Number(), Toast.LENGTH_LONG).show();

                }
            });

            // 파이어베이스 유저데이터를 가져와서 저장하는 부분
            userinfoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        UserDAO dao = snapshot.getValue(UserDAO.class);
                        userDAOList.add(dao);

                    }

                }

               @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // [미구현] 로그인 기능 - 아직 아이디 비번 체크 미구현
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //만약 가져온 데이터중 지금 ID(핸드폰번호)와 비밀번호가 일치한다면 로그인 ok
                for(int i=0; i<userDAOList.size(); i++){

                    if(userDAOList.get(i).getId().equals(id_edittext.getText().toString()) &&
                            userDAOList.get(i).getPw().equals(pw_edittext.getText().toString())){

                        Toast.makeText(getApplicationContext(), "로그인 ON!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("room_name", userDAOList.get(i).getRoom_name());
                        sharededitor.putString("room_name", userDAOList.get(i).getRoom_name());
                        sharededitor.commit();
                        startActivity(intent);
                        break;
                    }
                    // ID와 PW가 틀릴때
                    else if(userDAOList.get(i).getId().equals(id_edittext.getText().toString()) &&
                            !userDAOList.get(i).getPw().equals(pw_edittext.getText().toString())){

                        Toast.makeText(getApplicationContext(), "ID와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    // ID가 존재하지않을때 -> 회원가입이 필요할때
                    else{
                        Toast.makeText(getApplicationContext(), "회원가입이 필요하신 ID입니다", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        // 회원가입 액티비티로 넘어감
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });








    }




}
