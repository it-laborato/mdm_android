package org.laborato.mdmlab.launcher.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class RemoteFile {
    @JsonIgnore
    private long _id;

    private String url;
    private String path;
    private long lastUpdate;
    private String checksum;
    private boolean remove;
    private String description;
    private boolean varContent;

    public RemoteFile() {}

    public RemoteFile(RemoteFile remoteFile) {
        _id = remoteFile._id;
        lastUpdate = remoteFile.lastUpdate;
        url = remoteFile.url;
        checksum = remoteFile.checksum;
        remove = remoteFile.remove;
        path = remoteFile.path;
        description = remoteFile.description;
        varContent = remoteFile.varContent;
    }

    @JsonIgnore
    public long getId() {
        return _id;
    }

    @JsonIgnore
    public void setId(long _id) {
        this._id = _id;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVarContent() {
        return varContent;
    }

    public void setVarContent(boolean varContent) {
        this.varContent = varContent;
    }
}
