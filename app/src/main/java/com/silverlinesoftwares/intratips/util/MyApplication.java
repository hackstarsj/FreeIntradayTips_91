package com.silverlinesoftwares.intratips.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;


import com.google.android.gms.ads.MobileAds;


public class MyApplication extends Application {
    private SharedPreferences sharedPreferences;
    public static final String PREFS = "MY_SHARED";
    AppOpenAdManager appOpenManager;
    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(
                this,
                initializationStatus -> {});

        appOpenManager = new AppOpenAdManager(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
