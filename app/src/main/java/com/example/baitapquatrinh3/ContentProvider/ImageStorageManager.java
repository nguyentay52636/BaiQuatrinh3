package com.example.baitapquatrinh3.ContentProvider;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.example.baitapquatrinh3.models.Image;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
public class ImageStorageManager { }
//
//    public static void saveMetadataToJson(Context context, String fileName, List<Image> images) {
//        Gson gson = new Gson();
//        String json = gson.toJson(images);
//
//        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
//            fos.write(json.getBytes());
//            Log.d("JSONSave", "Metadata saved to JSON file: " + fileName);
//        } catch (Exception e) {
//            Log.e("JSONSaveError", "Error saving JSON data: ", e);
//        }
//    }
//
//    public static List<Image> readMetadataFromJson(Context context, String fileName) {
//        List<Image> imageList = new ArrayList<>();
//        try (FileInputStream fis = context.openFileInput(fileName)) {
//            byte[] data = new byte[fis.available()];
//            fis.read(data);
//            String json = new String(data);
//
//            // Sử dụng Gson để chuyển đổi JSON thành danh sách Image
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<Image>>(){}.getType();
//            imageList = gson.fromJson(json, type);
//
//            Log.d("JSONRead", "Metadata read from JSON file: " + fileName);
//        } catch (Exception e) {
//            Log.e("JSONReadError", "Error reading JSON data: ", e);
//        }
//        return imageList;
//    }
//}
