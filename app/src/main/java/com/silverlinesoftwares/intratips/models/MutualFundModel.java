package com.silverlinesoftwares.intratips.models;

public class MutualFundModel {
    String symbol;
    String prices;
    String chnage;
    String change_percentage;
    String avg_50;
    String avg_200;
    String avg_30;
    String ytd;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getChnage() {
        return chnage;
    }

    public void setChnage(String chnage) {
        this.chnage = chnage;
    }

    public String getChange_percentage() {
        return change_percentage;
    }

    public void setChange_percentage(String change_percentage) {
        this.change_percentage = change_percentage;
    }

    public String getAvg_50() {
        return avg_50;
    }

    public void setAvg_50(String avg_50) {
        this.avg_50 = avg_50;
    }

    public String getAvg_200() {
        return avg_200;
    }

    public void setAvg_200(String avg_200) {
        this.avg_200 = avg_200;
    }

    public String getAvg_30() {
        return avg_30;
    }

    public void setAvg_30(String avg_30) {
        this.avg_30 = avg_30;
    }

    public String getYtd() {
        return ytd;
    }

    public void setYtd(String ytd) {
        this.ytd = ytd;
    }

    public MutualFundModel(String symbol, String chnage, String change_percentage,String prices, String avg_50, String avg_200, String avg_30, String ytd) {
        this.symbol = symbol;
        this.prices = prices;
        this.chnage = chnage;
        this.change_percentage = change_percentage;
        this.avg_50 = avg_50;
        this.avg_200 = avg_200;
        this.avg_30 = avg_30;
        this.ytd = ytd;
    }
}
