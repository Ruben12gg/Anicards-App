package com.example.anicards_new;

public class AnimeHome {
    private String title;
    private String date;
    private String pic;
    private String pfp;
    private String author;
    private String collection;
    private String activity;

    public AnimeHome() {
    }


    public AnimeHome(String title, String author, String collection, String activity, String date, String pic, String pfp) {
        this.title = title;
        this.date = date;
        this.pic = pic;
        this.pfp = pfp;
        this.author = author;
        this.collection = collection;
        this.activity = activity;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String desc) {
        this.date = date;
    }

}

