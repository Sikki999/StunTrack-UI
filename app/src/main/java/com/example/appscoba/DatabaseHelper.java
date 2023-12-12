package com.example.appscoba;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final  String DATABASE_NAME = "catatan.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableQuery = "CREATE TABLE catatan (id INTEGER PRIMARY KEY AUTOINCREMENT," + "agenda TEXT,waktu TEXT, keterangan TEXT, tanggal TEXT, periode_akademik TEXT,semester TEXT)";
        db.execSQL(createTableQuery);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String dropTableQuery = "DROP TABLE IF EXISTS catatan";
        db.execSQL(dropTableQuery);
        onCreate(db);
    }
    public Cursor getSpinnerData(String columnName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + columnName + " FROM catatan";
        return db.rawQuery(query, null);
    }
}
