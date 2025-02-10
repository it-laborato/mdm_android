package com.hmdm.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hmdm.launcher.Const;
import com.hmdm.launcher.util.RemoteLogger;

public class ShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        RemoteLogger.log(context, Const.LOG_INFO, "Shutting down the device");
    }
}
