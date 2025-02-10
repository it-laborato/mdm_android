package com.hmdm;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class MDMPushMessage {
    private String type;
    private JSONObject data;

    public static final String MessageConfigUpdated = "configUpdated";

    public MDMPushMessage(String action, Bundle bundle) throws MDMException {
        if (!action.startsWith(Const.INTENT_PUSH_NOTIFICATION_PREFIX)) {
            throw new MDMException(MDMError.ERROR_INVALID_PARAMETER);
        }
        type = action.substring(Const.INTENT_PUSH_NOTIFICATION_PREFIX.length());
        if (bundle != null) {
            String packedPayload = bundle.getString(Const.INTENT_PUSH_NOTIFICATION_EXTRA);
            if (packedPayload != null) {
                try {
                    data = new JSONObject(packedPayload);
                } catch (JSONException e) {
                    throw new MDMException(MDMError.ERROR_INVALID_PARAMETER);
                }
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
