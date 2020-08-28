package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.ResponseModel;

public interface ResendCodeListener {
    void onSucessSent(ResponseModel data);
    void onFailedSent(String msg);
}
