package objectsManagement;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import helper.supportSystem.SupportSystem;
import models.CategoryModel;
import static helper.supportSystem.SupportSystem.clearConsole;

import static helper.supportSystem.SupportSystem.toSlug;
import static java.lang.System.out;
import static java.lang.System.err;

import java.io.IOException;

public class Book {
    // private int id;
    private String name;
    private String slug;
    private String author;
    private String categories;
    private String description;
    private boolean isFeatured;
    private boolean forRent;
    private String releaseDate; // Assuming release_date is a single character
    private Integer quantity;
    private Integer discount;
    private Integer regularPrice;
    private Integer sellingPrice;
    private Integer rentingPrice;
    private String createdAt;
    private boolean status;

    // Constructors, getters, and setters

    public Book() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the date and time (optional)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDateTime = currentDateTime.format(formatter);
        this.createdAt = formattedDateTime;
        this.status = true;
    }

    public Book(String name, String author, String categories, String description, boolean isFeatured,
            boolean forRent, String releaseDate, int quantity, int discount, int regularPrice, int rentingPrice,
            String createdAt, boolean status) {
        // this.id = id;
        this.name = name;
        this.slug = SupportSystem.toSlug(name);
        this.author = author;
        this.categories = categories;
        this.description = description;
        this.isFeatured = isFeatured;
        this.forRent = forRent;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
        this.discount = discount;
        this.regularPrice = regularPrice;
        if (discount > 0) {
            this.sellingPrice = Math.round(this.regularPrice * (100 - this.discount) / 100);
        } else {
            this.sellingPrice = this.regularPrice;
        }
        this.rentingPrice = rentingPrice;
        this.createdAt = createdAt;
        this.status = status;
    }

    public void input(Scanner scanner) throws IOException {
        out.println("\n================================================================");
        out.print("Nhập tên sách: ");
        String name = scanner.nextLine();
        setName(name);
        setSlug(toSlug(name));
//         scanner.nextLine();

        out.println("\n================================================================");
        out.print("Nhập tác giả: ");
        String author = scanner.nextLine();
        setAuthor(author);
//         scanner.nextLine();
        ArrayList<Category> cates = CategoryModel.getArrayListCategories();
        ArrayList<String> selected_cates = new ArrayList<>();
        int size = cates.size();
        boolean valid = false;
        CategoryModel.showAllCategory(false);
        do {
            out.println("\n================================================================");
            err.println("\nNOTE: Nhập 's' để lưu!");

            if (selected_cates.size() > 0) {
                out.print("\nDanh mục đã chọn: ");

                for (String c_name : selected_cates) {
                    out.print(c_name + " ");
                }
                out.print("\n");
            }

            out.print("Chọn danh mục: ");
            try {
                String str_cateid = scanner.next();
                scanner.nextLine();
                if (str_cateid.equals("s")) {
                    if (selected_cates.size() == 0) {
                        err.println("Dữ liệu nhập vào không hợp lệ!");
                    } else {
                        String tmp_name = "";
                        for (int i = 0; i < selected_cates.size(); i++) {
                            tmp_name += selected_cates.get(i) + ((i == selected_cates.size() - 1) ? "" : ", ");
                        }
                        setCategories(tmp_name);
                        valid = true;
                    }
                } else {
                    int int_cateid = Integer.parseInt(str_cateid);
                    if (int_cateid < 1 || int_cateid > size) {
                        err.println("Dữ liệu nhập vào không hợp lệ!");
                    } else {
                        Category tmp_cate = cates.get(int_cateid - 1);
                        if (selected_cates.indexOf(tmp_cate.getName()) != 0) {
                            selected_cates.add(tmp_cate.getName());
                        }
                    }

                }
            } catch (NumberFormatException e) {
                err.println("Dữ liệu nhập vào không hợp lệ!");
            }

        } while (!valid);

        out.println("\n================================================================");
        out.print("Nhập mô tả: ");
        setDescription(scanner.nextLine());
//         scanner.nextLine();

        valid = false;
        do {
            out.println("\n================================================================");
            out.println("Đặt làm sách nổi bật?");
            out.println("1. Có");
            out.println("2. Không");
            out.print("Chọn một số (1 hoặc 2): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    setFeatured(true);
                    valid = true;
                } else if (choice == 2) {
                    setFeatured(false);
                    valid = true;
                } else {
                    out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } else {
                out.println("Vui lòng nhập một số.");
                scanner.nextLine();
            }
        } while (!valid);

        boolean is_rent = false;
        valid = false;
        out.println("\n================================================================");
        do {
            out.println("Có đem cho thuê?");
            out.println("1. Có");
            out.println("2. Không");
            out.print("Chọn một số (1 hoặc 2): ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    is_rent = true;
                    valid = true;
                } else if (choice == 2) {
                    is_rent = false;
                    valid = true;
                } else {
                    out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } else {
                out.println("Vui lòng nhập một số.");
                scanner.nextLine();
            }
        } while (!valid);
        setForRent(is_rent);

        if (is_rent) {
            out.print("Nhập giá thuê: ");
            setRentingPrice(scanner.nextInt());
            scanner.nextLine();
        } else {
            setRentingPrice(null);
        }

        out.println("\n================================================================");
        out.print("Nhập ngày phát hành: ");
        setReleaseDate(scanner.next());

        valid = false;
        Integer quantity = 0;
        out.println("\n================================================================");
        do {
            out.print("Nhập số lượng: ");

            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();

                if (quantity >= 0) {
                    setQuantity(quantity);
                    valid = true;
                } else {
                    out.println("Dữ liệu không hợp lệ.");
                }
            } else {
                out.println("Vui lòng nhập một số nguyên.");
                scanner.nextLine();
            }
        } while (!valid);

        valid = false;
        Integer price = 0;
        out.println("\n================================================================");
        do {
            out.print("Nhập giá ban đầu: ");

            if (scanner.hasNextInt()) {
                price = scanner.nextInt();

                if (price >= 0) {
                    setRegularPrice(price);
                    valid = true;
                } else {
                    out.println("Dữ liệu không hợp lệ.");
                }
            } else {
                out.println("Vui lòng nhập một số nguyên.");
                scanner.nextLine();
            }
        } while (!valid);

        valid = false;
        Integer discountValue = 0;
        out.println("\n================================================================");
        do {
            out.print("Nhập giảm giá (từ 0 đến 100): ");

            if (scanner.hasNextInt()) {
                discountValue = scanner.nextInt();

                if (discountValue >= 0 && discountValue <= 100) {
                    setDiscount(discountValue);
                    valid = true;
                } else {
                    out.println("Giảm giá phải nằm trong khoảng từ 0 đến 100. Vui lòng nhập lại.");
                }
            } else {
                out.println("Vui lòng nhập một số nguyên.");
                scanner.nextLine();
            }
        } while (!valid);

        out.println("\n================================================================");
        setSellingPrice(price * (1 - discountValue/100));
        scanner.nextLine();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String category) {
        this.categories = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isForRent() {
        return forRent;
    }

    public void setForRent(boolean forRent) {
        this.forRent = forRent;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getRentingPrice() {
        return rentingPrice;
    }

    public void setRentingPrice(Integer rentingPrice) {
        this.rentingPrice = rentingPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
