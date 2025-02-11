package org.laborato.mdmlab.launcher.json;

import org.json.JSONObject;

public class PushMessageJson extends PushMessage {
    private JSONObject payloadJSON;

    public PushMessageJson() {
    }

    public PushMessageJson(String messageType, JSONObject payloadJSON) {
        setMessageType(messageType);
        this.payloadJSON = payloadJSON;
    }

    @Override
    public JSONObject getPayloadJSON() {
        return payloadJSON;
    }

    public void setPayloadJSON(JSONObject payloadJSON) {
        this.payloadJSON = payloadJSON;
    }
}
