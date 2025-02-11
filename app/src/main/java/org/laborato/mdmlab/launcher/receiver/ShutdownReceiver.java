package org.laborato.mdmlab.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.util.RemoteLogger;

public class ShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        RemoteLogger.log(context, Const.LOG_INFO, "Shutting down the device");
    }
}
