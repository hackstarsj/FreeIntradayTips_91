package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.MajorHolderModel;
import com.silverlinesoftwares.intratips.models.ResultModel;

import java.util.List;

public interface ResultListener {
    void onSucess(List<ResultModel> data);
    void onFailed(String msg);
}
