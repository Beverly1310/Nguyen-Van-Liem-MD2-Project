package business.implement;

import business.config.Alert;
import business.config.IOFile;
import business.config.InputMethods;
import business.config.Pagination;
import business.design.UserDesign;
import business.entity.Favorite;
import business.entity.History;
import business.entity.Rate;
import business.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import presentation.Login;


import java.time.LocalDateTime;
import java.util.List;

import static business.implement.AuthenticationImplement.userList;
import static business.implement.FavoriteImplement.favoriteList;
import static business.implement.HistoryImplement.historyList;
import static business.implement.MovieImplement.moviesList;
import static business.implement.RateImplement.rateList;

public class UserImplement implements UserDesign {
    ////////////////////////////////////ADMIN///////////////////////////////////////////////
    // hiển thị danh sách tài khoản
    @Override
    public void displayAll() {
        Pagination.paginate(userList, Alert.USER_NOTFOUND);
    }

    //tạo mới người dùng
    @Override
    public void createUser() {
        System.out.println("Nhập số lượng người dùng muốn thêm");
        int count = InputMethods.getInteger();
        for (int i = 0; i < count; i++) {
            User newUser = new User();
            System.out.println("Nhập thông tin cho người dùng thứ " + (i + 1));
            newUser.inputData();
            newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(5)));
            userList.add(newUser);
            IOFile.updateFile(IOFile.USER_PATH, userList);
        }
    }
// tìm kiếm người dùng
    @Override
    public void searchUser() {
        User findUser = findUserById();
        if (findUser != null) {
            findUser.displayData();
        } else {
            System.out.println(Alert.USER_NOTFOUND);
        }
    }
// tìm người dùng bằng Id
    private static User findUserById() {
        System.out.println("Nhập ID người dùng cần tìm:");
        int userId = InputMethods.getInteger();
        return userList.stream().filter(user -> user.getUserId() == userId).findFirst().orElse(null);
    }
// thay đổi thông tin người dùng
    @Override
    public void changeStatusUser() {
        User user = findUserById();
        if (user != null) {
            user.setStatus(!user.isStatus());
            user.setUpdatedAt(LocalDateTime.now());
            System.out.println(Alert.COMPLETE_CHANGE);
        } else {
            System.out.println(Alert.USER_NOTFOUND);
        }
        IOFile.updateFile(IOFile.USER_PATH, userList);
    }
// thống kê số lượng người dùng mới và user mới trong tháng
    @Override
    public void getNewMovieAndNewUserInMonth() {
        System.out.println("Chọn tháng muốn lấy thống kê");
        byte month = InputMethods.getByte();
        int countMovie = (int) moviesList.stream().filter(movies -> movies.getCreatedDate().getMonthValue() == month).count();
        int countUser = (int) userList.stream().filter(user -> user.getCreatedAt().getMonthValue() == month).count();
        System.out.println("Số lượng phim mới trong tháng " + month + " là: " + countMovie);
        System.out.println("Số lượng người dung mới trong tháng " + month + " là: " + countUser);
    }
// thống kê đánh giá
    public void checkRate() {
        double averageRate = (double) rateList.stream().map(Rate::getRate).reduce(0, Integer::sum) / rateList.size();
        String direction = null;
        if (averageRate > 0 && averageRate < 3) {
            direction = "Tiêu cực (；′⌒`)";
        } else if (averageRate >= 3 && averageRate < 4) {
            direction = "Trung bình ( ͡• ͜ʖ ͡• )";
        } else if (averageRate >= 4 && averageRate <= 5) {
            direction = "Tích cực ( •̀ ω •́ )✧";
        }
        System.out.printf("Chiều hướng đánh giá: %-8s || Điểm đánh giá trung bình: %-5.2f\n", direction, averageRate);
        System.out.println("Các đánh giá hiện tại");
        Pagination.paginate(rateList, "Đánh giá này không tồn tại");
    }

    /////////////////////////////////////USER///////////////////////////////////////////////
    // hiển thị dah sách yêu thích
    @Override
    public void displayFavoriteList() {
        // lấy ra list yêu thích của người dùng đang đăng nhập
        Favorite myFavorite = favoriteList.stream().filter(favorite -> favorite.getUserId() == Login.user.getUserId()).findFirst().orElse(null);
        if (myFavorite != null) {
            myFavorite.displayData();
        } else {
            System.out.println("\u001B[31mDanh sách yêu thích của bạn trống, hãy thêm phim yêu thích của mình trước\u001B[0m");
        }
    }
