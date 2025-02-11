package org.eclipse.paho.android.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.laborato.mdmlab.launcher.Const;
import org.laborato.mdmlab.launcher.util.RemoteLogger;

import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;

import java.util.concurrent.TimeUnit;

public class WorkerPingSender implements MqttPingSender {

    // Identifier for Intents, log messages, etc..
    private static final String TAG = "WorkerPingSender";

    private static final String WORKER_TAG_MQTT = "org.laborato.mdmlab.launcher.WORK_TAG_MQTT";

    private ClientComms comms;
    private MqttService service;

    private static WorkerPingSender instance;

    private WorkerPingSender(MqttService service) {
        if (service == null) {
            throw new IllegalArgumentException(
                    "Neither service nor client can be null.");
        }
        this.service = service;
    }

    public static WorkerPingSender getInstance(MqttService service) {
        if (instance == null) {
            instance = new WorkerPingSender(service);
        }
        instance.service = service;
        return instance;
    }

    @Override
    public void init(ClientComms comms) {
        this.comms = comms;
    }

    @Override
    public void start() {
        schedule(comms.getKeepAlive());
    }

    @Override
    public void stop() {
        WorkManager.getInstance(service.getApplicationContext()).cancelUniqueWork(WORKER_TAG_MQTT);
        RemoteLogger.log(service, Const.LOG_DEBUG, "MQTT ping cancelled");
    }

    @Override
    public void schedule(long delayInMilliseconds) {
        // Minimal interval is 15 mins
        // If delay is less than 15 mins, just schedule it right now
        long sec = delayInMilliseconds / 1000l;
        RemoteLogger.log(service, Const.LOG_DEBUG, "MQTT ping scheduled: " + sec + " sec");
        OneTimeWorkRequest queryRequest =
                new OneTimeWorkRequest.Builder(WorkerPingSender.InternalWorker.class)
                        .addTag(Const.WORK_TAG_COMMON)
                        .setInitialDelay(sec, TimeUnit.SECONDS)
                        .build();
        WorkManager.getInstance(service.getApplicationContext()).enqueueUniqueWork(WORKER_TAG_MQTT, ExistingWorkPolicy.REPLACE, queryRequest);
    }

    public static class InternalWorker extends Worker {

        private Context context;

        public InternalWorker(
                @NonNull final Context context,
                @NonNull WorkerParameters params) {
            super(context, params);
            this.context = context;
        }

        @NonNull
        @Override
        public Result doWork() {
            RemoteLogger.log(context, Const.LOG_DEBUG, "Sending MQTT Ping at:" + System.currentTimeMillis());
            PingDeathDetector.getInstance().registerPing();
            WorkerPingSender.instance.comms.checkForActivity(null);
            return null;
        }
    }
}
