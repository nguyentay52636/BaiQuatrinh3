package com.example.baitapquatrinh3;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.baitapquatrinh3.CalendarReminder.AlarmReceiver;

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

        // Kiểm tra nếu người dùng đã nhập mô tả
        if (description.isEmpty()) {
            reminderDescription.setError("Please enter a reminder description");
            return;
        }

        // Cài đặt thời gian cho nhắc nhở
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Nếu thời gian đã qua, đặt lại cho ngày hôm sau
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Tạo một Intent để gửi nhắc nhở
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("reminder_text", description);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Cài đặt AlarmManager để thông báo vào thời gian đã chọn
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
