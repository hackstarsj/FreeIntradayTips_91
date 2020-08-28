package com.silverlinesoftwares.intratips.models;

import java.util.List;

public class ScreenerItem {
    String title;
    boolean click;
    List<ScreenerSubItem> screenerSubItemList;

    public ScreenerItem(String title, List<ScreenerSubItem> screenerSubItemList) {
        this.title = title;
        this.screenerSubItemList = screenerSubItemList;
        click=false;
    }


    public void setClick(boolean click) {
        this.click = click;
    }

    public boolean getClick() {
        return click;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScreenerSubItem> getScreenerSubItemList() {
        return screenerSubItemList;
    }

    public void setScreenerSubItemList(List<ScreenerSubItem> screenerSubItemList) {
        this.screenerSubItemList = screenerSubItemList;
    }
}
