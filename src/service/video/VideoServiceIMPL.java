package service.video;

import config.Config;
import model.Channel;
import model.Video;

import java.util.List;

public class VideoServiceIMPL implements IVideoService {
    List<Video> videoList = new Config<Video>().readFromFile(Config.PATH_VIDEO);
    @Override
    public List<Video> findAll() {
        return videoList;
    }

    @Override
    public void save(Video video) {
        if (findById(video.getId()) == null) {
            videoList.add(video);
        } else {
            int index = videoList.indexOf(findById(video.getId()));
            videoList.set(index, video);
        }
        new Config<Video>().writeToFile(Config.PATH_VIDEO, videoList);
    }

    @Override
    public Video findById(int id) {
        for (int i = 0; i < videoList.size(); i++) {
            if (videoList.get(i).getId() == id) {
                return videoList.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        int index = videoList.indexOf(findById(id));
        videoList.remove(index);
        new Config<Video>().writeToFile(Config.PATH_VIDEO, videoList);
    }
}
