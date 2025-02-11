package org.laborato.mdmlab.launcher.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.db.DatabaseHelper;
import org.laborato.mdmlab.launcher.db.LogConfigTable;
import org.laborato.mdmlab.launcher.db.LogTable;
import org.laborato.mdmlab.launcher.json.RemoteLogConfig;
import org.laborato.mdmlab.launcher.json.RemoteLogItem;
import org.laborato.mdmlab.launcher.worker.RemoteLogWorker;

import java.util.List;

/**
 * Remote logging engine which uses SQLite for configuration
 * and storing unsent logs
 */
public class RemoteLogger {
    public static long lastLogRemoval = 0;

    public static void updateConfig(Context context, List<RemoteLogConfig> rules) {
        LogConfigTable.replaceAll(DatabaseHelper.instance(context).getWritableDatabase(), rules);
    }

    public static void log(Context context, int level, String message) {
        switch (level) {
            case Const.LOG_VERBOSE:
                Log.v(Const.LOG_TAG, message);
                break;
            case Const.LOG_DEBUG:
                Log.d(Const.LOG_TAG, message);
                break;
            case Const.LOG_INFO:
                Log.i(Const.LOG_TAG, message);
                break;
            case Const.LOG_WARN:
                Log.w(Const.LOG_TAG, message);
                break;
            case Const.LOG_ERROR:
                Log.e(Const.LOG_TAG, message);
                break;
        }

        RemoteLogItem item = new RemoteLogItem();
        item.setTimestamp(System.currentTimeMillis());
        item.setLogLevel(level);
        item.setPackageId(context.getPackageName());
        item.setMessage(message);
        postLog(context, item);
    }

    public static void postLog(Context context, RemoteLogItem item) {
        DatabaseHelper dbHelper = DatabaseHelper.instance(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (LogConfigTable.match(db, item)) {
            db = dbHelper.getWritableDatabase();
            LogTable.insert(db, item);
            sendLogsToServer(context);
        }

        // Remove old logs once per hour
        long now = System.currentTimeMillis();
        if (now > lastLogRemoval + 3600000L) {
            db = dbHelper.getWritableDatabase();
            LogTable.deleteOldItems(db);
            lastLogRemoval = now;
        }
    }

    public static void sendLogsToServer(Context context) {
        RemoteLogWorker.scheduleUpload(context);
    }
}
