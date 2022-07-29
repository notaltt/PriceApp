package com.example.pricelistapp.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pricelistapp.History.HistoryActivity;
import com.example.pricelistapp.Home.HomeActivity;
import com.example.pricelistapp.PriceList;
import com.example.pricelistapp.R;

import com.example.pricelistapp.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DBHelper dbHelper;
    ListView listView;
    ArrayList<PriceList> arrayList;
    ListAdapter adapter;
    String itemName, storeName, quantity, price;
    PriceList listModel;

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

        listView.setMultiChoiceModeListener(modeListener);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
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
        if(item.getTitle().equals("insert")){
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
                            int quant = Integer.parseInt(quantityList.getText().toString());
                            double pri = Double.parseDouble(priceList.getText().toString());
                            double mult = quant * pri;
                            String price = String.valueOf(mult);
                            boolean result = dbHelper.insertData(storeList, itemList, quantList, pList, price);
                            if(result == true){
                                showListData();
                                Toast.makeText(ListActivity.this, "new product added", Toast.LENGTH_SHORT);
                            } else {
                                Toast.makeText(ListActivity.this, "new product not added", Toast.LENGTH_SHORT);
                            }
                        }
                    });
            builder.create().show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
            builder.setTitle("TOTAL")
                    .setMessage("Total of items: " +dbHelper.getTotalItems() +
                            "\nTotal of price: "+dbHelper.getTotalPrice())
                    .setNegativeButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long l, boolean b) {
            listModel = arrayList.get(position);
            itemName = listModel.getItemName();
            storeName = listModel.getStoreName();
            quantity = listModel.getQuantity();
            price = listModel.getPrice();
            actionMode.setTitle(itemName);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.abs2_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {;
            if(menuItem.getTitle().equals("delete")){
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("DELETING " + itemName)
                        .setMessage("Are you sure deleting "+itemName+"?")
                        .setIcon(R.drawable.ic_delete)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int result = dbHelper.delete(itemName);
                                if(result>0){
                                    showListData();
                                    actionMode.finish();
                                    Toast.makeText(ListActivity.this, itemName+" is deleted to list", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ListActivity.this, itemName+" is not deleted to list", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.create().show();
            } else {
                LayoutInflater inflater = (LayoutInflater) ListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.listinsert_layout, null);
                EditText itemNameList = view.findViewById(R.id.itemNameList);
                EditText storeNameList = view.findViewById(R.id.storeNameList);
                EditText quantityList = view.findViewById(R.id.quantityList);
                EditText priceList = view.findViewById(R.id.priceList);
                String item = listModel.getItemName();
                String store = listModel.getStoreName();
                String quantity = listModel.getQuantity();
                String price = listModel.getPrice();
                itemNameList.setText(item);
                storeNameList.setText(store);
                quantityList.setText(quantity);
                priceList.setText(price);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setView(view)
                        .setTitle("UPDATE LIST")
                        .setMessage("UPDATE A LIST")
                        .setIcon(R.drawable.ic_update)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("UPDATE LIST", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String itemList = itemNameList.getText().toString();
                                String storeList = storeNameList.getText().toString();
                                String quantList = quantityList.getText().toString();
                                String pList = priceList.getText().toString();
                                int quant = Integer.parseInt(quantityList.getText().toString());
                                double pri = Double.parseDouble(priceList.getText().toString());
                                double mult = quant * pri;
                                String price = String.valueOf(mult);
                                boolean result = dbHelper.updateData(storeList, itemList, quantList, pList, price);
                                if(result == true){
                                    showListData();
                                    Toast.makeText(ListActivity.this, "new product added", Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(ListActivity.this, "new product not added", Toast.LENGTH_SHORT);
                                }
                            }
                        });
                builder.create().show();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };
}