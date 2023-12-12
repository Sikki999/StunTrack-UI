package com.example.appscoba.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "crud";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "nama";
    private static final String COLUMN_EMAIL = "email";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                    map.put("name", cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    map.put("email", cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                    list.add(map);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Helper", "Error getting data: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return list;
    }

    public void insert(String name, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            String QUERY = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ", " + COLUMN_EMAIL + ") " +
                    "VALUES ('" + name + "', '" + email + "')";
            database.execSQL(QUERY);
        } catch (Exception e) {
            Log.e("Helper", "Error inserting data: " + e.getMessage());
        } finally {
            database.close();
        }
    }

    public void update(int id, String name, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            String QUERY = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + " = '" + name + "', " +
                    COLUMN_EMAIL + " = '" + email + "' WHERE " + COLUMN_ID + " = " + id;
            database.execSQL(QUERY);
        } catch (Exception e) {
            Log.e("Helper", "Error updating data: " + e.getMessage());
        } finally {
            database.close();
        }
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            String QUERY = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
            database.execSQL(QUERY);
        } catch (Exception e) {
            Log.e("Helper", "Error deleting data: " + e.getMessage());
        } finally {
            database.close();
        }
    }
}