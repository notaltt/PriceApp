package com.example.pricelistapp.Search;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

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
import android.widget.SearchView;
import android.widget.Toast;


import com.example.pricelistapp.History.HistoryActivity;
import com.example.pricelistapp.Home.HomeActivity;
import com.example.pricelistapp.List.ListActivity;
import com.example.pricelistapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    DBHelper dbHelper;
    ListView listView;
    ArrayList<PriceList> arrayList;
    ArrayList<PriceList> searchList;
    PriceAdapter adapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listView);
        showPriceData();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
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

    private void showPriceData() {
        arrayList = dbHelper.getStudentData();
        adapter = new PriceAdapter(SearchActivity.this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem search = menu.findItem(R.id.search_bar);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(SearchActivity.this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*if(item.getTitle().equals("insert")){
            LayoutInflater inflater = (LayoutInflater) SearchActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.insert_layout,null);
            EditText serialNumber = view.findViewById(R.id.serialNumber);
            EditText storeName = view.findViewById(R.id.storeName);
            EditText category = view.findViewById(R.id.category);
            EditText itemName = view.findViewById(R.id.itemName);
            EditText netWeight = view.findViewById(R.id.netWeight);
            EditText quantity = view.findViewById(R.id.quantity);
            EditText price = view.findViewById(R.id.price);
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
            builder.setView(view)
                    .setTitle("add product")
                    .setMessage("hi")
                    .setIcon(R.drawable.ic_insert)
                    .setPositiveButton("add new product", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int serial = Integer.parseInt(serialNumber.getText().toString());
                            String store = storeName.getText().toString();
                            String cat = category.getText().toString();
                            String item = itemName.getText().toString();
                            String net = netWeight.getText().toString();
                            int quan = Integer.parseInt(quantity.getText().toString());
                            double pri = Double.parseDouble(price.getText().toString());
                            boolean result = dbHelper.insertData(serial, store, cat, item, net, quan, pri);
                            if(result == true){
                                showPriceData();
                                Toast.makeText(SearchActivity.this, "new product added", Toast.LENGTH_SHORT);
                            }else {
                                Toast.makeText(SearchActivity.this, "new product not added", Toast.LENGTH_SHORT);
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.create().show();
        } else {

        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        searchList = new ArrayList<>();
        for(PriceList priceList : arrayList){
            String serial = priceList.getSerialNumber().toLowerCase();
            if(serial.contains(newText)){
                searchList.add(priceList);
            }
        }
        adapter.searchFilter(searchList);
        return true;
    }

}