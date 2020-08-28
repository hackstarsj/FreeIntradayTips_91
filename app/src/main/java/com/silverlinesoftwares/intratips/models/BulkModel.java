package com.silverlinesoftwares.intratips.models;

public class BulkModel {
    private String dates;
    private String symbol;
    private String client_name;
    private String buy_sell;
    private String quantity;
    private String prices;

    public BulkModel(String dates, String symbol, String client_name, String buy_sell, String quantity, String prices) {
        this.dates = dates;
        this.symbol = symbol;
        this.client_name = client_name;
        this.buy_sell = buy_sell;
        this.quantity = quantity;
        this.prices = prices;
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

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getBuy_sell() {
        return buy_sell;
    }

    public void setBuy_sell(String buy_sell) {
        this.buy_sell = buy_sell;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
}
