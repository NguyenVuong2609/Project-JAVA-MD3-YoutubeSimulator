package view.user;

import config.Config;
import controller.PlayListController;
import controller.UserController;
import model.ListVideo;
import model.User;
import model.Video;

import java.util.ArrayList;
import java.util.List;

public class PlayListView {
    public static PlayListView playListViewInstance;

    public static PlayListView getPlayListViewInstance() {
        if (playListViewInstance == null) playListViewInstance = new PlayListView();
        return playListViewInstance;
    }

    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
    PlayListController playListController = new PlayListController();
    UserController userController = UserController.getUserControllerInstance();
    List<ListVideo> allListVideo = playListController.getPlayLists();

    //! Hiển thị các playlist
    public void showMyPlayLists() {
        List<ListVideo> myAllPlayLists = userLogin.get(0).getMyPlaylist();
        if (myAllPlayLists != null) {
            System.out.printf("---------------- My All Playlist ----------------");
            for (int i = 0; i < myAllPlayLists.size(); i++) {
                System.out.printf("%d. Playlist Name: %s - Videos: %d \n", i, myAllPlayLists.get(i).getName(), myAllPlayLists.get(i).getPlaylist().size());
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
        } else {
            System.err.println("You don't have any playlist! Please create first!");
            Config.breakTime();
            MyChannelView.getMyChannelViewInstance();
        }
    }

    //! Tạo mới playlist
    public void createPlayList() {
        User user = userLogin.get(0);
        List<ListVideo> playList = user.getMyPlaylist() == null ? new ArrayList<>() : user.getMyPlaylist();
        int id;
        if (allListVideo.size() == 0) {
            id = 1;
        } else {
            id = allListVideo.get(allListVideo.size() - 1).getId() + 1;
        }
        System.out.println("Enter your Playlist's Name: ");
        String name = Config.validateString();
        ListVideo newList = new ListVideo(id, name, user);
        playList.add(newList);
        user.setMyPlaylist(playList);
        playListController.createPlayList(newList);
        userController.updateUserLogin(user);
        userController.updateUser(user, 0);
        System.out.println(Config.SUCCESS_ALERT);
        while (true) {
            System.out.println("Type BACK to return Menu: ");
            String back = Config.scanner().nextLine();
            if (back.equalsIgnoreCase("back")) {
                Config.breakTime();
                YoutubeView.getYoutubeViewInstance();
                break;
            }
        }
    }

    //! Xóa playlist
    public void deletePlaylist() {
        boolean flag = false;
        User user = userLogin.get(0);
        List<ListVideo> allPlaylist = user.getMyPlaylist();
        ListVideo listDelete = null;
        if (allPlaylist != null) {
            System.out.printf("---------------- My All Playlist ----------------");
            for (int i = 0; i < allPlaylist.size(); i++) {
                System.out.printf("%d. Playlist Name: %s - Videos: %d \n", i, allPlaylist.get(i).getName(), allPlaylist.get(i).getPlaylist().size());
            }
            int id;
            while (true) {
                System.out.println("Enter a Playlist Id that you want to delete");
                id = Config.validateInt();
                for (ListVideo list : allPlaylist) {
                    if (list.getId() == id) {
                        listDelete = list;
                        flag = true;
                        break;
                    }
                }
                System.out.println(Config.ID_NOT_EXIST);
                if (flag)
                    break;
            }
            while (true) {
                System.out.println("Are you sure to delete this playlist? Type Y/N");
                String choice = Config.validateString();
                if (choice.equalsIgnoreCase("y")) {
                    allPlaylist.remove(listDelete);
                    user.setMyPlaylist(allPlaylist);
                    playListController.deletePlayList(id);
                    userController.updateUserLogin(user);
                    userController.updateUser(user, 0);
                    System.out.println(Config.SUCCESS_ALERT);
                    break;
                }
                if (choice.equalsIgnoreCase("n")) {
                    while (true) {
                        System.out.println("Type BACK to return Menu: ");
                        String back = Config.scanner().nextLine();
                        if (back.equalsIgnoreCase("back")) {
                            Config.breakTime();
                            YoutubeView.getYoutubeViewInstance();
                            break;
                        }
                    }
                    break;
                }
                System.out.println(Config.OOA_ALERT);
            }
        }
    }
}
