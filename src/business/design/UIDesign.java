package business.design;

public interface UIDesign<E> {
    E findById();

    void createData();

    void displayAll();

    void updateData();

    void deleteData();
}
