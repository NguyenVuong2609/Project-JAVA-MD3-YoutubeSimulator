package view.user;

import config.ColorConsole;
import config.Config;

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
        System.out.printf("|" + "  6. %-84s" + "|\n", "Show top 5 channel by views: ");
        System.out.printf("|" + "  7. %-84s" + "|\n", "Back");
        System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
        System.out.println("Please enter your choice: ");
        int choice = Config.validateInt();
    }
}
