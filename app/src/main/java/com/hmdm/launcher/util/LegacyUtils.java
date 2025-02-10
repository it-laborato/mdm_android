package com.hmdm.launcher.util;

import android.content.ComponentName;
import android.content.Context;

import com.hmdm.launcher.AdminReceiver;

/**
 * For compatibility with old builds
 * Legacy admin receiver is ru.headwind.kiosk.AdminReceiver, it is replaced in legacy build variants
 */
public class LegacyUtils {
    public static ComponentName getAdminComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), AdminReceiver.class);
    }
}
