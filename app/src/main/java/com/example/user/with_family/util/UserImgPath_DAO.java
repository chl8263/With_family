package com.example.user.with_family.util;

/**
 * Created by choi on 2017-12-10.
 */

public class UserImgPath_DAO {
    private String userimg;
    public UserImgPath_DAO(){}
    public UserImgPath_DAO(String userimg) {
        this.userimg = userimg;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }
}
