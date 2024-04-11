package business.config;

import business.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    public static final String USER_PATH = "src/business/data/user.txt";
    public static final String CATEGORY_PATH = "src/business/data/category.txt";
    public static final String MOVIES_PATH = "src/business/data/movies.txt";
    public static final String HISTORY_PATH = "src/business/data/history.txt";
    public static final String RATE_PATH = "src/business/data/rate.txt";
    public static final String FAVORITE_PATH = "src/business/data/favorite.txt";
    public static final String LOGIN_STATUS_PATH = "src/business/data/loginstatus.txt";

    // đọc dữ liệu trong file
    public static <T> List<T> getFile(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<T> list = new ArrayList<>();
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    //ghi lại dữ liệu mới vào file
    public static <T> void updateFile(String path, List<T> list) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // đọc dữ liệu trong file
    public static User getUserLogin() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        User user = new User();
        try {
            fis = new FileInputStream(LOGIN_STATUS_PATH);
            ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    //ghi lại dữ liệu mới vào file
    public static void updateUserLogin(User user) {
        File newFile = new File(IOFile.LOGIN_STATUS_PATH);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(LOGIN_STATUS_PATH);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}