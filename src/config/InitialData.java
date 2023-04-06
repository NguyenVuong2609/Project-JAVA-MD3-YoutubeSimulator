package config;

import model.Role;
import model.RoleName;
import model.User;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.UserServiceIMPL;

import java.util.HashSet;
import java.util.Set;

public class InitialData {
//    public static void main(String[] args) {
//        IRoleService roleService = new RoleServiceIMPL();
//        Set<Role> roleSet1 = new HashSet<>();
//        roleSet1.add(roleService.findByName(RoleName.ADMIN));
//        User admin = new User(0,"Admin", "admin", "admin@gmail.com", "admin", "", false, roleSet1);
//        new UserServiceIMPL().save(admin);
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(roleService.findByName(RoleName.PM));
//        User user = new User(1,"PM", "pm", "pm@gmail.com" , "pm" ,"", false, roleSet);
//        new UserServiceIMPL().save(user);
//    }
}
