package com.silverlinesoftwares.intratips.models;

public class CommodityModel {
    String symbol;
    String price;
    String change_percentage;
    String change;
    String volume;
    String open_interest;

    public CommodityModel(String symbol, String price, String change_percentage, String change, String volume, String open_interest) {
        this.symbol = symbol;
        this.price = price;
        this.change_percentage = change_percentage;
        this.change = change;
        this.volume = volume;
        this.open_interest = open_interest;
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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getOpen_interest() {
        return open_interest;
    }

    public void setOpen_interest(String open_interest) {
        this.open_interest = open_interest;
    }
}
