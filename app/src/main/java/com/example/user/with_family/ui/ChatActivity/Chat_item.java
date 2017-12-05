package com.example.user.with_family.ui.ChatActivity;

/**
 * Created by choi on 2017-12-05.
 */

public class Chat_item {
    private String name;
    private String content;
    private String time;
    public Chat_item(){

    }
    public Chat_item(String name, String content, String time) {
        this.name = name;
        this.content = content;
        this.time = time;
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
