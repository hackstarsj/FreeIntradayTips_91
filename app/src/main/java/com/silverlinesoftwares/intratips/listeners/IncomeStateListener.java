package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.IncomeStatementModel;

import java.util.List;

public interface IncomeStateListener {
    void onSucess(List<IncomeStatementModel> data,List<IncomeStatementModel> incomeStatementModels);
    void onFailed(String msg);
}
