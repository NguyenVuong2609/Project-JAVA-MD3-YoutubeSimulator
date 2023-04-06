package model;

import java.util.ArrayList;
import java.util.List;

public class Video {
    private int id;
    private String videoName;
    User owner;
    Category category;
    private List<User> likeList;
    private int views;
    private int durations;
    public Video() {
    }

    public Video(int id, String videoName, User owner, Category category, List<User> likeList, int views, int durations) {
        this.id = id;
        this.videoName = videoName;
        this.owner = owner;
        this.category = category;
        this.likeList = likeList;
        this.views = views;
        this.durations = durations;
    }

    public Video(int id, String videoName, User owner, Category category, int durations) {
        this.id = id;
        this.videoName = videoName;
        this.owner = owner;
        this.category = category;
        this.durations = durations;
        this.views = 0;
        this.likeList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<User> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<User> likeList) {
        this.likeList = likeList;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDurations() {
        return durations;
    }

    public void setDurations(int durations) {
        this.durations = durations;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", videoName='" + videoName + '\'' +
                ", owner=" + owner +
                ", category=" + category +
                ", likeList=" + likeList +
                ", views=" + views +
                ", durations=" + durations +
                '}';
    }
}
