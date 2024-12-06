package com.example.baitapquatrinh3.ChildrenActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.baitapquatrinh3.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_PATH = "image_path";
    public static final String EXTRA_IMAGE_PATHS = "image_paths";
    public ViewPager2 viewPager;
    private List<String> imagePaths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_details_image);

        PhotoView photoView = findViewById(R.id.photoViewDetails);

        Intent intent = getIntent();
        String imagePath = intent.getStringExtra(EXTRA_IMAGE_PATH);
        Glide.with(this)
                .load(imagePath)
                .into(photoView);
    }




    //
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Kiểm tra cử chỉ vuốt 3 ngón tay
            if (e1.getPointerCount() == 3 && e2.getPointerCount() == 3) {
                if (distanceX > 100) { // Vuốt qua phải
                    showNextImage();
                } else if (distanceX < -100) { // Vuốt qua trái
                    showPreviousImage();
                }
            }
            return true;
        }
    }

    // Chuyển đến ảnh kế tiếp
    private void showNextImage() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < imagePaths.size() - 1) {
            viewPager.setCurrentItem(currentItem + 1);
        } else {
            Toast.makeText(this, "Đã đến ảnh cuối cùng!", Toast.LENGTH_SHORT).show();
        }
    }

    // Chuyển đến ảnh trước
    private void showPreviousImage() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > 0) {
            viewPager.setCurrentItem(currentItem - 1);
        } else {
            Toast.makeText(this, "Đã đến ảnh đầu tiên!", Toast.LENGTH_SHORT).show();
        }
    }
}

