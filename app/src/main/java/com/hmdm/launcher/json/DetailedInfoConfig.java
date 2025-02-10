package com.hmdm.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DetailedInfoConfig {
    private Boolean sendData;
    private Integer intervalMins;

    public Boolean getSendData() {
        return sendData;
    }

    public void setSendData(Boolean sendData) {
        this.sendData = sendData;
    }

    public Integer getIntervalMins() {
        return intervalMins;
    }

    public void setIntervalMins(Integer intervalMins) {
        this.intervalMins = intervalMins;
    }
}
