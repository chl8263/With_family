package com.example.user.with_family.netWork;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.user.with_family.ui.Streaming.VoiceActivity;
import com.example.user.with_family.util.Contact;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by choi on 2017-12-09.
 */

public class MainServiceRecvUDP extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service", "시작중");
        MOVE_RECV move_recv = new MOVE_RECV();
        move_recv.start();
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

            }
        }
    };

    private class MOVE_RECV extends Thread {
        private DatagramPacket packet = null;
        private DatagramSocket socket = null;
        byte[] data = new byte[21];

        public MOVE_RECV() {

        }

        @Override
        public void run() {
            super.run();
            try {
                while (true) {
                    socket = new DatagramSocket(Integer.parseInt(Contact.MyMainrecvPort));
                    Log.e("MOVE", "Move Thread Wait");
                    packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);
                    Log.e("MOVE_RECV", "good");
                    String msg = new String(packet.getData());
                    Log.e("wwwwwwwwwwwwwww", msg);
                    String splite[] = msg.split("/");
                    if (splite[0].equals("c")) {
                        Intent intent = new Intent(getApplicationContext(), VoiceActivity.class);
                        intent.putExtra("other", "RECV/" + splite[1] + "/");
                        startActivity(intent);

                    }else if(splite[0].equals("m")){

                    }else if(splite[0].equals("s")){
                        Intent intent = new Intent(Contact.voice_success);
                        sendBroadcast(intent);
                    }
                    socket.close();
                    Log.e("MOVE_RECV", "끝!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



