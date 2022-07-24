package com.example.pricelistapp.Search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "PriceList.db";
    public static final String TABLE_NAME = "priceList";
    public static final String COL_SERIAL = "serialNumber";
    public static final String COL_STORE = "storeName";
    public static final String COL_CATEGORY = "category";
    public static final String COL_NAME = "itemName";
    public static final String COL_WEIGHT = "netWeight";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_PRICE = "price";


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" create table " + TABLE_NAME +
                " (serialNumber INTEGER PRIMARY KEY, storeName TEXT, category TEXT, itemName TEXT, netWeight INTEGER, quantity INTEGER, price REAL) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" drop table if exists " + TABLE_NAME);
    }

    public ArrayList<PriceList> getPriceListData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<PriceList> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from "+TABLE_NAME+"", null);

        while (cursor.moveToNext()){
            int serialNumber = cursor.getInt(0);
            int netWeight = cursor.getInt(1);
            int quantity = cursor.getInt(2);
            String storeName = cursor.getString(3);
            String category = cursor.getString(4);
            String itemName = cursor.getString(5);
            double price = cursor.getDouble(6);

            PriceList priceList = new PriceList(serialNumber, netWeight, quantity, storeName, category, itemName, price);
            arrayList.add( priceList );
        }
        return arrayList;
    }
}
