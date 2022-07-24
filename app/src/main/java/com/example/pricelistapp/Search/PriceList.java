package com.example.pricelistapp.Search;

public class PriceList {
    public int serialNumber, netWeight, quantity;
    public String storeName, category, itemName;
    public double price;

    public PriceList(int serialNumber, int netWeight, int quantity, String storeName, String category, String itemName, double price) {
        this.serialNumber = serialNumber;
        this.netWeight = netWeight;
        this.quantity = quantity;
        this.storeName = storeName;
        this.category = category;
        this.itemName = itemName;
        this.price = price;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
