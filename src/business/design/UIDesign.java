package business.design;

public interface UIDesign<T> {
    T findById();

    void createData();

    void displayAll();

    void updateData();

    void deleteData();
}
