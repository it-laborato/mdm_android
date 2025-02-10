package com.hmdm.launcher.json;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class Download {
    @JsonIgnore
    private long _id;

    private String url;
    private String path;
    private long attempts;
    private long lastAttemptTime;
    private boolean downloaded;
    private boolean installed;

    public Download() {}

    public Download(Download download) {
        _id = download._id;
        url = download.url;
        path = download.path;
        attempts = download.attempts;
        lastAttemptTime = download.lastAttemptTime;
        downloaded = download.downloaded;
        installed = download.installed;
    }

    @SuppressLint("Range")
    public Download(Cursor cursor) {
        setId(cursor.getLong(cursor.getColumnIndex("_id")));
        setUrl(cursor.getString(cursor.getColumnIndex("url")));
        setPath(cursor.getString(cursor.getColumnIndex("path")));
        setAttempts(cursor.getLong(cursor.getColumnIndex("attempts")));
        setLastAttemptTime(cursor.getLong(cursor.getColumnIndex("lastAttemptTime")));
        setDownloaded(cursor.getInt(cursor.getColumnIndex("downloaded")) != 0);
        setInstalled(cursor.getInt(cursor.getColumnIndex("installed")) != 0);
    }

    @JsonIgnore
    public long getId() {
        return _id;
    }

    @JsonIgnore
    public void setId(long _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getAttempts() {
        return attempts;
    }

    public void setAttempts(long attempts) {
        this.attempts = attempts;
    }

    public long getLastAttemptTime() {
        return lastAttemptTime;
    }

    public void setLastAttemptTime(long lastAttemptTime) {
        this.lastAttemptTime = lastAttemptTime;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }
}
