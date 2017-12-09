package com.example.user.with_family.util;

/**
 * Created by 안탄 on 2017-11-19.
 */

public class UserDAO {
    private String id;
    private String pw;
    private String bir;
    private String name;
    private String nick;
    private String friend1;
    private String friend2;
    private String friend3;
    private String friend4;
    private String userimg;
    private String room_name;
    private String check;
    private String temp_room;
    private String invite_id;
    private String ip;
    private String mainsendport;
    private String mainrecvport;
    private String soundsendport;
    private String soundrecvport;



    public UserDAO(){

    }

    public UserDAO(String id, String pw, String bir, String name, String nick, String friend1, String friend2, String friend3, String friend4,
                   String userimg, String room_name, String ip, String mainsendport, String mainrecvport, String soundsendport, String soundrecvport){
        this.id = id;
        this.pw = pw;
        this.bir = bir;
        this.name = name;
        this.nick = nick;
        this.friend1 = friend1;
        this.friend2 = friend2;
        this.friend3 = friend3;
        this.friend4 = friend4;
        this.userimg = userimg;
        this.room_name = room_name;
        this.ip = ip;
        this.mainsendport = mainsendport;
        this.mainrecvport = mainrecvport;
        this.soundsendport = soundsendport;
        this.soundrecvport = soundrecvport;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMainsendport() {
        return mainsendport;
    }

    public void setMainsendport(String mainsendport) {
        this.mainsendport = mainsendport;
    }

    public String getMainrecvport() {
        return mainrecvport;
    }

    public void setMainrecvport(String mainrecvport) {
        this.mainrecvport = mainrecvport;
    }

    public String getSoundsendport() {
        return soundsendport;
    }

    public void setSoundsendport(String soundsendport) {
        this.soundsendport = soundsendport;
    }

    public String getSoundrecvport() {
        return soundrecvport;
    }

    public void setSoundrecvport(String soundrecvport) {
        this.soundrecvport = soundrecvport;
    }

    public String getInvite_id() {
        return invite_id;
    }

    public void setInvite_id(String invite_id) {
        this.invite_id = invite_id;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getTemp_room() {
        return temp_room;
    }

    public void setTemp_room(String temp_room) {
        this.temp_room = temp_room;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }
    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getFriend3() {
        return friend3;
    }

    public void setFriend3(String friend3) {
        this.friend3 = friend3;
    }

    public String getFriend4() {
        return friend4;
    }

    public void setFriend4(String friend4) {
        this.friend4 = friend4;
    }


    public String getFriend1() {
        return friend1;
    }

    public void setFriend1(String friend1) {
        this.friend1 = friend1;
    }

    public String getFriend2() {
        return friend2;
    }

    public void setFriend2(String friend2) {
        this.friend2 = friend2;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getBir() {
        return bir;
    }

    public void setBir(String bir) {
        this.bir = bir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
