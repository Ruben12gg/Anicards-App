package com.example.anicards_new;

public class AnimeSeen {
    private String title;
    private String date;
    private String pic;
    private String rating;

    public AnimeSeen() {
    }


    public AnimeSeen(String title, String pic, String date) {
        this.title = title;
        this.date = date;
        this.pic = pic;
        this.rating = rating;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
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

