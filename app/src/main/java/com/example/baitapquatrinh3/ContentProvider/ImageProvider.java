package com.example.baitapquatrinh3.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.baitapquatrinh3.Helper.DatabaseHelper;
import com.example.baitapquatrinh3.models.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.baitapquatrinh3.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/images");
    private static final int IMAGES = 1;
    private static final int IMAGE_ID = 2;

    private DatabaseHelper databaseHelper;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "images", IMAGES);
        uriMatcher.addURI(AUTHORITY, "images/#", IMAGE_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                cursor = db.query(DatabaseHelper.TABLE_IMAGES, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case IMAGE_ID:
                selection = DatabaseHelper.COLUMN_ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(DatabaseHelper.TABLE_IMAGES, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long id;

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                id = db.insert(DatabaseHelper.TABLE_IMAGES, null, values);
                if (id > 0) {
                    Uri imageUri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(id));
                    getContext().getContentResolver().notifyChange(imageUri, null);
                    return imageUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsUpdated;

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                rowsUpdated = db.update(DatabaseHelper.TABLE_IMAGES, values, selection, selectionArgs);
                break;
            case IMAGE_ID:
                selection = DatabaseHelper.COLUMN_ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                rowsUpdated = db.update(DatabaseHelper.TABLE_IMAGES, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsDeleted;

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                rowsDeleted = db.delete(DatabaseHelper.TABLE_IMAGES, selection, selectionArgs);
                break;
            case IMAGE_ID:
                selection = DatabaseHelper.COLUMN_ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                rowsDeleted = db.delete(DatabaseHelper.TABLE_IMAGES, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case IMAGES:
                return "vnd.android.cursor.dir/" + AUTHORITY + ".images";
            case IMAGE_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + ".images";
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }
    public List<Image> getAllImages() {
        List<Image> imageList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllImages();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_PATH));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIMESTAMP));

                // Tạo đối tượng Image và thêm vào danh sách
                imageList.add(new Image(id, imagePath,timestamp));
            } while (cursor.moveToNext());

            cursor.close();
        }
        return imageList;
    }
    public static List<Image> getImageList(Context context) {
        List<Image> imageList = new ArrayList<>();

        // Sử dụng ContentResolver để truy vấn dữ liệu
        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,  // URI của ContentProvider
                null,         // Các cột cần lấy
                null,         // Selection
                null,         // Selection Args
                null          // Sort Order
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Đọc dữ liệu từ Cursor
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE_PATH));
                String formattedDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIMESTAMP));

                // Thêm vào danh sách
                imageList.add(new Image(id, filePath, formattedDate));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return imageList;
    }

}

