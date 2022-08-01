package com.example.pricelistapp.History;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "historyData.db";
    public static final String TABLE_NAME = "history";
    public static final String COL_DATE = "date";
    public static final String COL_TOTAL = "totalItems";
    public static final String COL_PRICE = "totalPrice";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+"(date TEXT, totalItems TEXT, totalPrice TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean insertData(String totalItems, String totalPrice){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DATE, getDateTime());
        contentValues.put(COL_TOTAL, totalItems);
        contentValues.put(COL_PRICE, totalPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result ==  -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<HistoryModel> getListData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<HistoryModel> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME, null);
        while(cursor.moveToNext()){
            String date = cursor.getString(0);
            String totalItems = cursor.getString(1);
            String totalPrice = cursor.getString(2);
            HistoryModel historyModel = new HistoryModel(date, totalPrice, totalItems);
            arrayList.add(historyModel);
        }
        return arrayList;
    }

    public int delete(String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "date = ?", new String[]{date});
    }

    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+TABLE_NAME);
    }
}
