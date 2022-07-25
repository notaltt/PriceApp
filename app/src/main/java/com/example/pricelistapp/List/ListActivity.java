package com.example.pricelistapp.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pricelistapp.History.HistoryActivity;
import com.example.pricelistapp.Home.HomeActivity;
import com.example.pricelistapp.R;
import com.example.pricelistapp.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DBHelper dbHelper;
    ListView listView;
    ArrayList<ListModel> arrayList;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listView1);
        showListData();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.list);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.list:
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

    private void showListData() {
        arrayList = dbHelper.getListData();
        adapter = new ListAdapter(ListActivity.this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insert_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LayoutInflater inflater = (LayoutInflater) ListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listinsert_layout, null);
        EditText itemNameList = view.findViewById(R.id.itemNameList);
        EditText storeNameList = view.findViewById(R.id.storeNameList);
        EditText quantityList = view.findViewById(R.id.quantityList);
        EditText priceList = view.findViewById(R.id.priceList);
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setView(view)
                .setTitle("INSERT LIST")
                .setMessage("make a list")
                .setIcon(R.drawable.ic_insert)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ADD NEW LIST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String itemList = itemNameList.getText().toString();
                        String storeList = storeNameList.getText().toString();
                        String quantList = quantityList.getText().toString();
                        String pList = priceList.getText().toString();
                        boolean result = dbHelper.insertData(storeList, itemList, quantList, pList);
                        if(result == true){
                            showListData();
                            Toast.makeText(ListActivity.this, "new product added", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(ListActivity.this, "new product not added", Toast.LENGTH_SHORT);
                        }
                    }
                });
        builder.create().show();
        return super.onOptionsItemSelected(item);
    }
}