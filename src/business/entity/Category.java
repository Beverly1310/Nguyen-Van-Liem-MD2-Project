package business.entity;

import business.config.InputMethods;
import business.design.DisplayData;

import java.io.Serializable;
import java.util.Comparator;

import static business.implement.AuthenticationImplement.userList;
import static business.implement.CategoryImplement.categoryList;

public class Category implements Serializable, DisplayData {
    private int categoryId;
    private String categoryName;
    private String description;
    private boolean status;

    public Category() {
        this.categoryId = getNewId();
        this.status = true;
    }

    public Category(String categoryName, String description) {
        this.categoryId = getNewId();
        this.categoryName = categoryName;
        this.description = description;
        this.status = true;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void inputData() {
        inputCategoryName();
        inputCategoryDescription();
        inputCategoryStatus();
    }

    private int getNewId() {
        int max = categoryList.stream().map(Category::getCategoryId).max(Comparator.naturalOrder()).orElse(0);
        return max + 1;
    }

    public void inputCategoryName() {
        while (true) {
            System.out.println("Nhập tên danh mục:");
            String categoryName = InputMethods.getString();
            if (categoryName.isBlank()) {
                System.err.println("Tên danh mục không được để trống");
                ;
            } else {
                if (categoryList.stream().noneMatch(category -> category.getCategoryName().equals(categoryName))) {
                    this.categoryName = categoryName;
                    return;
                } else {
                    System.err.println("Tên danh mục đã tồn tại");
                }
            }
        }
    }

    public void inputCategoryDescription() {
        System.out.println("Nhập mô tả:");
        this.description = InputMethods.getString();
    }

    public void inputCategoryStatus() {
        System.out.println("Nhập trạng thái:");
        this.status = InputMethods.getBoolean();
    }
    @Override
    public void displayData() {
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("ID: %-5d || Tên danh mục: %-10s\n", this.categoryId, this.categoryName);
        System.out.printf("Mô tả: %-15s\n", this.description);
        System.out.printf("Trạng thái: %-5s\n", this.status ? "Hoạt động" : "Không hoạt động");
        System.out.println("--------------------------------------------------------------------------");
    }
}
