package com.wizarpos.wizarviewagent.aidl;
import com.wizarpos.wizarviewagent.aidl.AppInfo;
interface IWizarviewService{
   int refreshAppList();
   int downloadAppInfoByAppID(int appID);
   AppInfo [] queryAppInfos(int installType, int status, int appType );
   AppInfo queryAppInfoByAppID(int appID);
   AppInfo[] queryAppInfoByPackageName(String packageName, int appType);
   String queryAppInfoDownloadProgress(int appID);
   String getRegisterAddress();
   int changeGroup(String token);
}