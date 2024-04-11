package business.design;

import business.entity.Movies;

public interface MovieDesign<Movies> extends UIDesign<Movies> {
    void searchByName();

    void displayNewestMovie();

    void displayMovieByCategory();

    void sortMovieByName();

    void displayMovie();

    void addToFavoriteList(Movies movies);

    void watchMovie();

    void getTop10View();

    void displayListMovie();

    void leaveComment(Movies movies);
    void displayMostFamoustMovie();
}
