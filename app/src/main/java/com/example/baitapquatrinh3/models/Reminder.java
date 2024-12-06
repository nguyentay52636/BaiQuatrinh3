package com.example.baitapquatrinh3.models;

public class Reminder {
    private String time;
    private String title;
    private boolean isActive;

    public Reminder(String time, String title, boolean isActive) {
        this.time = time;
        this.title = title;
        this.isActive = isActive;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
