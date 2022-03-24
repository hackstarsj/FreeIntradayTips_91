package com.silverlinesoftwares.intratips.models;

public class StrongBuySellModel {
    String stock;
    String full_name;
    String exchange;
    String change;
    String changePer;
    String ltp;
    String vol;
    String mktCap;
    boolean isLow;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangePer() {
        return changePer;
    }

    public void setChangePer(String changePer) {
        this.changePer = changePer;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getMktCap() {
        return mktCap;
    }

    public void setMktCap(String mktCap) {
        this.mktCap = mktCap;
    }

    public boolean isLow() {
        return isLow;
    }

    public void setLow(boolean low) {
        isLow = low;
    }

    public StrongBuySellModel(String stock, String full_name, String exchange, String change, String changePer, String ltp, String vol, String mktCap, boolean isLow) {
        this.stock = stock;
        this.full_name = full_name;
        this.exchange = exchange;
        this.change = change;
        this.changePer = changePer;
        this.ltp = ltp;
        this.vol = vol;
        this.mktCap = mktCap;
        this.isLow = isLow;
    }
}
