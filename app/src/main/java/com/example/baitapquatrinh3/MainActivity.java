package com.example.baitapquatrinh3;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapquatrinh3.Adapter.ImageAdapter;
import com.example.baitapquatrinh3.ChildrenActivity.ReminderListActivity;
import com.example.baitapquatrinh3.ContentProvider.ImageProvider;
import com.example.baitapquatrinh3.Helper.MenuHandler;
import com.example.baitapquatrinh3.models.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private FloatingActionButton btnCamera;
    private RecyclerView recyclerView;
    private ArrayList<Image> imageList;
    private ImageAdapter imageAdapter;
    private ImageProvider imageProvider;
    private FrameLayout container;
    private MenuHandler menuHandler ;
    private  FloatingActionButton btnCalendar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuHandler = new MenuHandler(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnCamera = findViewById(R.id.fabCamera);
        recyclerView = findViewById(R.id.recyclerView);
        btnCalendar  =   findViewById(R.id.fabCalender);
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageList, this::onImageItemClicked);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuHandler.loadImagesFromProvider(getApplicationContext(),imageList);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReminderListActivity.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.view_gallery) {
                    menuHandler.handleViewGallery();
                    return true;

                } else if (itemId == R.id.delete_selected) {
//                    showDeleteConfirmationDialog();
                    handleDeleteSelected();

                    return true;
                } else if (itemId == R.id.delete_all) {
                    menuHandler.handleDeleteAll();
                    return true;
                } else if (itemId == R.id.open_camera) {
                    openCamera();
                    return true;
                } else {
                    return false;
                }
            }
        });


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera() ;
            }
        });

    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
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
        menuHandler.loadImagesFromProvider(getApplicationContext(),imageList);
    }

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



//    public void showDeleteConfirmationDialog() {
//        new AlertDialog.Builder(MainActivity.this)
//                .setTitle("Xác nhận xóa")
//                .setMessage("Bạn có chắc chắn muốn xóa các hình ảnh đã chọn?")
//                .setPositiveButton("Có", (dialog, which) -> {
//                    handleDeleteSelected();
//                })
//                .setNegativeButton("Hủy", null)
//                .show();
//    }

    public void handleDeleteSelected () {
        List<Image> selectedImages = imageAdapter.getSelectedImages();

        int selectedCount = selectedImages.size();
        if (selectedCount > 0) {
            imageAdapter.deleteSelectedImages();
//            menuHandler.loadImagesFromProvider(getApplicationContext(),imageList);
            Toast.makeText(MainActivity.this, "Đã xóa " + selectedCount + " ảnh", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Chưa chọn ảnh nào!", Toast.LENGTH_SHORT).show();
        }
    }
}




