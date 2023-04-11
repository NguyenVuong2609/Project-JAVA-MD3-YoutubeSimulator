package view.admin;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import model.Role;
import model.RoleName;
import model.User;
import view.user.MyChannelView;
import view.user.UserView;
import view.user.YoutubeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileView {
    public static ProfileView profileViewInstance;

    public static ProfileView getProfileViewInstance() {
        if (profileViewInstance == null) {
            profileViewInstance = new ProfileView();
        }
        return profileViewInstance;
    }

    UserController userController = new UserController();

    public ProfileView() {
        User user = userController.getUserLogin();
        Set<Role> roleSet = user.getRoles();
        List<Role> roles = new ArrayList<>(roleSet);
        if (roles.get(0).getName() == RoleName.ADMIN) {
            System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ ADMIN MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
            System.out.printf("|" + "  1. %-85s" + "|\n", "User management");
            System.out.printf("|" + "  2. %-85s" + "|\n", "Category management");
            System.out.printf("|" + "  3. %-85s" + "|\n", "Log out");
            System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ ADMIN MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
            System.out.println("Please enter your choice: ");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    UserManagementView.getUserManagementViewInstance();
                    break;
                case 2:
                    CategoryView.getCategoryViewInstance();
                    break;
                case 3:
                    UserView.getUserViewInstance().logout();
                    break;
                default:
                    System.out.println(Config.OOA_ALERT);
                    ProfileView.getProfileViewInstance();
            }
        } else if (roles.get(0).getName() == RoleName.USER) {
            System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ USER MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
            System.out.printf("|" + "  1. %-84s" + "|\n", "View video");
            System.out.printf("|" + "  2. %-84s" + "|\n", "My channel");
            System.out.printf("|" + "  3. %-84s" + "|\n", "Log out");
            System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ USER MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
            System.out.println("Please enter your choice: ");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    YoutubeView.getYoutubeViewInstance();
                    break;
                case 2:
                    MyChannelView.getMyChannelViewInstance();
                    break;
                case 3:
                    UserView.getUserViewInstance().logout();
                    break;
                default:
                    System.out.println(Config.OOA_ALERT);
                    ProfileView.getProfileViewInstance();
            }
        } else {
            System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ PM MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
            System.out.printf("|" + "  1. %-83s" + "|\n", "User management");
            System.out.printf("|" + "  2. %-83s" + "|\n", "Category management");
            System.out.printf("|" + "  3. %-83s" + "|\n", "Log out");
            System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ PM MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
            System.out.println("Please enter your choice: ");
            int choice = Config.validateInt();
            switch (choice) {
                case 1:
                    UserManagementView.getUserManagementViewInstance();
                    break;
                case 2:
                    CategoryView.getCategoryViewInstance();
                    break;
                case 3:
                    UserView.getUserViewInstance().logout();
                    break;
                default:
                    System.out.println(Config.OOA_ALERT);
                    ProfileView.getProfileViewInstance();
            }
        }
    }
}
