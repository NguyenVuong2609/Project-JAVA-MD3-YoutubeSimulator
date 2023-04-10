package view.user;

import config.ColorConsole;
import config.Config;
import controller.ChannelController;
import model.Channel;
import model.Video;
import view.admin.ProfileView;

import java.util.List;

public class YoutubeView {
    public static YoutubeView youtubeViewInstance;
    public static YoutubeView getYoutubeViewInstance(){
        if (youtubeViewInstance == null)
            youtubeViewInstance = new YoutubeView();
        return youtubeViewInstance;
    }
    ChannelController channelController = ChannelController.getChannelControllerInstance();
    public YoutubeView() {
        System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
        System.out.printf("|" + "  1. %-84s" + "|\n", "Search a video by name: ");
        System.out.printf("|" + "  2. %-84s" + "|\n", "Search a playlist by name: ");
        System.out.printf("|" + "  3. %-84s" + "|\n", "Search a channel by name: ");
        System.out.printf("|" + "  4. %-84s" + "|\n", "Search videos by category: ");
        System.out.printf("|" + "  5. %-84s" + "|\n", "Show top 5 videos by views: ");
        System.out.printf("|" + "  6. %-84s" + "|\n", "Create a playlist: ");
        System.out.printf("|" + "  7. %-84s" + "|\n", "Delete a playlist: ");
        System.out.printf("|" + "  8. %-84s" + "|\n", "Back");
        System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
        System.out.println("Please enter your choice: ");
        int choice = Config.validateInt();
        switch (choice) {
            case 1:
                VideoView.getVideoViewInstance().showSearchVideoList();
                break;
            case 2:
                PlayListView.getPlayListViewInstance().findPlayListByName();
                break;
            case 3:
                findChannelByName();
                break;
            case 4:
                VideoView.getVideoViewInstance().showListVideoByCategoryName();
                break;
            case 5:
                VideoView.getVideoViewInstance().showTopFiveVideosByView();
                break;
            case 6:
                PlayListView.getPlayListViewInstance().createPlayList();
                break;
            case 7:
                PlayListView.getPlayListViewInstance().deletePlaylist();
                break;
            case 8:
                ProfileView.getProfileViewInstance();
                break;
            default:
                System.out.println(Config.OOA_ALERT);
                getYoutubeViewInstance();
        }
    }

    //! Tìm kiếm kênh theo tên
    public void findChannelByName() {
        System.out.println("Enter a channel name: ");
        String name = Config.validateString();
        Channel channel = channelController.findByName(name);
        if (channel != null) {
            System.out.println("Channel: " + channel.getName());
            System.out.println("Video: ");
            List<Video> listVideo = channel.getVideoList();
            if (listVideo != null){
                VideoView.getVideoViewInstance().showSearchListVideoMethod(listVideo);
            } else {
                System.out.println("This channel doesn't have any videos.");
            }
        } else {
            System.err.println("No result.");
            Config.breakTime();
            YoutubeView.getYoutubeViewInstance();
        }
    }
}
