package business.implement;

import business.config.Alert;
import business.config.IOFile;
import business.config.InputMethods;
import business.config.Pagination;
import business.design.MovieDesign;
import business.entity.*;
import presentation.Login;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static business.implement.FavoriteImplement.favoriteList;
import static business.implement.HistoryImplement.historyList;
import static business.implement.RateImplement.rateList;

public class MovieImplement implements MovieDesign<Movies> {
    public static List<Movies> moviesList;

    static {
        File moviesFile = new File(IOFile.MOVIES_PATH);
        if (moviesFile.length() == 0) {
            moviesList = new ArrayList<>();
            IOFile.updateFile(IOFile.MOVIES_PATH, moviesList);
        } else {
            moviesList = IOFile.getFile(IOFile.MOVIES_PATH);
        }
    }

    ////////////////////////////////////////ADMIN//////////////////////////////////
    // tìm movie bằng id
    @Override
    public Movies findById() {
        System.out.println("Nhập ID phim cần tìm:");
        int movieId = InputMethods.getInteger();
        return moviesList.stream().filter(movies -> movies.getMovieId() == movieId).findFirst().orElse(null);
    }

    // thêm mới phim
    @Override
    public void createData() {
        System.out.println("Nhập số lượng phim muốn thêm: ");
        int count = InputMethods.getInteger();
        for (int i = 0; i < count; i++) {
            Movies newMovie = new Movies();
            System.out.println("Nhập thông tin cho phim thứ: " + (i + 1));
            newMovie.inputData();
            moviesList.add(newMovie);
            IOFile.updateFile(IOFile.MOVIES_PATH, moviesList);
        }
    }

    // cập nhật phim
    @Override
    public void updateData() {
        Movies movies = findById();
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Nhập thông tin muốn cập nhật");
            System.out.println("1. Thay đổi tên phim");
            System.out.println("2. Thay đổi mô tả");
            System.out.println("3. Thay đổi url video");
            System.out.println("4. Thay đổi url image");
            System.out.println("5. Thoát");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    System.out.println("Tên phim cũ: " + movies.getMovieName());
                    movies.setMovieName("");
                    movies.inputMovieName();
                    break;
                case 2:
                    System.out.println("Mô tả cũ:");
                    System.out.println(movies.getDescription());
                    movies.inputMovieDescription();
                    break;
                case 3:
                    System.out.println("Url video cũ:");
                    System.out.println(movies.getVideoUrl());
                    movies.inputMovieVideoUrl();
                    break;
                case 4:
                    System.out.println("Url image cũ:");
                    System.out.println(movies.getImageUrl());
                    movies.inputMovieImageUrl();
                    break;
                case 5:
                    isExit = true;
                    break;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
        System.out.println("Sửa thành công (～￣▽￣)～");
        movies.setUpdateDate(LocalDateTime.now());
        IOFile.updateFile(IOFile.MOVIES_PATH, moviesList);
    }

    // xóa phim
    @Override
    public void deleteData() {
        Movies movies = findById();
        if (movies != null) {
            moviesList.remove(movies);
            movies.setUpdateDate(LocalDateTime.now());
            IOFile.updateFile(IOFile.MOVIES_PATH, moviesList);
            System.out.println("Xóa thành công 👌");
        } else {
            System.out.println(Alert.MOVIE_NOTFOUND);
        }
    }

    // lấy 10 phim nhiều lượt xem nhất
    public void getTop10View() {
        System.out.println("Nhập tháng muốn nhận thống kê");
        byte month = InputMethods.getByte();
        if (month >= 1 && month <= 12) {
            // lấy ra list movie đã sắp xếp lại theo lượng view giảm dần
            List<Movies> movies = moviesList.stream().filter(movies1 -> movies1.getCreatedDate().getMonthValue() == month).sorted((o1, o2) -> o2.getView() - o1.getView()).toList();
            if (movies.size() <= 10 && !movies.isEmpty()) {// trong trường hợp list có ít hơn 10 ptu
                Pagination.paginate(movies, Alert.MOVIE_NOTFOUND);
//                movies.forEach(Movies::displayData);
            } else if (movies.size() > 10) {// trường hợp lớn hơn 10 ptu
                for (int i = 0; i < 10; i++) {
                    movies.get(i).displayData();
                }
            } else {
                System.out.println(Alert.NONE_MOVIE_ON_THE_LIST);
            }
        }
    }

