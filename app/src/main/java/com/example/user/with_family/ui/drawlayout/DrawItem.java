package com.example.user.with_family.ui.drawlayout;

import android.support.annotation.NonNull;

/**
 * Created by choi on 2017-11-25.
 */

public class DrawItem implements Comparable<DrawItem>{
    private String content;
    private String dday;
    public DrawItem(String content , String dday) {
        this.content = content;
        this.dday = dday;
    }

    public String getDday() {
        return dday;
    }

    public void setDday(String dday) {
        this.dday = dday;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(@NonNull DrawItem drawItem) {

        if(Integer.parseInt(this.dday) < Integer.parseInt(drawItem.dday)){
            return -1;
        }else{
            return 1;
        }
    }
}
