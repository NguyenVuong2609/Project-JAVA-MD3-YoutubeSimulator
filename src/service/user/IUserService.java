package service.user;

import model.User;
import service.IGenericService;

public interface IUserService extends IGenericService<User> {
    boolean existedByUsername(String username);
    boolean existByEmail(String email);
    boolean checkLogin(String username, String password);
    User getCurentUser();
    void logout();
}
