package com.hmdm.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DeviceCreateOptions {
    private String customer;
    private String configuration;
    private List<String> groups;

    public DeviceCreateOptions() {}

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public void setGroups(String[] groups) {
        if (groups == null) {
            this.groups = null;
            return;
        }
        this.groups = new LinkedList<>();
        for (String group : groups) {
            this.groups.add(group);
        }
    }

    public void setGroups(Set<String> groups) {
        if (groups == null) {
            this.groups = null;
            return;
        }
        this.groups = new LinkedList<>();
        for (String group : groups) {
            this.groups.add(group);
        }
    }

    public void setGroups(String groups) {
        if (groups == null) {
            this.groups = null;
            return;
        }
        String[] groupArray = groups.split(",");
        setGroups(groupArray);
    }

    public Set<String> getGroupSet() {
        if (groups == null) {
            return null;
        }
        Set<String> result = new HashSet<>();
        for (String group : groups) {
            result.add(group);
        }
        return result;
    }
}
