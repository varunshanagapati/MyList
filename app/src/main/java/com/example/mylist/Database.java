package com.example.mylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notesapp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "allTasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void insertInput(com.example.mylist.Input input) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, input.getTitle());
        values.put(COLUMN_CONTENT, input.getContent());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<com.example.mylist.Input> getAllTasks() {
        List<com.example.mylist.Input> tasksList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));

            com.example.mylist.Input input = new com.example.mylist.Input(id, title, content);
            tasksList.add(input);
        }

        cursor.close();
        db.close();
        return tasksList;
    }

    public void updateNote(com.example.mylist.Input input) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, input.getTitle());
        values.put(COLUMN_CONTENT, input.getContent());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(input.getId())};
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public com.example.mylist.Input getNodeById(int noteId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + noteId;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
        String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));

        cursor.close();
        db.close();
        return new com.example.mylist.Input(id, title, content);
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(noteId)};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }
}