// xóa khỏi danh sách yêu thích
    @Override
    public void removeFromFavoriteList() {
        // lấy ra list yêu thích của người dùng đang đăng nhập
        Favorite myFavorite = favoriteList.stream().filter(favorite -> favorite.getUserId() == Login.user.getUserId()).findFirst().orElse(null);
        if (myFavorite != null) {
            System.out.println("Nhập ID phim muốn xóa khỏi danh sách yêu thích");
            int movieId = InputMethods.getInteger();
            List<Integer> listMovieID = myFavorite.getMovieId();// lấy ra list yêu thích của tài khoản
            if (listMovieID.stream().anyMatch(myMovie -> myMovie == movieId)) { // nếu tìm thấy thì xóa khỏi danh sách
                listMovieID.remove((Integer) movieId);
                myFavorite.setMovieId(listMovieID);
            } else {
                System.out.println(Alert.MOVIE_NOTFOUND);
            }
        } else {
            System.out.println("\u001B[31mDanh sách yêu thích của bạn trống Σ(っ °Д °;)っ\u001B[0m");
        }
        IOFile.updateFile(IOFile.FAVORITE_PATH, favoriteList);
    }
//hiển thị thông tin người dùng đang đăng nhập
    @Override
    public void displayInformation() {
        Login.user.displayData();
    }
// thay đổi mật khẩu
    @Override
    public void changePassword() {
        User user = Login.user;
        while (true) {
            System.out.println("Nhập mật khẩu hiện tại: ");
            String password = InputMethods.getString();
            if (BCrypt.checkpw(password, user.getPassword())) {
                user.inputPassword();
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
                break;
            } else {
                System.out.println(Alert.WRONG_PASSWORD);
            }
        }
        Login.user.setUpdatedAt(LocalDateTime.now());
        IOFile.updateFile(IOFile.USER_PATH, userList);
    }
// thay đổi thông tin tài khoản
    @Override
    public void updateInformation() {
        User user = Login.user;
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Chọn thông tin muốn thay đổi");
            System.out.println("1. Thay đổi họ và tên");
            System.out.println("2. Thay đổi email");
            System.out.println("3. Thay đổi mật khẩu");
            System.out.println("4. Thay đổi hình đại diện");
            System.out.println("5.Thoát");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    System.out.println("Tên cũ: " + user.getFullName());
                    user.inputFullname();
                    break;
                case 2:
                    System.out.println("Email cũ: " + user.getEmail());
                    user.setEmail("");
                    user.inputEmail();
                    break;
                case 3:
                    while (true) {
                        System.out.println("Nhập mật khẩu hiện tại: ");
                        String password = InputMethods.getString();
                        if (BCrypt.checkpw(password, user.getPassword())) {
                            user.inputPassword();
                            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
                            break;
                        } else {
                            System.out.println(Alert.WRONG_PASSWORD);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Avatar cũ: " + user.getAvatar());
                    user.inputAvatar();
                    break;
                case 5:
                    isExit = true;
                    break;
                default:
                    break;
            }
        }
        IOFile.updateFile(IOFile.USER_PATH, userList);
        System.out.println(Alert.COMPLETE_CHANGE);
    }

// xem lịch sử phim
    @Override
    public void readHistory() {
        // lấy ra list lịch sử của tk đang đăng nhập
        History myHistory = historyList.stream().filter(history -> history.getUserId() == Login.user.getUserId()).findFirst().orElse(null);
        if (myHistory != null) {
            myHistory.displayData();
        } else {
            System.out.println("\u001B[31mLịch sử xem phim của bạn trống 🤡\u001B[0m");
        }
    }
//xóa tài khoản
    @Override
    public void deleteAccount() {
        System.out.println("Nhập mật khẩu hiện tại");
        String password = InputMethods.getString();
        if (BCrypt.checkpw(password, Login.user.getPassword())) {
            userList.remove(Login.user);
            IOFile.updateFile(IOFile.USER_PATH, userList);
            System.out.println("Đã xó tài khoản, đang trở lại trang đăng nhập...");
        } else {
            System.out.println(Alert.WRONG_PASSWORD);
        }
    }
}
