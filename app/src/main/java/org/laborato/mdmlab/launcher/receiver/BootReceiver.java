package org.laborato.mdmlab.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.helper.Initializer;
import org.laborato.mdmlab.launcher.helper.SettingsHelper;
import org.laborato.mdmlab.launcher.pro.ProUtils;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Const.LOG_TAG, "Got the BOOT_RECEIVER broadcast");

        SettingsHelper settingsHelper = SettingsHelper.getInstance(context.getApplicationContext());
        if (!settingsHelper.isBaseUrlSet()) {
            // We're here before initializing after the factory reset! Let's ignore this call
            return;
        }

        long lastAppStartTime = settingsHelper.getAppStartTime();
        long bootTime = System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime();
        Log.d(Const.LOG_TAG, "appStartTime=" + lastAppStartTime + ", bootTime=" + bootTime);
        if (lastAppStartTime < bootTime) {
            Log.i(Const.LOG_TAG, "Laborato MDM wasn't started since boot, start initializing services");
        } else {
            Log.i(Const.LOG_TAG, "Laborato MDM is already started, ignoring BootReceiver");
            return;
        }

        Initializer.init(context);
        Initializer.startServicesAndLoadConfig(context);

        SettingsHelper.getInstance(context).setMainActivityRunning(false);
        if (ProUtils.kioskModeRequired(context)) {
            Log.i(Const.LOG_TAG, "Kiosk mode required, forcing Laborato MDM to run in the foreground");
            // If kiosk mode is required, then we just simulate clicking Home and starting MainActivity
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(homeIntent);
            return;
        }
    }
}
