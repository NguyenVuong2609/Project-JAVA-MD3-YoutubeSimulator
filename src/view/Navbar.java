package view;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import model.User;
import view.admin.ProfileView;
import view.user.UserView;



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
            System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "Welcome " + user.getName() + ColorConsole.RESET);
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
                        UserView.getUserViewInstance().showListUser();
                        break;
                    case 1:
                        UserView.getUserViewInstance().register();
                        break;
                    case 2:
                        UserView.getUserViewInstance().formLogin();
                        break;
                    case 3:
                        System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + "See you again!!!" + ColorConsole.RESET);
                        Config.breakTime();
                        System.exit(0);
                    default:
                        System.out.println(Config.OOA_ALERT);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Navbar();
    }
}
