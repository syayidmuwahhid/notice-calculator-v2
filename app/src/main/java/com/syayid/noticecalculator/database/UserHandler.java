package com.syayid.noticecalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.syayid.noticecalculator.models.Users;

import java.util.ArrayList;
import java.util.List;

public class UserHandler extends DBHandler<Users>{
    protected static UserHandler sInstance;

    public UserHandler(Context context) {
        super(context);
    }

    public static synchronized UserHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new UserHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void add(Users user) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getName());
            values.put(KEY_USER_PASSWORD, user.getPassword());
            values.put(KEY_USER_LEVEL, user.getLevel());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Error while trying to add post to database", e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    @Override
    public List<Users> getDatas() {
        List<Users> users = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    users.add(new Users(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            Integer.parseInt(cursor.getString(3))
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
        return users;
    }

    //GET 1 data
    @Override
    public Users getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_USER_ID,
                        KEY_USER_NAME, KEY_USER_PASSWORD, KEY_USER_LEVEL }, KEY_USER_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Users user = new Users(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)));

        return user;
    }

    // Update the user's profile picture url
    @Override
    public int update(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        values.put(KEY_USER_LEVEL, user.getLevel());

        // Syntax UPDATE <table_name> SET column1 = value1, column2 = value2, ... WHERE condition;
        return db.update(TABLE_USERS, values, KEY_USER_ID + " = ?", new String[] { String.valueOf(user.getId()) });
    }

    @Override
    public void empty() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_USERS, null, null);
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
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
                new String[] { id });
        db.close();
    }

    @Override
    public void delete(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }
}
