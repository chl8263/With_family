package com.example.user.with_family.ui.f_home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.with_family.R;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText id_editText;
    private Button ok_btn;
    private Button cancle_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
