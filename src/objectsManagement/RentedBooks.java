package objectsManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RentedBooks {
	private String userTel;
	private String booksName;
	private int totalPrice;
	private String dateEnd;
	private String createdAt;
	private boolean status;
	
	public RentedBooks() {
    	LocalDateTime currentDateTime = LocalDateTime.now();
    	
        // Format the date and time (optional)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        //Time for rent is 10 days
        //The rental period is 10 days
        this.dateEnd = currentDateTime.plusDays(10).format(formatter);
        this.createdAt = currentDateTime.format(formatter);
        this.status = true;
	}
	
	public RentedBooks(String userTel, String books, int totalPrice, String dateEnd,String createdAt, boolean status) {
		this.userTel = userTel;
		this.booksName = books;
		this.totalPrice = totalPrice;
		this.dateEnd = dateEnd;
		this.createdAt = createdAt;
		this.status = status;
	}

    // Getter và Setter cho userTel
    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    // Getter và Setter cho booksName
    public String getBooksName() {
        return booksName;
    }

    public void setBooksName(String booksName) {
        this.booksName = booksName;
    }

    // Getter và Setter cho totalPrice
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Getter và Setter cho dateEnd
    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    // Getter và Setter cho createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Getter và Setter cho status
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
