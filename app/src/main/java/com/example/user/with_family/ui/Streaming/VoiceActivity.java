package com.example.user.with_family.ui.Streaming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.with_family.R;
import com.example.user.with_family.netWork.MainSendUdp;
import com.example.user.with_family.netWork.Sound_recv;
import com.example.user.with_family.netWork.Sound_send;
import com.example.user.with_family.util.Contact;
import com.example.user.with_family.util.SocketDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by choi on 2017-12-09.
 */

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView profile;
    private ImageView profileimg;
    private TextView status;
    private ImageView callstatus;
    private String getName;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myMainRoom = firebaseDatabase.getReference("register").child("r_room").child("룸").child("ip_tree");
    private DatabaseReference ip_tree;
    private String ip;
    private String main_recv_port;
    private String sound_send_port;
    private String sound_recv_port;
    private int flag;

    private Sound_send sound_send;
    private Sound_recv sound_recv;
    private MediaPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        init();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contact.voice_success);
        intentFilter.addAction(Contact.voice_exit);
        registerReceiver(receiver, intentFilter);

        player = MediaPlayer.create(getApplicationContext(), R.raw.voice);
        player.setLooping(true);
        player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
       /* if (player != null) {
            player.stop();
            player.release();
        }*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
       /* if (player != null) {
            player.stop();
            player.release();
        }*/

    }

    private void init() {
        profile = (TextView) findViewById(R.id.profile);
        profileimg = (ImageView) findViewById(R.id.profileImg);
        status = (TextView) findViewById(R.id.status);
        callstatus = (ImageView) findViewById(R.id.callstatus);
        callstatus.setOnClickListener(this);
        Intent intent = getIntent();
        getName = intent.getStringExtra("other");
        String[] split = getName.split("/");
        if (split[0].equals("SEND")) {
            profile.setText(split[1]);
            ip_tree = myMainRoom.child(split[1]);

            status.setText("연결중....");
            callstatus.setImageResource(R.drawable.voice_red);
            flag = 0;
            Log.e("ippppppppppppp", "ggggggggggggggg");
            ip_tree.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SocketDAO socketDAO = dataSnapshot.getValue(SocketDAO.class);
                    Log.e("ipppppppp", socketDAO.getIp());
                    ip = socketDAO.getIp();
                    main_recv_port = socketDAO.getMainrecvport();
                    sound_send_port = socketDAO.getSoundrecvport();

                    MainSendUdp mainSendUdp = new MainSendUdp("c/" + Contact.MyName + "/", socketDAO.getIp(), Integer.parseInt(socketDAO.getMainrecvport()));
                    mainSendUdp.start();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else if (split[0].equals("RECV")) {

            profile.setText(split[1]);
            ip_tree = myMainRoom.child(split[1]);

            status.setText("전화왔습니다....");
            callstatus.setImageResource(R.drawable.voice_blue);
            flag = 1;
            Log.e("ippppppppppppp", "ggggggggggggggg");
            ip_tree.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SocketDAO socketDAO = dataSnapshot.getValue(SocketDAO.class);
                    Log.e("ipppppppp", socketDAO.getIp());
                    ip = socketDAO.getIp();
                    main_recv_port = socketDAO.getMainrecvport();
                    sound_send_port = socketDAO.getSoundrecvport();
                   /* MainSendUdp mainSendUdp = new MainSendUdp("s/" + Contact.MyName + "/", socketDAO.getIp(), Integer.parseInt(socketDAO.getMainrecvport()));
                    mainSendUdp.start();*/
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            callstatus.setImageResource(R.drawable.voice_blue);

        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callstatus:
                if (flag == 0) {
                    //사운드 소켓을 모두 종료하고 엑티비티를 닫는다.
                    sound_send.interrupt();
                    MainSendUdp mainSendUdp = new MainSendUdp("/" + Contact.MyName + "/", ip, Integer.parseInt(main_recv_port));
                    mainSendUdp.start();
                    finish();
                } else if (flag == 1) {
                    MainSendUdp mainSendUdp = new MainSendUdp("s/" + Contact.MyName + "/", ip, Integer.parseInt(main_recv_port));
                    mainSendUdp.start();
                    status.setText("연결됨!");
                    flag = 0;
                    callstatus.setImageResource(R.drawable.voice_red);

                    sound_recv = new Sound_recv(Integer.parseInt(Contact.MySoundrecvPort));
                    sound_recv.start();
                    sound_send = new Sound_send(ip, Integer.parseInt(sound_send_port));
                    sound_send.start();

                    if (player != null) {
                        player.stop();
                        player.release();
                    }
                }
                break;
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Contact.voice_success)) {
                status.setText("연결됨!");

                if (player != null) {
                        player.stop();
                        player.release();
                }

                sound_recv = new Sound_recv(Integer.parseInt(Contact.MySoundrecvPort));
                sound_recv.start();
                sound_send = new Sound_send(ip, Integer.parseInt(sound_send_port));
                sound_send.start();
            }else if (intent.getAction().equals(Contact.voice_exit)) {
                finish();
            }
        }
    };

}
