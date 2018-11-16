package com.rainingkitkat.mobilepos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class dbHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private final static String DATABASE_NAME = "pos.db";
    private final static String TABLE_NAME = "groceryitems";
    private final static String COL1 = "ID";
    private final static String COL2 = "BarcodeNumber";
    private final static String COL3 = "ProductName";

    public dbHandler(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
        
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("dbHandler", "onCreate");

        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);

        Log.d("dbHandler", "Successfully Created Table");

        addData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addData(SQLiteDatabase db){
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
