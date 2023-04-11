package view.admin;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import model.Role;
import model.RoleName;
import model.User;
import view.user.UserView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserManagementView {
    public static UserManagementView userManagementViewInstance;
    public static UserManagementView getUserManagementViewInstance(){
        if (userManagementViewInstance == null)
            userManagementViewInstance = new UserManagementView();
        return userManagementViewInstance;
    }
    UserController userController = UserController.getUserControllerInstance();

    public UserManagementView() {
        User user = userController.getUserLogin();
        Set<Role> roleSet = user.getRoles();
        boolean flag = true;
        while (flag) {
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
                        UserView.getUserViewInstance().changeUserRole();
                        flag = false;
                        break;
                    case 2:
                        UserView.getUserViewInstance().blockUser();
                        flag = false;
                        break;
                    case 3:
                        UserView.getUserViewInstance().deleteUserById();
                        flag = false;
                        break;
                    case 4:
                        ProfileView.getProfileViewInstance();
                        flag = false;
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
                        UserView.getUserViewInstance().blockUser();
                        break;
                    case 2:
                        UserView.getUserViewInstance().deleteUserById();
                        break;
                    case 3:
                        ProfileView.getProfileViewInstance();
                        break;
                    default:
                        System.out.println(Config.OOA_ALERT);
                }
            }
        }
    }
}
