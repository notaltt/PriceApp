package com.example.pricelistapp.History;

public class HistoryModel {
    String date, totalPrice, totalItems, id;

    public HistoryModel(String id, String date, String totalPrice, String totalItems) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }
}
