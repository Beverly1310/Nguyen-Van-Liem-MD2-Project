package business.implement;

import business.config.IOFile;
import business.entity.Favorite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FavoriteList {
    public static List<Favorite> favoriteList;
    static {
        File favoriteFile = new File(IOFile.FAVORITE_PATH);
        if (favoriteFile.length() == 0) {
            favoriteList = new ArrayList<>();
            IOFile.updateFile(IOFile.FAVORITE_PATH, favoriteList);
        } else {
            favoriteList = IOFile.getFile(IOFile.FAVORITE_PATH);
        }
    }
}
