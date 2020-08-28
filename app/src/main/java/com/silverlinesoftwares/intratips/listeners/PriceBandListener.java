package com.silverlinesoftwares.intratips.listeners;

import org.json.JSONObject;

public interface PriceBandListener {
    void onSucess(JSONObject data);
    void onFailed(String msg);
}
