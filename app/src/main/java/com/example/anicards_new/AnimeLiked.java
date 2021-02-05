package com.example.anicards_new;

public class AnimeLiked {
    private String title;
    private String date;
    private String pic;

    public AnimeLiked() {
    }


    public AnimeLiked(String title, String pic, String date) {
        this.title = title;
        this.date = date;
        this.pic = pic;
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

}

