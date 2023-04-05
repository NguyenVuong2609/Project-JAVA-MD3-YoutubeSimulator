package view;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import model.Role;
import model.RoleName;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileView {
    UserController userController = new UserController();

    public ProfileView() {
        User user = userController.getUserLogin();
        Set<Role> roleSet = user.getRoles();
        if (user != null) {
            List<Role> roles = new ArrayList<>(roleSet);
            if (roles.get(0).getName() == RoleName.ADMIN) {
                System.out.println("Admin Menu");
                System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ ADMIN MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
                System.out.printf("|" + "  1. %-87s" + "|\n", "Show list user");
                System.out.printf("|" + "  2. %-87s" + "|\n", "Log out");
                System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ ADMIN MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
                System.out.println("Please enter your choice: ");
                int choice = Config.validateInt();
                switch (choice) {
                    case 1:
                        new UserView().showListUser();
                        break;
                    case 2:
                        new UserView().logout();
                        break;
                }
            } else if (roles.get(0).getName()== RoleName.USER){
                System.out.println("User Menu");
            } else {
                System.out.println("PM Menu");
            }
        }
    }
}
