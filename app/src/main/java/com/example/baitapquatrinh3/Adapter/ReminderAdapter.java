package com.example.baitapquatrinh3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapquatrinh3.R;
import com.example.baitapquatrinh3.models.Reminder;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminderList;

    public ReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.tvReminderTime.setText(reminder.getTime());
        holder.tvReminderTitle.setText(reminder.getTitle());
        holder.switchReminder.setChecked(reminder.isActive());

        // Thay đổi trạng thái khi bật/tắt switch
        holder.switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> reminder.setActive(isChecked));
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView tvReminderTime, tvReminderTitle;
        Switch switchReminder;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReminderTime = itemView.findViewById(R.id.tvReminderTime);
            tvReminderTitle = itemView.findViewById(R.id.tvReminderTitle);
            switchReminder = itemView.findViewById(R.id.switchReminder);
        }
    }
}
