package business.entity;

import business.design.DisplayData;

import java.io.Serializable;

public class Rate implements Serializable,  DisplayData {
    private int rateId;
    private int userId;
    private int rate;
    private int movieId;
    private String comment;

    public Rate() {
    }

    public Rate(int rateId, int userId, int rate, int movieId, String comment) {
        this.rateId = rateId;
        this.userId = userId;
        this.rate = rate;
        this.movieId = movieId;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    @Override
    public void displayData() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("ID: %-5s || ID người dung: %-5s || ID phim: %-5s\n", this.rateId, this.userId, this.movieId);
        System.out.printf("Số điểm: %-5s\n", this.rate);
        System.out.printf("Bình luận: %-15s\n", this.comment);
        System.out.println("--------------------------------------------------------------------------");
    }
}
