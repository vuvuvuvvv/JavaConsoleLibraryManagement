package models;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
//java exception
import java.io.IOException;
import helper.connection.JsonConnection;
import java.util.ArrayList;

import objectsManagement.Book;
import objectsManagement.Category;

import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.System.err;
import java.text.DecimalFormat;
import java.util.Arrays;
import static helper.supportSystem.SupportSystem.toSlug;
import static helper.supportSystem.SupportSystem.clearConsole;

public class BookModel {
	// private

	private static DecimalFormat decimalFormat = new DecimalFormat("###,###.###"); // public

	private static List<JsonObject> getJsonBooksData() throws IOException {
		List<JsonObject> arrayObjectsBookJson = JsonConnection.getJsonDataFromTable("books");
		return arrayObjectsBookJson;
	}

	public static ArrayList<Book> getArrayListBooks() throws IOException {
		List<JsonObject> arrayObjectsBookJson = getJsonBooksData();
		ArrayList<Book> books = new ArrayList<>();
		for (JsonObject bookObj : arrayObjectsBookJson) {
			Book tmpBook = new Book(bookObj.get("name").getAsString(), bookObj.get("author").getAsString(),
					bookObj.get("categories").getAsString(),
					bookObj.get("description").getAsString(),
					bookObj.get("isFeatured").getAsBoolean(), bookObj.get("forRent").getAsBoolean(),
					bookObj.get("releaseDate").getAsString(), bookObj.get("quantity").getAsInt(),
					bookObj.get("discount").getAsInt(), bookObj.get("regularPrice").getAsInt(),
					bookObj.get("rentingPrice").getAsInt(), bookObj.get("createdAt").getAsString(),
					bookObj.get("status").getAsBoolean());
			books.add(tmpBook);
		}
		return books;
	}

	public static Book getBookByIndex(int stt) throws IOException {
		return getArrayListBooks().get(stt - 1);
	}

	private static void headerRow() {
		// 3-24-18-18-16-25-15
		out.print(
				"\n+==========================================================================================================================================+");
		out.print(
				"\n|                                                                TẤT CẢ SÁCH                                                               |");
		out.print(
				"\n+==========================================================================================================================================+");
		out.print(
				"\n| STT |         TÊN SÁCH         |       TÁC GIẢ      |      THỂ LOẠI      |  NGÀY PHÁT HÀNH  |          GIÁ BÁN         |       THUÊ      |");
		out.print(
				"\n+------------------------------------------------------------------------------------------------------------------------------------------+");
	}

	private static void footerRow() {
		out.print(
				"\n|                                             COPYRIGHT © 2023 BY 404NOTFOUND - NGUYỄN THẾ VŨ                                              |");
		out.print(
				"\n+==========================================================================================================================================+");
	}

