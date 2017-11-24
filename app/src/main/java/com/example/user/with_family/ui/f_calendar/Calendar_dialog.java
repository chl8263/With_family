package com.example.user.with_family.ui.f_calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.with_family.Interfaces.CalendarListener;
import com.example.user.with_family.R;
import com.example.user.with_family.db.DBManager;
import com.example.user.with_family.util.Contact;

/**
 * Created by User on 2017-11-23.
 */

public class Calendar_dialog extends AppCompatActivity implements View.OnClickListener{
    private TextView title;
    private ImageView submit;
    private EditText content;
    private String getTitle;
    private String DB_NAME;
    private DBManager dbManager;
    private Cursor cursor;
    private CalendarListener calendarListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);   //타이틀바 없애는코드
        setContentView(R.layout.calendar_dialog);
        setFinishOnTouchOutside(false);         //다이얼로그 테마로 다이얼로그를 띄울때 다른곳을 터치할시에 꺼지는것을 방지
        init();
    }
    private void init(){
        title = (TextView)findViewById(R.id.calender_dialog_Title);
        submit = (ImageView)findViewById(R.id.calender_dialog_submit);
        submit.setOnClickListener(this);
        content = (EditText)findViewById(R.id.calender_dialog_content);
        Intent intent = getIntent();
        getTitle = intent.getStringExtra("title");
        DB_NAME = intent.getStringExtra("DB_NAME");
        Bundle bundle = intent.getExtras();
        if(bundle !=null){
            calendarListener = bundle.getParcelable("callback");
        }
        title.setText(getTitle);

        dbManager = new DBManager(getApplicationContext(),"Write",null,1);

        dbManager = new DBManager(getApplicationContext(), "Write", null, 1);
        SQLiteDatabase redadb = dbManager.getReadableDatabase();
        try {
            cursor = redadb.query("'" + DB_NAME + "'", null, null, null, null, null, null);

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                Log.e("MOVE_TO_NEXT",cursor.getString(0));
                String a = cursor.getString(0).toString();
                content.setText(a);
            }
        } catch (SQLiteException e) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calender_dialog_submit:
                Log.e("CHECK_",DB_NAME);
                try {
                    SQLiteDatabase db = dbManager.getWritableDatabase();
                    db.execSQL("CREATE TABLE if not exists '" + DB_NAME + "' (content TEXT);");
                    ContentValues values =new ContentValues();
                    values.put("content",content.getText().toString());
                    //db.execSQL("delete from "+DB_NAME);
                    db.delete("'"+DB_NAME+"'",null,null);
                    db.insert("'"+DB_NAME+"'",null,values);
                    db.close();
                }catch (SQLiteException e){
                    e.printStackTrace();
                }
                //setResult(Activity.RESULT_OK);
                sendBroadcast(new Intent(Contact.SAVE_DB));

                finish();
                break;
        }
    }
}
