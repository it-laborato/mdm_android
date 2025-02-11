package org.laborato.mdmlab.launcher.util;

import android.content.SharedPreferences;
import android.util.Log;

import org.laborato.mdmlab.launcher.BuildConfig;
import org.laborato.mdmlab.launcher.Const;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferenceLogger {

    private static boolean DEBUG = BuildConfig.DEVICE_ADMIN_DEBUG;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void _log(SharedPreferences preferences, String message) {
        Log.d(Const.LOG_TAG, message);
        if (DEBUG) {
            String logString = preferences.getString(Const.PREFERENCES_LOG_STRING, "");
            logString += sdf.format(new Date()) + " " + message;
            logString += "\n";
            preferences.edit().putString(Const.PREFERENCES_LOG_STRING, logString).commit();
        }
    }

    public synchronized static void log(SharedPreferences preferences, String message) {
        _log(preferences, message);
    }

    public synchronized static String getLogString(SharedPreferences preferences) {
        if (DEBUG) {
            return preferences.getString(Const.PREFERENCES_LOG_STRING, "");
        }
        return "";
    }

    public synchronized static void clearLogString(SharedPreferences preferences) {
        if (DEBUG) {
            preferences.edit().putString(Const.PREFERENCES_LOG_STRING, "").commit();
        }
    }

    public synchronized static void printStackTrace(SharedPreferences preferences, Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        _log(preferences, errors.toString());
    }
}
