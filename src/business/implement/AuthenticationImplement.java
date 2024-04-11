package business.implement;

import business.config.IOFile;
import business.design.IAuthentication;
import business.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AuthenticationImplement implements IAuthentication {
    public static List<User> userList;

    static {
        File userFile = new File(IOFile.USER_PATH);
        if (userFile.length() == 0) {
            userList = new ArrayList<>();
            User admin = new User("Admin","Admin@gmail.com","Admin",BCrypt.hashpw("Admin",BCrypt.gensalt(5)), true,"AdminAvatar");
            userList.add(admin);
            IOFile.updateFile(IOFile.USER_PATH, userList);
        } else {
            userList = IOFile.getFile(IOFile.USER_PATH);
            if (userList.stream().noneMatch(user -> user.getUserName().equals("Admin"))){
                User admin = new User("Admin","Admin@gmail.com","Admin",BCrypt.hashpw("Admin",BCrypt.gensalt(5)), true,"AdminAvatar");
                userList.add(admin);
                IOFile.updateFile(IOFile.USER_PATH, userList);
            }
        }
    }

    @Override
    public User login(String username, String password) {
        User userLogin = getUserFromUsername(username);
        if (userLogin == null) {
            return null;
        }
        boolean checkLogin = BCrypt.checkpw(password, userLogin.getPassword());
        if (checkLogin) {
            return userLogin;
        }
        return null;
    }

    @Override
    public void register(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
        userList.add(user);
        IOFile.updateFile(IOFile.USER_PATH, userList);
    }

    private User getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUserName().equals(username)).findFirst().orElse(null);
    }

}
