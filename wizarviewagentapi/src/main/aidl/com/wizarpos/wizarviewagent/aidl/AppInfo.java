package com.wizarpos.wizarviewagent.aidl;


import android.os.Parcel;
import android.os.Parcelable;

public class AppInfo implements Parcelable {

    public static final int INSTALL_TYPE_OPTIONAL = 0x00000001;
    public static final int INSTALL_TYPE_REQUIRED = 0x00000002;
    public static final int INSTALL_TYPE_REQUIRED_SILENT = 0x00000004;
    public static final int INSTALL_TYPE_ALL = 0xFFFFFFFF;

    public static final int STATUS_STOP = 0x0000001;
    public static final int STATUS_DOWNLOADING = 0x0000002;
    public static final int STATUS_DOWNLOADED = 0x00000004;
    public static final int STATUS_INSTALLED = 0x0000008;
    public static final int STATUS_ALL = 0xFFFFFFFF;

    public static final int CONTENT_TYPE_APK = 0x0000001;
    public static final int CONTENT_TYPE_FIRMWARE = 0x0000002;
    public static final int CONTENT_TYPE_PARAMETER = 0x0000004;
    public static final int CONTENT_TYPE_UNDEFINED = 0x0000008;
    public static final int CONTENT_TYPE_ALL = 0xFFFFFFFF;


    private int appID;
    private int appVersionID;
    /**
     * 在本机上的当前状态<br/>
     * 未下载--{@link AppInfo#STATUS_STOP}<br/>
     * 下载中--{@link AppInfo#STATUS_DOWNLOADING}<br/>
     * 已下载未安装--{@link AppInfo#STATUS_DOWNLOADED}<br/>
     * 已安装--{@link AppInfo#STATUS_INSTALLED}<br/>
     */
    private int status;
    private int versionCodeByApk;
    private String versionNameByApk;
    private String packageName;
    private String appName;
    private String fileName;
    private int contentType;
    private String downloadPath;
    private long fileLength;
    /**
     * 安装类型<br/>
     * 可选--{@link AppInfo#INSTALL_TYPE_OPTIONAL}<br/>
     * 必选--{@link AppInfo#INSTALL_TYPE_REQUIRED}<br/>
     * 静默安装的必选-- {@link AppInfo#INSTALL_TYPE_REQUIRED_SILENT}
     */
    private int installType;

    public long getFileLength() {
        return fileLength;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppName() {
        return appName;
    }

    public int getInstallType() {
        return installType;
    }


    @Override
    public int describeContents() {
//		import com.wizarpos.wizarviewagent.service.aidl.AppInfo;
        return 0;
    }

    public int getAppID() {
        return appID;
    }

    public int getAppVersionID() {
        return appVersionID;
    }


    public int getStatus() {
        return status;
    }

    public int getVersionCodeByApk() {
        return versionCodeByApk;
    }

    public String getVersionNameByApk() {
        return versionNameByApk;
    }

    public String getFileName() {
        return fileName;
    }

    public int getContentType() {
        return contentType;
    }

    public AppInfo(int appID, int appVersionID, String appName, int status, String packageName, int versionCodeByApk,
                   String versionNameByApk, String downloadPath, String fileName, int contentType, int installType, long fileLength) {
        this.appID = appID;
        this.appVersionID = appVersionID;
        this.appName = appName;
        this.status = status;
        this.packageName = packageName;
        this.versionCodeByApk = versionCodeByApk;
        this.versionNameByApk = versionNameByApk;
        this.fileName = fileName;
        this.contentType = contentType;
        this.installType = installType;
        this.downloadPath = downloadPath;
        this.fileLength = fileLength;
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {

        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

    public AppInfo(Parcel source) {
        this.appID = source.readInt();
        this.appVersionID = source.readInt();
        this.appName = source.readString();
        this.status = source.readInt();
        this.packageName = source.readString();

        this.downloadPath = source.readString();
        this.versionCodeByApk = source.readInt();
        this.versionNameByApk = source.readString();
        this.fileName = source.readString();
        ;
        this.contentType = source.readInt();
        this.installType = source.readInt();
        this.fileLength = source.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.appID);
        dest.writeInt(this.appVersionID);
        dest.writeString(this.appName);
        dest.writeInt(this.status);
        dest.writeString(this.packageName);

        dest.writeString(downloadPath);
        dest.writeInt(this.versionCodeByApk);
        dest.writeString(this.versionNameByApk);
        dest.writeString(this.fileName);
        dest.writeInt(this.contentType);
        dest.writeInt(this.installType);
        dest.writeLong(this.fileLength);
        ;
    }

    @Override
    public String toString() {
        return String.format("[appID=%d,appVersionID=%d,appName=%s,status=%d,packageName=%s,versionCodeByApk=%d,versionNameByApk=%s,fileName=%s,contentType=%s,[installType=%s],fileLength=%d",
                appID, appVersionID, appName, status, packageName, versionCodeByApk, versionNameByApk,
                fileName, contentType, installType, fileLength);
    }
}