    ////////////////////////////////////////USER///////////////////////////////////
    // tìm kiếm phim theo tên
    @Override
    public void searchByName() {
        System.out.println("Nhập tên phim cần tìm:");
        String movieName = InputMethods.getString();
        Movies movies = moviesList.stream().filter(movies1 -> movies1.getMovieName().equals(movieName)).findFirst().orElse(null);// tìm kiếm bằng strem
        if (movies != null) {// tìm thấy thì hiển thị thông tin
            System.out.println("Kết quả:");
            System.out.println("Tên phim: " + movies.getMovieName());
            System.out.println("Thuộc danh mục: " + movies.getCategoryName());
            System.out.println("Mô tả: " + movies.getDescription());
            System.out.println("1. Thêm vào danh sách yêu thích");
            System.out.println("2. Thoát");
            System.out.println(Alert.PLEASE_CHOSE);
            if (!Login.user.isRole()) {// cho phép người dùng thêm vào danh sách yêu thích
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        addToFavoriteList(movies);
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println(Alert.PLEASE_RE_ENTER);
                }
            }
        } else {
            System.out.println(Alert.MOVIE_NOTFOUND);
        }
    }
// hiển thị phim mới nhất
    @Override
    public void displayNewestMovie() {
        List<Movies> listNewestMovie = moviesList.stream().sorted((o1, o2) -> o2.getCreatedDate().compareTo(o1.getCreatedDate())).toList(); // sắp xếp list theo ngày tạo giảm dần
        if (!listNewestMovie.isEmpty() && listNewestMovie.size() <= 3) {// nếu líst có ít hơn 3 ptu
            listNewestMovie.forEach(Movies::displayData);
        } else if (listNewestMovie.size() > 3) { // nếu list nhiều hơn 3 ptu
            for (int i = 0; i < 3; i++) {
                listNewestMovie.get(i).displayData();
            }
        } else {
            System.out.println(Alert.NONE_MOVIE_ON_THE_LIST);
        }
    }
// hiển thị danh sách phim theo tên danh mục
    @Override
    public void displayMovieByCategory() {
        System.out.println("Nhập tên danh mục phim cần hiển thị:");
        String categoryName = InputMethods.getString();
        List<Movies> movies = moviesList.stream().filter(movies1 -> movies1.getCategoryName().equals(categoryName)).toList();
        if (movies.isEmpty()) {
            System.out.println("\u001B[31mDanh mục không có phim nào (´。＿。｀)\u001B[0m");
        } else {
            Pagination.paginate(movies, Alert.MOVIE_NOT_IN_LIST);
        }
    }
// sắp xếp movie theo tên
    @Override
    public void sortMovieByName() {
        moviesList.sort((o1, o2) -> o1.getMovieName().compareTo(o2.getMovieName()));
        IOFile.updateFile(IOFile.MOVIES_PATH, moviesList);
        displayAll();
    }
//  hiển thị chi tiết mộ bộ phim
    @Override
    public void displayMovie() {
        System.out.println("Nhập tên phim cần tìm:");
        String movieName = InputMethods.getString();
        Movies movies = moviesList.stream().filter(movies1 -> movies1.getMovieName().equals(movieName)).findFirst().orElse(null);
        if (movies != null) {
            System.out.println("Kết quả:");
            movies.displayData();
        } else {
            System.out.println(Alert.MOVIE_NOTFOUND);
        }
    }
