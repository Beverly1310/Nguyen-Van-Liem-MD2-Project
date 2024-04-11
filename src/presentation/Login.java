package presentation;

import business.config.Alert;
import business.config.IOFile;
import business.config.InputMethods;
import business.design.IAuthentication;
import business.entity.User;
import business.implement.AuthenticationImplement;

import java.io.File;

public class Login {
    private static IAuthentication iAuthentication = new AuthenticationImplement();
    public static User user = null;

    // tự động đăng nhập
    static {
        File loginStatus = new File(IOFile.LOGIN_STATUS_PATH);
        // nếu có một đối tượng user đã đăng nhập từ trước thì gắn user đó vào biến user
        if (loginStatus.length() != 0) {
            user = IOFile.getUserLogin();
        }
        // tự động đăng nhập nếu user khác null
        if (user != null) {
            {
                if (user.isRole()) {
                    AdminMenu.Menu();
                } else {
                    UserMenu.Menu();
                }
            }
        }
    }

    // trang chủ
    public static void main(String[] args) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("=================TRANG CHỦ=================\n" +
                    "1. Đăng nhập\n" +
                    "2. Đăng ký\n" +
                    "3.Thoát");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    isExit = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
// đăng nhập
    private static void login() {
        System.out.println("=================ĐĂNG NHẬP=================");
        System.out.println("Nhập Username:");
        String username = InputMethods.getString();
        System.out.println("Nhập Password:");
        String password = InputMethods.getString();
        // kiểm tra tài khoản nhập vào
        User userLogin = iAuthentication.login(username, password);
        if (userLogin == null) {
            System.out.println("\u001B[31mThông tin đăng nhập không chính xác.\u001B[0m");
            System.out.println("1. Đăng nhập lại.");
            System.out.println("2. Bạn chưa có tài khoản? Đăng ký ngay.");
            System.out.println("3. Thoát");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    login();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        } else {
            IOFile.updateUserLogin(userLogin);
            if (userLogin.isRole()) {
                user = userLogin;
                AdminMenu.Menu();
            } else {
                if (!userLogin.isStatus()) {
                    System.out.println("\u001B[31mTài khoản của bạn đã bị khóa, vui lòng liện hệ Admin để được hỗ trợ(0943xxxxxx)\u001B[0m");
                } else {
                    user = userLogin;
                    UserMenu.Menu();
                }
            }
        }
    }
// đăng kí
    private static void register() {
        System.out.println("=================ĐĂNG KÝ=================");
        User user = new User();
        user.inputUsername();
        user.inputPassword();
        user.inputEmail();
        user.inputFullname();
        user.inputAvatar();
        user.inputKey();
        iAuthentication.register(user);
        System.out.println("Đăng ký thành công");
    }
}

