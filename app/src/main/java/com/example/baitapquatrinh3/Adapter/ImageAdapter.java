package com.example.baitapquatrinh3.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baitapquatrinh3.ImageDetailActivity;
import com.example.baitapquatrinh3.R;
import com.example.baitapquatrinh3.models.Image;
import com.github.chrisbanes.photoview.PhotoView;

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
            selectedItems = new ArrayList<>(); // Nếu không có dữ liệu, khởi tạo selectedItems rỗng
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
        Glide.with(holder.photoView.getContext())
                .load(new File(image.getFilePath()))
                .into(holder.photoView);
        holder.textViewDate.setText(image.getFormattedDate());
        holder.textViewId.setText("ID: " + image.getId());

        if (selectedItems.size() > position) {
            holder.checkBox.setChecked(selectedItems.get(position));
        }

        holder.itemView.setOnClickListener(v->  {
            Toast.makeText(holder.itemView.getContext(), "Bạn vừa chọn ảnh ID số: " + image.getId(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(holder.itemView.getContext(), ImageDetailActivity.class);
            intent.putExtra(ImageDetailActivity.EXTRA_IMAGE_PATH,image.getFilePath());
//
            holder.itemView.getContext().startActivity(intent);

        });
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (selectedItems.size() > position) {
                selectedItems.set(position, isChecked);
            }
        });


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    // ViewHolder để chứa các thành phần giao diện của mỗi item
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        PhotoView photoView;
        TextView textViewDate;
        CheckBox checkBox;
        TextView textViewId  ;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            checkBox = itemView.findViewById(R.id.checkBox);
            photoView = itemView.findViewById(R.id.photo_view);
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
    public void deleteSelectedImages() {
        List<Image> selectedImages = getSelectedImages();
        images.removeAll(selectedImages);

        // Cập nhật lại danh sách selectedItems
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedItems.set(i, false);
        }
        notifyDataSetChanged();  // Đảm bảo RecyclerView được cập nhật
    }



}
