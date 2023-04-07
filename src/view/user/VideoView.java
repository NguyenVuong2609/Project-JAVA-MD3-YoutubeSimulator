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
    public static VideoView videoViewInstance;
    public static VideoView getVideoViewInstance(){
        if (videoViewInstance == null)
            videoViewInstance = new VideoView();
        return videoViewInstance;
    }
    VideoController videoController = new VideoController();
    ChannelController channelController = new ChannelController();
    UserController userController = new UserController();
    CategoryController categoryController = new CategoryController();
    List<Video> videoList = videoController.getVideoList();
    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
    List<Category> categoryList = categoryController.getCategoryList();

    //! Tạo video mới
    public void createVideo() {
        User user = userLogin.get(0);
        if (user.getMyChannel() != null) {
            Category category = null;
            int id;
            if (videoList.size() == 0) {
                id = 1;
            } else {
                id = videoList.get(videoList.size() - 1).getId() + 1;
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
                if (categoryId == 0) break;
                if (categoryController.findCategoryById(categoryId) != null) {
                    category = categoryController.findCategoryById(categoryId);
                    break;
                }
                System.out.println(Config.ID_NOT_EXIST);
            }
            do {
                System.out.println("Enter video's durations(seconds): ");
                durations = Config.validateInt();
                if (durations <= 0) System.out.println(Config.FORMAT_ALERT);
            } while (durations <= 0);
            Channel myChannel = user.getMyChannel();
            List<Video> myListVideo = myChannel.getVideoList();
            Video video = new Video(id, videoName, user, category, durations);
            myListVideo.add(video);
            myChannel.setVideoList(myListVideo);
            user.setMyChannel(myChannel);
            videoController.createVideo(video);
            channelController.updateChannel(myChannel);
            userController.updateUser(user, 0);
            userController.updateUserLogin(user);
            System.out.println(Config.SUCCESS_ALERT);
        } else {
            System.out.println(Config.CNE_ALERT);
        }
        Config.breakTime();
        MyChannelView.getMyChannelViewInstance();
    }

    //! Hiển thị danh sách video của tôi
    public void showAllMyVideo() {
        User user = userLogin.get(0);
        if (user.getMyChannel() != null) {
            List<Video> myVideoList = user.getMyChannel().getVideoList();
            for (int i = 0; i < myVideoList.size(); i++) {
                System.out.println(ColorConsole.GREEN_BOLD_BRIGHT + (i + 1) + ". " + myVideoList.get(i).getVideoName() + " - Views: " + myVideoList.get(i).getViews() + " - Durations: " + (myVideoList.get(i).getDurations() / 60) + " min " + (myVideoList.get(i).getDurations() % 60) + " sec" + ColorConsole.RESET);
            }
        }
        while (true) {
            System.out.println("Type BACK to return Menu: ");
            String back = Config.scanner().nextLine();
            if (back.equalsIgnoreCase("back")) {
                Config.breakTime();
                MyChannelView.getMyChannelViewInstance();
                break;
            }
        }
    }

    //! Xóa video
    public void deleteVideo() {
        User user = userLogin.get(0);
        Channel myChannel = userLogin.get(0).getMyChannel();
        if (myChannel != null) {
            if (myChannel.getVideoList().size() != 0) {
                System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "-------------------------------- My Video --------------------------------" + ColorConsole.RESET);
                for (Video video : myChannel.getVideoList()) {
                    System.out.printf(ColorConsole.YELLOW_BOLD_BRIGHT + "ID: %s - Name: %s  - Category: %s - Views: %s - Duration: %s min %s sec - Likes: %s \n" + ColorConsole.RESET, video.getId(), video.getVideoName(), video.getCategory(), video.getViews(), video.getDurations() / 60, video.getDurations() % 60, video.getLikeList().size());
                }
                Video videoDelete = null;
                int id;
                while (true) {
                    System.out.println("Enter an video ID that you want to delete: ");
                    id = Config.validateInt();
                    for (int i = 0; i < myChannel.getVideoList().size(); i++) {
                        if (myChannel.getVideoList().get(i).getId() == id) {
                            videoDelete = myChannel.getVideoList().get(i);
                            break;
                        }
                    }
                    if (videoDelete != null)
                        break;
                    System.out.println(Config.ID_NOT_EXIST);
                }
                while (true) {
                    System.out.println("Are you sure to delete this video? Type Y/N");
                    String choice = Config.validateString();
                    if (choice.equalsIgnoreCase("y")) {
                        List<Video> myVideoList = myChannel.getVideoList();
                        myVideoList.remove(videoDelete);
                        myChannel.setVideoList(myVideoList);
                        user.setMyChannel(myChannel);
                        videoController.deleteVideo(id);
                        channelController.updateChannel(myChannel);
                        userController.updateUserLogin(user);
                        userController.updateUser(user, 0);
                        System.out.println(Config.SUCCESS_ALERT);
                        Config.breakTime();
                        MyChannelView.getMyChannelViewInstance();
                        break;
                    }
                    if (choice.equalsIgnoreCase("n")) {
                        Config.breakTime();
                        MyChannelView.getMyChannelViewInstance();
                        break;
                    }
                }
            } else {
                System.err.println("You don't have any video to delete!");
                Config.breakTime();
                MyChannelView.getMyChannelViewInstance();
            }
        } else {
            System.err.println(Config.CNE_ALERT);
            Config.breakTime();
            MyChannelView.getMyChannelViewInstance();
        }
    }

    //! Sửa video
    public void editMyVideo() {
        User user = userLogin.get(0);
        Channel myChannel = userLogin.get(0).getMyChannel();
        if (myChannel != null) {
            if (myChannel.getVideoList().size() != 0) {
                System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "-------------------------------- My Video --------------------------------" + ColorConsole.RESET);
                for (Video video : myChannel.getVideoList()) {
                    System.out.printf(ColorConsole.YELLOW_BOLD_BRIGHT + "ID: %s - Name: %s  - Category: %s - Views: %s - Duration: %s min %s sec - Likes: %s \n" + ColorConsole.RESET, video.getId(), video.getVideoName(), video.getCategory(), video.getViews(), video.getDurations() / 60, video.getDurations() % 60, video.getLikeList().size());
                }
                Video videoEdit = null;
                int id;
                int index = 0;
                while (true) {
                    System.out.println("Enter an video ID that you want to delete: ");
                    id = Config.validateInt();
                    for (int i = 0; i < myChannel.getVideoList().size(); i++) {
                        if (myChannel.getVideoList().get(i).getId() == id) {
                            videoEdit = myChannel.getVideoList().get(i);
                            index = i;
                            break;
                        }
                    }
                    if (videoEdit != null)
                        break;
                    System.out.println(Config.ID_NOT_EXIST);
                }
                System.out.println("Enter a new video name or press Enter to skip: ");
                String newVideoName = Config.scanner().nextLine();
                newVideoName = newVideoName.equals("") ? videoEdit.getVideoName() : newVideoName;
                List<Video> myVideoList = myChannel.getVideoList();
                videoEdit.setVideoName(newVideoName);
                myVideoList.set(index, videoEdit);
                myChannel.setVideoList(myVideoList);
                user.setMyChannel(myChannel);
                videoController.updateVideo(videoEdit);
                channelController.updateChannel(myChannel);
                userController.updateUserLogin(user);
                userController.updateUser(user, 0);
                System.out.println(Config.SUCCESS_ALERT);
                Config.breakTime();
                MyChannelView.getMyChannelViewInstance();
            } else {
                System.err.println("You don't have any video to delete!");
                Config.breakTime();
                MyChannelView.getMyChannelViewInstance();
            }
        } else {
            System.err.println(Config.CNE_ALERT);
            Config.breakTime();
            MyChannelView.getMyChannelViewInstance();
        }
    }
}
