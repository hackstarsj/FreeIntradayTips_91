package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.InsiderRosterModel;
import com.silverlinesoftwares.intratips.models.InsiderTransactionModel;
import com.silverlinesoftwares.intratips.models.MajorHolderModel;

import java.util.List;

public interface ShareHolderListener {
    void onMajorLoaded(List<MajorHolderModel> data);
    void onInsiderLoader(List<InsiderRosterModel> insiderRosterModels);
    void onInsiderTransaction(List<InsiderTransactionModel> list);
    void onFailed(String msg);
}
