package view.user;

import config.ColorConsole;
import config.Config;
import view.admin.CategoryView;
import view.admin.ProfileView;
import view.admin.UserManagementView;

public class MyChannelView {
    public MyChannelView(){
        System.out.println(ColorConsole.WHITE_BRIGHT + "❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ MY CHANNEL ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀");
        System.out.printf("|" + "  1. %-85s" + "|\n", "Create My Channel");
        System.out.printf("|" + "  2. %-85s" + "|\n", "Edit My Channel Name");
        System.out.printf("|" + "  3. %-85s" + "|\n", "Delete My Channel");
        System.out.printf("|" + "  4. %-85s" + "|\n", "Show My Channel Info");
        System.out.printf("|" + "  5. %-85s" + "|\n", "Back");
        System.out.println("❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀ MY CHANNEL ❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀❀" + ColorConsole.RESET);
        System.out.println("Please enter your choice: ");
        int choice = Config.validateInt();
        switch (choice) {
            case 1:

                break;
            case 2:

                break;
            case 5:
                new ProfileView();
                break;
            default:
                System.out.println(Config.OOA_ALERT);
        }
    }
}
