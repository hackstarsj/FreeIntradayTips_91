package com.silverlinesoftwares.intratips.listeners;

import com.silverlinesoftwares.intratips.models.ResponseModel;

public interface DetailsResponseListener {
    void onProfile(ResponseModel data);
    void onFailedProfile(String msg);
}
