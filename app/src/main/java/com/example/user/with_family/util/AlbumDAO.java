package com.example.user.with_family.util;

/**
 * Created by 안탄 on 2017-12-07.
 */

public class AlbumDAO {
    private String date;
    private String img_ref;

    public AlbumDAO(){

    }

    public AlbumDAO(String date, String img_ref){
        this.date = date;
        this.img_ref = img_ref;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg_ref() {
        return img_ref;
    }

    public void setImg_ref(String img_ref) {
        this.img_ref = img_ref;
    }
}
