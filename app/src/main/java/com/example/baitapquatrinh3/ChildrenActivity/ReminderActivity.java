package com.example.baitapquatrinh3.ChildrenActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.baitapquatrinh3.CalendarReminder.AlarmReceiver;
import com.example.baitapquatrinh3.R;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText reminderDescription;
    private Button setReminderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        timePicker = findViewById(R.id.timePicker);
        reminderDescription = findViewById(R.id.reminderDescription);
        setReminderButton = findViewById(R.id.setReminderButton);

        setReminderButton.setOnClickListener(v -> setReminder());
    }

    private void setReminder() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String description = reminderDescription.getText().toString();

        if (description.isEmpty()) {
            reminderDescription.setError("Please enter a reminder description");
            return;
        }

        // Tạo thời gian cho lịch nhắc
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Đặt ý định cho AlarmReceiver
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("reminder_text", description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Reminder created successfully for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
        Intent reminderListIntent = new Intent(ReminderActivity.this, ReminderListActivity.class);
        startActivity(reminderListIntent);
    }

}
