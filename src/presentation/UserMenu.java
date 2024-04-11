package presentation;

import business.config.Alert;
import business.config.IOFile;
import business.config.InputMethods;
import business.design.MovieDesign;
import business.design.UserDesign;
import business.implement.MovieImplement;
import business.implement.UserImplement;

import static presentation.AdminMenu.movieManager;
import static presentation.AdminMenu.userManager;

public class UserMenu {
    public static void Menu() {
        boolean isExit = false;
        System.out.println("Chào mừng trở lại,"+Login.user.getUserName()+"（づ￣3￣）づ╭❤️～");
        while (!isExit) {
            System.out.println("|============================USER MENU============================|");
            System.out.println("\u001B[33m|---------------------------1.Trang chủ---------------------------|\u001B[0m");
            System.out.println("\u001B[34m|----------------------2.Trang danh sách phim---------------------|\u001B[0m");
            System.out.println("\u001B[35m|-------------------------3.Chi tiết phim-------------------------|\u001B[0m");
            System.out.println("\u001B[36m|----------------------4.Danh sách yêu thích----------------------|\u001B[0m");
            System.out.println("\u001B[33m|------------------------5.Thông tin cá nhân----------------------|\u001B[0m");
            System.out.println("\u001B[34m|------------------------6.Lịch sử xem phim-----------------------|\u001B[0m");
            System.out.println("|--------------------------7. Đăng xuất---------------------------|");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    goToMainPage();
                    break;
                case 2:
                    goToListPage();
                    break;
                case 3:
                    goToDetailPage();
                    break;
                case 4:
                    goToFavoritePage();
                    break;
                case 5:
                   isExit = goToInfoPage();
                    break;
                case 6:
                    goToHistoryPage();
                    break;
                case 7:
                    Login.user=null;
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
    private static void goToMainPage(){
        boolean isStop = false;
        while (!isStop){
            System.out.println("----------------------------Trang chủ--------------------------");
            System.out.println("\u001B[33m1. Tìm kiếm phim\u001B[0m");
            System.out.println("\u001B[34m2. Hiển thị tất cả phim hiện có\u001B[0m");
            System.out.println("\u001B[35m3. Hiển thị danh sách phim mới\u001B[0m");
            System.out.println("\u001B[36m4. Hiển thị danh sách phim nổi bật\u001B[0m");
            System.out.println("5. Quay lại");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    movieManager.searchByName();
                    break;
                case 2:
                    movieManager.displayAll();
                    break;
                case 3:
                    movieManager.displayNewestMovie();
                    break;
                case 4:
                    movieManager.displayMostFamoustMovie();
                    break;
                case 5:
                    isStop=true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
    private static void goToListPage(){
        boolean isStop = false;
        while (!isStop){
            System.out.println("----------------------Trang danh sách phim----------------------");
            System.out.println("\u001B[33m1. Danh sách phim\u001B[0m");
            System.out.println("\u001B[34m2. Hiển thị phim theo danh mục\u001B[0m");
            System.out.println("\u001B[35m3. Sắp xếp phim theo tên\u001B[0m");
            System.out.println("4. Quay lại");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    movieManager.displayListMovie();
                    break;
                case 2:
                    movieManager.displayMovieByCategory();
                    break;
                case 3:
                    movieManager.sortMovieByName();
                    break;
                case 4:
                    isStop=true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
    private static void goToDetailPage(){
        boolean isStop = false;
        while (!isStop){
            System.out.println("----------------------Chi tiết phim----------------------");
            System.out.println("\u001B[33m1. Hiển thị dữ liệu chi tiết\u001B[0m");
            System.out.println("\u001B[34m2. Xem phim\u001B[0m");
            System.out.println("3. Quay lại");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    movieManager.displayMovie();
                    break;
                case 2:
                    movieManager.watchMovie();
                    break;
                case 3:
                    isStop=true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
    private static void goToFavoritePage(){
        boolean isStop = false;
        while (!isStop){
            System.out.println("--------------------------Danh sách yêu thích----------------------------");
            System.out.println("\u001B[33m1. Hiển thị danh sách yêu thích\u001B[0m");
            System.out.println("\u001B[34m2. Xóa phim khỏi danh sách yêu thích\u001B[0m");
            System.out.println("3. Quay lại");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    userManager.displayFavoriteList();
                    break;
                case 2:
                    userManager.removeFromFavoriteList();
                    break;
                case 3:
                    isStop=true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }
    private static boolean goToInfoPage(){
        boolean isStop = false;
        while (!isStop){
            System.out.println("--------------------------Thông tin cá nhân--------------------------");
            System.out.println("\u001B[33m1. Hiển thị thông tin cá nhân\u001B[0m");
            System.out.println("\u001B[34m2. Đổi mật khẩu\u001B[0m");
            System.out.println("\u001B[35m3. Chỉnh sửa thông tin cá nhân\u001B[0m");
            System.out.println("\u001B[36m4. Xóa tài khoản\u001B[0m");
            System.out.println("5. Quay lại");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    userManager.displayInformation();
                    break;
                case 2:
                    userManager.changePassword();
                    break;
                case 3:
                    userManager.updateInformation();
                    break;
                case 4:
                    userManager.deleteAccount();
                    Login.user=null;
                    IOFile.updateUserLogin(Login.user);
                    return true;
                case 5:
                    isStop=true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
        return false;
    }
    private static void goToHistoryPage(){
        boolean isStop = false;
        while (!isStop){
            System.out.println("--------------------------Lịch sử xem phim---------------------------------");
            System.out.println("\u001B[33m1. Hiển thị lịch sử\u001B[0m");
            System.out.println("2. Quay lại");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice){
                case 1:
                    userManager.readHistory();
                    break;
                case 2:
                    isStop=true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
    }

}
