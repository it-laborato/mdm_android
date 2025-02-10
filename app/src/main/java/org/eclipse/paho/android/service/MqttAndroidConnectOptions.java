package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MqttAndroidConnectOptions extends MqttConnectOptions {
    public static final int PING_ALARM = 0;
    public static final int PING_WORKER = 1;

    private int pingType;

    public int getPingType() {
        return pingType;
    }

    public void setPingType(int pingType) {
        this.pingType = pingType;
    }
}
