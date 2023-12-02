package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import helper.connection.JsonConnection;
import objectsManagement.SaledBooks;

public class SaledBookModel {
	
	
    public static ArrayList<SaledBooks> getListSaledBooks() throws IOException {
    	
    	List<JsonObject> arrayObjectsSaledBooksJson = JsonConnection.getJsonDataFromTable("saled_books");
    	
    	ArrayList<SaledBooks> saledBooks = new ArrayList<>(arrayObjectsSaledBooksJson.size());
    	
    	
    	for(JsonObject saledBooksObj : arrayObjectsSaledBooksJson) {
    		SaledBooks tmpSaledBooks = new SaledBooks(
    				saledBooksObj.get("tel").getAsString(),
    				saledBooksObj.get("userName").getAsString(),
    				saledBooksObj.get("booksName").getAsString(),
    				saledBooksObj.get("totalPrice").getAsInt(),
    				saledBooksObj.get("createdAt").getAsString()
            );
    		saledBooks.add(tmpSaledBooks);
    	}
    	
    	return saledBooks;
    }
}
