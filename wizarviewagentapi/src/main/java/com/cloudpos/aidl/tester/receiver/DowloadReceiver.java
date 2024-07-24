package com.cloudpos.aidl.tester.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wizarpos.wizarviewagent.aidl.AppInfo;

public class DowloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        if (status != null && status.equals(AppInfo.STATUS_DOWNLOADED)) {
            Log.d("DowloadReceiver", "download status success! : " + status);
        }
    }
}
