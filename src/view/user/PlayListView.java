package view.user;

import config.Config;
import controller.PlayListController;
import model.ListVideo;
import model.User;

import java.util.List;

public class PlayListView {
    public static PlayListView playListViewInstance;

    public static PlayListView getPlayListViewInstance() {
        if (playListViewInstance == null)
            playListViewInstance = new PlayListView();
        return playListViewInstance;
    }

    List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
    PlayListController playListController = new PlayListController();

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
}
