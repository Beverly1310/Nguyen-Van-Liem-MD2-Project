package presentation;

import business.config.Alert;
import business.config.IOFile;
import business.config.InputMethods;
import business.design.CategoryDesign;
import business.design.MovieDesign;
import business.design.UserDesign;
import business.entity.Category;
import business.entity.Movies;
import business.implement.CategoryImplement;
import business.implement.MovieImplement;
import business.implement.UserImplement;

public class AdminMenu {
    public static UserDesign userManager = new UserImplement();
    public static MovieDesign<Movies> movieManager = new MovieImplement();
    private static CategoryDesign<Category> categoryManager = new CategoryImplement();

    public static void Menu() {
        System.out.println("Chào mừng trở lại," + Login.user.getUserName()+"ヾ(•ω•`)o");
        boolean isExit = false;
        while (!isExit) {
            System.out.println("|============================ADMIN MENU=============================|");
            System.out.println("\u001B[33m|-------------------------1.Quản lý User----------------------------|\u001B[0m");
            System.out.println("\u001B[34m|-----------------------2.Quản lý danh mục--------------------------|\u001B[0m");
            System.out.println("\u001B[35m|--------------------------3.Quản lý phim---------------------------|\u001B[0m");
            System.out.println("\u001B[36m|---------------------------4.Thống kê------------------------------|\u001B[0m");
            System.out.println("|--------------------------5. Đăng xuất-----------------------------|\u001B[0m");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    manageUser();
                    break;
                case 2:
                    manageCategory();
                    break;
                case 3:
                    manageMovie();
                    break;
                case 4:
                    manageStatistics();
                    break;
                case 5:
                    Login.user = null;
                    IOFile.updateUserLogin(Login.user);
                    System.out.println("Tạm biệt, hẹn gặp lại "+"ヾ(￣▽￣) Bye~Bye~");
                    isExit = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
// trang quản lý tài khoản
    private static void manageUser() {
        boolean isStop = false;
        while (!isStop) {
            System.out.println("---------------------------Quản lý User---------------------------");
            System.out.println("\u001B[33m1. Hiển thị danh sách user\u001B[0m");
            System.out.println("\u001B[34m2. Thêm mới user\u001B[0m");
            System.out.println("\u001B[35m3. Tìm kiếm user\u001B[0m");
            System.out.println("\u001B[36m4. Đổi trạng thái user\u001B[0m");
            System.out.println("5. Quay lại\u001B[0m");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    userManager.displayAll();
                    break;
                case 2:
                    userManager.createUser();
                    break;
                case 3:
                    userManager.searchUser();
                    break;
                case 4:
                    userManager.changeStatusUser();
                    break;
                case 5:
                    isStop = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
// trang quản lý danh mục
    private static void manageCategory() {
        boolean isStop = false;
        while (!isStop) {
            System.out.println("------------------------Quản lý danh mục--------------------------");
            System.out.println("\u001B[33m1. Hiển thị danh sách danh mục\u001B[0m");
            System.out.println("\u001B[34m2. Thêm danh mục\u001B[0m");
            System.out.println("\u001B[35m3. Sửa danh mục\u001B[0m");
            System.out.println("\u001B[36m4. Xóa danh mục\u001B[0m");
            System.out.println("\u001B[33m5. Tìm kiếm danh mục\u001B[0m");
            System.out.println("6. Quay lại\u001B[0m");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    categoryManager.displayAll();
                    break;
                case 2:
                    categoryManager.createData();
                    break;
                case 3:
                    categoryManager.updateData();
                    break;
                case 4:
                    categoryManager.deleteData();
                    break;
                case 5:
                    categoryManager.searchCategory();
                    break;
                case 6:
                    isStop = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
// trang quản lý phim
    private static void manageMovie() {
        boolean isStop = false;
        while (!isStop) {
            System.out.println("--------------------------Quản lý phim----------------------------");
            System.out.println("\u001B[33m1. Hiển thị danh sách phim\u001B[0m");
            System.out.println("\u001B[34m2. Thêm mới phim\u001B[0m");
            System.out.println("\u001B[35m3. Sửa thông tin phim\u001B[0m");
            System.out.println("\u001B[36m4. Xóa phim\u001B[0m");
            System.out.println("\u001B[33m5. Sắp xếp phim theo tên\u001B[0m");
            System.out.println("\u001B[34m6. Tìm kiếm phim theo tên\u001B[0m");
            System.out.println("7. Quay lại\u001B[0m");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    movieManager.displayAll();
                    break;
                case 2:
                    movieManager.createData();
                    break;
                case 3:
                    movieManager.updateData();
                    break;
                case 4:
                    movieManager.deleteData();
                    break;
                case 5:
                    movieManager.sortMovieByName();
                    break;
                case 6:
                    movieManager.searchByName();
                    break;
                case 7:
                    isStop = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
//trang thống kê
    private static void manageStatistics() {
        boolean isStop = false;
        while (!isStop) {
            System.out.println("--------------------------Thống kê---------------------------------");
            System.out.println("\u001B[33m1. Thống kê top 10 phim có lượt xem cao nhất trong tháng\u001B[0m");
            System.out.println("\u001B[34m2. Thống kê số lượng phim mới, người dùng mới trong tháng\u001B[0m");
            System.out.println("\u001B[35m3. Xem đánh giá\u001B[0m");
            System.out.println("4. Quay lại\u001B[0m");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    movieManager.getTop10View();
                    break;
                case 2:
                    userManager.getNewMovieAndNewUserInMonth();
                    break;
                case 3:
                    userManager.checkRate();
                    break;
                case 4:
                    isStop = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
}
