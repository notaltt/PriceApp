package com.example.pricelistapp.Search;

import static com.example.pricelistapp.Search.DBHelper.DB_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.pricelistapp.History.HistoryActivity;
import com.example.pricelistapp.Home.HomeActivity;
import com.example.pricelistapp.List.ListActivity;
import com.example.pricelistapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ArrayList<PriceList> arrayList = new ArrayList<PriceList>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.search);

        ListView listView = findViewById(R.id.listView);

        DBHelper mDBHELPER = new DBHelper(SearchActivity.this, "PriceList");
        File database = getApplicationContext().getDatabasePath(DBHelper.DB_NAME);

        if(database.exists() == false){
            mDBHELPER.getReadableDatabase();
            if(!copyDatabase(SearchActivity.this)){
                return;
            }
        }

        arrayList = mDBHELPER.getPriceList();
        adapter = new Adapter(arrayList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

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

    public boolean copyDatabase(Context context){
        try {
            InputStream inputStream =context.getAssets().open(DBHelper.DB_NAME);
            String OutFileName = DBHelper.DB_LOCATION+ DBHelper.DB_NAME;
            File f = new File(OutFileName);
            f.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(OutFileName);

            byte[] buffer = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buffer))>0){
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return  true;
        } catch (IOException e){
            return false;
        }
    }
}