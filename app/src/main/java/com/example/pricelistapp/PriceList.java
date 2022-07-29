package com.example.pricelistapp;

public class PriceList {
    String serialNumber;
    String storeName, category, itemName, netWeight;
    String quantity;
    String price;
    String totalPrice;

    public PriceList(String serialNumber, String storeName, String category, String itemName, String netWeight, String quantity, String price) {
        this.serialNumber = serialNumber;
        this.storeName = storeName;
        this.category = category;
        this.itemName = itemName;
        this.netWeight = netWeight;
        this.quantity = quantity;
        this.price = price;
    }

    public PriceList(String storeName, String itemName, String quantity, String price, String totalPrice){
        this.storeName = storeName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
