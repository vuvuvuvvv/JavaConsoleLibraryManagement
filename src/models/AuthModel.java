package models;

import java.io.IOException;
import java.util.Scanner;

import objectsManagement.User;

import static java.lang.System.out;
import static models.UserModel.getListUsers;
import static helper.supportSystem.SupportSystem.clearConsole;

public class AuthModel {
	private static boolean loggedIn;

	private static User currentUser;

	public AuthModel() {
		loggedIn = false;
		currentUser = null;
	}

	public boolean isAuthenticated() {
		return loggedIn;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public boolean isAdmin() {
		return checkRole(0);
	}

	public boolean isSeller() {
		return checkRole(0);
	}

	private boolean checkRole(int role) {
		User user = getCurrentUser();
		if (user != null) {
			if (user.getRole() == role)
				return true;
			return false;
		}
		return false;
	}

	public User authenticate(String account, String password) throws IOException {
		account = account.trim();
		password = password.trim();
		for (User user : getListUsers()) {
			String acc;
			if (account.indexOf("@") != -1) {
				acc = user.getEmail();
			} else {
				acc = user.getTel();
			}

			if (account.equals(acc)) {
				if (password.equals(user.getPassword())) {
					this.setCurrentUser(user);
					loggedIn = true;
					return user;
				} else {
					return null;
				}
			}

		}
		return null;
	}

	public void login(Scanner scanner) throws IOException, InterruptedException {
		while (true) {
			out.println("\n================================================================");
			out.print("\n+===========================================================+");
			out.print("\n|                         ĐĂNG NHẬP                         |");
			out.println("\n+-----------------------------------------------------------+");
			out.print("Email hoặc số điện thoại đăng ký: ");
			var acc = scanner.next();
			out.print("Nhập mật khẩu                   : ");
			var pw = scanner.next();
			User user = authenticate(acc, pw);
			if (user != null) {
				out.println("\n================================================================");
				out.print("\n Đăng nhập thành công! Đang chuyển hướng...");
				Thread.sleep(1500);
				break;
			} else {
				out.println("\n================================================================");
				out.print("\n Tài khoản hoặc mật khẩu không chính xác!");
				Thread.sleep(1500);
				break;
			}
		}
	}

	public void logout() throws IOException, InterruptedException {
		loggedIn = false;
		currentUser = null;
		out.println("\n================================================================");
		out.print("\n Đang đăng xuất, vui lòng đợi...");
		Thread.sleep(1500);
	}

}
