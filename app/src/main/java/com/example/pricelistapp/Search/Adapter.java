package com.example.pricelistapp.Search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pricelistapp.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    Context context;
    ArrayList<PriceList> arrayList;

    public Adapter(Context context, ArrayList<PriceList> arrayList) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listview_layout, null);
        TextView itemName = view.findViewById(R.id.itemName);
        TextView storeName = view.findViewById(R.id.storeName);
        TextView quantity = view.findViewById(R.id.quantity);
        TextView netWeight = view.findViewById(R.id.netWeight);
        PriceList priceList = arrayList.get(i);

        String nameItem = priceList.getItemName();
        String nameStore = priceList.getStoreName();
        int quanTity = priceList.getQuantity();
        int weightNet = priceList.getNetWeight();

        itemName.setText( nameItem );
        storeName.setText( nameStore );
        quantity.setText( quanTity );
        netWeight.setText( weightNet );
        return view;
    }
}
