package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListVideo implements Serializable {
    private int id;
    private String name;
    List<Video> playlist;
    User owner;

    public ListVideo() {
    }

    public ListVideo(int id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.playlist = new ArrayList<>();
        this.owner = owner;
    }

    public ListVideo(int id, String name, List<Video> playlist, User owner) {
        this.id = id;
        this.name = name;
        this.playlist = playlist;
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

    public List<Video> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Video> playlist) {
        this.playlist = playlist;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "ListVideo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playlist=" + playlist +
                '}';
    }
}
