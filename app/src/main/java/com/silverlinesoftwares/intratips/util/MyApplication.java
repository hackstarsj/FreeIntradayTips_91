package com.silverlinesoftwares.intratips.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.multidex.MultiDex;


import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by pankajsharma on 21-Aug-17.
 */
public class MyApplication extends Application {
    private SharedPreferences sharedPreferences;
    public static final String PREFS = "MY_SHARED";
    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        MobileAds.initialize(
                this,
                new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {}
                });

        AppOpenAdManager appOpenManager = new AppOpenAdManager(this);
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
