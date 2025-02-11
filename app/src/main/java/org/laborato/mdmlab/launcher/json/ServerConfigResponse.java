package org.laborato.mdmlab.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class ServerConfigResponse extends ServerResponse {
    private ServerConfig data;

    public ServerConfig getData() {
        return data;
    }

    public void setData( ServerConfig data ) {
        this.data = data;
    }
}
