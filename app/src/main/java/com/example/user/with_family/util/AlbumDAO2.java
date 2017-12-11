package com.example.user.with_family.util;

/**
 * Created by 안탄 on 2017-12-11.
 */

public class AlbumDAO2 {
    String date;
    String room_name;

    public AlbumDAO2(){

    }

    public AlbumDAO2(String date, String room_name){
        this.date = date;
        this.room_name = room_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }
}
