package view.user;

import config.ColorConsole;
import config.Config;
import config.YoutubeFrame;
import controller.PlayListController;
import controller.UserController;
import controller.VideoController;
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
    List<Video> videoList = new Config<Video>().readFromFile(Config.PATH_VIDEO);
    PlayListController playListController = new PlayListController();
    UserController userController = UserController.getUserControllerInstance();
    VideoController videoController = VideoController.getVideoControllerInstance();
    List<ListVideo> allListVideo = playListController.getPlayLists();

    //! Hiển thị các playlist
    public void showMyPlayLists() {
        userLogin= new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
        List<ListVideo> myAllPlayLists = userLogin.get(0).getMyPlaylist();
        if (myAllPlayLists != null) {
            System.out.print("---------------- My All Playlist ---------------- \n");
            for (int i = 0; i < myAllPlayLists.size(); i++) {
                System.out.printf("%d. Playlist Name: %s - Videos: %d \n", (i + 1), myAllPlayLists.get(i).getName(), myAllPlayLists.get(i).getPlaylist().size());
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
        userLogin= new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
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
        userLogin= new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
        boolean flag = false;
        User user = userLogin.get(0);
        List<ListVideo> allPlaylist = user.getMyPlaylist();
        ListVideo listDelete = null;
        if (allPlaylist != null) {
            System.out.print("---------------- My All Playlist ----------------\n");
            for (int i = 0; i < allPlaylist.size(); i++) {
                System.out.printf("%d. Playlist Name: %s - Videos: %d \n", (i + 1), allPlaylist.get(i).getName(), allPlaylist.get(i).getPlaylist().size());
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
                if (flag) break;
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
                    YoutubeView.getYoutubeViewInstance();
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
        } else {
            System.err.println("You don't have any playlist to delete!");
            YoutubeView.getYoutubeViewInstance();
        }
    }

    //! Tìm playlist theo tên
    public void findPlayListByName() {
        System.out.println("Enter a playlist's name: ");
        String name = Config.validateString();
        List<ListVideo> searchData = playListController.findListOfPlayListByName(name);
        if (searchData.size() > 0) {
            for (int i = 0; i < searchData.size(); i++) {
                System.out.printf("%s. Playlist's name: %s - Video: %s\n", (i + 1), searchData.get(i).getName(), searchData.get(i).getPlaylist().size());
            }
            int index;
            do {
                System.out.println("Enter an playlist's index that you want to view: ");
                index = Config.validateInt();
            } while (index <= 0 || index > searchData.size());
            List<Integer> idList = searchData.get(index - 1).getPlaylist();
            for (int i = 0; i < idList.size(); i++) {
                for (Video video : videoList) {
                    if (idList.get(i) == video.getId()) {
                        System.out.printf("%s. Video's name: %s - Views: %s - Owner: %s\n", (i + 1), video.getVideoName(), video.getViews(), video.getOwner().getName());
                        break;
                    }
                    System.out.println((i + 1) + ". This video is unavailable");
                }
            }
            int videoIndex;
            System.out.println("Enter a video's index that you want to view: ");
            videoIndex = Config.validateInt();
            if (VideoController.getVideoControllerInstance().findVideoById(idList.get(videoIndex - 1)) != null) {
                YoutubeFrame.getYoutubeViewInstance().showVideoFrame(VideoController.getVideoControllerInstance().findVideoById(idList.get(videoIndex - 1)));
                YoutubeFrame.getYoutubeViewInstance().actionMenu(VideoController.getVideoControllerInstance().findVideoById(idList.get(videoIndex - 1)));
            } else {
                System.err.println("Can't play this video.");
                YoutubeView.getYoutubeViewInstance();
            }
        } else {
            System.err.println("No result!");
            YoutubeView.getYoutubeViewInstance();
        }
    }

    //! Thêm video vào playlist
    public void addVideoToPlaylistMenu(Video video) {
        userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
        List<ListVideo> allMyPlayList = userLogin.get(0).getMyPlaylist();
        if (allMyPlayList != null) {
            for (int i = 0; i < allMyPlayList.size(); i++) {
                System.out.printf(ColorConsole.GREEN_BRIGHT + "%s. Playlist: %s - Videos: %s \n" + ColorConsole.RESET, (i + 1), allMyPlayList.get(i).getName(), allMyPlayList.get(i).getPlaylist().size());
            }
            int index;
            do {
                System.out.println("Enter a playlist's index that you want to add this video: ");
                index = Config.validateInt();
                if (index < 1 || index > allMyPlayList.size()) System.out.println(Config.OOA_ALERT);
            } while (index < 1 || index > allMyPlayList.size());
            boolean check = addVideoToPlaylist(allMyPlayList.get(index - 1), video);
            if (check) {
                System.out.println(Config.SUCCESS_ALERT);
            } else {
                System.err.println("This video is added before! Can't add more!");
            }
        } else {
            System.err.println("You don't have any playlist to add this video!");
        }
    }

    //! Thêm video vào playlist
    public boolean addVideoToPlaylist(ListVideo listVideo, Video video) {
        userLogin= new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
        User user = userLogin.get(0);
        List<ListVideo> allMyPlayList = user.getMyPlaylist();
        if (listVideo.getPlaylist().size() == 0) {
            listVideo.getPlaylist().add(video.getId());
            int index = allMyPlayList.indexOf(listVideo);
            allMyPlayList.set(index, listVideo);
            user.setMyPlaylist(allMyPlayList);
            playListController.updatePlaylist(listVideo);
            userController.updateUser(user, 0);
            userController.updateUserLogin(user);
            return true;
        } else if (listVideo.getPlaylist().size() > 0) {
            boolean flag = true;
            for (int i = 0; i < listVideo.getPlaylist().size(); i++) {
                if (listVideo.getPlaylist().get(i) != video.getId()) {
                    listVideo.getPlaylist().add(video.getId());
                    flag = false;
                }
            }
            if (flag) return false;
            int index = allMyPlayList.indexOf(listVideo);
            allMyPlayList.set(index, listVideo);
            user.setMyPlaylist(allMyPlayList);
            playListController.updatePlaylist(listVideo);
            userController.updateUser(user, 0);
            userController.updateUserLogin(user);
            return true;
        }
        return false;
    }

    //! Sửa playlist
    public void editMyPlaylist() {
        userLogin= new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
        List<ListVideo> allMyPlaylist = userLogin.get(0).getMyPlaylist();
        int index;
        if (allMyPlaylist != null) {
            for (int i = 0; i < allMyPlaylist.size(); i++) {
                System.out.printf(ColorConsole.GREEN_BRIGHT + "%s. Playlist: %s - Videos: %s \n" + ColorConsole.RESET, (i + 1), allMyPlaylist.get(i).getName(), allMyPlaylist.get(i).getPlaylist().size());
            }
            do {
                System.out.println("Enter a playlist's index that you want to edit: ");
                index = Config.validateInt();
                if (index < 1 || index > allMyPlaylist.size()) System.out.println(Config.OOA_ALERT);
            } while (index < 1 || index > allMyPlaylist.size());
            List<Integer> targetList = allMyPlaylist.get(index - 1).getPlaylist();
            for (int i = 0; i < targetList.size(); i++) {
                Video vid = videoController.findVideoById(targetList.get(i));
                if (vid != null) {
                    System.out.printf("%s. Video name: %s - Owner: %s\n", (i + 1), vid.getVideoName(), vid.getOwner().getName());
                } else {
                    System.out.println((i + 1) + ". This video is unavailable.");
                }
            }
            String choice;
            do {
                System.out.println("Do you want to remove any video in this playlist? Type Y/N");
                choice = Config.validateString();
                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n"))
                    break;
            } while (true);
            if (choice.equalsIgnoreCase("y")) {
                int vidIndex;
                do {
                    System.out.println("Enter a video's index that you want to remove from playlist: ");
                    vidIndex = Config.validateInt();
                    if (vidIndex < 1 || vidIndex > targetList.size())
                        System.out.println(Config.OOA_ALERT);
                } while (vidIndex < 1 || vidIndex > targetList.size());
                targetList.remove(vidIndex - 1);
                allMyPlaylist.get(index - 1).setPlaylist(targetList);
                userLogin.get(0).setMyPlaylist(allMyPlaylist);
                playListController.updatePlaylist(allMyPlaylist.get(index - 1));
                userController.updateUser(userLogin.get(0), 0);
                userController.updateUserLogin(userLogin.get(0));
                System.out.println(Config.SUCCESS_ALERT);
                Config.breakTime();
                MyChannelView.getMyChannelViewInstance();
            }
            if (choice.equalsIgnoreCase("n")){
                MyChannelView.getMyChannelViewInstance();
            }
        }
    }
}
