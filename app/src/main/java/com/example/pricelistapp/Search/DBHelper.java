package com.example.pricelistapp.Search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME ;
    public static String DB_TABLE ;
    public static final String DB_LOCATION = "/data/data/"+ PricelistAdapter.context.getPackageName()+"/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBHelper(@Nullable Context context, String dbName) {
        super(context, DB_NAME, null, 1);
        DB_NAME = dbName+".db";
        DB_TABLE = "priceList";
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void OpenDatabase(){
        String DBLocation = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDatabase!=null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(DBLocation, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void CloseDatabase(){
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    public ArrayList<PriceList> getPriceList(){
        PriceList priceList = null;
        ArrayList<PriceList> arrayList = new ArrayList<PriceList>();
        OpenDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from "+ DB_TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            priceList = new PriceList(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            arrayList.add(priceList);
            cursor.moveToNext();
        }
        cursor.close();
        CloseDatabase();
        return arrayList;
    }
}