package controller;

import model.Video;
import service.video.IVideoService;
import service.video.VideoServiceIMPL;

import java.util.List;

public class VideoController {
    IVideoService videoService = new VideoServiceIMPL();

    public List<Video> getVideoList() {
        return videoService.findAll();
    }
    public void createVideo(Video video){
        videoService.save(video);
    }
    public Video findVideoById(int id){
        return videoService.findById(id);
    }
    public void deleteVideo(int id){
        videoService.deleteById(id);
    }
}
