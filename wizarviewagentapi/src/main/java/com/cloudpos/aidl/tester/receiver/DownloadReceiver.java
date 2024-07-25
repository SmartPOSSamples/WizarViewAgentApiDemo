package com.cloudpos.aidl.tester.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra("status",-1);
        Log.d("DownloadReceiver", "download status result is! : " + status);
    }
}
