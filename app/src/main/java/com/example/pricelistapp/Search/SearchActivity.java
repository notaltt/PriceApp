package com.example.pricelistapp.Search;

import static com.example.pricelistapp.Search.DBHelper.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pricelistapp.History.HistoryActivity;
import com.example.pricelistapp.Home.HomeActivity;
import com.example.pricelistapp.List.ListActivity;
import com.example.pricelistapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SQLiteDatabase sqLiteDatabase;
    DBHelper dbHelper;
    ListView listView;
    ArrayList<PriceList> arrayList;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listView);
        showPriceListData();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void showPriceListData() {
        arrayList = dbHelper.getPriceListData();
        adapter = new Adapter(this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}