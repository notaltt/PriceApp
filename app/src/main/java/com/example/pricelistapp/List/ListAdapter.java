package com.example.pricelistapp.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pricelistapp.PriceList;
import com.example.pricelistapp.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<PriceList> arrayList;

    public ListAdapter(Context context, ArrayList<PriceList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listactivity_layout, null);

        TextView nameList = convertView.findViewById(R.id.item_nameList);
        TextView storeList = convertView.findViewById(R.id.store_nameList);
        TextView quantList = convertView.findViewById(R.id.quantity2List);
        TextView priList = convertView.findViewById(R.id.price2List);
        TextView totalList = convertView.findViewById(R.id.totalPrice);

        PriceList listModel = arrayList.get(position);

        String name1 = listModel.getItemName();
        String store2 = listModel.getStoreName();
        String quant2 = listModel.getQuantity();
        String price2 = listModel.getPrice();
        String total1 = listModel.getTotalPrice();

        nameList.setText(name1);
        storeList.setText(store2);
        quantList.setText(quant2);
        priList.setText(price2);
        totalList.setText(total1);

        return convertView;
    }
}
