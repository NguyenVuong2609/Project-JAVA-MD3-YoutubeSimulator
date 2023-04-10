package view.user;

import config.ColorConsole;
import config.Config;
import config.YoutubeFrame;
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

    public static VideoView getVideoViewInstance() {
        if (videoViewInstance == null)
            videoViewInstance = new VideoView();
        return videoViewInstance;
    }

    VideoController videoController = VideoController.getVideoControllerInstance();
    ChannelController channelController = new ChannelController();
    UserController userController = UserController.getUserControllerInstance();
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
            if (myVideoList.size() > 0) {
                for (int i = 0; i < myVideoList.size(); i++) {
                    System.out.println(ColorConsole.GREEN_BOLD_BRIGHT + (i + 1) + ". " + myVideoList.get(i).getVideoName() + " - Views: " + myVideoList.get(i).getViews() + " - Durations: " + (myVideoList.get(i).getDurations() / 60) + " min " + (myVideoList.get(i).getDurations() % 60) + " sec" + ColorConsole.RESET);
                }
            } else {
                System.err.println("You don't have any videos!");
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

    //! Hiển thị danh sách tìm kiếm video theo tên
    public void showSearchVideoList() {
        System.out.println("Enter a video name that you want to view: ");
        String searchData = Config.validateString();
        List<Video> searchList = videoController.findListVideoByName(searchData);
        if (searchList.size() != 0) {
            showSearchListVideoMethod(searchList);
        } else {
            System.err.println("No result!");
            Config.breakTime();
            YoutubeView.getYoutubeViewInstance();
        }
    }

    public User findUserLikedVideo(User user, Video video) {
        return videoController.findUserLikedVideo(user, video);
    }

    //! Tìm kiếm người theo dõi
    public User findChannelFollower(User user, Channel channel) {
        return channelController.findChannelFollower(user, channel);
    }

    //! Like video
    public void likeVideo(Video video) {
        List<User> likedUserList = video.getLikeList();
        User user = userLogin.get(0);
        Channel channel = video.getOwner().getMyChannel();
        List<Video> listVideo = channel.getVideoList();
        User check = findUserLikedVideo(userLogin.get(0), video);
        if (check == null) {
            likedUserList.add(user);
        } else {
            likedUserList.remove(user);
        }
        video.setLikeList(likedUserList);
        for (int i = 0; i < listVideo.size(); i++) {
            if (listVideo.get(i).getId() == video.getId()) {
                listVideo.set(i, video);
                break;
            }
        }
        channel.setVideoList(listVideo);
        user.setMyChannel(channel);
        videoController.updateVideo(video);
        channelController.updateChannel(channel);
        userController.updateUser(user, 0);
        userController.updateUserLogin(user);
    }

    //! Tăng view
    public void updateView(Video video) {
        User owner = video.getOwner();
        Channel channel = owner.getMyChannel();
        List<Video> listVideo = channel.getVideoList();
        int views = video.getViews();
        views++;
        video.setViews(views);
        for (int i = 0; i < listVideo.size(); i++) {
            if (listVideo.get(i).getId() == video.getId()) {
                listVideo.set(i, video);
                break;
            }
        }
        channel.setVideoList(listVideo);
        owner.setMyChannel(channel);
        videoController.updateVideo(video);
        channelController.updateChannel(channel);
        userController.updateUser(owner, 0);
        if (owner.getId() == userLogin.get(0).getId())
            userController.updateUserLogin(owner);
    }

    //! Hiển thị danh sách video theo category
    public void showListVideoByCategoryName() {
        System.out.println("Enter a category's name: ");
        String name = Config.validateString();
        if (categoryController.existByName(name)) {
            List<Video> videoList = categoryController.showListVideoByCategoryName(name);
            if (videoList.size() > 0) {
                showSearchListVideoMethod(videoList);
            } else {
                System.err.println("This category doesn't have any videos.");
                Config.breakTime();
                YoutubeView.getYoutubeViewInstance();
            }
        } else {
            System.out.println("This category doesn't exist.");
            Config.breakTime();
            YoutubeView.getYoutubeViewInstance();
        }
    }

    //! In ra danh sách video
    public void showSearchListVideoMethod(List<Video> searchList) {
        for (Video video : searchList) {
            System.out.printf(ColorConsole.YELLOW_BOLD_BRIGHT + "ID: %s - Name: %s  - Category: %s - Views: %s - Duration: %s min %s sec - Likes: %s \n" + ColorConsole.RESET, video.getId(), video.getVideoName(), video.getCategory(), video.getViews(), video.getDurations() / 60, video.getDurations() % 60, video.getLikeList().size());
        }
        System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "Found " + searchList.size() + (searchList.size() == 1 ? " result" : " results") + ColorConsole.RESET);
        for (int i = 0; i < searchList.size(); i++) {
            Video video = searchList.get(i);
            System.out.printf("%d. ID:%s - %s - Durations: %s min %s sec - Views: %d - Owner: %s \n", (i + 1), video.getId(), video.getVideoName(), video.getDurations() / 60, video.getDurations() % 60, video.getViews(), video.getOwner().getName());
        }
        int index;
        while (true) {
            System.out.println("Enter a video's index that you want to view: ");
            index = Config.validateInt();
            if (index > 0 && index <= searchList.size())
                break;
            System.out.println(Config.OOA_ALERT);
        }
        YoutubeFrame.getYoutubeViewInstance().showVideoFrame(searchList.get(index - 1));
        YoutubeFrame.getYoutubeViewInstance().actionMenu(searchList.get(index - 1));
    }

    //! Hiển thị top 5 video nhiều view nhất
    public void showTopFiveVideosByView() {
        System.out.println("Top 5 videos by view: ");
        List<Video> sortList = videoController.sortVideoByView();
        List<Video> topFiveList = new ArrayList<>();
        if (sortList.size() > 5) {
            for (int i = 0; i < 5; i++) {
                topFiveList.add(sortList.get(i));
            }
            showSearchListVideoMethod(topFiveList);
        } else if (sortList.size() > 0) {
            showSearchListVideoMethod(sortList);
        } else {
            System.err.println("No result!");
            Config.breakTime();
            YoutubeView.getYoutubeViewInstance();
        }
    }

    //! Follow channel
    public void followChannel(Channel channel) {
        User user = userLogin.get(0);
        if (channel.getOwner().getId() != user.getId()) {
            List<User> followerList = channel.getFollowerList();
            User owner = channel.getOwner();
            if (user.getMyChannel() == null) {
                followerList.add(user);
            } else {
                boolean check = true;
                for (int i = 0; i < followerList.size(); i++) {
                    if (followerList.get(i).getId() == user.getId()) {
                        followerList.remove(user);
                        check = false;
                        break;
                    }
                }
                if (check) {
                    followerList.add(user);
                }
            }
            channel.setFollowerList(followerList);
            owner.setMyChannel(channel);
            channelController.updateChannel(channel);
            userController.updateUser(owner, 0);
            userController.updateUserLogin(user);
            System.out.println(Config.SUCCESS_ALERT);
        } else {
            System.err.println("You can't follow your channel!");
        }
    }
}
