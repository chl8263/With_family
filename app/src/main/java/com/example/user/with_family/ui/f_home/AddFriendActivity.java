package com.example.user.with_family.ui.f_home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.with_family.R;

public class AddFriendActivity extends Activity {

    private EditText id_editText;
    private Button ok_btn;
    private Button cancle_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_friend);



        id_editText = (EditText)findViewById(R.id.add_friend_id_txt);
        ok_btn = (Button)findViewById(R.id.add_friend_id_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Result", id_editText.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancle_btn = (Button)findViewById(R.id.add_friend_id_cancle);
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
