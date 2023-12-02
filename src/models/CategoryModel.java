package models;

// import static helper.supportSystem.SupportSystem.clearConsole;
//import static helper.supportSystem.SupportSystem.toSlug;
import static helper.supportSystem.SupportSystem.formatMenuDataCol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

import com.google.gson.JsonObject;
import helper.connection.JsonConnection;
import objectsManagement.Category;

public class CategoryModel {
	
	
    public static ArrayList<Category> getArrayListCategories() throws IOException {
    	
    	List<JsonObject> arrayObjectsCateJson = JsonConnection.getJsonDataFromTable("categories");
    	
    	ArrayList<Category> categories = new ArrayList<>();
    	
    	for(JsonObject cateObj : arrayObjectsCateJson) {
    		Category tmpCate = new Category(
    				cateObj.get("name").getAsString(),
    				cateObj.get("status").getAsBoolean()
            );
    		categories.add(tmpCate);
    	}
    	
    	return categories;
    }
    
    public static void showAllCategory(boolean seacrh) throws IOException {
		out.print("\n+===========================================================================================+");
		out.print("\n|                                     DANH MỤC THỂ LOẠI                                     |");
		out.print("\n+-------------------------------------------------------------------------------------------+");
    	
    	int col = 0;
    	int col_per_row = 4;
    	String data_row = "";
    	int arr_size = getArrayListCategories().size();
    	for(int i=0; i< arr_size; i++) {
    		Category cate = getArrayListCategories().get(i);

    		if(col == col_per_row) {
    			out.print("\n"+data_row);
    			out.print("\n+-------------------------------------------------------------------------------------------+");
    			col = 0;
    			data_row = "";
    		}
    		
    		data_row += ((col==0)?"| ":" ") + formatMenuDataCol(cate.getName(), (i+1)) + " |";
    		col+=1;
    		
    		if(i == arr_size - 1) {
    			int remaining_col = col_per_row - col;
    			
    			for(int j=0; j<remaining_col; j++) {
    	    		data_row += "|||||||||||||||||||||||";
    			}
    			out.print("\n"+data_row);
    			out.print("\n+-------------------------------------------------------------------------------------------+");
    			col = 0;
    			data_row = "";
    			break;
    		}
    	}
    	if(seacrh) {
			out.printf("\n| %-45s | %-44s |","s. Tìm kiếm","0. Thoát");
		}
		

		out.print("\n+-------------------------------------------------------------------------------------------+");
		out.print("\n|                      COPYRIGHT © 2023 BY 404NOTFOUND - NGUYỄN THẾ VŨ                      |");
		out.print("\n+===========================================================================================+");
    	
    }
    
}