	private static void bataRow(Book book, int stt) {
		String sellingPrice = decimalFormat.format(book.getSellingPrice()) + "VNĐ";
		if (book.getDiscount() != 0) {
			sellingPrice += " (Sale -" + book.getDiscount() + "%)";
		}
		String rentingPrice = decimalFormat.format(book.getRentingPrice()) + "VNĐ";

		ArrayList<String> bookInfo = new ArrayList<>();
		bookInfo.add(book.getName());
		bookInfo.add(book.getAuthor());
		bookInfo.add(book.getCategories());
		int[] space = new int[] { 24, 18, 18 };

		for (int j = 0; j < bookInfo.size(); j++) {
			String[] wordsArray = bookInfo.get(j).split(" ");
			String text_rows = "";
			if (toSlug(bookInfo.get(j)).length() > space[j]) {
				// Convert the array to an ArrayList
				ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));
				for (int i = 0; i < wordsList.size(); i++) {
					String word = wordsList.get(i);
					// Tru di 3 dau "." va 1 " "
					if (toSlug(text_rows).length() + toSlug(word).length() > space[j] - 3 - 1) {
						int count_space = space[j] - toSlug(text_rows).length();
						if (count_space > 2) {
							text_rows += " ...";
							for (int k = 0; k < count_space - 3 - 1; k++) {
								text_rows += " ";
							}
						} else {
							for (int k = 0; k < count_space; k++) {
								text_rows += " ";
							}
						}
						break;
					}
					text_rows += ((text_rows.length() == 0) ? "" : " ") + word;
				}
			} else {
				text_rows = bookInfo.get(j);
				int count_space = space[j] - toSlug(text_rows).length();
				if (count_space > 0) {
					for (int k = 0; k < count_space; k++) {
						text_rows += " ";
					}
				}
			}
			bookInfo.set(j, text_rows);
		}

		out.printf("\n| %-3d | " + bookInfo.get(0) + " | " + bookInfo.get(1) + " | " + bookInfo.get(2)
				+ " | %-16s | %24s | %15s |", stt, book.getReleaseDate(), sellingPrice, rentingPrice);
		out.print(
				"\n+------------------------------------------------------------------------------------------------------------------------------------------+");
	}

	public static void showAllBooks(ArrayList<Book> books) throws IOException {
		if (books == null) {
			books = getArrayListBooks();
		}
		headerRow();
		for (int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			bataRow(book, i + 1);
		}
		footerRow();
	}

	private static void detailBookDataRow(String label, String data) {
		if (toSlug(data).length() > 40) {
			String[] wordsArray = data.split(" ");
			// Convert the array to an ArrayList
			ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(wordsArray));
			String text_rows = "";
			int row = 0;
			for (int i = 0; i < wordsList.size(); i++) {
				String word = wordsList.get(i);

				if (toSlug(text_rows).length() + toSlug(word).length() > 39) {
					int count_space = 40 - toSlug(text_rows).length();
					if (count_space > 0) {
						for (int j = 0; j < count_space; j++) {
							text_rows += " ";
						}
					}
					if (row == 0) {
						out.print("\n|" + label + "| " + text_rows + " |");
					} else {
						out.print("\n|                        | " + text_rows + " |");
					}
					text_rows = "";
					row += 1;
				}
				text_rows += ((text_rows.length() == 0) ? "" : " ") + word;
				if (i == wordsList.size() - 1) {
					int count_space = 40 - toSlug(text_rows).length();
					if (count_space != 0) {
						for (int j = 0; j < count_space; j++) {
							text_rows += " ";
						}
					}

					out.print("\n|                        | " + text_rows + " |");
				}
			}
		} else {
			if (toSlug(data).length() != 40) {
				int count_space = 40 - toSlug(data).length();
				for (int j = 0; j < count_space; j++) {
					data += " ";
				}
			}
			out.print("\n|" + label + "| " + data + " |");
		}
		out.print("\n+-------------------------------------------------------------------+");
	}

	public static void showDetailBook(Book book) throws IOException {

		// Book book = getArrayListBooks().get(stt - 1);

		String sellingPrice = decimalFormat.format(book.getSellingPrice()) + "VNĐ";
		if (book.getDiscount() != 0) {
			String discount = "(Sale -" + book.getDiscount() + "%)";
			sellingPrice += discount;
		}
		String rentingPrice = decimalFormat.format(book.getRentingPrice()) + "VNĐ";

		out.print("\n+===================================================================+");
		out.print("\n|                      THÔNG TIN CHI TIẾT SÁCH                      |");
		out.print("\n+-------------------------------------------------------------------+");
		detailBookDataRow(" TÊN SÁCH               ", book.getName());
		detailBookDataRow(" TÁC GIẢ                ", book.getAuthor());
		detailBookDataRow(" THỂ LOẠI               ", book.getAuthor());
		detailBookDataRow(" NGÀY XUẤT BẢN          ", book.getReleaseDate());
		detailBookDataRow(" GIÁ BÁN                ", sellingPrice);
		detailBookDataRow(" THUÊ                   ", rentingPrice);
		detailBookDataRow(" MÔ TẢ                  ", book.getDescription());
		out.print("\n|           COPYRIGHT © 2023 BY 404NOTFOUND - NGUYỄN THẾ VŨ         |");
		out.print("\n+===================================================================+");
	}

	public static ArrayList<Book> getBooksByQueryName(String query) throws IOException {
		// Cho ve chuoi slug, be nho quey thanh cac word
		String[] q_words = toSlug(query).split("-");

		ArrayList<Book> queryBooks = new ArrayList<>();
		for (Book book : getArrayListBooks()) {
			// So khop tung word voi slug
			for (String w : q_words) {
				if (book.getSlug().contains(w)) {
					queryBooks.add(book);
					break;
				}
			}

		}
		return queryBooks;
	}

	private static ArrayList<Book> getBooksByCategories(ArrayList<String> cates) throws IOException {

		ArrayList<Book> queryBooks = new ArrayList<>();

		for (Book book : getArrayListBooks()) {
			boolean match = true;
			// So khop tung word voi slug
			for (String cate : cates) {
				if (!book.getCategories().contains(cate)) {
					match = false;
					break;
				}
			}
			if (match) {
				queryBooks.add(book);
				break;
			}

		}
		return queryBooks;
	}

	public static ArrayList<Book> searchByCate(Scanner scanner) throws IOException, InterruptedException {
		ArrayList<Book> books = null;
		ArrayList<Integer> choices = new ArrayList<>();
		ArrayList<String> selected_cates = new ArrayList<>();
		ArrayList<Category> cates = CategoryModel.getArrayListCategories();
		while (true) {
			out.println("\n================================================================");
			CategoryModel.showAllCategory(true);
			for (Integer k = 0; k < cates.size(); k++) {
				choices.add(k + 1);
			}
			err.println("\n*NOTE: Bạn có thể chọn nhiều mục, nếu đã đủ hãy nhập 's' để tìm kiếm!");
			if (selected_cates.size() > 0) {
				out.print("Loại đã chọn: ");
				for (String cate : selected_cates) {
					out.print(cate + " ");
				}
				out.println("");
			}
			out.print("\nChọn thể loại để tìm kiếm: ");
			String sl_cate = scanner.next();
			out.println(sl_cate);
			try {
				int intValue = Integer.parseInt(sl_cate);
				if (choices.contains(intValue)) {
					selected_cates.add(cates.get(intValue - 1).getName());
				} else {
					err.println("Không hợp lệ! Mời nhập lại");
					Thread.sleep(1000);
				}
			} catch (NumberFormatException e) {
				out.print(sl_cate);
				if (sl_cate.equals("s")) {
					books = BookModel.getBooksByCategories(selected_cates);
					break;
				} else {
					err.println("Không hợp lệ! Mời nhập lại");
					Thread.sleep(1000);
				}
			}
		}
		return books;
	}

	public static void addBooks(Scanner scanner) throws IOException {
		Book book = new Book();

		book.input(scanner);
		out.println("\n================================================================");
		out.print("Xem lại: ");
		BookModel.showDetailBook(book);
		boolean valid = false;
		do {
			out.println("\nBạn muốn lưu?");
			out.println("1. Có");
			out.println("2. Không");
			out.print("Chọn một số (1 hoặc 2): ");

			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();
				scanner.nextLine();

				if (choice == 1) {
					// Get list jsonObjetct
					List<JsonObject> books = getJsonBooksData();
					// Convert from String to JsonObject
					JsonObject bookJsonObj = new Gson().fromJson(book.toJson(), JsonObject.class);
					// Add bookJsonObj to list jsonObject
					books.add(bookJsonObj);

					JsonConnection.updateDataToJson("books", books);

					valid = true;
				} else if (choice == 2) {

					valid = true;
				} else {
					out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
				}
			} else {
				out.println("Vui lòng nhập một số.");
				scanner.nextLine();
			}
		} while (!valid);

	}

	public static void removeBooks(Scanner scanner) throws IOException, InterruptedException {
		boolean valid = true;
		do {
			out.print("Chọn sách để xóa: ");

			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();
				scanner.nextLine();

				if (choice >= 1 && choice <= getArrayListBooks().size()) {
					// Get list jsonObjetct
					List<JsonObject> books = getJsonBooksData();
					books.remove(choice - 1);

					JsonConnection.updateDataToJson("books", books);

					valid = true;
				} else {
					out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
				}
			} else {
				out.println("Vui lòng nhập một số.");
				scanner.nextLine();
			}
		} while (!valid);
		out.print("\nĐang xóa...");
		Thread.sleep(1000);
		out.print("\n Xóa thành công!");
		Thread.sleep(1000);
	}

	public static void editBooks(Scanner scanner) throws IOException, InterruptedException {
		boolean valid = true;
		BookModel.showAllBooks(getArrayListBooks());
		do {
			out.print("\nChọn sách để sửa: ");

			if (scanner.hasNextInt()) {
				int choice = scanner.nextInt();
				scanner.nextLine();

				if (choice >= 1 && choice <= getArrayListBooks().size()) {
					Book book = new Book();

					book.input(scanner);
					out.println("\n================================================================");
					out.print("Xem lại: ");
					BookModel.showDetailBook(book);
					boolean sl_valid = false;
					do {
						out.println("\nBạn muốn lưu?");
						out.println("1. Có");
						out.println("2. Không");
						out.print("Chọn một số (1 hoặc 2): ");

						if (scanner.hasNextInt()) {
							int s_choice = scanner.nextInt();
							scanner.nextLine();

							if (s_choice == 1) {
								// Get list jsonObjetct
								List<JsonObject> books = getJsonBooksData();
								// Convert from String to JsonObject
								JsonObject bookJsonObj = new Gson().fromJson(book.toJson(), JsonObject.class);
								// Add bookJsonObj to list jsonObject
								books.set(s_choice -1, bookJsonObj);

								JsonConnection.updateDataToJson("books", books);

								sl_valid = true;
							} else if (s_choice == 2) {

								sl_valid = true;
							} else {
								out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
							}
						} else {
							out.println("Vui lòng nhập một số.");
							scanner.nextLine();
						}
					} while (!sl_valid);
				} else {
					out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
				}
			} else {
				out.println("Vui lòng nhập một số.");
				scanner.nextLine();
			}
		} while (!valid);
		out.print("\nĐang thay đổi...");
		Thread.sleep(1000);
		out.print("\n Xóa thành công!");
		Thread.sleep(1000);
	}

}
