package model;

import java.util.ArrayList;
import java.util.List;

public class ListVideo {
    private int id;
    private String name;
    List<Video> playlist;

    public ListVideo() {
    }

    public ListVideo(int id, String name) {
        this.id = id;
        this.name = name;
        this.playlist = new ArrayList<>();
    }

    public ListVideo(int id, String name, List<Video> playlist) {
        this.id = id;
        this.name = name;
        this.playlist = playlist;
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

    @Override
    public String toString() {
        return "ListVideo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playlist=" + playlist +
                '}';
    }
}
