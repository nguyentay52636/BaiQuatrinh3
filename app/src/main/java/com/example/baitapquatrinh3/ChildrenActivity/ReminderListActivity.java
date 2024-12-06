package com.example.baitapquatrinh3.ChildrenActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitapquatrinh3.Adapter.ReminderAdapter;
import com.example.baitapquatrinh3.R;
import com.example.baitapquatrinh3.models.Reminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ReminderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private List<Reminder> reminderList;
    private FloatingActionButton btnAddReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        btnAddReminder = findViewById(R.id.btbFabCalendar) ;
        btnAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderListActivity.this, ReminderActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReminders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Reminder> reminders = new ArrayList<>();
        reminders.add(new Reminder("08:00 AM", "Đi làm", true));
        reminders.add(new Reminder("12:00 PM", "Ăn trưa", false));
        reminders.add(new Reminder("07:00 PM", "Tập thể dục", true));
        ReminderAdapter adapter = new ReminderAdapter(reminders);
        recyclerView.setAdapter(adapter);

    }

}
