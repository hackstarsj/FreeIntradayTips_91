package com.silverlinesoftwares.intratips.models;

public class InsiderRosterModel {
    private String title;
    private String data_1;
    private String data_2;
    private String data_3;
    private boolean is_head;

    public InsiderRosterModel(String title, String data_1, String data_2, String data_3, boolean is_head) {
        this.title = title;
        this.data_1 = data_1;
        this.data_2 = data_2;
        this.data_3 = data_3;
        this.is_head = is_head;
    }

    public boolean isIs_head() {
        return is_head;
    }

    public void setIs_head(boolean is_head) {
        this.is_head = is_head;
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

    public void setData_3(String data_3) {
        this.data_3 = data_3;
    }
    
}