//thêm vào favorite list
    @Override
    public void addToFavoriteList(Movies movies) {
        Favorite myFavorite = favoriteList.stream().filter(favorite -> favorite.getUserId() == Login.user.getUserId()).findFirst().orElse(null); // lấy ra favorite list của ng dùng
        if (myFavorite != null) {// nếu người dùng đã có danh sách yêu thích
            List<Integer> listMovieId = myFavorite.getMovieId();// lấy ra danh sách yêu thích
            listMovieId.add(movies.getMovieId());// thêm phim vào sanh sách
            myFavorite.setMovieId(listMovieId);// set lại danh sách
        } else {// nếu người dùng chưa có
            myFavorite = new Favorite();// tạo mới đối tượng
//            myFavorite.setMovieId(new ArrayList<>());// tạo mới danh sách yêu thích
            myFavorite.setUserId(Login.user.getUserId());// set Id của người dùng là id người dùng hiện tại
            List<Integer> listMovieId = new ArrayList<>();// tạo một list id phim
            listMovieId.add(movies.getMovieId());// thêm id của movie hiện tại vào list
            myFavorite.setMovieId(listMovieId);// set list id cho thuộc tính id của favorite vừa tạo
            favoriteList.add(myFavorite);// thêm đối tượng favorite vào list favorite
        }
        IOFile.updateFile(IOFile.FAVORITE_PATH, favoriteList);
    }
//xem phim
    @Override
    public void watchMovie() {
        boolean isExit = false;
        while (!isExit) {
            //tìm phim muốn xem
            System.out.println("1. Hiển thị danh sách phim");
            System.out.println("2. Tìm kiếm phim");
            System.out.println("3. Thoát");
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    moviesList.forEach(movies -> System.out.println((moviesList.indexOf(movies) + 1) + ". " + movies.getMovieName()));//in ra danh sách phim hiện có
                    while (true) {
                        System.out.println("Chọn phim muốn xem: ");
                        int choiceMovie = InputMethods.getInteger();
                        if (choiceMovie >= 1 && choiceMovie <= moviesList.size()) {
                            if (watchByList(choiceMovie)) {//xem phim
                                addToHistoryAndIncreaseView(choiceMovie);// thêm vào lịch sử của người dùng và tăng lượt xem của phim
                                System.out.println("1. Để lại đánh giá của bạn\n" +
                                        "2. Thoát\n" +
                                        "Nhập lựa chọn");
                                byte commentOrNot = InputMethods.getByte();// chọn để lại đánh giá hay không
                                switch (commentOrNot) {
                                    case 1:
                                        leaveComment(moviesList.get(choiceMovie - 1));
                                        return;
                                    case 2:
                                        return;
                                    default:
                                        System.out.println(Alert.PLEASE_RE_ENTER);
                                        break;
                                }
                            }
                        } else {
                            System.out.println("\u001B[31mSố thứ tự nhập vào không chính xác, mời nhập lại\u001B[0m");
                        }
                    }
                case 2:
                    System.out.println("Nhập tên phim muốn xem:");//tìm phim muốn xem
                    String movieName = InputMethods.getString();
                    if (moviesList.stream().anyMatch(movies -> movies.getMovieName().equals(movieName))) {
                        int indexMovie = moviesList.indexOf(moviesList.stream().filter(movies -> movies.getMovieName().equals(movieName)).findFirst().orElse(null));
                        if (watchByList(indexMovie + 1)) {
                            addToHistoryAndIncreaseView(indexMovie + 1);
                            System.out.println("1. Để lại đánh giá của bạn\n" +
                                    "2. Thoát");
                            System.out.println(Alert.PLEASE_CHOSE);
                            byte commentOrNot = InputMethods.getByte();
                            switch (commentOrNot) {
                                case 1:
                                    leaveComment(moviesList.get(indexMovie));
                                    return;
                                case 2:
                                    return;
                                default:
                                    System.out.println(Alert.PLEASE_RE_ENTER);
                                    break;
                            }
                            return;
                        }
                    } else {
                        System.out.println(Alert.MOVIE_NOTFOUND);
                    }
                    break;
                case 3:
                    isExit = true;
                    break;
                default:
                    break;
            }
        }
    }

    public void leaveComment(Movies movies) {
        System.out.println("Nhập đánh giá của bạn(1-5): ");
        byte rate = InputMethods.getByte();
        System.out.println("Nhập bình luận");
        String comment = InputMethods.getString();
        Rate newRate = new Rate();// tạo dtuong rate
        newRate.setUserId(Login.user.getUserId());//set các thông tin cho đối tượng
        newRate.setMovieId(movies.getMovieId());
        newRate.setRate(rate);
        newRate.setComment(comment);
        rateList.add(newRate);// thêm vào list
        IOFile.updateFile(IOFile.RATE_PATH, rateList);
    }
