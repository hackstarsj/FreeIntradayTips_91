package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.SummaryModel;

public interface StockDetailListener {
    void onSummayLoaded(SummaryModel data);
    void onFailed(String msg);
}
