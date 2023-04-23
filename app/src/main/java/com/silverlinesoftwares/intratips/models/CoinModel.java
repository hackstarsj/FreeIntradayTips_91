package com.silverlinesoftwares.intratips.models;

public class CoinModel {
    String symbol;
    String name;
    String coin_logo;
    String price;
    String change;
    String change_per;
    String market_cap;
    String volume;
    String volume_24;

    public CoinModel(String symbol, String name, String coin_logo, String price, String change, String change_per, String market_cap, String volume, String volume_24) {
        this.symbol = symbol;
        this.name = name;
        this.coin_logo = coin_logo;
        this.price = price;
        this.change = change;
        this.change_per = change_per;
        this.market_cap = market_cap;
        this.volume = volume;
        this.volume_24 = volume_24;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoin_logo() {
        return coin_logo;
    }

    public void setCoin_logo(String coin_logo) {
        this.coin_logo = coin_logo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChange_per() {
        return change_per;
    }

    public void setChange_per(String change_per) {
        this.change_per = change_per;
    }

    public String getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(String market_cap) {
        this.market_cap = market_cap;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume_24() {
        return volume_24;
    }

    public void setVolume_24(String volume_24) {
        this.volume_24 = volume_24;
    }
}
