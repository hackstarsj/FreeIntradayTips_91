package com.silverlinesoftwares.intratips.models;

public class IncomeStatementModel {
    private String title;
    private String data_1;
    private String data_2;
    private String data_3;
    private String data_4;
    private String symbol="";
    private boolean is_head;
    private boolean is_bold;
    private boolean is_center=false;

    public boolean isIs_center() {
        return is_center;
    }

    public void setIs_center(boolean is_center) {
        this.is_center = is_center;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData_1() {
        return data_1;
    }

    public void setData_1(String data_1) {
        this.data_1 = data_1;
    }

    public String getData_2() {
        return data_2;
    }

    public void setData_2(String data_2) {
        this.data_2 = data_2;
    }

    public String getData_3() {
        return data_3;
    }

    public String getData_4() {
        return data_4;
    }

    public void setData_4(String data_4) {
        this.data_4 = data_4;
    }

    public void setData_3(String data_3) {
        this.data_3 = data_3;
    }

    public boolean isIs_head() {
        return is_head;
    }

    public void setIs_head(boolean is_head) {
        this.is_head = is_head;
    }

    public boolean isIs_bold() {
        return is_bold;
    }

    public void setIs_bold(boolean is_bold) {
        this.is_bold = is_bold;
    }
}
