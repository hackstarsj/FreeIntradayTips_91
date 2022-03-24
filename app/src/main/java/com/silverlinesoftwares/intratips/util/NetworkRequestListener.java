package com.silverlinesoftwares.intratips.util;

public interface NetworkRequestListener {
    void onStartLoading();
    void onCompletedLoading(String data, String from);
    void onErrorLoading(String message);
}
