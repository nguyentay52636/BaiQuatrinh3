package com.example.baitapquatrinh3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    public static final String TABLE_IMAGES = "images";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Câu lệnh tạo bảng
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_IMAGES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE_PATH + " TEXT, " +
                    COLUMN_TIMESTAMP + " INTEGER" +
                    ")";

    // Câu lệnh xóa bảng
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_IMAGES;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này gọi khi lần đầu tiên tạo cơ sở dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    // Phương thức này gọi khi cơ sở dữ liệu cần được nâng cấp (ví dụ thay đổi cấu trúc bảng)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    // Phương thức thêm một hình ảnh vào cơ sở dữ liệu
    public long addImage(String imagePath, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_TIMESTAMP, timestamp);

        // Chèn một dòng mới vào bảng và trả về ID của dòng được chèn
        return db.insert(TABLE_IMAGES, null, values);
    }

    // Phương thức lấy tất cả hình ảnh từ cơ sở dữ liệu
    public Cursor getAllImages() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Lấy tất cả các hàng từ bảng images, sắp xếp theo timestamp giảm dần
        return db.query(TABLE_IMAGES, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
    }

    // Phương thức xóa một hình ảnh theo ID
    public int deleteImage(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa hình ảnh với ID cụ thể
        return db.delete(TABLE_IMAGES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Phương thức cập nhật đường dẫn hình ảnh
    public int updateImagePath(long id, String newImagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_PATH, newImagePath);

        // Cập nhật hình ảnh theo ID
        return db.update(TABLE_IMAGES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
