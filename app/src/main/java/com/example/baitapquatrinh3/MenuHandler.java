package com.example.baitapquatrinh3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import android.widget.Toast;

import com.example.baitapquatrinh3.Adapter.ImageAdapter;
import com.example.baitapquatrinh3.ContentProvider.ImageProvider;
import com.example.baitapquatrinh3.models.Image;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuHandler {
    private ImageAdapter imageAdapter;
    private Context context ;
    private List<Image> imageList;
    private ImageProvider imageProvider ;
    public MenuHandler(Context context) {
        this.context = context ;
        this.imageList = new ArrayList<>();
    }
    //open libary images
    public  void handleViewGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivity(intent);
        Toast.makeText(context, "Mở thư viện ảnh!", Toast.LENGTH_SHORT).show();
    }
    public void handleDeleteSelected() {
        try {
            List<Image> selectedImages = imageAdapter.getSelectedImages();
            if (selectedImages == null || selectedImages.isEmpty()) {
                Toast.makeText(context, "Chưa chọn hình ảnh nào để xóa!", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Image image : selectedImages) {
                try {
                    int rowsDeleted = context.getContentResolver().delete(
                            Uri.withAppendedPath(imageProvider.CONTENT_URI, String.valueOf(image.getId())),
                            null,
                            null
                    );

                    if (rowsDeleted == 0) {
                        Toast.makeText(context, "Không thể xóa hình ảnh: " + image.getId(), Toast.LENGTH_SHORT).show();
                    } else {
                        imageList.remove(image);
                    }
                } catch (SecurityException e) {
                    Toast.makeText(context, "Không có quyền xóa hình ảnh: " + image.getId(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Lỗi khi xóa hình ảnh: " + image.getId(), Toast.LENGTH_SHORT).show();
                }
            }

            imageAdapter.notifyDataSetChanged();
            Toast.makeText(context, "Đã xóa các mục đã chọn!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, "Đã xảy ra lỗi trong quá trình xóa!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void handleDeleteAll() {
        // Logic xóa tất cả
        Toast.makeText(context, "Đã xóa tất cả!", Toast.LENGTH_SHORT).show();
    }
//    public void loadImagesFromProvider(Context context) {
//        Cursor cursor =  context.getContentResolver().query(ImageProvider.CONTENT_URI, null, null, null, "timestamp DESC");
//
//        if (cursor != null) {
//            try {
//                int idIndex = cursor.getColumnIndex("id");
//                int imagePathIndex = cursor.getColumnIndex("image_path");
//                int timestampIndex = cursor.getColumnIndex("timestamp");
//
//                if (idIndex == -1 || imagePathIndex == -1 || timestampIndex == -1) {
//                    Log.e("loadImagesFromProvider", "Cột không tồn tại trong bảng.");
//                    return;
//                }
//
////                imageList.clear();
////                Log.d("loadImagesFromProvider", "Bắt đầu tải hình ảnh...");
//
//                while (cursor.moveToNext()) {
//                    int id = cursor.getInt(idIndex);
//                    String imagePath = cursor.getString(imagePathIndex);
//                    String timestamp = cursor.getString(timestampIndex);
//
//                    // Định dạng thời gian
//                    String formattedDate = formatTimestamp(timestamp);
//
//                    imageList.add(new Image(id, imagePath, formattedDate));
//                    Log.d("loadImagesFromProvider", "Đã thêm hình ảnh: " + imagePath + " vào " + formattedDate);
//                }
//
//                // Kiểm tra nếu có dữ liệu hình ảnh
//                if (imageList.isEmpty()) {
//                    Log.d("loadImagesFromProvider", "Không có hình ảnh nào trong database.");
//                } else {
//                    Log.d("loadImagesFromProvider", "Tải thành công " + imageList.size() + " hình ảnh.");
//                }
//
//            } catch (Exception e) {
//                Log.e("loadImagesFromProvider", "Lỗi khi load dữ liệu từ ContentProvider", e);
//            } finally {
//                cursor.close(); // Đóng con trỏ sau khi sử dụng
//            }
//        } else {
//            Log.e("loadImagesFromProvider", "Cursor null, không thể truy vấn ContentProvider.");
//        }
//        if (imageAdapter != null) {
//            imageAdapter.notifyDataSetChanged();
//        }
//    }
public void loadImagesFromProvider(Context context, ArrayList<Image> imageList) {
    Cursor cursor = context.getContentResolver().query(ImageProvider.CONTENT_URI, null, null, null, "timestamp DESC");

    if (cursor != null) {
        try {
            int idIndex = cursor.getColumnIndex("id");
            int imagePathIndex = cursor.getColumnIndex("image_path");
            int timestampIndex = cursor.getColumnIndex("timestamp");

            if (idIndex == -1 || imagePathIndex == -1 || timestampIndex == -1) {
                Log.e("loadImagesFromProvider", "Cột không tồn tại trong bảng.");
                return;
            }

            // Xóa danh sách hiện có trước khi tải lại
            imageList.clear();
            Log.d("loadImagesFromProvider", "Bắt đầu tải hình ảnh...");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String imagePath = cursor.getString(imagePathIndex);
                String timestamp = cursor.getString(timestampIndex);

                // Định dạng thời gian
                String formattedDate = formatTimestamp(timestamp);

                imageList.add(new Image(id, imagePath, formattedDate));
                Log.d("loadImagesFromProvider", "Đã thêm hình ảnh: " + imagePath + " vào " + formattedDate);
            }

            // Kiểm tra nếu có dữ liệu hình ảnh
            if (imageList.isEmpty()) {
                Log.d("loadImagesFromProvider", "Không có hình ảnh nào trong database.");
            } else {
                Log.d("loadImagesFromProvider", "Tải thành công " + imageList.size() + " hình ảnh.");
            }
        } catch (Exception e) {
            Log.e("loadImagesFromProvider", "Lỗi khi load dữ liệu từ ContentProvider", e);
        } finally {
            cursor.close(); // Đóng con trỏ sau khi sử dụng
        }
    } else {
        Log.e("loadImagesFromProvider", "Cursor null, không thể truy vấn ContentProvider.");
    }
}

    private String formatTimestamp(String timestamp) {
        try {
            // Chuyển đổi timestamp từ String sang long
            long timeInMillis = Long.parseLong(timestamp);

            // Định dạng ngày/tháng/năm, giờ:phút:giây
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss", Locale.getDefault());
            return sdf.format(new Date(timeInMillis));
        } catch (NumberFormatException e) {
            Log.e("formatTimestamp", "Lỗi định dạng timestamp: " + timestamp, e);
            return "Invalid Date";
        }
    }
    public void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this.context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa các hình ảnh đã chọn?")
                .setPositiveButton("Có", (dialog, which) -> {
                    handleDeleteSelected();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

}
