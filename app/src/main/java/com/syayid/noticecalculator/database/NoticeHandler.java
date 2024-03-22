package com.syayid.noticecalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.syayid.noticecalculator.models.Notices;

import java.util.ArrayList;
import java.util.List;

public class NoticeHandler extends DBHandler<Notices>{
    protected static NoticeHandler sInstance;

    public NoticeHandler(Context context) {
        super(context);
    }

    public static synchronized NoticeHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new NoticeHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void add(Notices notices) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTICE_TIPE, notices.getTipe());
            values.put(KEY_NOTICE_HARGA, notices.getHarga());
            values.put(KEY_NOTICE_PROGRESIF, notices.getProgresif());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_NOTICES, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error while trying to add post to database", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    @Override
    public List<Notices> getDatas() {
        List<Notices> notices = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTICES;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    notices.add(new Notices(
                            cursor.getString(0),
                            Double.parseDouble(cursor.getString(1)),
                            Double.parseDouble(cursor.getString(2))
                    ));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return notices;
    }

    //GET 1 data
    @Override
    public Notices getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTICES, new String[] { KEY_NOTICE_TIPE,
                        KEY_NOTICE_HARGA, KEY_NOTICE_PROGRESIF }, KEY_NOTICE_TIPE + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Notices notices = new Notices(
                cursor.getString(0),
                Double.parseDouble(cursor.getString(1)),
                Double.parseDouble(cursor.getString(2))
        );

        return notices;
    }

    // Update
    @Override
    public int update(Notices notices) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTICE_TIPE, notices.getTipe());
        values.put(KEY_NOTICE_HARGA, notices.getHarga());
        values.put(KEY_NOTICE_PROGRESIF, notices.getProgresif());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_NOTICES, values, KEY_NOTICE_TIPE + " = ?", new String[] { notices.getTipe() });
    }

    @Override
    public int update(Notices notices, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTICE_TIPE, notices.getTipe());
        values.put(KEY_NOTICE_HARGA, notices.getHarga());
        values.put(KEY_NOTICE_PROGRESIF, notices.getProgresif());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_NOTICES, values, KEY_NOTICE_TIPE + " = ?", new String[] { id });
    }

    @Override
    public void empty() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_NOTICES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Error while trying to delete", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTICES, KEY_NOTICE_TIPE + " = ?",
                new String[] { id });
        db.close();
    }

    @Override
    public void delete(Notices notices) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTICES, KEY_NOTICE_TIPE + " = ?",
                new String[] { notices.getTipe() });
        db.close();
    }
}