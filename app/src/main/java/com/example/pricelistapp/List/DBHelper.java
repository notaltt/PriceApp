package com.example.pricelistapp.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "listdata.db";
    public static final String TABLE_NAME = "list";
    public static final String COL_STORE = "storeName";
    public static final String COL_ITEM = "itemName";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_PRICE = "price";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(storeName TEXT PRIMARY KEY, itemName TEXT, quantity TEXT, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
    }

    public boolean insertData(String storeName, String itemName, String quantity, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STORE, storeName);
        contentValues.put(COL_ITEM, itemName);
        contentValues.put(COL_QUANTITY, quantity);
        contentValues.put(COL_PRICE, price);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ListModel> getListData(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ListModel> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        while(cursor.moveToNext()){
            String storeName = cursor.getString(0);
            String itemName = cursor.getString(1);
            String quantity = cursor.getString(2);
            String price = cursor.getString(3);
            ListModel listModel = new ListModel(storeName, itemName, quantity, price);
            arrayList.add(listModel);
        }
        return arrayList;
    }
}
