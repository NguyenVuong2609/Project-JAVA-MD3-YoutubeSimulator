package controller;

import model.User;
import model.Video;
import service.video.IVideoService;
import service.video.VideoServiceIMPL;

import java.util.List;

public class VideoController {
    public static VideoController videoControllerInstance;
    public static VideoController getVideoControllerInstance(){
        if (videoControllerInstance == null)
            videoControllerInstance = new VideoController();
        return videoControllerInstance;
    }
    IVideoService videoService = new VideoServiceIMPL();

    public List<Video> getVideoList() {
        return videoService.findAll();
    }
    public void createVideo(Video video){
        videoService.save(video);
    }
    public void updateVideo(Video video){
        videoService.save(video);
    }
    public Video findVideoById(int id){
        return videoService.findById(id);
    }
    public void deleteVideo(int id){
        videoService.deleteById(id);
    }
    public List<Video> findListVideoByName(String name){
        return videoService.findByName(name);
    }
    public User findUserLikedVideo(User user, Video video){
        return videoService.findUserLikedVideo(user,video);
    }
}
