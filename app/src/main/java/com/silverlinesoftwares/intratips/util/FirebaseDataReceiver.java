package com.silverlinesoftwares.intratips.util;

import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;

/**
 * Created by sanjeev on 11/2/18.
 */

public class FirebaseDataReceiver extends WakefulBroadcastReceiver {
    private final String TAG = "FirebaseDataReceiver";

    public void onReceive(Context context, Intent intent) {
    ///    Log.d(TAG, "I'm in!!!");

//        Bundle dataBundle = intent.getBundleExtra("data");
  //      Log.d(TAG, dataBundle.toString());

    }
}
