package com.rey.businesscalculator;

public class ProductModel {

    String productName;
    float cost;
    float priceToSell;

    public ProductModel(String productName, float cost, float priceToSell) {
        this.productName = productName;
        this.cost = cost;
        this.priceToSell = priceToSell;
    }

    public String getProductName() {
        return productName;
    }

    public float getCost() {
        return cost;
    }

    public float getPriceToSell() {
        return priceToSell;
    }
}
