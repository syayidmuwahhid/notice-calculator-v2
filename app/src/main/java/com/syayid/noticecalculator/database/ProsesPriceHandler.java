package com.syayid.noticecalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.syayid.noticecalculator.models.ProsesPrices;

import java.util.ArrayList;
import java.util.List;

public class ProsesPriceHandler extends DBHandler<ProsesPrices>{
    protected static ProsesPriceHandler sInstance;

    public ProsesPriceHandler(Context context) {
        super(context);
    }

    public static synchronized ProsesPriceHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ProsesPriceHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void add(ProsesPrices prosesPrices) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PROSES_WILAYAH, prosesPrices.getWilayah());
            values.put(KEY_PROSES_HARGA, prosesPrices.getHarga());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_BIAYA_PROSES, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error while trying to add post to database", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    // Get all posts in the database
    public List<ProsesPrices> getDatas() {
        List<ProsesPrices> prosesPrices = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_BIAYA_PROSES;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    prosesPrices.add(new ProsesPrices(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
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
        return prosesPrices;
    }

    //GET 1 data
    @Override
    public ProsesPrices getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BIAYA_PROSES, new String[] { KEY_PROSES_ID,
                        KEY_PROSES_WILAYAH, KEY_PROSES_HARGA }, KEY_PROSES_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProsesPrices prosesPrices = new ProsesPrices(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Double.parseDouble(cursor.getString(2))
        );

        return prosesPrices;
    }

    // Update
    @Override
    public int update(ProsesPrices prosesPrices) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROSES_WILAYAH, prosesPrices.getWilayah());
        values.put(KEY_PROSES_HARGA, prosesPrices.getHarga());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_BIAYA_PROSES, values, KEY_PROSES_ID + " = ?", new String[] { String.valueOf(prosesPrices.getId()) });
    }

    @Override
    public void empty() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_BIAYA_PROSES, null, null);
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
        db.delete(TABLE_BIAYA_PROSES, KEY_PROSES_ID + " = ?",
                new String[] { id });
        db.close();
    }

    @Override
    public void delete(ProsesPrices prosesPrices) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIAYA_PROSES, KEY_PROSES_ID + " = ?",
                new String[] { String.valueOf(prosesPrices.getId()) });
        db.close();
    }
}
