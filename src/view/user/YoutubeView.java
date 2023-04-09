package view.user;

import config.ColorConsole;
import config.Config;
import view.admin.ProfileView;

import java.awt.*;

public class YoutubeView {
    public static YoutubeView youtubeViewInstance;
    public static YoutubeView getYoutubeViewInstance(){
        if (youtubeViewInstance == null)
            youtubeViewInstance = new YoutubeView();
        return youtubeViewInstance;
    }
    public YoutubeView() {
        System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
        System.out.printf("|" + "  1. %-84s" + "|\n", "Search a video by name: ");
        System.out.printf("|" + "  2. %-84s" + "|\n", "Search a playlist by name: ");
        System.out.printf("|" + "  3. %-84s" + "|\n", "Search a channel by name: ");
        System.out.printf("|" + "  4. %-84s" + "|\n", "Search videos by category: ");
        System.out.printf("|" + "  5. %-84s" + "|\n", "Show top 5 videos by views: ");
        System.out.printf("|" + "  6. %-84s" + "|\n", "Show top 5 channel by followers: ");
        System.out.printf("|" + "  7. %-84s" + "|\n", "Create a playlist: ");
        System.out.printf("|" + "  8. %-84s" + "|\n", "Delete a playlist: ");
        System.out.printf("|" + "  9. %-84s" + "|\n", "Back");
        System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
        System.out.println("Please enter your choice: ");
        int choice = Config.validateInt();
        switch (choice) {
            case 1:
                VideoView.getVideoViewInstance().showSearchVideoList();
                break;
            case 2:
                break;
            case 7:
                PlayListView.getPlayListViewInstance().createPlayList();
                break;
            case 8:
                PlayListView.getPlayListViewInstance().deletePlaylist();
                break;
            case 9:
                ProfileView.getProfileViewInstance();
                break;
            default:
                System.out.println(Config.OOA_ALERT);
                getYoutubeViewInstance();
        }
    }
}
