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
}
