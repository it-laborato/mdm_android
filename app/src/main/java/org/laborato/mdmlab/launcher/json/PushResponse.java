package org.laborato.mdmlab.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties( ignoreUnknown = true )
public class PushResponse {
    private String status;
    private List<PushMessage> data;

    public PushResponse() {}

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public List<PushMessage> getData() {
        return data;
    }

    public void setData( List<PushMessage> data ) {
        this.data = data;
    }
}
