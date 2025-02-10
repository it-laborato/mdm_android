package com.hmdm.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONObject;

@JsonIgnoreProperties( ignoreUnknown = true )
public class PushMessage {
    private String messageType;
    private String payload;

    public static final String TYPE_CONFIG_UPDATING = "configUpdating";
    public static final String TYPE_CONFIG_UPDATED = "configUpdated";
    public static final String TYPE_RUN_APP = "runApp";
    public static final String TYPE_UNINSTALL_APP = "uninstallApp";
    public static final String TYPE_DELETE_FILE = "deleteFile";
    public static final String TYPE_PURGE_DIR = "purgeDir";
    public static final String TYPE_DELETE_DIR = "deleteDir";
    public static final String TYPE_PERMISSIVE_MODE = "permissiveMode";
    public static final String TYPE_RUN_COMMAND = "runCommand";
    public static final String TYPE_REBOOT = "reboot";
    public static final String TYPE_EXIT_KIOSK = "exitKiosk";
    public static final String TYPE_CLEAR_DOWNLOADS = "clearDownloadHistory";
    public static final String TYPE_SETTINGS = "settings";

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType( String messageType ) {
        this.messageType = messageType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public JSONObject getPayloadJSON() {
        if (payload != null) {
            try {
                return new JSONObject(payload);
            } catch (Exception e) {
                // Bad payload
            }
        }
        return null;
    }
}
