package com.example.user.with_family.util;

/**
 * Created by 안탄 on 2017-11-22.
 */

public class Contacts {
    String url;
    int backurl;
    String name;
    String stats;
    String id;

   // String userimg;

    public Contacts(){

    }

    public Contacts(String url, int backurl, String name, String stats, String id){
        this.url = url;
        this.backurl = backurl;
        this.name = name;
        this.stats = stats;
        this.id = id;
        //this.userimg = userimg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBackurl() {
        return backurl;
    }

    public void setBackurl(int backurl) {
        this.backurl = backurl;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
