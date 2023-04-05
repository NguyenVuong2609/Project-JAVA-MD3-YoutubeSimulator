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
    public List<User> getListUser(){
        return userService.findAll();
    }
    public ResponseMessage login(SignInDTO data){
        if (userService.checkLogin(data.getUsername(), data.getPassword())){
            return new ResponseMessage("login_successful");
        } else {
            return new ResponseMessage("login_failed");
        }
    }
    public User getUserLogin(){
        return userService.getCurentUser();
    }
    public void logout(){
        userService.logout();
    }
}
