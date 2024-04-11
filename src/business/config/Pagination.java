package business.config;

import business.design.DisplayData;

import java.util.List;

public class Pagination {
    public static <E extends DisplayData> void paginate(List<E> list, String alertIfNull) {
        int firstIndexOfPage = 0;
        int lastIndexOfPage = 1;
        int elementPerPage = 2;
        int page = 1;
        int numberOfPage;
        if (list.size() % elementPerPage == 0) {// nếu sô ptu chia cho số ptu mỗi trang không dư
            numberOfPage = list.size() / elementPerPage;// sô trang = số ptu/ số ptu mỗi trang
        } else {
            numberOfPage = list.size() / elementPerPage + 1;// sô trang = số ptu/ số ptu mỗi trang +1
        }

        do {
            for (int i = 0; i < list.size(); i++) {
                if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                    if (list.get(i) != null) {
                        list.get(i).displayData();
                    } else {
                        System.err.println(alertIfNull);
                    }
                }
            }

            System.out.println("Trang : " + page + "/" + numberOfPage);
            if (page == 1) {
                System.out.println("2.Trang sau");
                System.out.println("3.Thoát");
            } else if (page == numberOfPage) {
                System.out.println("1.Trang Trước");
                System.out.println("3.Thoát");
            } else {
                System.out.println("1.Trang trước  ||  2.Trang sau");
                System.out.println("3.Thoát");
            }
            System.out.println("Mời nhập lựa chọn: ");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    if (page  <= numberOfPage && page -1 > 0) {
                        firstIndexOfPage -= elementPerPage;
                        lastIndexOfPage -= elementPerPage;
                        page -= 1;
                    } else {
                        System.err.println("Quá giới hạn trang,Mời nhập lại");
                    }
                    break;
                case 2:
                    if (page +1 <= numberOfPage && page > 0) {
                        firstIndexOfPage += elementPerPage;
                        lastIndexOfPage += elementPerPage;
                        page += 1;
                    } else {
                        System.err.println("Quá giới hạn trang,Mời nhập lại");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Mời nhập lại");
                    break;
            }
        }
        while (true);
    }
}