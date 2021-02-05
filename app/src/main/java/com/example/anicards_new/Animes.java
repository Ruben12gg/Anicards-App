package com.example.anicards_new;

public class Animes {
    private String title;
    private String desc;
    private String pic;
    private String rating;
    private String ageRate;
    private String launchDate;
    private String endDate;
    private String episodeLength;

    public Animes() {
    }


    public Animes(String title, String desc, String pic, String rating, String ageRate, String launchDate, String endDate, String episodeLength) {
        this.title = title;
        this.desc = desc;
        this.pic = pic;
        this.rating = rating;
        this.ageRate = ageRate;
        this.launchDate = launchDate;
        this.endDate = endDate;
        this.episodeLength = episodeLength;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAgeRate() {
        return ageRate;
    }

    public void setAgeRate(String ageRate) {
        this.ageRate = ageRate;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(String episodeLength) {
        this.episodeLength = episodeLength;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
