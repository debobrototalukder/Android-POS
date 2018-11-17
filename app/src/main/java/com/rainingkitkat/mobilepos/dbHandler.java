package com.rainingkitkat.mobilepos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class dbHandler extends SQLiteOpenHelper {


    private final static String DATABASE_NAME = "pos.db";
    private final static String TABLE_NAME = "groceryitems";
    private final static String COL1 = "ID";
    private final static String COL2 = "BarcodeNumber";
    private final static String COL3 = "ProductName";
    private final static String COL4 = "Price";

    public dbHandler(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);

        SQLiteDatabase db = getWritableDatabase();

        try {
            displayAllData(db);
        } catch (SQLException e){
            Log.e("dbHandler", "Database Does Not Exist");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " REAL)";
        sqLiteDatabase.execSQL(createTable);

        Log.d("dbHandler", "Successfully Created Table");

        //Adds Data into the tables
        addData(sqLiteDatabase, "036000291452", "Lay's Tomato Ketchup Chips - 40gm", 2.25f);
        addData(sqLiteDatabase, "0705632085943", "Coca-Cola Can Soft Drinks - 150ml", 2.5f);
        addData(sqLiteDatabase, "0701197952225", "Arwa Water - 1.5L", 1);
        addData(sqLiteDatabase, "9501101530003", "NESCAFE GOLD Double Choc MOCHA Instant Foaming Coffee with Chocolate Mix - 23gm x 8 Sticks", 18.5f);
        addData(sqLiteDatabase, "9771234567003", "White Eggs", 18);
        addData(sqLiteDatabase, "8901072002478", "Sliced Milk Bread", 5);
        addData(sqLiteDatabase, "1234567890128", "Snickers Ice Cream Cone - 110ml ", 6);
        addData(sqLiteDatabase, "725272730706", "Al Rawabi Low Fat Vitamin D Milk - 2L", 12);
        addData(sqLiteDatabase, "012345678905", "Chicken Sandwich", 4);
        addData(sqLiteDatabase, "9788679912077", "Alokozay Black Tea Bags - 100 Bags", 11.75f);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addData(SQLiteDatabase db, String barcode, String productName, float price){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, barcode);
        contentValues.put(COL3, productName);
        contentValues.put(COL4, price);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            Log.e("dbHandler", "Failed to insert values into database");
        } else {
            Log.i("dbHandler", "Values successfully added");
        }
    }

    public void displayAllData(SQLiteDatabase sqLiteDatabase){
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(res.getCount() == 0){
            Log.d("dbHandler", "Database Is Empty");
        } else {
            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()){
                buffer.append("ID : " + res.getString(0) + "\n");
                buffer.append("Barcode : " + res.getString(1) + "\n");
                buffer.append("Product : " + res.getString(2) + "\n");
                buffer.append("Price : " + res.getString(3) + "\n\n");
            }

            Log.i("dbHandler", String.valueOf(buffer));
        }
    }
}
