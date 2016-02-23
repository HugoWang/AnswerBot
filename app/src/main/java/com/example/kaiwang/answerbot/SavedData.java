package com.example.kaiwang.answerbot;

import android.provider.BaseColumns;

/**
 * Created by root on 23.2.2016.
 */
public class SavedData {

    public SavedData(){

    }

    public static abstract class SavedInfo implements BaseColumns{
        public static final String USER_ID = "User_ID";
        public static final String GENDER = "Gender";
        public static final String Age = "20";
        public static final String DATABASE_NAME = "User_Info";
        public static final String TABLE_NAME = "TABLE_INGO";

    }
}
