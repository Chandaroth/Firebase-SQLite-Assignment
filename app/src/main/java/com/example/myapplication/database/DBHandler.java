package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "User.db";
    public static final int DB_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String USER_TABLE = "CREATE TABLE " + User.UserDetails.TABLE_NAME + " ( " +
                User.UserDetails.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                User.UserDetails.COL_NAME + " TEXT," +
                User.UserDetails.COL_EMAIL + " TEXT," +
                User.UserDetails.COL_PHONE + " TEXT," +
                User.UserDetails.COL_PASSWORD + " TEXT)";
        db.execSQL(USER_TABLE);
    }

    //------------------------------Insert------------------------------------
    public boolean addUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();


       // values.put(User.UserDetails.COL_ID, id);
        values.put(User.UserDetails.COL_NAME, name);
        values.put(User.UserDetails.COL_EMAIL, email);
        values.put(User.UserDetails.COL_PHONE, phone);
        values.put(User.UserDetails.COL_PASSWORD, password);

        long sid = db.insert(User.UserDetails.TABLE_NAME, null, values);
        if (sid > 0) {
            return true;
        } else {
            return false;
        }
    }
/*
    //------------------------------Update------------------------------------
    public boolean updateUser(int id, String name, String email, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.UserDetails.COL_NAME, name);
        values.put(User.UserDetails.COL_EMAIL, email);
        values.put(User.UserDetails.COL_PHONE, phone);

        long sid = db.update(User.UserDetails.TABLE_NAME, values,
                User.UserDetails.COL_ID + " = ? ",
                new String[]{String.valueOf(id)});

        if (sid > 0) {
            return true;
        } else {
            return false;
        }
    }

    //------------------------------Delete------------------------------------
    public boolean deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        long sid = db.delete(User.UserDetails.TABLE_NAME, User.UserDetails.COL_ID + " = ? ",
                new String[]{String.valueOf(id)});
        if (sid > 0) {
            return true;
        } else {
            return false;
        }
    }

    //------------------------------ViewDATA------------------------------------
    //Query only name to ListView
    *//*public ArrayList<String> getUsers() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(User.UserDetails.TABLE_NAME,
                new String[]{User.UserDetails.COL_NAME}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(User.UserDetails.TABLE_NAME)));

        }
        return list;
    }
*//*
    //TESTING
    public Cursor getListContents(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM "+ User.UserDetails.TABLE_NAME,null);
        return data;

    }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
