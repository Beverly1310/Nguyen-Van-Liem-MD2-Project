package business.implement;

import business.config.IOFile;
import business.entity.Rate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RateList {
    public static List<Rate> rateList;

    static {
        File rateFile = new File(IOFile.RATE_PATH);
        if (rateFile.length() == 0) {
            rateList = new ArrayList<>();
            IOFile.updateFile(IOFile.RATE_PATH, rateList);
        } else {
            rateList = IOFile.getFile(IOFile.RATE_PATH);
        }
    }
}
