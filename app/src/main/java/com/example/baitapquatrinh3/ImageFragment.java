package com.example.baitapquatrinh3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;

public class ImageFragment extends Fragment {

    private ImageView imageView;
    private TextView textViewDate;
    private CheckBox checkBox;

    // Truyền dữ liệu vào Fragment qua Bundle
    private static final String ARG_IMAGE_PATH = "image_path";
    private static final String ARG_TIMESTAMP = "timestamp";

    public static ImageFragment newInstance(String imagePath, long timestamp) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_PATH, imagePath);
        args.putLong(ARG_TIMESTAMP, timestamp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_items, container, false);

        imageView = rootView.findViewById(R.id.imageView);
        textViewDate = rootView.findViewById(R.id.textViewDate);
        checkBox = rootView.findViewById(R.id.checkBox);

        // Lấy dữ liệu từ Bundle
        if (getArguments() != null) {
            String imagePath = getArguments().getString(ARG_IMAGE_PATH);
            long timestamp = getArguments().getLong(ARG_TIMESTAMP);

            // Hiển thị hình ảnh bằng Glide (hoặc các thư viện khác)
            Glide.with(this)
                    .load(imagePath)
                    .centerCrop()
                    .into(imageView);

            // Hiển thị thời gian
            textViewDate.setText("Date: " + timestamp);
        }

        return rootView;
    }
}
