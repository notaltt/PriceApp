package com.example.pricelistapp.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pricelistapp.Home.HomeActivity;
import com.example.pricelistapp.List.ListActivity;
import com.example.pricelistapp.R;
import com.example.pricelistapp.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DBHelper dbHelper;
    ArrayList<HistoryModel> arrayList;
    HistoryAdapter adapter;
    ListView listView;
    HistoryModel historyModel;
    String date, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.historyList);
        showListData();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
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
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.history:
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
        adapter = new HistoryAdapter(HistoryActivity.this, arrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
        builder.setTitle("Are you sure deleting all data in the list?")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteAll();
                        showListData();
                        Toast.makeText(HistoryActivity.this,"DELETED ALL DATA", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
        return super.onOptionsItemSelected(item);
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
            historyModel = arrayList.get(i);
            date = historyModel.getDate();
            id = historyModel.getId();
            actionMode.setTitle(date);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.abs3_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
            builder.setTitle("Are you sure deleting the "+date+"?")
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int result = dbHelper.delete(id);
                            if(result>0){
                                showListData();
                                actionMode.finish();
                                Toast.makeText(HistoryActivity.this, date+" is deleted to list", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(HistoryActivity.this, date+" is not deleted to list", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            builder.create().show();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }
    };
}