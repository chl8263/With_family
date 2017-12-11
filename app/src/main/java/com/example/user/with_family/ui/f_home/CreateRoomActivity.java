package com.example.user.with_family.ui.f_home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.with_family.R;

public class CreateRoomActivity extends Activity {

    private EditText id_editText;
    private Button ok_btn;
    private Button cancle_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_room);

        id_editText = (EditText)findViewById(R.id.create_room_id_txt);
        ok_btn = (Button)findViewById(R.id.create_room_id_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Result", "create_room");
                resultIntent.putExtra("room_name", id_editText.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancle_btn = (Button)findViewById(R.id.create_room_id_cancle);
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Result", "cancle");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });



    }

    // 뒤로가기 버튼 처리
    @Override
    public void onBackPressed(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Result", "cancle");
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
