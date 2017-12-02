package com.example.user.with_family.ui.f_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.with_family.R;

public class AddFriendActivity extends AppCompatActivity {

    private EditText id_editText;
    private Button ok_btn;
    private Button cancle_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
