package business.implement;

import business.config.IOFile;
import business.entity.History;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryImplement {
    public static List<History> historyList;
    static {
        File historyFile = new File(IOFile.HISTORY_PATH);
        if (historyFile.length() == 0) {
            historyList = new ArrayList<>();
            IOFile.updateFile(IOFile.HISTORY_PATH, historyList);
        } else {
            historyList = IOFile.getFile(IOFile.HISTORY_PATH);
        }
    }
}
