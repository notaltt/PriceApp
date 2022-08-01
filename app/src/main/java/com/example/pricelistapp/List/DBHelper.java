package com.example.pricelistapp.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pricelistapp.PriceList;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "listdata.db";
    public static final String TABLE_NAME = "list";
    public static final String COL_STORE = "storeName";
    public static final String COL_ITEM = "itemName";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_PRICE = "price";
    public static final String COL_TOTAL = "total";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(storeName TEXT PRIMARY KEY, itemName TEXT, quantity TEXT, price TEXT, total TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
    }

    public boolean insertData(String storeName, String itemName, String quantity, String price, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STORE, storeName);
        contentValues.put(COL_ITEM, itemName);
        contentValues.put(COL_QUANTITY, quantity);
        contentValues.put(COL_PRICE, price);
        contentValues.put(COL_TOTAL, total);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<PriceList> getListData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PriceList> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        while(cursor.moveToNext()){
            String storeName = cursor.getString(0);
            String itemName = cursor.getString(1);
            String quantity = cursor.getString(2);
            String price = cursor.getString(3);
            String total = cursor.getString(4);
            PriceList listModel = new PriceList(storeName, itemName, quantity, price, total);
            arrayList.add(listModel);
        }
        return arrayList;
    }

    public int delete(String itemName){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "itemName = ?", new String[]{itemName});
    }

    public boolean updateData(String storeName, String itemName, String quantity, String price, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STORE, storeName);
        contentValues.put(COL_ITEM, itemName);
        contentValues.put(COL_QUANTITY, quantity);
        contentValues.put(COL_PRICE, price);
        contentValues.put(COL_TOTAL, total);
        db.update(TABLE_NAME,contentValues,"itemName = ?", new String[]{itemName});
        return true;
    }

    public String getTotalPrice(){
        SQLiteDatabase db = this.getReadableDatabase();
        String totalPrice = "0";
        Cursor cursor = db.rawQuery("select (sum(total))from  "+TABLE_NAME, null);
        if(cursor.moveToFirst()){
            totalPrice = String.valueOf(cursor.getDouble(0));
        }

        return totalPrice;
    }

    public String getTotalItems(){
        SQLiteDatabase db = this.getReadableDatabase();
        String totalItems = "0";
        Cursor cursor = db.rawQuery("select (sum(quantity)) from  "+TABLE_NAME, null);
        if(cursor.moveToFirst()){
            totalItems = String.valueOf(cursor.getInt(0));
        }
        return totalItems;
    }

    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+TABLE_NAME);
    }
}
