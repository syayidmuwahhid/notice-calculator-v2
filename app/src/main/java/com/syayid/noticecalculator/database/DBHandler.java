package com.syayid.noticecalculator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

abstract public class DBHandler<T> extends SQLiteOpenHelper {
//    protected static DBHandler sInstance;
    private static final String DATABASE_NAME = "NoticeCalculator";
    private static final int DATABASE_VERSION = 1;

    // table name
    protected static final String TABLE_USERS = "users";
    protected static final String TABLE_NOTICES = "notices";
    protected static final String TABLE_BIAYA_PROSES = "biaya_proses";

    // user column tables
    protected static final String KEY_USER_ID = "id";
    protected static final String KEY_USER_NAME = "user";
    protected static final String KEY_USER_LEVEL = "level";
    protected static final String KEY_USER_PASSWORD = "password";

    // BIAYA PROSES column tables
    protected static final String KEY_PROSES_ID = "id";
    protected static final String KEY_PROSES_WILAYAH = "wilayah";
    protected static final String KEY_PROSES_HARGA = "harga";

    // BIAYA PROSES column tables
    protected static final String KEY_NOTICE_TIPE = "tipe";
    protected static final String KEY_NOTICE_HARGA = "harga";
    protected static final String KEY_NOTICE_PROGRESIF = "progresif";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_PASSWORD + " TEXT," +
                KEY_USER_LEVEL + " INTEGER" +
                ")";

        String CREATE_NOTICE_TABLE = "CREATE TABLE " + TABLE_NOTICES +
                "(" +
                KEY_NOTICE_TIPE + " TEXT PRIMARY KEY," +
                KEY_NOTICE_HARGA + " DOUBLE," +
                KEY_NOTICE_PROGRESIF + " DOUBLE" +
                ")";

        String CREATE_PROSES_TABLE = "CREATE TABLE " + TABLE_BIAYA_PROSES +
                "(" +
                KEY_PROSES_ID + " INTEGER PRIMARY KEY," +
                KEY_PROSES_WILAYAH + " TEXT UNIQUE," +
                KEY_PROSES_HARGA + " DOUBLE" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_NOTICE_TABLE);
        db.execSQL(CREATE_PROSES_TABLE);
    }

    // on Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTICES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIAYA_PROSES);
            onCreate(db);
        }
    }

    abstract public void add(T model);
    abstract public List<T> getDatas();
    abstract public T getData(String id);
    abstract public T getData(String key, String text);
    abstract public int update(T model);
    abstract public int update(T model, String id);
    abstract public void empty();
    abstract public void delete(String id);
    abstract public void delete(T model);
}
