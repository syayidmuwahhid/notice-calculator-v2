package com.syayid.noticecalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.syayid.noticecalculator.models.Arsip;

import java.util.ArrayList;
import java.util.List;

public class ArsipGabunganHandler extends DBHandler<Arsip>{
    protected static ArsipGabunganHandler sInstance;

    public ArsipGabunganHandler(Context context) {
        super(context);
    }

    public static synchronized ArsipGabunganHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ArsipGabunganHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void add(Arsip arsip) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ARSIP_GABUNGAN_TANGGAL, arsip.getTanggal());
            values.put(KEY_ARSIP_GABUNGAN_WILAYAH, arsip.getWilayah());
            values.put(KEY_ARSIP_GABUNGAN_BIAYA_PROSES, arsip.getBiaya_proses());
            values.put(KEY_ARSIP_GABUNGAN_JUMLAH_FAKTUR, arsip.getJumlah_faktur());
            values.put(KEY_ARSIP_GABUNGAN_TOTAL_SELURUH, arsip.getTotal_seluruh());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ARSIP_GABUNGAN, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error while trying to add post to database", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    @Override
    public List<Arsip> getDatas() {
        List<Arsip> arsip = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARSIP_GABUNGAN + " ORDER BY " + KEY_ARSIP_GABUNGAN_ID + " DESC";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    arsip.add(new Arsip(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            Double.parseDouble(cursor.getString(3)),
                            Integer.parseInt(cursor.getString(4)),
                            Long.parseLong(cursor.getString(5))
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
        return arsip;
    }

    @Override
    public List<Arsip> getDatas(String key, String value) {
        List<Arsip> arsip = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARSIP_GABUNGAN + " WHERE " + key + " = '" + value + "' ORDER BY " + KEY_ARSIP_GABUNGAN_ID + " DESC";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    arsip.add(new Arsip(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            Double.parseDouble(cursor.getString(3)),
                            Integer.parseInt(cursor.getString(4)),
                            Long.parseLong(cursor.getString(5))
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
        return arsip;
    }

    //GET 1 data
    @Override
    public Arsip getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARSIP_GABUNGAN, new String[] { KEY_ARSIP_GABUNGAN_ID,
                        KEY_ARSIP_GABUNGAN_TANGGAL, KEY_ARSIP_GABUNGAN_WILAYAH, KEY_ARSIP_GABUNGAN_BIAYA_PROSES, KEY_ARSIP_GABUNGAN_JUMLAH_FAKTUR,
                        KEY_ARSIP_GABUNGAN_TOTAL_SELURUH }, KEY_ARSIP_GABUNGAN_ID + "=?",
                new String[] { id }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Arsip arsip = new Arsip(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Long.parseLong(cursor.getString(5))
            );
            cursor.close(); // Jangan lupa untuk menutup Cursor setelah selesai digunakan
            return arsip;
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan nilai null atau objek Notices kosong
            return new Arsip(); // Atau sesuaikan dengan definisi konstruktor Notices Anda
        }
    }

    @Override
    public Arsip getData(String key, String text) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARSIP_GABUNGAN, new String[] { KEY_ARSIP_GABUNGAN_ID,
                        KEY_ARSIP_GABUNGAN_TANGGAL, KEY_ARSIP_GABUNGAN_WILAYAH, KEY_ARSIP_GABUNGAN_BIAYA_PROSES, KEY_ARSIP_GABUNGAN_JUMLAH_FAKTUR,
                        KEY_ARSIP_GABUNGAN_TOTAL_SELURUH }, key + "=?",
                new String[] { text }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Arsip arsip = new Arsip(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Long.parseLong(cursor.getString(5))
            );
            cursor.close(); // Jangan lupa untuk menutup Cursor setelah selesai digunakan
            return arsip;
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan nilai null atau objek Notices kosong
            return new Arsip(); // Atau sesuaikan dengan definisi konstruktor Notices Anda
        }
    }

    // Update
    @Override
    public int update(Arsip arsip) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARSIP_GABUNGAN_TANGGAL, arsip.getTanggal());
        values.put(KEY_ARSIP_GABUNGAN_WILAYAH, arsip.getWilayah());
        values.put(KEY_ARSIP_GABUNGAN_BIAYA_PROSES, arsip.getBiaya_proses());
        values.put(KEY_ARSIP_GABUNGAN_JUMLAH_FAKTUR, arsip.getJumlah_faktur());
        values.put(KEY_ARSIP_GABUNGAN_TOTAL_SELURUH, arsip.getTotal_seluruh());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_ARSIP_GABUNGAN, values, KEY_ARSIP_GABUNGAN_ID + " = ?", new String[] { String.valueOf(arsip.getId()) });
    }

    @Override
    public int update(Arsip arsip, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARSIP_GABUNGAN_TANGGAL, arsip.getTanggal());
        values.put(KEY_ARSIP_GABUNGAN_WILAYAH, arsip.getWilayah());
        values.put(KEY_ARSIP_GABUNGAN_BIAYA_PROSES, arsip.getBiaya_proses());
        values.put(KEY_ARSIP_GABUNGAN_JUMLAH_FAKTUR, arsip.getJumlah_faktur());
        values.put(KEY_ARSIP_GABUNGAN_TOTAL_SELURUH, arsip.getTotal_seluruh());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_ARSIP_GABUNGAN, values, KEY_ARSIP_GABUNGAN_ID + " = ?", new String[] { id });
    }

    @Override
    public void empty() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_ARSIP_GABUNGAN, null, null);
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
        db.delete(TABLE_ARSIP_GABUNGAN, KEY_ARSIP_GABUNGAN_ID + " = ?",
                new String[] { id });
        db.close();
    }

    @Override
    public void delete(Arsip arsip) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARSIP_GABUNGAN, KEY_ARSIP_GABUNGAN_ID + " = ?",
                new String[] { String.valueOf(arsip.getId()) });
        db.close();
    }
}
