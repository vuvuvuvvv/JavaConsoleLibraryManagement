package objectsManagement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import helper.supportSystem.SupportSystem;

public class User {
	private String name;
	private String slug;
	private String password;
	private int role;  //0: Admin, 1: Seller, 2: Customer
	private String tel;
	private String email;
	private String CICNumber;
	private boolean status;
	
	public User() {
		this.role = 2;
		this.status = true;
	}
	
    public User(String name, String password, int role, String tel, String email, String CICNumber, boolean status) {
        this.name = name;
        this.slug = SupportSystem.toSlug(name);
        this.password = password;
        this.role = role;
        this.tel = tel;
        this.email = email;
        this.CICNumber = CICNumber;
        this.status = status;
    }
    
    // Getter và setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setSlug();
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug() {
        this.slug = SupportSystem.toSlug(this.name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCICNumber() {
        return CICNumber;
    }

    public void setCICNumber(String CICNumber) {
        this.CICNumber = CICNumber;
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
