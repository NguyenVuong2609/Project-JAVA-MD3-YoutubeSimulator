package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Channel implements Serializable {
    private int id;
    private String name;
    List<Video> videoList;
    boolean earnMoneyStatus;
    List<User> followerList;
    User owner;

    public Channel() {
    }

    public Channel(int id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.videoList = new ArrayList<>();
        this.earnMoneyStatus = false;
        this.followerList = new ArrayList<>();
        this.owner = owner;
    }

    public Channel(int id, String name, List<Video> videoList, boolean earnMoneyStatus, List<User> followerList, User owner) {
        this.id = id;
        this.name = name;
        this.videoList = videoList;
        this.earnMoneyStatus = earnMoneyStatus;
        this.followerList = followerList;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public boolean isEarnMoneyStatus() {
        return earnMoneyStatus;
    }

    public void setEarnMoneyStatus(boolean earnMoneyStatus) {
        this.earnMoneyStatus = earnMoneyStatus;
    }

    public List<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<User> followerList) {
        this.followerList = followerList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", videoList=" + videoList +
                ", earnMoneyStatus=" + earnMoneyStatus +
                ", followerList=" + followerList +
                '}';
    }
}
