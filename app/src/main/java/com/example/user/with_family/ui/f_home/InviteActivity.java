package com.example.user.with_family.ui.f_home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.with_family.R;

public class InviteActivity extends Activity {

    private TextView title_text;
    private TextView room_text;
    //private EditText id_editText;
    private Button ok_btn;
    private Button cancle_btn;


    private String invite_room_name = "";
    private String invite_index = "";
    private String invite_name = "";

    public InviteActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_invite);

        Intent resultintent = getIntent();
        invite_room_name = resultintent.getStringExtra("room");
        invite_index = resultintent.getStringExtra("check");
        invite_name = resultintent.getStringExtra("invite_name");

        title_text = (TextView)findViewById(R.id.invite_title);
        title_text.setText(invite_name + "님의 초대입니다");

        room_text = (TextView)findViewById(R.id.invite_room_id_txt);
        room_text.setText(invite_room_name);


        //id_editText = (EditText)findViewById(R.id.invite_room_id_txt);
        ok_btn = (Button)findViewById(R.id.invite_room_id_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();


                // invite index가 wait일땐 친구초대 기능 때문에 돌아가는거
                if(invite_index.equals("wait")) {
                    resultIntent.putExtra("room_name", invite_room_name);
                }
                    resultIntent.putExtra("Result", "create_room");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancle_btn = (Button)findViewById(R.id.invite_room_id_cancle);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Result", "reject");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });



    }
}
