package org.laborato.mdmlab.launcher.util;

import android.os.Parcel;
import android.os.Parcelable;

public class AppInfo implements Parcelable {
    public static final int TYPE_APP = 0;
    public static final int TYPE_WEB = 1;
    public static final int TYPE_INTENT = 2;

    public int type;
    public Integer keyCode;
    public CharSequence name;
    public String packageName;
    public String url;
    public String iconUrl;
    public Integer screenOrder;
    public int useKiosk;
    public int longTap;
    public String intent;

    public AppInfo(){}

    protected AppInfo(Parcel in) {
        type = in.readInt();
        keyCode = (Integer)in.readSerializable();
        name = in.readString();
        packageName = in.readString();
        url = in.readString();
        iconUrl = in.readString();
        screenOrder = (Integer)in.readSerializable();
        useKiosk = in.readInt();
        longTap = in.readInt();
        intent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeSerializable(keyCode);
        dest.writeString(name != null ? name.toString() : null);
        dest.writeString(packageName);
        dest.writeString(url);
        dest.writeString(iconUrl);
        dest.writeSerializable(screenOrder);
        dest.writeInt(useKiosk);
        dest.writeInt(longTap);
        dest.writeString(intent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
