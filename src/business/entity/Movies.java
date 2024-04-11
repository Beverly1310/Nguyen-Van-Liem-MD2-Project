package business.entity;

import business.config.InputMethods;
import business.design.DisplayData;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import static business.implement.CategoryImplement.categoryList;
import static business.implement.MovieImplement.moviesList;

public class Movies implements Serializable, DisplayData {
    private int movieId;
    private String movieName;
    private String description;
    private String videoUrl;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private String categoryName;
    private int view;

    public Movies() {
        this.movieId = getNewId();
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.view = 0;
    }

    public Movies(String movieName, String description, String videoUrl, String imageUrl, String categoryName) {
        this.movieId = getNewId();
        this.movieName = movieName;
        this.description = description;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.categoryName = categoryName;
        this.view = 0;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public void inputData() {
        inputMovieName();
        inputCategoryName();
        inputMovieDescription();
        inputMovieVideoUrl();
        inputMovieImageUrl();
    }

    private int getNewId() {
        int max = moviesList.stream().map(Movies::getMovieId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }

    public void inputMovieName() {
        while (true) {
            System.out.println("Nhập tên phim:");
            String movieName = InputMethods.getString();
            if (movieName.isBlank()) {
                System.err.println("Tên phim không được để trống");
                ;
            } else {
                if (moviesList.stream().noneMatch(movies -> movies.getMovieName().equals(movieName))) {
                    this.movieName = movieName;
                    return;
                } else {
                    System.err.println("Tên phim đã tồn tại");
                }
            }
        }
    }

    public void inputCategoryName() {
        while (true) {
            System.out.println("Nhập danh mục cho phim: ");
            categoryList.forEach(category -> System.out.println(category.getCategoryId() + " " + category.getCategoryName()));
            String categoryName = InputMethods.getString();
            if (categoryList.stream().anyMatch(category -> category.getCategoryName().equals(categoryName))) {
                this.categoryName = categoryName;
                return;
            } else {
                System.err.println("Danh mục không tồn tại, mời nhập lại.");
            }
        }
    }

    public void inputMovieDescription() {
        System.out.println("Nhập mô tả:");
        this.description = InputMethods.getString();
    }

    public void inputMovieVideoUrl() {
        System.out.println("Nhập url video:");
        this.videoUrl = InputMethods.getString();
    }

    public void inputMovieImageUrl() {
        System.out.println("Nhập url hình ảnh:");
        this.imageUrl = InputMethods.getString();
    }
    @Override
    public void displayData() {
        System.out.printf("ID: %-5d || Tên phim: %-10s || Lượt xem: %-5d\n", this.movieId, this.movieName,this.view);
        System.out.printf("Mô tả: %-15s\n", this.description);
        System.out.printf("Video Url: %-15s\n", this.videoUrl);
        System.out.printf("Image Url: %-15s\n", this.imageUrl);
        System.out.printf("Ngày tạo: %-10s || Cập nhật gần nhất: %-10s\n", this.createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                this.updateDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("-------------------------------------------------------------");
    }
}
