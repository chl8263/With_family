package com.example.user.with_family.ui.Streaming;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.with_family.R;

/**
 * Created by choi on 2017-12-09.
 */

public class VoiceActivity extends AppCompatActivity{
    private ImageView profile;
    private TextView status;
    private ImageView callstatus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        init();

        MediaPlayer player = MediaPlayer.create(getApplicationContext(),R.raw.voice);
        player.start();
    }
    private void init(){
        profile = (ImageView)findViewById(R.id.profile);
        status = (TextView)findViewById(R.id.status);
        callstatus = (ImageView)findViewById(R.id.callstatus);


    }
}
