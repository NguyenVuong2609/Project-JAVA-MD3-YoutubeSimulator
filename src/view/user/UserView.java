package view.user;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessage;
import model.Role;
import model.RoleName;
import model.User;
import view.Navbar;
import view.admin.ProfileView;
import view.admin.UserManagementView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserView {
    public static UserView UserViewInstance;
    public static UserView getUserViewInstance(){
        if (UserViewInstance == null)
            UserViewInstance = new UserView();
        return UserViewInstance;
    }
    UserController userController = new UserController();
    List<User> userList = userController.getListUser();
    List<User> currentUserList = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);


    //! Đăng ký
    public void register() {
        int id;
        if (userList.size() == 0) {
            id = 1;
        } else {
            id = userList.get(userList.size() - 1).getId() + 1;
        }
        String name, email, username, password;
        do {
            System.out.println("Enter the name: ");
            name = Config.scanner().nextLine();
            if (!Config.validateName(name))
                System.err.println(Config.FORMAT_ALERT + " can't be void, max is 40 characters.");
        } while (!Config.validateName(name));
        do {
            System.out.println("Enter the email: ");
            email = Config.scanner().nextLine();
            if (!Config.validateEmail(email)) System.err.println(Config.FORMAT_ALERT);
        } while (!Config.validateEmail(email));
        do {
            System.out.println("Enter the username: ");
            username = Config.scanner().nextLine();
            if (!Config.validateUsername(username))
                System.err.println(Config.FORMAT_ALERT + " can't be void, can't include space, max is 30 characters!");
        } while (!Config.validateUsername(username));
        do {
            System.out.println("Enter the password: ");
            password = Config.scanner().nextLine();
            if (!Config.validatePassword(password))
                System.err.println(Config.FORMAT_ALERT + "password must include at least: 1 special symbol, 1 upper word, 1 lower word and 1 number");
        } while (!Config.validatePassword(password));
        Set<String> strRole = new HashSet<>();
        SignUpDTO sign = new SignUpDTO(id, name, username, email, password, strRole);
        while (true) {
            ResponseMessage responseMessage = userController.register(sign);
            if (responseMessage.getMessage().equals("user_existed")) {
                System.err.println("User name existed!");
                do {
                    System.out.println("Enter the username: ");
                    username = Config.scanner().nextLine();
                    if (!Config.validateUsername(username))
                        System.err.println(Config.FORMAT_ALERT + " can't be void, can't include space, max is 30 characters!");
                } while (!Config.validateUsername(username));
                sign.setUsername(username);
            } else if (responseMessage.getMessage().equals("email_existed")) {
                System.err.println("Email existed!");
                do {
                    System.out.println("Enter the email: ");
                    email = Config.scanner().nextLine();
                    if (!Config.validateEmail(email)) System.err.println(Config.FORMAT_ALERT);
                } while (!Config.validateEmail(email));
                sign.setEmail(email);
            } else if (responseMessage.getMessage().equals("create_success")) {
                System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                formLogin();
                break;
            }
        }
    }

    //! Hiển thị danh sách người dùng
    public void showListUser() {
        System.out.println(userList);
        System.out.println("Type BACK to return Menu: ");
        String back = Config.scanner().nextLine();
        if (back.equalsIgnoreCase("back")) {
            new Navbar();
        }
    }

    //! Đăng nhập
    public void formLogin() {
        int count = 1;
        System.out.println("Login Form");
        System.out.println("Enter your username: ");
        String username = Config.scanner().nextLine();
        System.out.println("Enter your password: ");
        String password = Config.scanner().nextLine();
        SignInDTO signInDTO = new SignInDTO(username, password);
        while (true) {
            ResponseMessage responseMessage = userController.login(signInDTO);
            if (responseMessage.getMessage().equals("login_failed")) {
                System.err.println("Login failed! Please check your account!");
                System.out.println("Enter your username: ");
                username = Config.scanner().nextLine();
                System.out.println("Enter your password: ");
                password = Config.scanner().nextLine();
                signInDTO.setUsername(username);
                signInDTO.setPassword(password);
                count++;
                if (count == 5) {
                    System.err.println("You entered more than 5 times, please try again later!");
                    new Navbar();
                    break;
                }
            } else {
                System.out.println("Login successful!");
                new Navbar();
                break;
            }
        }
    }

    //! Đăng xuất
    public void logout() {
        while (true) {
            System.out.println("Are you sure to log out? - Please type Y/N?");
            String choice = Config.validateString();
            if (choice.equalsIgnoreCase("y")) {
                userController.logout();
                new Navbar();
                break;
            }
            if (choice.equalsIgnoreCase("n")) {
                new ProfileView();
                break;
            }
        }
    }

    //! Thay dổi role người dùng
    public void changeUserRole() {
        displayUserListIgnoreAdmin();
        System.out.println("Enter an ID that you want to edit: ");
        int id = Config.validateInt();
        User user = userController.findUserDetailsById(id);
        if (user != null) {
            Set<Role> roleSet = user.getRoles();
            List<Role> roles = new ArrayList<>(roleSet);
            if (roles.get(0).getName() != RoleName.ADMIN) {
                System.out.printf("User: %s - Role: %s \n", user.getName(), roles.get(0).getName());
                while (true) {
                    if (roles.get(0).getName() == RoleName.USER) {
                        System.out.println("Do you want to change the role into PM? Type Y/N");
                    } else {
                        System.out.println("Do you want to change the role into USER? Type Y/N");
                    }
                    String choice = Config.validateString();
                    if (choice.equalsIgnoreCase("y")) {
                        userController.updateUser(user, 1);
                        System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                        new UserManagementView();
                        break;
                    }
                    if (choice.equalsIgnoreCase("n")) {
                        new UserManagementView();
                        break;
                    }
                    System.out.println(Config.OOA_ALERT);
                }
            } else {
                System.out.println(Config.OOA_ALERT);
                new UserManagementView();
            }
        }
        System.out.println(Config.ID_NOT_EXIST);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new UserManagementView();
    }

    //! Xóa tài khoản
    public void deleteUserById() {
        Set<Role> roleSet = currentUserList.get(0).getRoles();
        List<Role> roleList = new ArrayList<>(roleSet);
        if (roleList.get(0).getName() == RoleName.ADMIN) {
            displayUserListIgnoreAdmin();
            System.out.println("Enter an ID that you want to delete: ");
            int id = Config.validateInt();
            User user = userController.findUserDetailsById(id);
            if (user != null && id != 0) {
                System.out.printf("User: %s \n", user.getName());
                System.out.println("Are you sure to delete this account? Type Y/N");
                String choice = Config.validateString();
                if (choice.equalsIgnoreCase("y")) {
                    userController.deleteUserById(id);
                    System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                    new UserManagementView();
                }
            } else {
                System.out.println(Config.OOA_ALERT);
                new UserManagementView();
            }
        } else {
            displayUserListIgnoreAdmin();
            System.out.println("Enter an ID that you want to delete: ");
            int id = Config.validateInt();
            User user = userController.findUserDetailsById(id);
            if (user != null) {
                Set<Role> roleSetUser = user.getRoles();
                List<Role> roles = new ArrayList<>(roleSetUser);
                if (roles.get(0).getName() != RoleName.ADMIN && roles.get(0).getName() != RoleName.PM) {
                    userController.deleteUserById(id);
                    System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                } else {
                    System.out.println(Config.OOA_ALERT);
                }
                new UserManagementView();
            } else {
                System.out.println(Config.ID_NOT_EXIST);
                new UserManagementView();
            }
        }
    }

    //! Hiển thị người dùng ngoại trừ Admin
    public void displayUserListIgnoreAdmin() {
        for (User user : userList) {
            Set<Role> roleSet = user.getRoles();
            List<Role> roles = new ArrayList<>(roleSet);
            if (roles.get(0).getName() == RoleName.PM || roles.get(0).getName() == RoleName.USER)
                System.out.printf("ID: %d - Name: %s - %s - Status: %b \n", user.getId(), user.getName(), user.getRoles(), user.isStatus());
        }
    }

    //! Block user
    public void blockUser() {
        Set<Role> roleSet = currentUserList.get(0).getRoles();
        List<Role> roleList = new ArrayList<>(roleSet);
        if (roleList.get(0).getName() == RoleName.ADMIN) {
            displayUserListIgnoreAdmin();
            System.out.println("Enter an ID that you want to block/unblock: ");
            int id = Config.validateInt();
            User user = userController.findUserDetailsById(id);
            if (user != null) {
                if (user.isStatus()){
                    System.out.println("Are you sure to unblock this account? Type Y/N");
                } else {
                    System.out.println("Are you sure to block this account? Type Y/N");
                }
                String choice = Config.validateString();
                if (choice.equalsIgnoreCase("y")) {
                    userController.updateUser(user, 2);
                    System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                    new UserManagementView();
                }
                if (choice.equalsIgnoreCase("n")) {
                    new UserManagementView();
                }
            } else {
                System.out.println(Config.ID_NOT_EXIST);
                new UserManagementView();
            }
        } else {
            displayUserListIgnoreAdmin();
            System.out.println("Enter an ID that you want to block/unblock: ");
            int id = Config.validateInt();
            User user = userController.findUserDetailsById(id);
            if (user != null) {
                Set<Role> roleSetUser = user.getRoles();
                List<Role> roles = new ArrayList<>(roleSetUser);
                if (roles.get(0).getName() != RoleName.ADMIN && roles.get(0).getName() != RoleName.PM) {
                    if (user.isStatus()){
                        System.out.println("Are you sure to unblock this account? Type Y/N");
                    } else {
                        System.out.println("Are you sure to block this account? Type Y/N");
                    }
                    userController.updateUser(user, 2);
                    System.out.println(ColorConsole.YELLOW_BOLD_BRIGHT + Config.SUCCESS_ALERT + ColorConsole.RESET);
                } else {
                    System.out.println(Config.OOA_ALERT);
                }
                new UserManagementView();
            } else {
                System.out.println(Config.ID_NOT_EXIST);
                new UserManagementView();
            }
        }
    }
}
