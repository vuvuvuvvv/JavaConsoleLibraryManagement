package models;

import java.util.List;
import com.google.gson.JsonObject;
//java exception
import java.io.IOException;
import helper.connection.JsonConnection;
import objectsManagement.User;

import java.util.ArrayList;

public class UserModel {
	
	
	public UserModel() {
	}
	
	
    public static ArrayList<User> getListUsers() throws IOException {
    	
    	List<JsonObject> arrayObjectsUserJson = JsonConnection.getJsonDataFromTable("users");
    	
    	ArrayList<User> users = new ArrayList<>(arrayObjectsUserJson.size());
    	
    	
    	for(JsonObject userObj : arrayObjectsUserJson) {
    		User tmpUser = new User(
    				userObj.get("name").getAsString(),
    				userObj.get("password").getAsString(),
    				userObj.get("role").getAsInt(),
    				userObj.get("tel").getAsString(),
    				userObj.get("email").getAsString(),
    				userObj.get("CICNumber").getAsString(),
    				userObj.get("status").getAsBoolean()
            );
    		users.add(tmpUser);
    	}
    	
    	return users;
    }
}
