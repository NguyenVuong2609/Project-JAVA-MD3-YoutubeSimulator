package config;

import model.Role;
import model.RoleName;
import model.User;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.UserServiceIMPL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InitialData {
    static IRoleService roleService = new RoleServiceIMPL();
    static List<User> userList = new Config<User>().readFromFile(Config.PATH_USER);

    static {
        if (userList.size() == 0) {
            Set<Role> roleSet = new HashSet<>();
            Set<Role> roleSet1 = new HashSet<>();
            Set<Role> roleSet2 = new HashSet<>();
            roleSet.add(roleService.findByName(RoleName.ADMIN));
            roleSet1.add(roleService.findByName(RoleName.PM));
            roleSet2.add(roleService.findByName(RoleName.USER));
            User admin = new User(0, "Admin", "admin", "admin@gmail.com", "admin", "", false, roleSet);
            User pm = new User(1, "PM", "pm", "pm@gmail.com", "pm", "", false, roleSet1);
            User user1 = new User(2, "Do Chuan", "chuando123", "chuan@gmail.com", "Chuan@123", roleSet2);
            userList.add(admin);
            userList.add(pm);
            userList.add(user1);
            new Config<User>().writeToFile(Config.PATH_USER, userList);
        }
    }

//    public static void main(String[] args) {
//    }
}
