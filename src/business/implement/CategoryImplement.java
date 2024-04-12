package business.implement;

import business.config.Alert;
import business.config.IOFile;
import business.config.InputMethods;
import business.config.Pagination;
import business.design.CategoryDesign;
import business.entity.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import static business.implement.MovieImplement.moviesList;

public class CategoryImplement implements CategoryDesign<Category> {
    public static List<Category> categoryList;

    static {
        File categoryFile = new File(IOFile.CATEGORY_PATH);
        if (categoryFile.length() == 0) {
            categoryList = new ArrayList<>();
            IOFile.updateFile(IOFile.CATEGORY_PATH, categoryList);
        } else {
            categoryList = IOFile.getFile(IOFile.CATEGORY_PATH);
        }
    }
// tìm category theo id
    @Override
    public void searchCategory() {
        Category category = findById();
        if (category != null) {
            category.displayData();
        } else {
            System.out.println(Alert.CATEGORY_NOTFOUND);
        }
    }
// trả về đối tượng category thông qua id
    @Override
    public Category findById() {
        System.out.println("Nhập ID danh mục cần tìm:");
        int categoryId = InputMethods.getInteger();
        return categoryList.stream().filter(category -> category.getCategoryId() == categoryId).findFirst().orElse(null);
    }
// thêm mới category
    @Override
    public void createData() {
        System.out.println("Nhập số lượng danh mục muốn thêm");
        int count = InputMethods.getInteger();
        for (int i = 0; i < count; i++) {
            Category newCategory = new Category();
            System.out.println("Nhập thông tin cho danh mục thứ " + (i + 1));
            newCategory.inputData();
            categoryList.add(newCategory);
            IOFile.updateFile(IOFile.CATEGORY_PATH, categoryList);
        }
    }
// hiển thị danh sách category
    @Override
    public void displayAll() {
        if (categoryList.isEmpty()){
            System.out.println("\u001B[31mDanh sách danh mục trống🤡\u001B[0m");
        } else {
            Pagination.paginate(categoryList,Alert.CATEGORY_NOTFOUND);
        }
    }
// cập nhật category
    @Override
    public void updateData() {
        Category category = findById();
        if (category != null) {
            boolean isExit = false;
            while (!isExit) {
                System.out.println("Thay đổi thông tin cho danh mục");
                System.out.println("1. Tên danh mục");
                System.out.println("2. Mô tả");
                System.out.println("3. Trạng thái");
                System.out.println("4. Thoát");
                System.out.println(Alert.PLEASE_CHOSE);
                byte choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        System.out.println("Tên cũ: " + category.getCategoryName());
                        category.setCategoryName("");
                        category.inputCategoryName();
                        break;
                    case 2:
                        System.out.println("Mô tả cũ: ");
                        System.out.println(category.getDescription());
                        category.inputCategoryDescription();
                        break;
                    case 3:
                        System.out.println("Trạng thái cũ: " + category.isStatus());
                        category.inputCategoryStatus();
                        break;
                    case 4:
                        isExit = true;
                        break;
                    default:
                        System.out.println(Alert.PLEASE_RE_ENTER);
                        break;
                }
            }
        }
        IOFile.updateFile(IOFile.CATEGORY_PATH, categoryList);
    }
//xóa category
    @Override
    public void deleteData() {
        Category category = findById();
        if (moviesList.stream().noneMatch(movies -> movies.getCategoryName().equals(category.getCategoryName()))) {
            if (category != null) {
                categoryList.remove(category);
                System.out.println("Xóa thành công👌");
                IOFile.updateFile(IOFile.CATEGORY_PATH, categoryList);
            } else {
                System.out.println(Alert.CATEGORY_NOTFOUND);
            }
        }else {
            System.out.println("\u001B[31mDanh mục đã có phim, không thể xóa😒\u001B[0m");
        }
    }
}
