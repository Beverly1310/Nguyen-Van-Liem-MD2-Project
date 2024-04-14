package business.entity;

import business.config.Alert;
import business.config.InputMethods;
import business.design.Displayable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import static business.implement.FavoriteList.favoriteList;
import static business.implement.MovieImplement.moviesList;

public class Favorite implements Serializable, Displayable {
    private int favoriteId;
    private int userId;
    private List<Integer> movieId;

    public Favorite() {
        this.favoriteId = getNewId();
    }

    public Favorite(int favoriteId, int userId, List<Integer> movieId) {
        this.favoriteId = getNewId();
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getMovieId() {
        return movieId;
    }

    public void setMovieId(List<Integer> movieId) {
        this.movieId = movieId;
    }
    @Override
    public void displayData() {
        System.out.printf("ID: %-5d || ID người dung: %-5d", this.favoriteId, this.userId);
        System.out.println("Danh sách phim yêu thích");
//        this.movieId.forEach(System.out::println);
        paginate();
    }
    private void paginate() {
        int firstIndexOfPage = 0;
        int lastIndexOfPage = 2;
        int elementPerPage = 2;
        int page = 1;
        int numberOfPage;
        if (this.movieId.size() % elementPerPage == 0) {// nếu sô ptu chia cho số ptu mỗi trang không dư
            numberOfPage = this.movieId.size() / elementPerPage;// sô trang = số ptu/ số ptu mỗi trang
        } else {
            numberOfPage = this.movieId.size() / elementPerPage + 1;// sô trang = số ptu/ số ptu mỗi trang +1
        }
        do {
            for (int i = 0; i < this.movieId.size(); i++) {
                if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                   int movieId = this.movieId.get(i);
                    Movies favouriteMovie = moviesList.stream().filter(movies -> movies.getMovieId()==movieId).findFirst().orElse(null);
                    if (favouriteMovie != null) {
                        favouriteMovie.displayData();
                    } else {
                        System.out.println("\u001B[31mPhim này đã bị xóa khỏi danh sách phim, không thể hiển thị trong danh sách yêu thích\u001B[0m");
                        this.movieId.remove(this.movieId.get(i));
                    }
                }
            }
            System.out.println("Trang : " + page + "/" + numberOfPage);
            if (page == 1) {
                System.out.println("2.Trang sau");
                System.out.println("3.Thoát");
            } else if (page == numberOfPage) {
                System.out.println("1.Trang Trước");
                System.out.println("3.Thoát");
            } else {
                System.out.println("1.Trang trước  ||  2.Trang sau");
                System.out.println("3.Thoát");
            }
            System.out.println(Alert.PLEASE_CHOSE);
            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    if (page <= numberOfPage && page >= 0) {
                        firstIndexOfPage -= elementPerPage;
                        lastIndexOfPage -= elementPerPage;
                        page -= 1;
                    }
                    break;
                case 2:
                    if (page <= numberOfPage && page >= 0) {
                        firstIndexOfPage += elementPerPage;
                        lastIndexOfPage += elementPerPage;
                        page += 1;
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println(Alert.PLEASE_RE_ENTER);
                    break;
            }
        }
        while (true);
    }
    private int getNewId() {
        int max = favoriteList.stream().map(Favorite::getFavoriteId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }
}
