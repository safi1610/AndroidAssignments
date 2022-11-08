package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int VERSION_NUM = 1;
    public static final String TABLE_Of_My_ITEMS = "tableofMyItems";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "itemName";
    private static final String DATABASE_CREATE = "create table "
            +TABLE_Of_My_ITEMS+"("+ KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";


    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling on Create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion = "+ oldVer +" newVersion= "+ newVer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Of_My_ITEMS);
        onCreate(sqLiteDatabase);
    }

}
