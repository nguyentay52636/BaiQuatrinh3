package com.example.baitapquatrinh3.models;

public class Image {
    private int id;
    private String filePath;
    private String formattedDate;

    public Image(int id, String filePath, String formattedDate) {
        this.id = id;
        this.filePath = filePath;
        this.formattedDate = formattedDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFormattedDate() {
        return formattedDate;
    }
}
