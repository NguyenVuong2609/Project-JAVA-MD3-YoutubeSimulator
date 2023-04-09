package service.video;

import model.User;
import model.Video;
import service.IGenericService;

import java.util.List;

public interface IVideoService extends IGenericService<Video> {
    List<Video> findByName(String name);
    User findUserLikedVideo(User user, Video video);
}
