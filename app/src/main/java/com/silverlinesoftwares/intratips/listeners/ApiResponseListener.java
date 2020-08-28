package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.ResponseModel;

public interface ApiResponseListener {
    void onSucess(ResponseModel data);
    void onFailed(String msg);
}
