package com.silverlinesoftwares.intratips.models;

public class MostDeliverModel {
    String symbol;
    String traded_qty;
    String reliable_qty;
    String percentage_delivery;

    public String getTraded_qty() {
        return traded_qty;
    }

    public void setTraded_qty(String traded_qty) {
        this.traded_qty = traded_qty;
    }

    public String getReliable_qty() {
        return reliable_qty;
    }

    public void setReliable_qty(String reliable_qty) {
        this.reliable_qty = reliable_qty;
    }

    public String getPercentage_delivery() {
        return percentage_delivery;
    }

    public void setPercentage_delivery(String percentage_delivery) {
        this.percentage_delivery = percentage_delivery;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public MostDeliverModel(String symbol, String traded_qty, String reliable_qty, String percentage_delivery) {
        this.symbol = symbol;
        this.traded_qty = traded_qty;
        this.reliable_qty = reliable_qty;
        this.percentage_delivery = percentage_delivery;
    }
}
