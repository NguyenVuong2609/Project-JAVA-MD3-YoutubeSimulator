package view;

import config.ColorConsole;
import config.Config;
import controller.UserController;
import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessage;
import model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserView {
    UserController userController = new UserController();
    List<User> userList = userController.getListUser();

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
            if (!Config.validateEmail(email))
                System.err.println(Config.FORMAT_ALERT);
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
                System.err.println(Config.FORMAT_ALERT + "password must include at least: 1 special symbol, 1 upper word, 1 lower word");
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
                    if (!Config.validateEmail(email))
                        System.err.println(Config.FORMAT_ALERT);
                } while (!Config.validateEmail(email));
                sign.setEmail(email);
//                sign = new SignUpDTO(id, name, username, email, password, strRole);
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
            new ProfileView();
        }
    }

    //! Đăng nhập
    public void formLogin() {
        int count = 0;
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

    public void logout() {
        while (true) {
            System.out.println("Are you sure to log out? - Please type Y/N?");
            String choice = Config.validateString();
            if (choice.equalsIgnoreCase("y")){
                userController.logout();
                new Navbar();
                break;
            }
            if (choice.equalsIgnoreCase("n")){
                new ProfileView();
                break;
            }
        }
    }
}
