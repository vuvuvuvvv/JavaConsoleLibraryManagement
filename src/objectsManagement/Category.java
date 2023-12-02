package objectsManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Category {
    private String name;
    private boolean status;
    
    public Category() {
    	this.status = true;
    }
    
    public Category(String name, boolean status) {
    	this.name = name;
    	this.status = status;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
