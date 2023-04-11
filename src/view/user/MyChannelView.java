package view.user;

import config.ColorConsole;
import config.Config;
import controller.ChannelController;
import controller.UserController;
import model.Channel;
import model.User;
import view.admin.ProfileView;


import java.util.List;

public class MyChannelView {
    public static MyChannelView MyChannelViewInstance;

    public static MyChannelView getMyChannelViewInstance() {
        if (MyChannelViewInstance == null) MyChannelViewInstance = new MyChannelView();
        return MyChannelViewInstance;
    }

    ChannelController channelController = ChannelController.getChannelControllerInstance();
    UserController userController = UserController.getUserControllerInstance();
    List<Channel> channelList = new Config<Channel>().readFromFile(Config.PATH_CHANNEL);
    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);

    public MyChannelView() {
        System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ MY CHANNEL ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
        System.out.printf("|" + "  1. %-85s" + "|\n", "Create My Channel");
        System.out.printf("|" + "  2. %-85s" + "|\n", "Edit My Channel Name");
        System.out.printf("|" + "  3. %-85s" + "|\n", "Show My Video");
        System.out.printf("|" + "  4. %-85s" + "|\n", "Upload A Video");
        System.out.printf("|" + "  5. %-85s" + "|\n", "Edit My Video");
        System.out.printf("|" + "  6. %-85s" + "|\n", "Delete My Video");
        System.out.printf("|" + "  7. %-85s" + "|\n", "Delete My Channel");
        System.out.printf("|" + "  8. %-85s" + "|\n", "Show My Channel Info");
        System.out.printf("|" + "  9. %-85s" + "|\n", "Show My Playlist");
        System.out.printf("|" + "  10. %-84s" + "|\n", "Show all follower");
        System.out.printf("|" + "  11. %-84s" + "|\n", "Back");
        System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ MY CHANNEL ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
        System.out.println("Please enter your choice: ");
        int choice = Config.validateInt();
        switch (choice) {
            case 1:
                createChannel();
                break;
            case 2:
                updateChannelName();
                break;
            case 3:
                VideoView.getVideoViewInstance().showAllMyVideo();
                break;
            case 4:
                VideoView.getVideoViewInstance().createVideo();
                break;
            case 5:
                VideoView.getVideoViewInstance().editMyVideo();
                break;
            case 6:
                VideoView.getVideoViewInstance().deleteVideo();
                break;
            case 7:
                deleteMyChannel();
                break;
            case 8:
                showMyChannelInfo();
                break;
            case 9:
                PlayListView.getPlayListViewInstance().showMyPlayLists();
                break;
            case 10:
                showMyListFollower();
                break;
            case 11:
                ProfileView.getProfileViewInstance();
                break;
            default:
                System.out.println(Config.OOA_ALERT);
                getMyChannelViewInstance();
        }
    }

    //! Tạo kênh mới
    public void createChannel() {
        User user = userLogin.get(0);
        if (user.getMyChannel() == null) {
            int id;
            if (channelList.size() == 0) {
                id = 1;
            } else {
                id = channelList.get(channelList.size() - 1).getId() + 1;
            }
            System.out.println("-------------- CREATE NEW CHANNEL --------------");
            while (true) {
                System.out.println("Enter your channel's name: ");
                String name = Config.validateString();
                if (channelController.findByName(name) != null) {
                    System.out.println("This name already exist! Please choose other name!");
                } else {
                    Channel channel = new Channel(id, name, user);
                    user.setMyChannel(channel);
                    userController.updateUser(user, 0);
                    userController.updateUserLogin(user);
                    channelController.createChannel(channel);
                    System.out.println(Config.SUCCESS_ALERT);
                    Config.breakTime();
                    getMyChannelViewInstance();
                    break;
                }
            }
        } else {
            System.err.println("You already have a channel!");
            Config.breakTime();
            new MyChannelView();
        }
    }

    //! Cập nhật tên cho kênh
    public void updateChannelName() {
        User user = userLogin.get(0);
        if (user.getMyChannel() == null) {
            System.out.println(Config.CNE_ALERT);
        } else {
            while (true) {
                System.out.println("Enter your new channel's name: ");
                String name = Config.validateString();
                if (channelController.findByName(name) != null) {
                    System.out.println("This name already exist! Please choose other name!");
                } else {
                    Channel channel = user.getMyChannel();
                    channel.setName(name);
                    user.setMyChannel(channel);
                    userController.updateUser(user, 0);
                    userController.updateUserLogin(user);
                    System.out.println(Config.SUCCESS_ALERT);
                    Config.breakTime();
                    getMyChannelViewInstance();
                    break;
                }
            }
        }
    }

    //! Xóa kênh
    public void deleteMyChannel() {
        User user = userLogin.get(0);
        if (user.getMyChannel() != null) {
            while (true) {
                System.out.println("Are you sure to delete your channel with " + user.getMyChannel().getFollowerList().size() + " followers and " + user.getMyChannel().getVideoList().size() + " videos? Type Y/N");
                String choice = Config.validateString();
                if (choice.equalsIgnoreCase("y")) {
                    channelController.deleteChannel(user.getMyChannel().getId());
                    user.setMyChannel(null);
                    userController.updateUser(user, 0);
                    userController.updateUserLogin(user);
                    System.out.println(Config.SUCCESS_ALERT);
                    getMyChannelViewInstance();
                    break;
                }
                if (choice.equalsIgnoreCase("n")) {
                    getMyChannelViewInstance();
                    break;
                }
            }
        } else {
            System.err.println(Config.CNE_ALERT);
            Config.breakTime();
            getMyChannelViewInstance();
        }
    }

    //! Xem chi tiết kênh
    public void showMyChannelInfo() {
        Channel myChannel = userLogin.get(0).getMyChannel();
        if (myChannel != null) {
            if (!myChannel.isEarnMoneyStatus()){
                if (myChannel.getFollowerList().size() > 2 && channelController.calTotalAllVideosView(myChannel) > 10) {
                    myChannel.setEarnMoneyStatus(true);
                    userLogin.get(0).setMyChannel(myChannel);
                    channelController.updateChannel(myChannel);
                    userController.updateUserLogin(userLogin.get(0));
                    userController.updateUser(userLogin.get(0),0);
                }
            }
            while (true) {
                System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "---------------------------- My Channel ----------------------------");
                System.out.printf("|" + "  1. %-61s" + "|\n", "Name: " + myChannel.getName());
                System.out.printf("|" + "  2. %-61s" + "|\n", "Owner: " + myChannel.getOwner().getName());
                System.out.printf("|" + "  3. %-61s" + "|\n", "Follower: " + myChannel.getFollowerList().size());
                System.out.printf("|" + "  4. %-61s" + "|\n", "Video: " + myChannel.getVideoList().size());
                System.out.printf("|" + "  5. %-61s" + "|\n", "Total views: " + channelController.calTotalAllVideosView(myChannel));
                System.out.printf("|" + "  6. %-61s" + "|\n", "Earn Money Permission: " + (myChannel.isEarnMoneyStatus() ? "Yes" : "Not yet"));
                System.out.println("---------------------------- My Channel ----------------------------" + ColorConsole.RESET);
                System.out.println("Type BACK to return: ");
                String back = Config.scanner().nextLine();
                if (back.equalsIgnoreCase("back")) {
                    getMyChannelViewInstance();
                    break;
                }
            }
        } else {
            System.err.println(Config.CNE_ALERT);
            Config.breakTime();
            getMyChannelViewInstance();
        }
    }

    //! Hiển thị danh sách người theo dõi
    public void showMyListFollower() {
        Channel myChannel = userLogin.get(0).getMyChannel();
        for (User u : myChannel.getFollowerList()) {
            System.out.println("ID: " + u.getId() + " - Name: " + u.getName());
        }
        while (true){
            System.out.println("Type BACK to return: ");
            String back = Config.scanner().nextLine();
            if (back.equalsIgnoreCase("back")) {
                getMyChannelViewInstance();
                break;
            }
        }
    }
}
