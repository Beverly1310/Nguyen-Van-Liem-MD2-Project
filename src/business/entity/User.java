package business.entity;

import business.config.InputMethods;
import business.design.DisplayData;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static business.implement.AuthenticationImplement.userList;

public class User implements Serializable, DisplayData {
    private int userId;
    private String userName;
    private String email;
    private String fullName;
    private boolean status;
    private String password;
    private boolean role;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
        this.userId = getNewId();
        this.status = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String userName, String email, String fullName, String password, boolean role, String avatar) {
        this.userId = getNewId();
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.status = true;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void inputData() {
        inputUsername();
        inputEmail();
        inputPassword();
        inputFullname();
        inputRole();
        inputAvatar();
        this.updatedAt = LocalDateTime.now();
    }

    public void inputUsername() {
        while (true) {
            System.out.println("Nhập Username:");
            String userName = InputMethods.getString();
            if (userList.stream().noneMatch(user -> user.getUserName().equals(userName))) {
                this.userName = userName;
                return;
            } else {
                System.out.println("\u001B[31mTên đăng nhập đã tồn tại, mời bạn nhập lại.\u001B[0m");
            }
        }
    }

    public void inputPassword() {
        while (true) {
            System.out.println("Mời nhập mật khẩu:");
            String password = InputMethods.getString();
            if (password.length() >= 6) {
                while (true) {
                    System.out.println("Xác nhận mật khẩu:");
                    if (password.equals(InputMethods.getString())) {
                        this.password = password;
                        return;
                    } else {
                        System.out.println("\u001B[31mMật khẩu không chính xác, mời nhập lại\u001B[0m");
                    }
                }

            } else {
                System.out.println("\u001B[31mĐộ dài mật khẩu phải lớn hơn 6 ký tự.\u001B[0m");
            }
        }
    }

    public void inputEmail() {
        while (true) {
            System.out.println("Mời nhập email:");
            String email = InputMethods.getString();
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (email.matches(regex)) {
                if (userList.stream().noneMatch(user -> user.getEmail().equals(email))) {
                    this.email = email;
                    return;
                } else {
                    System.out.println("\u001B[31mEmail đã tồn tại\u001B[0m");
                }
            } else {
                System.out.println("\u001B[31mEmail không đúng định dạng\u001B[0m");
            }
        }
    }

    public void inputAvatar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập Url avatar:");
        this.avatar = scanner.nextLine();
    }

    public void inputKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập key nếu bạn là một người quản trị:");
        String key = scanner.nextLine();
        if (BCrypt.checkpw(key,BCrypt.hashpw("iamadmin",BCrypt.gensalt(5)))) {
            this.role = true;
            return;
        } else {
            this.role = false;
        }
    }

    public void inputFullname() {
        while (true) {
            System.out.println("Nhập họ và tên:");
            String name = InputMethods.getString();
            if (name.isBlank()) {
                System.out.println("\u001B[31mTên không được để trống\u001B[0m");
            } else {
                this.fullName = name;
                return;
            }
        }
    }

    public void inputRole() {
        System.out.println("Nhập vai trò của tài khoản");
        this.role = InputMethods.getBoolean();
    }

    private int getNewId() {
        int max = userList.stream().map(User::getUserId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }
    @Override
    public void displayData() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("Mã ID: %-5d || Họ và tện: %-10s || Email: %-10s\n", this.userId, this.fullName, this.email);
        System.out.printf("Trạng thái: %-5s || Vai trò: %-5s\n", this.status ? "Hoạt động" : "Khóa", this.role ? "Admin" : "User");
        System.out.printf("Avatar: %-15s\n", this.avatar.equals("") ? "n/a" : this.avatar);
        System.out.printf("Ngày tạo: %-10s || Cập nhật gần nhất: %-10s\n", this.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                this.updatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("--------------------------------------------------------------------------");
    }
}
