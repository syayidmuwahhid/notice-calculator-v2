package com.syayid.noticecalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.syayid.noticecalculator.models.ArsipProgresifDetail;

import java.util.ArrayList;
import java.util.List;

public class ArsipProgresifDetailHandler extends DBHandler<ArsipProgresifDetail> {
    protected static ArsipProgresifDetailHandler sInstance;

    public ArsipProgresifDetailHandler(Context context) {
        super(context);
    }

    public static synchronized ArsipProgresifDetailHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ArsipProgresifDetailHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void add(ArsipProgresifDetail arsip) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ARSIP_PROG_DETAIL_ID_ARSIP, arsip.getId_arsip_progresif());
            values.put(KEY_ARSIP_PROG_DETAIL_TIPE, arsip.getTipe());
            values.put(KEY_ARSIP_PROG_DETAIL_PROGKE, arsip.getProgresif_ke());
            values.put(KEY_ARSIP_PROG_DETAIL_BIAYA, arsip.getBiaya());
            values.put(KEY_ARSIP_PROG_DETAIL_SUBTOTAL, arsip.getSubtotal());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_ARSIP_PROG_DETAIL, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error while trying to add post to database", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    @Override
    public List<ArsipProgresifDetail> getDatas() {
        List<ArsipProgresifDetail> arsip = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARSIP_PROG_DETAIL + " ORDER BY " + KEY_ARSIP_PROG_DETAIL_ID + " DESC";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    arsip.add(new ArsipProgresifDetail(
                            Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
                            cursor.getString(2),
                            Integer.parseInt(cursor.getString(3)),
                            Double.parseDouble(cursor.getString(4)),
                            Double.parseDouble(cursor.getString(5))
                    ));
                } while (cursor.moveToNext());
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
    public List<ArsipProgresifDetail> getDatas(String key, String value) {
        List<ArsipProgresifDetail> arsip = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARSIP_PROG_DETAIL + " WHERE " + key + " = '" + value + "' ORDER BY " + KEY_ARSIP_PROG_DETAIL_ID + " ASC";

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    arsip.add(new ArsipProgresifDetail(
                            Integer.parseInt(cursor.getString(0)),
                            Integer.parseInt(cursor.getString(1)),
                            cursor.getString(2),
                            Integer.parseInt(cursor.getString(3)),
                            Double.parseDouble(cursor.getString(4)),
                            Double.parseDouble(cursor.getString(5))
                    ));
                } while (cursor.moveToNext());
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
    public ArsipProgresifDetail getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARSIP_PROG_DETAIL, new String[]{ KEY_ARSIP_PROG_DETAIL_ID,
                        KEY_ARSIP_PROG_DETAIL_ID_ARSIP, KEY_ARSIP_PROG_DETAIL_TIPE, KEY_ARSIP_PROG_DETAIL_PROGKE, KEY_ARSIP_PROG_DETAIL_BIAYA,
                        KEY_ARSIP_PROG_DETAIL_SUBTOTAL }, KEY_ARSIP_PROG_DETAIL_ID + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArsipProgresifDetail arsip = new ArsipProgresifDetail(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)),
                    Double.parseDouble(cursor.getString(5))
            );
            cursor.close(); // Jangan lupa untuk menutup Cursor setelah selesai digunakan
            return arsip;
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan nilai null atau objek Notices kosong
            return new ArsipProgresifDetail(); // Atau sesuaikan dengan definisi konstruktor Notices Anda
        }
    }

    @Override
    public ArsipProgresifDetail getData(String key, String text) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARSIP_PROG_DETAIL, new String[]{ KEY_ARSIP_PROG_DETAIL_ID,
                        KEY_ARSIP_PROG_DETAIL_ID_ARSIP, KEY_ARSIP_PROG_DETAIL_TIPE, KEY_ARSIP_PROG_DETAIL_PROGKE, KEY_ARSIP_PROG_DETAIL_BIAYA,
                        KEY_ARSIP_PROG_DETAIL_SUBTOTAL }, key + "=?",
                new String[]{text}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArsipProgresifDetail arsip = new ArsipProgresifDetail(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)),
                    Double.parseDouble(cursor.getString(5))
            );
            cursor.close(); // Jangan lupa untuk menutup Cursor setelah selesai digunakan
            return arsip;
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan nilai null atau objek Notices kosong
            return new ArsipProgresifDetail(); // Atau sesuaikan dengan definisi konstruktor Notices Anda
        }
    }

    // Update
    @Override
    public int update(ArsipProgresifDetail arsip) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARSIP_PROG_DETAIL_ID_ARSIP, arsip.getId_arsip_progresif());
        values.put(KEY_ARSIP_PROG_DETAIL_TIPE, arsip.getTipe());
        values.put(KEY_ARSIP_PROG_DETAIL_PROGKE, arsip.getProgresif_ke());
        values.put(KEY_ARSIP_PROG_DETAIL_BIAYA, arsip.getBiaya());
        values.put(KEY_ARSIP_PROG_DETAIL_SUBTOTAL, arsip.getSubtotal());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_ARSIP_PROG_DETAIL, values, KEY_ARSIP_PROG_DETAIL_ID + " = ?", new String[]{String.valueOf(arsip.getId())});
    }

    @Override
    public int update(ArsipProgresifDetail arsip, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ARSIP_PROG_DETAIL_ID_ARSIP, arsip.getId_arsip_progresif());
        values.put(KEY_ARSIP_PROG_DETAIL_TIPE, arsip.getTipe());
        values.put(KEY_ARSIP_PROG_DETAIL_PROGKE, arsip.getProgresif_ke());
        values.put(KEY_ARSIP_PROG_DETAIL_BIAYA, arsip.getBiaya());
        values.put(KEY_ARSIP_PROG_DETAIL_SUBTOTAL, arsip.getSubtotal());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_ARSIP_PROG_DETAIL, values, KEY_ARSIP_PROG_DETAIL_ID + " = ?", new String[]{id});
    }

    @Override
    public void empty() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_ARSIP_PROG_DETAIL, null, null);
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
        db.delete(TABLE_ARSIP_PROG_DETAIL, KEY_ARSIP_PROG_DETAIL_ID + " = ?",
                new String[]{id});
        db.close();
    }

    @Override
    public void delete(ArsipProgresifDetail arsip) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARSIP_PROG_DETAIL, KEY_ARSIP_PROG_DETAIL_ID + " = ?",
                new String[]{String.valueOf(arsip.getId())});
        db.close();
    }
}
