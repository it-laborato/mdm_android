package com.hmdm.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties( ignoreUnknown = true )
public class RemoteLogConfigResponse extends ServerResponse {

    private List<RemoteLogConfig> data;

    public RemoteLogConfigResponse() {}

    public List<RemoteLogConfig> getData() {
        return data;
    }

    public void setData( List<RemoteLogConfig> data ) {
        this.data = data;
    }
}