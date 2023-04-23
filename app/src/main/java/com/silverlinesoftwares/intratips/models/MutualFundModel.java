package com.silverlinesoftwares.intratips.models;

public class MutualFundModel {
    String fund_name;
    String category;
    String aum;
    String change_1w;
    String change_1m;
    String change_3m;
    String change_6m;
    String change_1y;
    String change_3y;
    String change_5y;

    public MutualFundModel(String fund_name, String category, String aum, String change_1w, String change_1m, String change_3m, String change_6m, String change_1y, String change_3y, String change_5y) {
        this.fund_name = fund_name;
        this.category = category;
        this.aum = aum;
        this.change_1w = change_1w;
        this.change_1m = change_1m;
        this.change_3m = change_3m;
        this.change_6m = change_6m;
        this.change_1y = change_1y;
        this.change_3y = change_3y;
        this.change_5y = change_5y;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAum() {
        return aum;
    }

    public void setAum(String aum) {
        this.aum = aum;
    }

    public String getChange_1w() {
        return change_1w;
    }

    public void setChange_1w(String change_1w) {
        this.change_1w = change_1w;
    }

    public String getChange_1m() {
        return change_1m;
    }

    public void setChange_1m(String change_1m) {
        this.change_1m = change_1m;
    }

    public String getChange_3m() {
        return change_3m;
    }

    public void setChange_3m(String change_3m) {
        this.change_3m = change_3m;
    }

    public String getChange_6m() {
        return change_6m;
    }

    public void setChange_6m(String change_6m) {
        this.change_6m = change_6m;
    }

    public String getChange_1y() {
        return change_1y;
    }

    public void setChange_1y(String change_1y) {
        this.change_1y = change_1y;
    }

    public String getChange_3y() {
        return change_3y;
    }

    public void setChange_3y(String change_3y) {
        this.change_3y = change_3y;
    }

    public String getChange_5y() {
        return change_5y;
    }

    public void setChange_5y(String change_5y) {
        this.change_5y = change_5y;
    }
}
