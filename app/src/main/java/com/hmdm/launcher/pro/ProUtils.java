package com.hmdm.launcher.pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.View;

import com.hmdm.launcher.R;
import com.hmdm.launcher.json.ServerConfig;

import java.util.Calendar;

/**
 * These functions are available in Pro-version only
 * In a free version, the class contains stubs
 */
public class ProUtils {

    public static boolean isPro() {
        return false;
    }

    public static boolean kioskModeRequired(Context context) {
        return false;
    }

    public static void initCrashlytics(Context context) {
        // Stub
    }

    public static void sendExceptionToCrashlytics(Throwable e) {
        // Stub
    }

    // Start the service checking if the foreground app is allowed to the user (by usage statistics)
    public static boolean checkAccessibilityService(Context context) {
        // Stub
        return true;
    }

    // Pro-version
    public static boolean checkUsageStatistics(Context context) {
        // Stub
        return true;
    }

    // Add a transparent view on top of the status bar which prevents user interaction with the status bar
    public static View preventStatusBarExpansion(Activity activity) {
        // Stub
        return null;
    }

    // Add a transparent view on top of a swipeable area at the right (opens app list on Samsung tablets)
    public static View preventApplicationsList(Activity activity) {
        // Stub
        return null;
    }

    public static View createKioskUnlockButton(Activity activity) {
        // Stub
        return null;
    }

    public static boolean isKioskAppInstalled(Context context) {
        // Stub
        return false;
    }

    public static boolean isKioskModeRunning(Context context) {
        // Stub
        return false;
    }

    public static Intent getKioskAppIntent(String kioskApp, Activity activity) {
        // Stub
        return null;
    }

    // Start COSU kiosk mode
    public static boolean startCosuKioskMode(String kioskApp, Activity activity, boolean enableSettings) {
        // Stub
        return false;
    }

    // Update app list in the kiosk mode
    public static void updateKioskAllowedApps(String kioskApp, Activity activity, boolean enableSettings) {
        // Stub
    }

    public static void unlockKiosk(Activity activity) {
        // Stub
    }

    public static void processConfig(Context context, ServerConfig config) {
        // Stub
    }

    public static void processLocation(Context context, Location location, String provider) {
        // Stub    
    }

    public static String getAppName(Context context) {
        return context.getString(R.string.app_name);
    }

    public static String getCopyright(Context context) {
        return "(c) " + Calendar.getInstance().get(Calendar.YEAR) + " " + context.getString(R.string.vendor);
    }
}
