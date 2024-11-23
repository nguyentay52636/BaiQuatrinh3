package com.example.baitapquatrinh3;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapquatrinh3.Adapter.ImageAdapter;
import com.example.baitapquatrinh3.ContentProvider.ImageProvider;
import com.example.baitapquatrinh3.models.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 2;
    private FloatingActionButton btnCamera;
    private RecyclerView recyclerView;
    private List<Image> imageList;
    private ImageAdapter imageAdapter;
    private ImageProvider imageProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnCamera = findViewById(R.id.fabCamera);
    recyclerView = findViewById(R.id.recyclerView);
    imageList = new ArrayList<>() ;
        imageAdapter = new ImageAdapter(imageList, this::onImageItemClicked);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadImagesFromProvider();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Lấy hình ảnh từ dữ liệu trả về
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Lưu ảnh vào bộ nhớ trong hoặc bộ nhớ ngoài
            File imageFile = saveImageToFile(imageBitmap);

            // Chuyển dữ liệu ảnh vào ContentValues
            ContentValues values = new ContentValues();
            values.put("image_path", imageFile.getAbsolutePath());
            values.put("timestamp", System.currentTimeMillis());

            // Chèn ảnh vào ContentProvider
            Uri imageUri = getContentResolver().insert(ImageProvider.CONTENT_URI, values);

            if (imageUri != null) {
                Log.d("Camera", "Image successfully saved and inserted into ContentProvider");
                Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Camera", "Failed to insert image into ContentProvider");
                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Lưu ảnh vào bộ nhớ trong và trả về file
    private File saveImageToFile(Bitmap imageBitmap) {
        FileOutputStream fos = null;
        File imageFile = new File(getFilesDir(), "image_" + System.currentTimeMillis() + ".jpg");

        try {
            fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            Log.e("Camera", "Error saving image", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e("Camera", "Error closing file output stream", e);
            }
        }
        return imageFile;
    }
    private void onImageItemClicked(Image image) {
        Toast.makeText(this, "Bạn đã chọn: " + image.getFormattedDate(), Toast.LENGTH_SHORT).show();
    }
    private void loadImagesFromProvider() {

        Cursor cursor = getContentResolver().query(ImageProvider.CONTENT_URI, null, null, null, "timestamp DESC");

        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex("id");
                int imagePathIndex = cursor.getColumnIndex("image_path");
                int timestampIndex = cursor.getColumnIndex("timestamp");

                // Kiểm tra nếu cột không tồn tại
                if (idIndex == -1 || imagePathIndex == -1 || timestampIndex == -1) {
                    Log.e("loadImagesFromProvider", "Cột không tồn tại trong bảng.");
                    return;
                }

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String imagePath = cursor.getString(imagePathIndex);
                    String timestamp = cursor.getString(timestampIndex);

                    // Thêm đối tượng Image vào danh sách
                    imageList.add(new Image(id, imagePath, timestamp));
                }
            } catch (Exception e) {
                Log.e("loadImagesFromProvider", "Lỗi khi load dữ liệu từ ContentProvider", e);
            } finally {
                cursor.close(); // Đóng con trỏ sau khi sử dụng
            }
        } else {
            Log.e("loadImagesFromProvider", "Cursor null, không thể truy vấn ContentProvider.");
        }

        imageAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }





}
