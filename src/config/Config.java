package config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Config<T> {
    public static final String FORMAT_ALERT = "Format is incorrect";
    public static final String EMPTY_ALERT = "Can't be void, please re-enter";
    public static final String SUCCESS_ALERT = ColorConsole.YELLOW_BOLD_BRIGHT + "Successful!" + ColorConsole.RESET;
    public static final String PATH_USER = "src/database/user.txt";
    public static final String PATH_USER_LOGIN = "src/database/user_login.txt";
    public static final String PATH_CATEGORY = "src/database/category.txt";
    public static final String PATH_VIDEO = "src/database/video.txt";
    public static final String PATH_CHANNEL = "src/database/channel.txt";
    public static final String PATH_PLAYLIST = "src/database/listvideo.txt";
    public static final String CONTINUE_BACK_MENU = ColorConsole.GREEN_BRIGHT + "Enter any key to continue or BACK to return Menu" + ColorConsole.RESET;
    public static final String ID_NOT_EXIST = ColorConsole.RED_BOLD + "ID does not exist! Please try again" + ColorConsole.RESET;
    public static final String OOA_ALERT = ColorConsole.RED_BOLD + "Out of ability!" + ColorConsole.RESET;
    public static final String CNE_ALERT = "You didn't create a channel!";

    public static Scanner scanner() {
        Scanner sc = new Scanner(System.in);
        return sc;
    }

    public static int validateInt() {
        int data = 0;
        while (true) {
            try {
                data = Integer.parseInt(scanner().nextLine());
                return data;
            } catch (Exception e) {
                System.err.println(FORMAT_ALERT);
            }
        }
    }

    public static double validateDouble() {
        double data = 0;
        while (true) {
            try {
                data = Double.parseDouble(scanner().nextLine());
                return data;
            } catch (Exception e) {
                System.err.println(FORMAT_ALERT);
            }
        }
    }

    public static String validateString() {
        while (true) {
            String result = scanner().nextLine();
            if (result.equals("")) {
                System.err.println(EMPTY_ALERT);
                continue;
            }
            return result;
        }
    }

    public static boolean validatePassword(String data) {
        final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{1,10}$";
        return Pattern.matches(PASSWORD_REGEX, data);
    }

    public static boolean validateEmail(String data) {
        final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(EMAIL_REGEX, data);
    }

    public static boolean validateName(String data) {
        final String NAME_REGEX = ".{1,40}";
        return Pattern.matches(NAME_REGEX, data);
    }

    public static boolean validateUsername(String data) {
        final String USERNAME_REGEX = "^\\S{1,30}";
        return Pattern.matches(USERNAME_REGEX, data);
    }

    //! Thời gian nghỉ
    public static void breakTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //! Đọc file
    public List<T> readFromFile(String pathFile) {
        List<T> list = new ArrayList<>();
        try {
            File file = new File(pathFile);
            String currentPath = file.getAbsolutePath();
            if (!file.exists()) {
                String newPath = currentPath.replace("src\\", "");
                file = new File(newPath);
                if (!file.exists())
                    file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            if (fileInputStream.available() != 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                list = (List<T>) objectInputStream.readObject();
                fileInputStream.close();
                objectInputStream.close();
            }
        } catch (FileNotFoundException f) {
            System.err.println("File not found!");
        } catch (EOFException i) {
            System.err.println("Empty");
        } catch (IOException i) {
            System.err.println("IOE exception!");
        } catch (ClassNotFoundException c) {
            System.err.println("Class Not Found!");
        }
        return list;
    }

    //! Ghi file
    public void writeToFile(String pathFile, List<T> list) {
        try {
            File file = new File(pathFile);
            String currentPath = file.getAbsolutePath();
            if (!file.exists()) {
                String newPath = currentPath.replace("src\\", "");
                file = new File(newPath);
                if (!file.exists())
                    file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (FileNotFoundException f) {
            System.err.println("File not found!");
        } catch (IOException i) {
            System.err.println("IOE exception!");
        }
    }
}
