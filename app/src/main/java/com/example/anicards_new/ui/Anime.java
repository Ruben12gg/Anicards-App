package com.example.anicards_new.ui;


public class Anime {
    private String title;
    private String synopsis;
    private  String picUrl;

    public Anime(String title, String synopsis, String picUrl) {
        this.title = title;
        this.synopsis = synopsis;
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
