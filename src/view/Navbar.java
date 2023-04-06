package view;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import model.Role;
import model.RoleName;
import model.User;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.UserServiceIMPL;

import java.util.*;

public class Navbar {
    UserController userController = new UserController();

    public Navbar() {
        User user = userController.getUserLogin();
        if (user != null) {
            try {
                for (int i = 3; i > 0; i--) {
                    System.out.println(ColorConsole.GREEN_BOLD_BRIGHT + "Waiting..." + i + " seconds" + ColorConsole.RESET);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Welcome " + user.getName());
            new ProfileView();
        } else {
            while (true) {
                System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
                System.out.printf("|" + "  1. %-87s" + "|\n", "Register");
                System.out.printf("|" + "  2. %-87s" + "|\n", "Login");
                System.out.printf("|" + "  3. %-87s" + "|\n", "Exit");
                System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ YOUTUBE MENU ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
                System.out.println("Please enter your choice: ");
                int choice = Config.validateInt();
                switch (choice) {
                    case 0:
                        new UserView().showListUser();
                        break;
                    case 1:
                        new UserView().register();
                        break;
                    case 2:
                        new UserView().formLogin();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println(Config.OOA_ALERT);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Navbar();
//        String data = Config.scanner().nextLine();
//        System.out.println(Config.validateUsername(data));
//        List<User> userList = new Config<User>().readFromFile(Config.PATH_USER);
//        Set<Role> setRoles = userList.get(1).getRoles();
//        List<Role> listRoles = new ArrayList<>(setRoles);
//        System.out.println(listRoles.get(0).getName());
    }
}
