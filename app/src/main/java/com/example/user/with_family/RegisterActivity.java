package com.example.user.with_family;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    FirebaseDatabase database;
    DatabaseReference data_allRef ;


    SharedPreferences sharedPreferences;
    private EditText id_editText;
    private EditText pw_editText;
    private EditText name_editText;
    private EditText birday_editText;
    private EditText nick_editText;
    private Button register_ok_btn;
    private Button register_return_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        data_allRef = database.getReference().child("register");

        id_editText = (EditText)findViewById(R.id.register_ID_edit);
        pw_editText = (EditText)findViewById(R.id.register_PW_edit);
        name_editText = (EditText)findViewById(R.id.register_name_edit);
        birday_editText = (EditText)findViewById(R.id.register_bir_edit);
        nick_editText = (EditText)findViewById(R.id.register_nickname_edit);
        register_ok_btn = (Button)findViewById(R.id.register_ok);
        register_return_btn = (Button)findViewById(R.id.register_return);

        sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);

        id_editText.setHint(sharedPreferences.getString("myid", "01012345678"));

        register_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> dataValues = new HashMap<>();

                dataValues.put("id", id_editText.getHint().toString());
                dataValues.put("pw", pw_editText.getText().toString());
                dataValues.put("name", name_editText.getText().toString());
                dataValues.put("bir", birday_editText.getText().toString());
                dataValues.put("nick", nick_editText.getText().toString());
                dataValues.put("friend1", "null");
                dataValues.put("friend2", "null");
                dataValues.put("friend3", "null");
                dataValues.put("friend4", "null");


                DatabaseReference dr = data_allRef.child("user").child(id_editText.getHint().toString());
                dr.setValue(dataValues);


                finish();

            }
        });


        // 초기화버튼을 눌렀을시 빈칸으로 초기화
        register_return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw_editText.setText("");
                name_editText.setText("");
                birday_editText.setText("");
                nick_editText.setText("");
            }
        });

    }
}
