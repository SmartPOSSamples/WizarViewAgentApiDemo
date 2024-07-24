package com.wizarpos.wizarviewagent.aidl;
import com.wizarpos.wizarviewagent.aidl.AppInfo;
import android.graphics.Bitmap;
import com.wizarpos.wizarviewagent.aidl.DownloadInfo;


interface ICloudPosTmsApiService{
   int refreshAppList();
   int downloadAppInfoByAppID(int appID);
   AppInfo [] queryAppInfos(int installType, int status, int appType );
   AppInfo queryAppInfoByAppID(int appID);
   AppInfo[] queryAppInfoByPackageName(String packageName, int appType);
   DownloadInfo queryAppInfoDownloadProgress(int appID);
   Bitmap getAppInfoIcon(int appID);
   Bitmap[] getAppScreenShot(int appID);
   String getAppDescription(int appID);

}