package com.silverlinesoftwares.intratips.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BgService extends Service {
    public BgService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
