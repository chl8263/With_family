package com.example.user.with_family.netWork;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

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
        byte[] data = new byte[4];

        public MOVE_RECV() {

        }

        @Override
        public void run() {
            super.run();

            try {
                while (true) {
                    socket = new DatagramSocket(9006);
                    Log.e("MOVE", "MAKE");
                    Log.e("MOVE", "Move Thread Wait");
                    packet = new DatagramPacket(data, data.length);
                    socket.receive(packet);
                    Log.e("MOVE_RECV", "good");
                    String msg = new String(packet.getData());
                    Log.e("MOVE_RECEV", msg);
                    String splite [] =msg.split("/");
                    if(splite[0].equals("c")){

                    }else if(splite[0].equals("m")){

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



