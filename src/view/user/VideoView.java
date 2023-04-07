package view.user;

import config.ColorConsole;
import config.Config;
import controller.CategoryController;
import controller.ChannelController;
import controller.UserController;
import controller.VideoController;
import model.Category;
import model.Channel;
import model.User;
import model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoView {
    VideoController videoController = new VideoController();
    ChannelController channelController = new ChannelController();
    UserController userController = new UserController();
    CategoryController categoryController = new CategoryController();
    List<Video> videoList = videoController.getVideoList();
    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
    List<Category> categoryList = categoryController.getCategoryList();

    public void createVideo() {
        User user = userLogin.get(0);
        if (user.getMyChannel() != null) {
            Category category = null;
            int id;
            if (categoryList.size() == 0) {
                id = 1;
            } else {
                id = categoryList.get(categoryList.size() - 1).getId() + 1;
            }
            String videoName;
            int durations;
            System.out.println("---------------- CREATE NEW VIDEO ----------------");
            System.out.println("Enter video's name:");
            videoName = Config.validateString();
            System.out.println("Category List");
            for (Category cat : categoryList) {
                System.out.printf("ID: %d - Name: %s\n", cat.getId(), cat.getName());
            }
            while (true) {
                System.out.println("Choose a category ID for your video or type \"0\" to skip: ");
                int categoryId = Config.validateInt();
                if (categoryId == 0)
                    break;
                if (categoryController.findCategoryById(categoryId) != null) {
                    category = categoryController.findCategoryById(categoryId);
                    break;
                }
                System.out.println(Config.ID_NOT_EXIST);
            }
            do {
                System.out.println("Enter video's durations(seconds): ");
                durations = Config.validateInt();
                if (durations <= 0)
                    System.out.println(Config.FORMAT_ALERT);
            } while (durations <= 0);
            Channel myChannel = channelController.findChannelById(user.getMyChannel().getId());
            List<Video> myListVideo = myChannel.getVideoList();
            Video video = new Video(id, videoName, user, category, durations);
            myListVideo.add(video);
            myChannel.setVideoList(myListVideo);
            user.setMyChannel(myChannel);
            videoController.createVideo(video);
            channelController.updateChannel(myChannel);
            userController.updateUser(user,0);
        } else {
            System.out.println(Config.CNE_ALERT);
            Config.breakTime();
            MyChannelView.getMyChannelViewInstance();
        }
    }
    public void deleteVideo() {
        Channel myChannel = userLogin.get(0).getMyChannel();
        if (myChannel != null) {
            System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "---------------- My Video ----------------" + ColorConsole.RESET);
            for (Video video : myChannel.getVideoList()) {
                System.out.printf(ColorConsole.YELLOW_BOLD_BRIGHT + "ID: %s - Name: %s  - Category: %s - Views: %s - Duration: %s - Likes: %s" + ColorConsole.RESET, video.getId(), video.getVideoName(), video.getCategory(), video.getViews(), video.getDurations(), video.getLikeList().size());
            }
            System.out.println("Enter an video ID that you want to delete: ");
            int id = Config.validateInt();

        } else {
            System.err.println(Config.CNE_ALERT);
            Config.breakTime();
            MyChannelView.getMyChannelViewInstance();
        }
    }
}
