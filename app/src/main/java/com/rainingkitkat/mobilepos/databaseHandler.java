package com.rainingkitkat.mobilepos;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class databaseHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private final static String DATABASE_NAME = "pos.db";
    private final static String TABLE_NAME = "grocery";
    private final static String COL1 = "ID";
    private final static String COL2 = "BarcodeNumber";
    private final static String COL3 = "ProductName";

    public databaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

        //db = this.getWritableDatabase();
        Log.d("dbHandler", "Trying To Print Lines");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT);";
            sqLiteDatabase.execSQL(createTable);

            Log.i("dbHandler", "Database Created Successfully");

            //insertData();
        } catch (SQLException e) {
            Log.e("dbHandler", "Error Creating Tables");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(dropTable);
        onCreate(sqLiteDatabase);
    }

    public void insertData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, "036000291452");
        contentValues.put(COL3, "Chips");

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            Log.e("dbHandler", "Failed to insert values into database");
        } else {
            Log.i("dbHandler", "Values successfully added");
        }

    }
}
