package org.laborato.mdmlab;

class Const {
    static final String SERVICE_ACTION = "org.laborato.mdmlab.action.Connect";
    static final String PACKAGE = "org.laborato.mdmlab.launcher";
    static final String ADMIN_RECEIVER_CLASS = "org.laborato.mdmlab.launcher.AdminReceiver";

    public static final String INTENT_PUSH_NOTIFICATION_PREFIX = "org.laborato.mdmlab.push.";
    public static final String INTENT_PUSH_NOTIFICATION_EXTRA = "org.laborato.mdmlab.PUSH_DATA";

    public static final String LOG_TAG ="MDMLABAPI";

    public static final String NOTIFICATION_CONFIG_UPDATED = "org.laborato.mdmlab.push.configUpdated";

    public static final int MDMLAB_RECONNECT_DELAY_FIRST = 5000;
    public static final int MDMLAB_RECONNECT_DELAY_NEXT = 60000;
}
