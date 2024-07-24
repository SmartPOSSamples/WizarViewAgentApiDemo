package com.wizarpos.wizarviewagent.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class DownloadInfo implements Parcelable {
    public static final int STATUS_STOP = 1;
    public static final int STATUS_DOWNLOADING = 2;
    public static final int STATUS_DOWNLOADED = 4;
    public static final int STATUS_FAILED = 8;
    public static final int STATUS_WAITDOWNLOADING = 16;
    public static final int STATUS_ALL = 0xFFFFFFFF;

    private int id;
    private int percent;
    private int size;
    private int speed;
    private int status;


    public DownloadInfo() {
    }

    protected DownloadInfo(Parcel in) {
        id = in.readInt();
        percent = in.readInt();
        size = in.readInt();
        speed = in.readInt();
        status = in.readInt();
    }

    public static final Creator<DownloadInfo> CREATOR = new Creator<DownloadInfo>() {
        @Override
        public DownloadInfo createFromParcel(Parcel in) {
            return new DownloadInfo(in);
        }

        @Override
        public DownloadInfo[] newArray(int size) {
            return new DownloadInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        private int id;
//        private int percent;
//        private int size;
//        private int speed;
//        private int status;
        dest.writeInt(this.id);
        dest.writeInt(this.percent);
        dest.writeInt(this.size);
        dest.writeInt(this.speed);
        dest.writeInt(this.status);
    }
}
