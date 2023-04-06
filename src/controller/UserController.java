package controller;

import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessage;
import model.Role;
import model.RoleName;
import model.User;
import service.role.IRoleService;
import service.role.RoleServiceIMPL;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {
    private IUserService userService = new UserServiceIMPL();
    private IRoleService roleService = new RoleServiceIMPL();

    public ResponseMessage register(SignUpDTO data) {
        if (userService.existedByUsername(data.getUsername())) {
            return new ResponseMessage("username_existed");
        }
        if (userService.existByEmail(data.getEmail())) {
            return new ResponseMessage("email_existed");
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findByName(RoleName.USER));
        User user = new User(data.getId(), data.getName(), data.getUsername(), data.getEmail(), data.getPassword(), roleSet);
        userService.save(user);
        return new ResponseMessage("create_success");
    }

    public List<User> getListUser() {
        return userService.findAll();
    }

    public ResponseMessage login(SignInDTO data) {
        if (userService.checkLogin(data.getUsername(), data.getPassword())) {
            return new ResponseMessage("login_successful");
        } else {
            return new ResponseMessage("login_failed");
        }
    }

    public User getUserLogin() {
        return userService.getCurentUser();
    }

    public void logout() {
        userService.logout();
    }

    public void updateUser(User user, int status) {
        if (status == 1){
            Set<Role> roleSet = user.getRoles();
            List<Role> roleList = new ArrayList<>(roleSet);
            if (roleList.get(0).getName() == RoleName.USER){
                Set<Role> newRoleSet = new HashSet<>();
                newRoleSet.add(roleService.findByName(RoleName.PM));
                user.setRoles(newRoleSet);
                userService.save(user);
            } else if (roleList.get(0).getName() == RoleName.PM){
                Set<Role> newRoleSet = new HashSet<>();
                newRoleSet.add(roleService.findByName(RoleName.USER));
                user.setRoles(newRoleSet);
                userService.save(user);
            }
        }
        if (status == 2){
            user.setStatus(!user.isStatus());
            userService.save(user);
        }
    }

    public User findUserDetailsById(int id) {
        return userService.findById(id);
    }
    public void deleteUserById(int id){
        userService.deleteById(id);
    }
}
