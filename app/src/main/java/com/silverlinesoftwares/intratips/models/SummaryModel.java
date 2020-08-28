package com.silverlinesoftwares.intratips.models;

import android.text.Spanned;

import java.io.Serializable;

public class SummaryModel implements Serializable {
    private String market_cap="";
    private String book_value="";
    private String roce="";
    private String stockPe="";
    private String roe="";
    private Spanned high52=null;
    private Spanned low52=null;
    private String dividendYield="";
    private String sellsgrowth="";
    private String faceValue="";
    private String volume="";
    private String target1Y="";
    private String averageVolume="";
    private String vWap="";
    private String lowerPriceBand="";
    private String upperPriceband="";


    public String getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(String market_cap) {
        this.market_cap = market_cap;
    }

    public String getBook_value() {
        return book_value;
    }

    public void setBook_value(String book_value) {
        this.book_value = book_value;
    }

    public String getRoce() {
        return roce;
    }

    public void setRoce(String roce) {
        this.roce = roce;
    }

    public String getStocPe() {
        return stockPe;
    }

    public void setStockPe(String stopPe) {
        this.stockPe = stopPe;
    }

    public String getRoe() {
        return roe;
    }

    public void setRoe(String roe) {
        this.roe = roe;
    }

    public Spanned getHigh52() {
        return high52;
    }

    public void setHigh52(Spanned high52) {
        this.high52 = high52;
    }

    public Spanned getLow52() {
        return low52;
    }

    public void setLow52(Spanned low52) {
        this.low52 = low52;
    }

    public String getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(String dividendYield) {
        this.dividendYield = dividendYield;
    }

    public String getSellsgrowth() {
        return sellsgrowth;
    }

    public void setSellsgrowth(String sellsgrowth) {
        this.sellsgrowth = sellsgrowth;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTarget1Y() {
        return target1Y;
    }

    public void setTarget1Y(String target1Y) {
        this.target1Y = target1Y;
    }

    public String getAverageVolume() {
        return averageVolume;
    }

    public void setAverageVolume(String averageVolume) {
        this.averageVolume = averageVolume;
    }

    public String getvWap() {
        return vWap;
    }

    public void setvWap(String vWap) {
        this.vWap = vWap;
    }

    public String getLowerPriceBand() {
        return lowerPriceBand;
    }

    public void setLowerPriceBand(String lowerPriceBand) {
        this.lowerPriceBand = lowerPriceBand;
    }

    public String getUpperPriceband() {
        return upperPriceband;
    }

    public void setUpperPriceband(String upperPriceband) {
        this.upperPriceband = upperPriceband;
    }
}
