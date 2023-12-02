package objectsManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaledBooks {
	private String tel;
	private String userName;
	private String booksName;
	private int totalPrice;
	private String createdAt;
	
	public SaledBooks() {
    	LocalDateTime currentDateTime = LocalDateTime.now();
    	
        // Format the date and time (optional)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedDateTime = currentDateTime.format(formatter);
        this.createdAt = formattedDateTime;
	}
	
	public SaledBooks(String tel, String userName, String books, int totalPrice,String createdAt) {
		this.tel = tel;
		this.userName = userName;
		this.booksName = books;
		this.totalPrice = totalPrice;
		this.createdAt = createdAt;
	}

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBooksName() {
        return booksName;
    }

    public void setBooksName(String booksName) {
        this.booksName = booksName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String geCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
