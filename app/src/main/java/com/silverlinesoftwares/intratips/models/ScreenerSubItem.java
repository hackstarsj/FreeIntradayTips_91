package com.silverlinesoftwares.intratips.models;

public class ScreenerSubItem {
    String item;
    String url;
    String methods;

    public ScreenerSubItem(String item, String url,String methods) {
        this.item = item;
        this.url = url;
        this.methods=methods;

    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
