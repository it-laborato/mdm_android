package org.laborato.mdmlab.launcher.util;

import android.content.ComponentName;
import android.content.Context;

import org.laborato.mdmlab.launcher.AdminReceiver;

/**
 * For compatibility with old builds
 * Legacy admin receiver replaced in legacy build variants
 */
public class LegacyUtils {
    public static ComponentName getAdminComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), AdminReceiver.class);
    }
}
