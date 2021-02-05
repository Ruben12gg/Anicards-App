package com.example.anicards_new;

public class AnimeToWatch {
    private String title;
    private String date;
    private String picUrl;
    private String rating;

    public AnimeToWatch() {
    }


    public AnimeToWatch(String title, String picUrl, String date) {
        this.title = title;
        this.date = date;
        this.picUrl= picUrl;
        this.rating = rating;
    }

    public void setPic(String pic) {
        this.picUrl = pic;
    }

    public String getPic() {
        return picUrl;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

