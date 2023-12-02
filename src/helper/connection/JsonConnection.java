package helper.connection;

//gson
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

//import static java.lang.System.out;

//list
import java.util.List;
import java.util.ArrayList;

//java writer
import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
//java exception
import java.io.IOException;


interface JsonConvertible {
    String toJson();
}


public class JsonConnection {
    private static String filePath = ".\\data\\database.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // protected String tableName;
    //
    // public JsonConnection(String tableName) {
    // this.tableName = tableName;
    // }

    private static JsonObject getJsonObjectFromDB() throws UnsupportedEncodingException, FileNotFoundException {
        // Tạo luồng đọc tệp, chuyển dữ liệu đọc được thành các ký tự
        // (UTF-8)->phân tích cú pháp dữ liệu JSON từ tệp tin đã được mở.
        JsonReader reader = new JsonReader(new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath),
                        "UTF-8")));

        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        return jsonObject;
    }

    public static List<JsonObject> getJsonDataFromTable(String tableName) throws IOException {
        JsonObject jsonObject = getJsonObjectFromDB();
        JsonArray jsonArray = jsonObject.getAsJsonArray(tableName);
        List<JsonObject> dataList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            dataList.add(jsonArray.get(i).getAsJsonObject());
        }
        return dataList;
    }

    public static void updateDataToJson(String tableName, List<JsonObject> newData)
            throws UnsupportedEncodingException, FileNotFoundException {

        JsonObject jsonObject = getJsonObjectFromDB();

        JsonArray jsonArray = new JsonArray();

        for (JsonObject object : newData) {
            jsonArray.add(object);
        }
        jsonObject.add(tableName, jsonArray);
        saveJsonObjectToFile(jsonObject);
    }

    private static void saveJsonObjectToFile(JsonObject jsonObject) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}