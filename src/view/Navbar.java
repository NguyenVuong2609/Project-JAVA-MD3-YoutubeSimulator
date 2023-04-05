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

import java.util.HashSet;
import java.util.Set;

public class Navbar {
    UserController userController = new UserController();

    public Navbar() {
        User user = userController.getUserLogin();
        if (user != null) {
            System.out.println("Welcome " + user.getName());
            int choice = Config.scanner().nextInt();
            switch (choice) {
                case 1:
                    new ProfileView();
                    break;
            }
        } else {
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
            }
        }
    }

    public static void main(String[] args) {
//        IRoleService roleService = new RoleServiceIMPL();
        new Navbar();
//        String data = Config.scanner().nextLine();
//        System.out.println(Config.validateUsername(data));
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(roleService.findByName(RoleName.PM));
//        User user = new User(1,"PM", "pm", "pm@gmail.com" , "pm" ,"", false, roleSet);
//        new UserServiceIMPL().save(user);
    }
}
