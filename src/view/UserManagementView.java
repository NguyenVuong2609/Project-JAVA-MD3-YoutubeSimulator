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

public class UserManagementView {
    UserController userController = new UserController();

    public UserManagementView() {
        User user = userController.getUserLogin();
        Set<Role> roleSet = user.getRoles();
        while (true) {
            if (user != null) {
                List<Role> roles = new ArrayList<>(roleSet);
                if (roles.get(0).getName() == RoleName.ADMIN) {
                    System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ USER MANAGEMENT ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
                    System.out.printf("|" + "  1. %-90s" + "|\n", "Change Role");
                    System.out.printf("|" + "  2. %-90s" + "|\n", "Block User");
                    System.out.printf("|" + "  3. %-90s" + "|\n", "Delete User");
                    System.out.printf("|" + "  4. %-90s" + "|\n", "Back");
                    System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ USER MANAGEMENT ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
                    System.out.println("Please enter your choice: ");
                    int choice = Config.validateInt();
                    switch (choice) {
                        case 1:
                            new UserView().changeUserRole();
                            break;
                        case 2:
                            new UserView().blockUser();
                            break;
                        case 3:
                            new UserView().deleteUserById();
                            break;
                        case 4:
                            new ProfileView();
                            break;
                        default:
                            System.out.println(Config.OOA_ALERT);
                    }
                } else {
                    System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ USER MANAGEMENT ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
                    System.out.printf("|" + "  1. %-89s" + "|\n", "Block User");
                    System.out.printf("|" + "  2. %-89s" + "|\n", "Delete User");
                    System.out.printf("|" + "  3. %-89s" + "|\n", "Back");
                    System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ USER MANAGEMENT ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
                    System.out.println("Please enter your choice: ");
                    int choice = Config.validateInt();
                    switch (choice) {
                        case 1:
                            new UserView().blockUser();
                            break;
                        case 2:
                            new UserView().deleteUserById();
                            break;
                        case 3:
                            new ProfileView();
                            break;
                        default:
                            System.out.println(Config.OOA_ALERT);
                    }
                }
            }
        }
    }
}
