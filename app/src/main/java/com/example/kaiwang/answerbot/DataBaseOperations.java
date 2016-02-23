package com.example.kaiwang.answerbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 23.2.2016.
 */
public class DataBaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + SavedData.SavedInfo.TABLE_NAME+"("+
            SavedData.SavedInfo.USER_ID+" TEXT,"+ SavedData.SavedInfo.GENDER+" TEXT,"+
            SavedData.SavedInfo.Age+")";

    public DataBaseOperations(Context context) {
        super(context, SavedData.SavedInfo.TABLE_NAME,null,database_version);
        Log.d("Database Operations","Created Table");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInfo(DataBaseOperations dbo, String user_id, String gender, int age){
        SQLiteDatabase SQ = dbo.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SavedData.SavedInfo.USER_ID,user_id);
        cv.put(SavedData.SavedInfo.GENDER,gender);
        cv.put(SavedData.SavedInfo.Age,age);
        SQ.insert(SavedData.SavedInfo.TABLE_NAME,null, cv);
    }
}
