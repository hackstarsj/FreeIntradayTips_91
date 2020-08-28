package com.silverlinesoftwares.intratips.models;

public class PriceBandHitterModel {
    private String symbol;
    private String series;
    private String ltp;
    private String chng;
    private String perChng;
    private String priceBandPer;
    private String highPrice;
    private String lowPrice;
    private String highPrice52;
    private String lowPrice52;
    private String tradedQuantity;
    private String turnoverInLakhs;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getChng() {
        return chng;
    }

    public void setChng(String chng) {
        this.chng = chng;
    }

    public String getPerChng() {
        return perChng;
    }

    public void setPerChng(String perChng) {
        this.perChng = perChng;
    }

    public String getPriceBandPer() {
        return priceBandPer;
    }

    public void setPriceBandPer(String priceBandPer) {
        this.priceBandPer = priceBandPer;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHighPrice52() {
        return highPrice52;
    }

    public void setHighPrice52(String highPrice52) {
        this.highPrice52 = highPrice52;
    }

    public String getLowPrice52() {
        return lowPrice52;
    }

    public void setLowPrice52(String lowPrice52) {
        this.lowPrice52 = lowPrice52;
    }

    public String getTradedQuantity() {
        return tradedQuantity;
    }

    public void setTradedQuantity(String tradedQuantity) {
        this.tradedQuantity = tradedQuantity;
    }

    public String getTurnoverInLakhs() {
        return turnoverInLakhs;
    }

    public void setTurnoverInLakhs(String turnoverInLakhs) {
        this.turnoverInLakhs = turnoverInLakhs;
    }
}
