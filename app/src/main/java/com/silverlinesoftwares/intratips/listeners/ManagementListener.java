package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.MajorHolderModel;

import java.util.List;

public interface ManagementListener {
    void onSucess(List<MajorHolderModel> data);
    void onFailed(String msg);
}
