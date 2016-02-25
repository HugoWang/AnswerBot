package com.example.kaiwang.answerbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseOperations extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="USER_INFO";
    public static final String TABLE_NAME = "USER_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USER_ID";
    public static final String COL_3 = "USER_GENDER";
    public static final String COL_4 = "USER_AGE";

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID TEXT," +
            "USER_GENDER TEXT, USER_AGE INTEGER)";

    public DataBaseOperations(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        Log.d("Database Operations", "Created Table");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
    }

    public boolean putInfo(String user_id, String gender, int age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,user_id);
        cv.put(COL_3, gender);
        cv.put(COL_4, age);
        long res = db.insert(TABLE_NAME,null, cv);
        return res != -1;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }
    public void updateInfo(String id,String user_id, String gender, int age){
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,id);
        cv.put(COL_2,user_id);
        cv.put(COL_3,gender);
        cv.put(COL_4,age);
        sq.update(TABLE_NAME,cv,"ID=?",new String[] {id});
    }
}
