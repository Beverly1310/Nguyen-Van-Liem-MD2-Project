package business.config;

import business.design.DisplayData;

import java.util.List;

public class Pagination {
    public static <E extends DisplayData> void paginate(List<E> list, String alertIfNull) {
        int firstIndexOfPage = 0;// chỉ số đầu của trang
        int lastIndexOfPage = 1;// chỉ số cuối của trang
        int elementPerPage = 2;// số ptu của trang
        int page = 1;// trang hiện tại
        int numberOfPage;// số trang
        if (list.size() % elementPerPage == 0) {// nếu tổ số ptu chia cho số ptu mỗi trang không dư
            numberOfPage = list.size() / elementPerPage;// sô trang = số ptu/ số ptu mỗi trang
        } else {
            numberOfPage = list.size() / elementPerPage + 1;// sô trang = số ptu/ số ptu mỗi trang +1
        }
          if (list.isEmpty()){
              numberOfPage=1;
        }
          // in ra các phtu
        do {
            for (int i = 0; i < list.size(); i++) {
                if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                    if (list.get(i) != null) {
                        list.get(i).displayData();
                    } else {
                        list.remove(list.get(i));
                        System.err.println(alertIfNull);
                    }
                }
            }
// in nút điều hướng
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
            // chọn nút điều hướng
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
