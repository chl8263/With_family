package com.example.user.with_family.util;

/**
 * Created by 안탄 on 2017-11-19.
 */

public class room_UserDAO {
    private String id;
    private String pw;
    private String bir;
    private String name;
    private String nick;
    private String userimg;


    public room_UserDAO(){

    }

    public room_UserDAO(String id, String pw, String bir, String name, String nick, String userimg){
        this.id = id;
        this.pw = pw;
        this.bir = bir;
        this.name = name;
        this.nick = nick;
        this.userimg = userimg;
    }
    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
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
