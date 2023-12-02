package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import helper.connection.JsonConnection;
import objectsManagement.RentedBooks;

public class RentedBookModel {
	
	
    public static ArrayList<RentedBooks> getListRentedBooks() throws IOException {
    	
    	List<JsonObject> arrayObjectsRentedBooksJson = JsonConnection.getJsonDataFromTable("rented_books");
    	
    	ArrayList<RentedBooks> rentedBooks = new ArrayList<>(arrayObjectsRentedBooksJson.size());
    	
    	
    	for(JsonObject rentedBooksObj : arrayObjectsRentedBooksJson) {
    		RentedBooks tmpSaledBooks = new RentedBooks(
    				rentedBooksObj.get("userTel").getAsString(),
    				rentedBooksObj.get("booksName").getAsString(),
    				rentedBooksObj.get("totalPrice").getAsInt(),
    				rentedBooksObj.get("dateEnd").getAsString(),
    				rentedBooksObj.get("createdAt").getAsString(),
    				rentedBooksObj.get("status").getAsBoolean()
            );
    		rentedBooks.add(tmpSaledBooks);
    	}
    	
    	return rentedBooks;
    }
}
