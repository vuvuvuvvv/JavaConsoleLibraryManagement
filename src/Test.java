import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
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

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(new InputStreamReader(System.in, "UTF-8"));
        scanner.useLocale(new Locale("vi", "VN"));
//
//		ArrayList<Integer> choices = new ArrayList<>();
//		ArrayList<String> selected_cates = new ArrayList<>();
//		ArrayList<Category> cates = CategoryModel
//				.getArrayListCategories();
//		

//		BookModel.addBooks(scanner);
		BookModel.editBooks(scanner);
//		BookModel.showDetailBook(BookModel.getBookByIndex(1));
	}
}
