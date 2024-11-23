package com.example.baitapquatrinh3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapquatrinh3.R;
import com.example.baitapquatrinh3.models.Image;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Image> images;
    private OnItemClickListener listener;

    // Interface cho sự kiện click vào item
    public interface OnItemClickListener {
        void onItemClicked(Image image);
    }

    // Constructor cho Adapter
    public ImageAdapter(List<Image> images, OnItemClickListener listener) {
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item trong RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        // Gán dữ liệu cho từng item
        Image image = images.get(position);

        // Sử dụng Glide để tải ảnh
        Glide.with(holder.imageView.getContext())
                .load(new File(image.getFilePath())) // Đường dẫn file
                .into(holder.imageView);

        // Hiển thị ngày giờ định dạng
        holder.textViewDate.setText(image.getFormattedDate());

        // Thiết lập sự kiện khi click vào item
        holder.itemView.setOnClickListener(v -> listener.onItemClicked(image));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    // ViewHolder để chứa các thành phần giao diện của mỗi item
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewDate;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
