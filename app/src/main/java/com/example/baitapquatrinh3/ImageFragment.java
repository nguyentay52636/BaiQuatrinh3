package com.example.baitapquatrinh3;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int VIEW_GALLERY = 1000010;
    public static final int DELETE_SELECTED = 1000006;
    public static final int DELETE_ALL = 1000007;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  // Cho phép fragment này có menu
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate menu từ tệp XML cho ImageFragment
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case VIEW_GALLERY:
                // Mở thư viện ảnh
                handleViewGallery();
                return true;
            case DELETE_SELECTED:
                // Xóa các mục đã chọn
                handleDeleteSelected();
                return true;
            case DELETE_ALL:
                // Xóa tất cả
                handleDeleteAll();
                return true;
            case REQUEST_IMAGE_CAPTURE:
                // Mở camera
                openCamera();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleViewGallery() {
        // Logic để mở thư viện ảnh
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, VIEW_GALLERY);
        Toast.makeText(getContext(), "Mở thư viện ảnh!", Toast.LENGTH_SHORT).show();
    }

    private void handleDeleteSelected() {
        // Logic xóa các mục đã chọn
        Toast.makeText(getContext(), "Xóa mục đã chọn!", Toast.LENGTH_SHORT).show();
    }

    private void handleDeleteAll() {
        // Logic xóa tất cả
        Toast.makeText(getContext(), "Đã xóa tất cả!", Toast.LENGTH_SHORT).show();
    }

    private void openCamera() {
        // Mở Camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
