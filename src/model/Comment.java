package model;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private String content;
    User user;
    Video video;

    public Comment() {
    }

    public Comment(int id, String content, User user, Video video) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
