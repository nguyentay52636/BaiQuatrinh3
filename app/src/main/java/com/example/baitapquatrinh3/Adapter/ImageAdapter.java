package com.example.baitapquatrinh3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapquatrinh3.R;
import com.example.baitapquatrinh3.models.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Image> images;
    private OnItemClickListener listener;
    private List<Boolean> selectedItems;
    // Interface cho sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClicked(Image image);
    }

    // Constructor cho Adapter
    public ImageAdapter(List<Image> images, OnItemClickListener listener) {
        this.images = images;
        this.listener = listener;
        if (images != null && !images.isEmpty()) {
            selectedItems = new ArrayList<>(Collections.nCopies(images.size(), false));
        } else {
            selectedItems = new ArrayList<>();
        }


    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = images.get(position);

        // Sử dụng Glide để tải ảnh
        Glide.with(holder.imageView.getContext())
                .load(new File(image.getFilePath()))
                .into(holder.imageView);

        // Hiển thị ngày giờ định dạng
        holder.textViewDate.setText(image.getFormattedDate());
        holder.textViewId.setText("ID: " + image.getId());

//        holder.checkBox.setChecked(selectedItems.get(position));
//        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            selectedItems.set(position, isChecked);
//        });
        holder.itemView.setOnClickListener(v -> listener.onItemClicked(image));
//
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    // ViewHolder để chứa các thành phần giao diện của mỗi item
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewDate;
        CheckBox checkBox;
        TextView textViewId  ;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            checkBox = itemView.findViewById(R.id.checkBox);
            imageView = itemView.findViewById(R.id.imageView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
    public List<Image> getSelectedImages() {
        List<Image> selectedImages = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i)) {
                selectedImages.add(images.get(i));
            }
        }
        return selectedImages;
    }

}