// thêm vào lịch sử và tăng lượt xem
    private static void addToHistoryAndIncreaseView(int choiceMovie) {
        moviesList.get(choiceMovie - 1).setView(moviesList.get(choiceMovie - 1).getView() + 1);// tăng lượt xem của phim
        History myHistory = historyList.stream().filter(history -> history.getUserId() == Login.user.getUserId()).findFirst().orElse(null);
        if (myHistory != null) {// thêm vào lịch sử nếu người dùng đã từng xem phim
            List<Movies> historyMovie = myHistory.getMovies();//thay đổi thuộc tính những phim đã xem của người dùng
            historyMovie.add(moviesList.get(choiceMovie - 1));
            myHistory.setMovies(historyMovie);
            myHistory.setUpdateDate(LocalDateTime.now());
        } else {// nếu người dùng chưa xem phim nào
            History newHistory = new History();//tạo lịch sử và set các thuộc tính
            newHistory.setUserId(Login.user.getUserId());
            List<Movies> history = new ArrayList<>();
            history.add(moviesList.get(choiceMovie - 1));
            newHistory.setMovies(history);
            myHistory = newHistory;
            historyList.add(myHistory);
        }
        IOFile.updateFile(IOFile.HISTORY_PATH, historyList);
    }
//hàm xem phim
    private static boolean watchByList(int choiceMovie) {
        System.out.println("Đang xem phim: " + moviesList.get(choiceMovie - 1).getMovieName());
        System.out.println("1.Tạm dừng");
        System.out.println("2.Dừng xem");
        System.out.println(Alert.PLEASE_CHOSE);
        byte choiceWatch = InputMethods.getByte();
        switch (choiceWatch) {
            case 1:
                System.out.println("Đang tạm dừng");
                System.out.println("1. Xem tiếp");
                System.out.println("2. Dừng xem");
                System.out.println(Alert.PLEASE_CHOSE);
                byte choiceContinue = InputMethods.getByte();
                switch (choiceContinue) {
                    case 1:
                        watchByList(choiceMovie);
                        break;
                    case 2:
                        return true;
                }
                break;
            case 2:
                return true;
            default:
                System.out.println(Alert.PLEASE_RE_ENTER);
                break;
        }
        return false;
    }

// hiển thị danh sách phim
    @Override
    public void displayAll() {
        if (moviesList.isEmpty()) {
            System.out.println(Alert.NONE_MOVIE_ON_THE_LIST);
        } else {
            Pagination.paginate(moviesList, Alert.MOVIE_NOT_IN_LIST);
        }
    }

// hiển thị danh sách phim nhưng chỉ có tên phim
    @Override
    public void displayListMovie() {
        if (!moviesList.isEmpty()) {
            System.out.println("Danh sách phim hiện có");
            for (int i = 0; i < moviesList.size(); i++) {
                System.out.println((i + 1) + ". " + moviesList.get(i).getMovieName());
            }
        } else {
            System.out.println(Alert.NONE_MOVIE_ON_THE_LIST);
        }
    }
// hiển thị những phim có lượt xem cao nhất
    public void displayMostFamoustMovie() {
        List<Movies> listMostFamousMovie = moviesList.stream().sorted((o1, o2) -> o2.getView() - (o1.getView())).toList();
        if (!listMostFamousMovie.isEmpty() && listMostFamousMovie.size() <= 3) {
            listMostFamousMovie.forEach(Movies::displayData);
        } else if (listMostFamousMovie.size() > 3) {
            for (int i = 0; i < 3; i++) {
                listMostFamousMovie.get(i).displayData();
            }
        } else {
            System.out.println(Alert.NONE_MOVIE_ON_THE_LIST);
        }
    }
}
