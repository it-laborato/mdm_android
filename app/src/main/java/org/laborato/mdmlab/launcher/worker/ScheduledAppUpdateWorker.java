package org.laborato.mdmlab.launcher.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.helper.ConfigUpdater;
import org.laborato.mdmlab.launcher.helper.SettingsHelper;
import org.laborato.mdmlab.launcher.util.RemoteLogger;

import java.util.concurrent.TimeUnit;

public class ScheduledAppUpdateWorker extends Worker {

    // Minimal interval is 15 minutes as per docs
    public static final int FIRE_PERIOD_MINS = 15;

    private static final String WORK_TAG_SCHEDULED_UPDATES = "org.laborato.mdmlab.launcher.WORK_TAG_SCHEDULED_UPDATES";

    public static void schedule(Context context) {
        SettingsHelper settingsHelper = SettingsHelper.getInstance(context);
        if (settingsHelper.getConfig() != null) {
            settingsHelper.setLastAppUpdateState(ConfigUpdater.checkAppUpdateTimeRestriction(settingsHelper.getConfig()));
        }
        Log.d(Const.LOG_TAG, "Scheduled app updates worker runs each " + FIRE_PERIOD_MINS + " mins");
        PeriodicWorkRequest queryRequest =
                new PeriodicWorkRequest.Builder(ScheduledAppUpdateWorker.class, FIRE_PERIOD_MINS, TimeUnit.MINUTES)
                        .addTag(Const.WORK_TAG_COMMON)
                        .build();
        WorkManager.getInstance(context.getApplicationContext()).enqueueUniquePeriodicWork(WORK_TAG_SCHEDULED_UPDATES,
                ExistingPeriodicWorkPolicy.REPLACE, queryRequest);
    }

    private Context context;
    private SettingsHelper settingsHelper;

    public ScheduledAppUpdateWorker(
            @NonNull final Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
        settingsHelper = SettingsHelper.getInstance(context);
    }

    @Override
    // This is running in a background thread by WorkManager
    public Result doWork() {
        if (settingsHelper.getConfig() == null) {
            Log.d(Const.LOG_TAG, "ScheduledAppUpdateWorker: config=null");
            return Result.failure();
        }
        if (settingsHelper.getConfig().getAppUpdateFrom() == null || settingsHelper.getConfig().getAppUpdateTo() == null) {
            // No need to do anything
            Log.d(Const.LOG_TAG, "ScheduledAppUpdateWorker: scheduled app update not set");
            return Result.success();
        }

        boolean lastAppUpdateState = settingsHelper.getLastAppUpdateState();
        boolean canUpdateAppsNow = ConfigUpdater.checkAppUpdateTimeRestriction(settingsHelper.getConfig());
        Log.d(Const.LOG_TAG, "ScheduledAppUpdateWorker: lastAppUpdateState=" + lastAppUpdateState + ", canUpdateAppsNow=" + canUpdateAppsNow);
        if (lastAppUpdateState == canUpdateAppsNow) {
            // App update state not changed
            return Result.success();
        }

        if (!lastAppUpdateState && canUpdateAppsNow) {
            // Need to update apps now
            RemoteLogger.log(context, Const.LOG_DEBUG, "Running scheduled app update");
            settingsHelper.setConfigUpdateTimestamp(System.currentTimeMillis());
            ConfigUpdater.forceConfigUpdate(context);
        }
        settingsHelper.setLastAppUpdateState(canUpdateAppsNow);
        return Result.success();
    }
}
