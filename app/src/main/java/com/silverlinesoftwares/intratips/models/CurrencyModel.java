package com.silverlinesoftwares.intratips.models;

public class CurrencyModel {
    String symbol;
    String price;
    String change_percentage;
    String change;

    public CurrencyModel(String symbol, String price, String change_percentage, String change) {
        this.symbol = symbol;
        this.price = price;
        this.change_percentage = change_percentage;
        this.change = change;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange_percentage() {
        return change_percentage;
    }

    public void setChange_percentage(String change_percentage) {
        this.change_percentage = change_percentage;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }


}
