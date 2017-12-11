package com.example.user.with_family.ui.f_chat;

/**
 * Created by choi on 2017-12-02.
 */

public class ChatRoom_item {
    private String name;
    private String content;
    private String time;
    private String imgpath;

    public ChatRoom_item(String name, String content, String time,String imgpath) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.imgpath = imgpath;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
