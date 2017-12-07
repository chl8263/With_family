package com.example.user.with_family;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.with_family.util.Contact;
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

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;
    private TelephonyManager telephonyManager;
    private EditText id_edittext;
    private EditText pw_edittext;
    private Button login_btn;
    private Button login_register;

    private static List<UserDAO> userDAOList = new ArrayList<>();      // 유저 정보들 저장해놓을 리스트

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);
        sharededitor = sharedPreferences.edit();
        databaseRef = FirebaseDatabase.getInstance();
        userinfoRef = databaseRef.getReference().child("register").child("user");
        id_edittext = (EditText)findViewById(R.id.login_edit_id_text);
        pw_edittext = (EditText)findViewById(R.id.login_edit_pw_text);
        login_btn = (Button)findViewById(R.id.login_loginBtn);
        login_register = (Button)findViewById(R.id.login_register);

        telephonyManager = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);      // 전화번호 가져오는 서비스

        checkPermission(Contact.PERMISSIONS);
        init();


    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(String[] permissions) {

        requestPermissions(permissions, MY_PERMISSION_REQUEST_STORAGE);
    }

    // Application permission 23
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                int cnt = permissions.length;
                for (int i = 0; i < cnt; i++) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        //Log.i(LOG_TAG, "Permission[" + permissions[i] + "] = PERMISSION_GRANTED");

                    } else {


                    }
                }
                break;
        }
    }

    private void init() {

        userinfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDAO dao = snapshot.getValue(UserDAO.class);
                    userDAOList.add(dao);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // [미구현] 로그인 기능 - 아직 아이디 비번 체크 미구현
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("버튼은 눌림" + userDAOList.size());
                //만약 가져온 데이터중 지금 ID(핸드폰번호)와 비밀번호가 일치한다면 로그인 ok
                for (int i = 0; i < userDAOList.size(); i++) {
                    System.out.println("유저 정보 " + i + " " + userDAOList.get(i).getId());
                    if (userDAOList.get(i).getId().equals(id_edittext.getText().toString()) &&
                            userDAOList.get(i).getPw().equals(pw_edittext.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "로그인 ON!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("room_name", userDAOList.get(i).getRoom_name());
                        sharededitor.putString("myid", userDAOList.get(i).getId());
                        sharededitor.putString("room_name", userDAOList.get(i).getRoom_name());
                        sharededitor.commit();
                        startActivity(intent);
                        break;
                    }
                    // ID와 PW가 틀릴때
                    else if (userDAOList.get(i).getId().equals(id_edittext.getText().toString()) &&
                            !userDAOList.get(i).getPw().equals(pw_edittext.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "ID와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    // ID가 존재하지않을때 -> 회원가입이 필요할때
                    else {
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
