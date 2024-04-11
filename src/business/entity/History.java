package business.entity;

import business.config.InputMethods;
import business.config.Pagination;
import business.design.DisplayData;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static business.implement.AuthenticationImplement.userList;
import static business.implement.HistoryImplement.historyList;

public class History implements Serializable, DisplayData {
    private int historyId;
    private int userId;
    private List<Movies> movies;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    public History() {
        this.historyId = getNewId();
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public History(int userId, List<Movies> movies) {
        this.historyId = getNewId();
        this.userId = userId;
        this.movies = movies;
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    @Override
    public void displayData() {
        System.out.printf("ID: %-5d || ID người dùng: %-5d\n", this.historyId, this.userId);
        System.out.println("Các phim đã xem:");
        System.out.printf("Ngày tạo: %-10s || Cập nhật gần nhất: %-10s\n", this.createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                this.updateDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        // phân trang
        Pagination.paginate(this.movies,"Phim này đã bị khóa khỏi danh sách phim, không thể hiển thị trong lịch sử");
    }

    private int getNewId() {
        int max = historyList.stream().map(History::getHistoryId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }
}
