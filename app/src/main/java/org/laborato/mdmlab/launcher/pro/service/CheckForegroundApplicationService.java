package org.laborato.mdmlab.launcher.pro.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * In open-source version, the service checking foreground apps is just a stub;
 * this option is available in Pro-version only
 */
public class CheckForegroundApplicationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // Stub
        return null;
    }
}
