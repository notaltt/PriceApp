package com.example.pricelistapp.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pricelistapp.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<HistoryModel> arrayList;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.historylist_layout, null);

        TextView date = view.findViewById(R.id.historyDate);
        TextView items = view.findViewById(R.id.historyTotalItems);
        TextView price = view.findViewById(R.id.historyTotalPrice);

        HistoryModel historyModel = arrayList.get(i);

        String date1 = historyModel.getDate();
        String items1 = historyModel.getTotalItems();
        String price1 = historyModel.getTotalPrice();

        date.setText(date1);
        items.setText(items1);
        price.setText(price1);

        return view;
    }
}
