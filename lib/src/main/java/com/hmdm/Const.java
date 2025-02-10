package com.hmdm;

class Const {
    static final String SERVICE_ACTION = "com.hmdm.action.Connect";
    static final String PACKAGE = "com.hmdm.launcher";
    static final String LEGACY_PACKAGE = "ru.headwind.kiosk";
    static final String ADMIN_RECEIVER_CLASS = "com.hmdm.launcher.AdminReceiver";

    public static final String INTENT_PUSH_NOTIFICATION_PREFIX = "com.hmdm.push.";
    public static final String INTENT_PUSH_NOTIFICATION_EXTRA = "com.hmdm.PUSH_DATA";

    public static final String LOG_TAG ="HeadwindMDMAPI";

    public static final String NOTIFICATION_CONFIG_UPDATED = "com.hmdm.push.configUpdated";

    public static final int HMDM_RECONNECT_DELAY_FIRST = 5000;
    public static final int HMDM_RECONNECT_DELAY_NEXT = 60000;
}
