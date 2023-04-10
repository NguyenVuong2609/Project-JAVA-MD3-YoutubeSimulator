package config;

import model.Channel;
import model.User;
import model.Video;
import view.user.MyChannelView;
import view.user.PlayListView;
import view.user.VideoView;
import view.user.YoutubeView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class YoutubeFrame {
    public static YoutubeFrame youtubeFrameInstance;

    public static YoutubeFrame getYoutubeViewInstance() {
        if (youtubeFrameInstance == null)
            youtubeFrameInstance = new YoutubeFrame();
        return youtubeFrameInstance;
    }

    List<User> users = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);

    public void showVideoFrame(Video video) {
        String videoName = video.getVideoName();
        int size = videoName.length();
        int blank = 98 - size;
        User user = users.get(0);
        Channel channel = video.getOwner().getMyChannel();
        User checkLike = VideoView.getVideoViewInstance().findUserLikedVideo(user, video);
        User checkSubscriber = VideoView.getVideoViewInstance().findChannelFollower(user, channel);
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.printf("|%45s%-53s", " ", "YOUTUBE");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.print("|");
        for (int i = 0; i < blank / 2; i++) {
            System.out.print(" ");
        }
        System.out.print(videoName);
        for (int i = 0; i < blank / 2; i++) {
            System.out.print(" ");
        }
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("|                                                                                                  |");
        System.out.println("+--------------------------------------------------------------------------------------------------+");
        System.out.printf(" 1. ▶ Play | 2. %-7s | 3. Add to playlist | 4. %-8s | Views: %-3s | Channel: %-20s \n", (checkLike == null ? "\uD83D\uDE0D Like" : "\uD83D\uDE16 Dislike"), (checkSubscriber == null ? "Follow" : "Unfollow"), video.getViews(), video.getOwner().getMyChannel().getName());
    }

    public void progressBar(Video video) {
        int i = 0;
        int j;
        try {
            while (i <= 100) {
                System.out.println("\\033[H\\033[2J");
                showVideoFrame(video);
                System.out.println();
                j = 0;
                for (j = 0; j < i; j++) {
                    System.out.print('■');
                }
                for (; j <= 100; j++) System.out.print(' ');
                System.out.print(i + "% - Durations: " + (video.getDurations() / 60) + " min " + (video.getDurations() % 60) + " sec");
                TimeUnit.SECONDS.sleep(1);
                i += 10;
                System.out.println();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void actionMenu(Video video) {
        boolean flag = true;
        while (flag) {
            System.out.println("1. Play video");
            System.out.println("2. Like/Unlike this video");
            System.out.println("3. Add this video to playlist");
            System.out.println("4. Follow/Unfollow this channel");
            System.out.println("5. Back");
            System.out.println("Enter an action: ");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    progressBar(video);
                    VideoView.getVideoViewInstance().updateView(video);
                    showVideoFrame(video);
                    actionMenu(video);
                    flag = false;
                    break;
                case 2:
                    VideoView.getVideoViewInstance().likeVideo(video);
                    showVideoFrame(video);
                    actionMenu(video);
                    flag = false;
                    break;
                case 3:
                    PlayListView.getPlayListViewInstance().addVideoToPlaylistMenu(video);
                    showVideoFrame(video);
                    actionMenu(video);
                    flag = false;
                    break;
                case 5:
                    YoutubeView.getYoutubeViewInstance();
                    flag = false;
                    break;
                default:
                    System.out.println(Config.OOA_ALERT);
            }
        }
    }
}
