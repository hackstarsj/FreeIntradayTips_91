package com.silverlinesoftwares.intratips.models;

public class ShortSellModel {
    private String dates;
    private String symbol;
    private String quantity;

    public ShortSellModel(String dates, String symbol, String quantity) {
        this.dates = dates;
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


}
