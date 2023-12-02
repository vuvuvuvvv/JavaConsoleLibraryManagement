import java.io.IOException;
import java.util.List;

import java.util.Scanner;

import models.BookModel;
import models.UserModel;
import models.CategoryModel;
import models.RentedBookModel;
import models.SaledBookModel;
import models.AuthModel;
import objectsManagement.Book;
import objectsManagement.User;
import objectsManagement.Category;
import objectsManagement.RentedBooks;
import objectsManagement.SaledBooks;

import static helper.supportSystem.SupportSystem.formatMenuDataCol;
import static helper.supportSystem.SupportSystem.toSlug;
import static helper.supportSystem.SupportSystem.clearConsole;
import static java.lang.System.out;
import static java.lang.System.err;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class App {

	private static AuthModel auth = new AuthModel();

	public static void main(String[] args) throws IOException, InterruptedException {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				out.println("\n================================================================");
				menuStart();
				var select = scanner.next();

				if (auth.isAuthenticated()) {

					ArrayList<Book> books = null;
					Integer size_lib = null;

					if (auth.isAdmin()) {
						boolean loop_menu = true;
						switch (select) {
						case "0":
							out.print("\n Tạm biệt!");
							scanner.close();
							System.exit(0);
							break;
						case "1":
							while (loop_menu) {
								out.println("\n================================================================");
								ArrayList<String> menu = new ArrayList<>();
								menu.add("Toàn bộ người dùng");
								menu.add("Thêm người dùng");
								menu.add("Xóa người dùng");
								menu.add("Sửa thông tin");
								menu.add("Quay lại");
								out.print(
										"\n+===========================================================================================+");
								out.print(
										"\n|                                     QUẢN LÝ NGƯỜI DÙNG                                    |");
								out.print(
										"\n+-------------------------------------------MENU--------------------------------------------+");
								generateMenuDataRow(menu);
								generateFooterMenu();
								out.print("\n Chọn menu: ");

								select = scanner.next();
								switch (select) {
								case "0":
									loop_menu = false;
									break;
								case "1":
									auth.login(scanner);
									break;
								default:
									out.print("Không hợp lệ! Mời nhập lại.");
									break;
								}
							}
							break;
						case "2":
							out.println("\n================================================================");
							String query = "";
							boolean searched = false;
							boolean sorted = false;
							boolean filtered = false;
							while (loop_menu) {
								ArrayList<String> menu = new ArrayList<>();
								if (books == null) {
									books = BookModel.getArrayListBooks();
								}
								size_lib = books.size();
								// DOING:
								menu.add("Tìm kiếm");
								menu.add("Xem sách");
								menu.add("Thêm sách");
								menu.add("Xóa");
								menu.add("Sửa thông tin");
								// menu.add("Sắp xếp");
								if (searched) {
									menu.add("Xóa tìm kiếm");
									out.print("\nKẾT QUẢ TÌM KIẾM CHO: \"" + query + "\"");
								}
								if (sorted) {
									menu.add("Xóa sắp xếp");
								}
								if (filtered) {
									menu.add("Xóa bộ lọc");
								}
								menu.add("Quay lại");
								BookModel.showAllBooks(books);
								generateMenuDataRow(menu);
								generateFooterMenu();
								out.print("\n Chọn menu: ");
								select = scanner.next();
								switch (select) {
								case "0":
									loop_menu = false;
									break;
								case "1":
									boolean func_loop = true;
									while (func_loop) {
										ArrayList<String> func_menu = new ArrayList<>();
										func_menu.add("Theo tên");
										func_menu.add("Theo thể loại");
										// func_menu.add("Tên + thể loại");
										func_menu.add("Quay lại");
										out.print(
												"\n+-------------------------------------------MENU--------------------------------------------+");
										generateMenuDataRow(func_menu);
										// generateFooterMenu();
										out.print("\n Chọn menu: ");
										select = scanner.next();
										switch (select) {
										case "0":
											func_loop = false;
											break;
										case "1": // SEARCH:query
											out.println(
													"\n================================================================");
											out.print("\nNhập từ khóa để tìm kiếm:");
											query = scanner.next();
											out.println(
													"\n================================================================");
											books = BookModel.getBooksByQueryName(query);
											searched = true;
											func_loop = false;
											break;
										case "2": // SEARCH:cate
											books = BookModel.searchByCate(scanner);
											// searched = true;
											func_loop = false;
											break;
										// case "3":
										// auth.login(scanner);
										// func_loop = false;
										// break;
										default:
											err.print("Không hợp lệ! Mời nhập lại.");
											Thread.sleep(1000);
											break;
										}
									}
									// loop_menu = true;
									break;
								case "2":
									boolean valid = false;
									do {
										out.println(
												"\n================================================================");

										out.print("Chọn sách để xem: ");
										try {
											String str_bookid = scanner.next();
											scanner.nextLine();
											int int_bookid = Integer.parseInt(str_bookid);
											if (int_bookid < 1 || int_bookid > size_lib) {
												err.println("Dữ liệu nhập vào không hợp lệ!");
											} else {
												BookModel.showDetailBook(BookModel.getBookByIndex(int_bookid));
												boolean sl_valid = false;
												out.print("\n1. Quay lại ");
												do {
													out.print("\nChọn: ");
													try {
														String sl = scanner.next();
														scanner.nextLine();
														int int_sl = Integer.parseInt(sl);
														if (int_sl != 1) {
															err.println("Dữ liệu nhập vào không hợp lệ!");
														} else {
															valid = true;
															sl_valid = true;
														}
													} catch (NumberFormatException e) {
														err.println("Dữ liệu nhập vào không hợp lệ!");
													}
												} while (!sl_valid);
											}
										} catch (NumberFormatException e) {
											err.println("Dữ liệu nhập vào không hợp lệ!");
										}
									} while (!valid);

									break;
								case "3":
									BookModel.addBooks(scanner);
									break;
								case "4":
									BookModel.removeBooks(scanner);
									break;
								case "5":
									BookModel.editBooks(scanner);
									break;
								case "6":
									if (searched) {
										books = null;
										searched = false;
									} else if (sorted) {
										books = null;
										sorted = false;
									} else if (filtered) {
										books = null;
										filtered = false;
									} else {
										out.print("Không hợp lệ! Mời nhập lại.");
									}
									break;
								default:
									err.print("Không hợp lệ! Mời nhập lại.");
									Thread.sleep(1000);
									break;
								}
							}
							break;
						case "3":
							while (loop_menu) {
								out.println("\n================================================================");
								ArrayList<String> menu = new ArrayList<>();
								menu.add("DS sách đang thuê");
								menu.add("DS sách đã trả");
								menu.add("Xóa");
								menu.add("Sửa thông tin");
								menu.add("Quay lại");
								out.print(
										"\n+===========================================================================================+");
								out.print(
										"\n|                                   QUẢN LÝ SÁCH ĐÃ THUÊ                                    |");
								out.print(
										"\n+-------------------------------------------MENU--------------------------------------------+");
								generateMenuDataRow(menu);
								generateFooterMenu();
								out.print("\n Chọn menu: ");
								select = scanner.next();
								switch (select) {
								case "0":
									loop_menu = false;
									break;
								case "1":
									auth.login(scanner);
									break;
								default:
									out.print("Không hợp lệ! Mời nhập lại.");
									break;
								}
							}
							break;
						case "4":
							while (loop_menu) {
								out.println("\n================================================================");
								ArrayList<String> menu = new ArrayList<>();
								menu.add("DS sách đã bán");
								menu.add("Xóa");
								menu.add("Quay lại");
								out.print(
										"\n+===========================================================================================+");
								out.print(
										"\n|                                    QUẢN LÝ SÁCH ĐÃ BÁN                                     |");
								out.print(
										"\n+-------------------------------------------MENU--------------------------------------------+");
								generateMenuDataRow(menu);
								generateFooterMenu();
								out.print("\n Chọn menu: ");
								select = scanner.next();
								switch (select) {
								case "0":
									loop_menu = false;
									break;
								case "1":
									auth.login(scanner);
									break;
								default:
									out.print("Không hợp lệ! Mời nhập lại.");
									break;
								}
							}
							break;
						case "5":
							while (loop_menu) {
								out.println("\n================================================================");
								ArrayList<String> menu = new ArrayList<>();
								menu.add("DS thể loại");
								menu.add("Thêm thể loại");
								menu.add("Xóa thể loại");
								menu.add("Sửa thông tin");
								menu.add("Quay lại");
								out.print(
										"\n+===========================================================================================+");
								out.print(
										"\n|                                     QUẢN LÝ THỂ LOẠI                                      |");
								out.print(
										"\n+-------------------------------------------MENU--------------------------------------------+");
								generateMenuDataRow(menu);
								generateFooterMenu();
								out.print("\n Chọn menu: ");
								select = scanner.next();
								switch (select) {
								case "0":
									loop_menu = false;
									break;
								case "1":
									auth.login(scanner);
									break;
								default:
									out.print("Không hợp lệ! Mời nhập lại.");
									break;
								}
							}
							break;
						case "6":
							auth.login(scanner);
							break;
						case "7":
							auth.logout();
							break;
						default:
							out.print("Không hợp lệ! Mời nhập lại.");
							break;
						}
					} else if (auth.isSeller()) {
					} else {
					}
				} else {
					switch (select) {
					case "0":
						out.print("\n Tạm biệt!");
						scanner.close();
						System.exit(0);
						break;
					case "1":
						auth.login(scanner);
						break;
					default:
						err.print("Không hợp lệ! Mời nhập lại.");
						Thread.sleep(1000);
						break;
					}
				}

			}

		}

	}

	private static void menuStart() {
		// String[] arr_menu = new String[] {""};
		// ArrayList<String> menu = new ArrayList<>(Arrays.asList(arr_menu));
		ArrayList<String> menu = new ArrayList<>();

		if (auth.isAuthenticated()) {
			if (auth.isAdmin()) {
				menu.add("QL người dùng");
				menu.add("QL sách");
				menu.add("QL sách đã thuê");
				menu.add("QL sách đã bán");
				menu.add("QL thể loại");
				menu.add("Thống kê");
				menu.add("Đăng xuất");
			} else if (auth.isSeller()) {
				menu.add("QL người dùng");
				menu.add("QL sách đã thuê");
				menu.add("QL sách đã bán");
				menu.add("Đăng xuất");
			} else {
				menu.add("Toàn bộ sách");
				menu.add("Sách nổi bật");
				menu.add("Tìm sách");
				menu.add("Sách đã mượn");
				menu.add("Sách đã mua");
				menu.add("Đăng xuất");
			}
		} else {
			menu.add("Đăng nhập");
		}
		menu.add("Thoát");
		if (auth.isAuthenticated()) {
			out.print("Xin chào " + auth.getCurrentUser().getName());
		}
		out.print("\n+===========================================================================================+");
		out.print(
				"\n|                         CHÀO MỪNG BẠN ĐẾN VỚI THƯ VIỆN 404NOTFOUND                        |");
		out.print("\n+-------------------------------------------MENU--------------------------------------------+");
		generateMenuDataRow(menu);
		generateFooterMenu();
		out.print("\n Chọn menu: ");
	}

	private static void generateMenuDataRow(ArrayList<String> arr) {
		int col = 0;
		int col_per_row = 4;
		String data_row = "";
		int arr_size = arr.size();
		for (int i = 0; i < arr_size; i++) {
			String item = arr.get(i);

			if (col == col_per_row) {
				out.print("\n" + data_row);
				out.print(
						"\n+-------------------------------------------------------------------------------------------+");
				col = 0;
				data_row = "";
			}

			data_row += ((col == 0) ? "| " : " ") + formatMenuDataCol(item, (i != arr_size - 1) ? (i + 1) : (0)) + " |";
			col += 1;

			if (i == arr_size - 1) {
				int remaining_col = col_per_row - col;

				for (int j = 0; j < remaining_col; j++) {
					data_row += "|||||||||||||||||||||||";
				}
				out.print("\n" + data_row);
				out.print(
						"\n+-------------------------------------------------------------------------------------------+");
				col = 0;
				data_row = "";
				break;
			}
		}
	}

	private static void generateFooterMenu() {
		out.print("\n|                      COPYRIGHT © 2023 BY 404NOTFOUND - NGUYỄN THẾ VŨ                      |");
		out.print("\n+===========================================================================================+");
	}
}
