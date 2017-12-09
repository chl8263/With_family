package com.example.user.with_family.netWork;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by choi on 2017-12-09.
 */

public class MainSendUdp extends Thread {
    private String ip;
    private int port;
    private String msg;
    public MainSendUdp(String msg, String ip,int port){
        this.msg = msg;
        this.ip = ip;
        this.port = port;
    }
    @Override
    public void run() {
        super.run();
        DatagramSocket socket = null;
        DatagramPacket packet = null;
        InetAddress ServerAddress = null;

        byte protocalBytes[] = new byte[21];
        protocalBytes = msg.getBytes();

        try {
            Log.e("exit", "exit send");
            ServerAddress = InetAddress.getByName(ip);
            Log.e("port", port+"");
            packet = new DatagramPacket(protocalBytes, protocalBytes.length, ServerAddress, port);
            socket = new DatagramSocket();
            socket.send(packet);
            Log.e("exit", "exit dead");

        } catch (UnknownHostException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            Log.i("send", "destory no");
        } catch (SocketException e) {
            e.printStackTrace();
            Log.i("send", "destory no");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("send", "destory no");
        }
    }
}