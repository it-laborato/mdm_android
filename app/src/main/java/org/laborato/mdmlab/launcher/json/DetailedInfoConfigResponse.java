package org.laborato.mdmlab.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DetailedInfoConfigResponse extends ServerResponse {
    private DetailedInfoConfig data;

    public DetailedInfoConfigResponse() {}

    public DetailedInfoConfig getData() {
        return data;
    }

    public void setData( DetailedInfoConfig data ) {
        this.data = data;
    }
}
