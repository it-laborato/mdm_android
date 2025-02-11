package org.laborato.mdmlab.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.util.DeviceInfoProvider;
import org.laborato.mdmlab.launcher.util.RemoteLogger;

public class SimChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        // SIM card changed, log the new IMSI and number
        String phoneNumber = null;
        try {
            phoneNumber = DeviceInfoProvider.getPhoneNumber(context);
        } catch (Exception e) {
        }

        String simState = intent.getExtras().getString("ss");

        String message = null;
        if (simState.equals("LOADED")) {
            message = "SIM card loaded";
            if (phoneNumber != null && phoneNumber.length() > 0) {
                message += ". New phone number: " + phoneNumber;
            }
        } else if (simState.equals("ABSENT")) {
            message = "SIM card removed";
        }

        if (message != null) {
            RemoteLogger.log(context, Const.LOG_INFO, message);
        }
    }
}
