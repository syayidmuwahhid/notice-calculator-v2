package com.syayid.noticecalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.syayid.noticecalculator.models.BiayaProses;

import java.util.ArrayList;
import java.util.List;

public class BiayaProsesHandler extends DBHandler<BiayaProses>{
    protected static BiayaProsesHandler sInstance;

    public BiayaProsesHandler(Context context) {
        super(context);
    }

    public static synchronized BiayaProsesHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new BiayaProsesHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void add(BiayaProses biayaProses) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PROSES_WILAYAH, biayaProses.getWilayah());
            values.put(KEY_PROSES_HARGA, biayaProses.getHarga());

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
    public List<BiayaProses> getDatas() {
        List<BiayaProses> biayaProsesPrices = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_BIAYA_PROSES + " ORDER BY " + KEY_PROSES_WILAYAH + " ASC";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    biayaProsesPrices.add(new BiayaProses(
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
        return biayaProsesPrices;
    }

    @Override
    // Get all posts in the database
    public List<BiayaProses> getDatas(String key, String value) {
        List<BiayaProses> biayaProsesPrices = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_BIAYA_PROSES + " WHERE " + key + " = '" + value + "' ORDER BY " + KEY_PROSES_WILAYAH + " ASC";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    biayaProsesPrices.add(new BiayaProses(
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
        return biayaProsesPrices;
    }

    //GET 1 data
    @Override
    public BiayaProses getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BIAYA_PROSES, new String[] { KEY_PROSES_ID,
                        KEY_PROSES_WILAYAH, KEY_PROSES_HARGA }, KEY_PROSES_ID + "=?",
                new String[] { id }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            BiayaProses biayaProses = new BiayaProses(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Double.parseDouble(cursor.getString(2))
            );
            cursor.close(); // Jangan lupa untuk menutup Cursor setelah selesai digunakan
            return biayaProses;
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan nilai null atau objek Notices kosong
            return new BiayaProses(); // Atau sesuaikan dengan definisi konstruktor Notices Anda
        }
    }

    @Override
    public BiayaProses getData(String key, String text) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BIAYA_PROSES, new String[] { KEY_PROSES_ID,
                        KEY_PROSES_WILAYAH, KEY_PROSES_HARGA }, key + "=?",
                new String[] { text }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            BiayaProses biayaProses = new BiayaProses(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Double.parseDouble(cursor.getString(2))
            );
            cursor.close(); // Jangan lupa untuk menutup Cursor setelah selesai digunakan
            return biayaProses;
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan nilai null atau objek Notices kosong
            return new BiayaProses(); // Atau sesuaikan dengan definisi konstruktor Notices Anda
        }
    }

    // Update
    @Override
    public int update(BiayaProses biayaProses) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROSES_WILAYAH, biayaProses.getWilayah());
        values.put(KEY_PROSES_HARGA, biayaProses.getHarga());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_BIAYA_PROSES, values, KEY_PROSES_ID + " = ?", new String[] { String.valueOf(biayaProses.getId()) });
    }

    @Override
    public int update(BiayaProses biayaProses, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROSES_WILAYAH, biayaProses.getWilayah());
        values.put(KEY_PROSES_HARGA, biayaProses.getHarga());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_BIAYA_PROSES, values, KEY_PROSES_ID + " = ?", new String[] { id });
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
    public void delete(BiayaProses biayaProses) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BIAYA_PROSES, KEY_PROSES_ID + " = ?",
                new String[] { String.valueOf(biayaProses.getId()) });
        db.close();
    }
}
