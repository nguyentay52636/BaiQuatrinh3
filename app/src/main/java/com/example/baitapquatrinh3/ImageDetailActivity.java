package com.example.baitapquatrinh3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageDetailActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_PATH = "image_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_image);

        PhotoView photoView = findViewById(R.id.photoViewDetails);

        // Lấy đường dẫn ảnh từ Intent
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra(EXTRA_IMAGE_PATH);

        // Load ảnh vào PhotoView bằng Glide
        Glide.with(this)
                .load(imagePath)
                .into(photoView);
    }
}
