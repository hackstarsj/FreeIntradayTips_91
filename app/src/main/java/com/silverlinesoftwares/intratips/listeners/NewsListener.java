package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.NewsModel;

import java.util.List;

public interface NewsListener {
    void onSucess(List<NewsModel> newsModels);
    void onFailedNews(String msg);
}
