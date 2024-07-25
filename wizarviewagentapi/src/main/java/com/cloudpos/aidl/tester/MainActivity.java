package com.cloudpos.aidl.tester;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cloudpos.utils.Logger;
import com.cloudpos.utils.TextViewUtil;
import com.wizarpos.aidl.tester.R;
import com.wizarpos.wizarviewagent.aidl.AppInfo;
import com.wizarpos.wizarviewagent.aidl.ICloudPosTmsApiService;
import com.wizarpos.wizarviewagent.aidl.IWizarviewService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AbstractActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_run2 = (Button) this.findViewById(R.id.btn_run2);
        Button btn_run3 = (Button) this.findViewById(R.id.btn_run3);
        Button btn_run4 = (Button) this.findViewById(R.id.btn_run4);
        Button btn_run5 = (Button) this.findViewById(R.id.btn_run5);
        log_text = (TextView) this.findViewById(R.id.text_result);
        log_text.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.settings).setOnClickListener(this);
        btn_run2.setOnClickListener(this);
        btn_run3.setOnClickListener(this);
        btn_run4.setOnClickListener(this);
        btn_run5.setOnClickListener(this);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == R.id.log_default) {
                    log_text.append("\t" + msg.obj + "\n");
                } else if (msg.what == R.id.log_success) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoBlueTextView(log_text, str);
                } else if (msg.what == R.id.log_failed) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoRedTextView(log_text, str);
                } else if (msg.what == R.id.log_clear) {
                    log_text.setText("");
                }
            }
        };
        bindAgentApiService();
//        bindTmsApiService();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(agentConnection);
        this.unbindService(tmsConnection);
    }


    public void bindAgentApiService() {
        try {
            startAgentService(MainActivity.this,
                    "com.wizarpos.wizarviewagent",
                    "com.wizarpos.wizarviewagent.service.WizarviewService", agentConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindTmsApiService() {
        try {
            startTmsService(MainActivity.this,
                    "com.wizarpos.wizarviewagent.aidl",
                    "com.wizarpos.wizarviewagent.aidl.CloudPosTmsApiService", tmsConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean startAgentService(Context mContext, String packageName, String className, ServiceConnection connection) {
        boolean isSuccess = startAgentService(mContext, new ComponentName(packageName, className), connection);
        writerInLog("\t" + "bind agent service result: " + isSuccess + "\n", R.id.log_default);
        return isSuccess;
    }

    protected boolean startTmsService(Context mContext, String packageName, String className, ServiceConnection connection) {
        boolean isSuccess = startTmsService(mContext, new ComponentName(packageName, className), connection);
        writerInLog("\t" + "bind tms service result: " + isSuccess + "\n", R.id.log_default);
        return isSuccess;
    }

    protected boolean startAgentService(Context context, ComponentName comp, ServiceConnection connection) {
        Intent intent = new Intent();
        intent.setPackage(comp.getPackageName());
        intent.setComponent(comp);
        boolean isSuccess = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Logger.debug("bind agent service (%s, %s)", isSuccess, comp.getPackageName(), comp.getClassName());
        return isSuccess;
    }

    protected boolean startTmsService(Context context, ComponentName comp, ServiceConnection connection) {
        Intent intent = new Intent();
        intent.setPackage(comp.getPackageName());
        intent.setComponent(comp);
        boolean isSuccess = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Logger.debug("bind tms service (%s, %s)", isSuccess, comp.getPackageName(), comp.getClassName());
        return isSuccess;
    }

    @Override
    public void onClick(View arg0) {
        int index = arg0.getId();
        if (index == R.id.btn_run2) {
            try {
                int status = wizarviewService.refreshAppList();
                writerInSuccessLog("\t refresh AppList status: " + status + "\n");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (index == R.id.btn_run3) {
            try {
                int status = wizarviewService.refreshAppList();
                writerInSuccessLog("\t refresh AppList status: " + status + "\n");
                AppInfo[] appInfos = wizarviewService.queryAppInfos(AppInfo.INSTALL_TYPE_ALL, AppInfo.STATUS_ALL, AppInfo.CONTENT_TYPE_ALL);
                writerInSuccessLog("\t" + "query appInfos: " + JSONObject.toJSONString(appInfos) + "\n");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (index == R.id.btn_run4) {
            try {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please choose an app to download:");
                getAppInfosDialog(index);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (index == R.id.btn_run5) {
            try {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please choose an app to view its download progress:");
                getAppInfosDialog(index);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (index == R.id.settings) {
            log_text.setText("");
        }
    }

    public void getAppInfosDialog(final int type) throws RemoteException {
        int status = wizarviewService.refreshAppList();
        writerInSuccessLog("\t refresh AppList status: " + status + "\n");
        AppInfo[] appInfos = wizarviewService.queryAppInfos(AppInfo.INSTALL_TYPE_ALL, AppInfo.STATUS_ALL, AppInfo.CONTENT_TYPE_ALL);
        if (appInfos != null) {
            List<String> names = new ArrayList<>(appInfos.length);
            String[] options = new String[appInfos.length];
            for (AppInfo mode : appInfos) {
                names.add(mode.getAppID() + " : " + mode.getAppName());
            }
            names.toArray(options);
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (wizarviewService == null) {
                            return;
                        }
                        if (type == R.id.btn_run4) {
                            int code = wizarviewService.downloadAppInfoByAppID(appInfos[which].getAppID());
                            writerInSuccessLog("\t" + "download result: " + code + "\n");
                        } else if (type == R.id.btn_run5) {
                            String result = wizarviewService.queryAppInfoDownloadProgress(appInfos[which].getAppID());
                            writerInSuccessLog("\t" + "query download progress status: " + result + "\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            writerInSuccessLog("\t get appInfos : " + JSONObject.toJSONString(appInfos) + "\n");
        }

    }

    private ServiceConnection agentConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                writerInLog("onServiceConnected:" + service.getInterfaceDescriptor() + "\n", R.id.log_success);
                wizarviewService = IWizarviewService.Stub.asInterface(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection tmsConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                writerInLog("\t" + "onServiceConnected: " + service.getInterfaceDescriptor() + " \n", R.id.log_success);
                cloudPosTmsApiService = ICloudPosTmsApiService.Stub.asInterface(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    AlertDialog.Builder builder;
    IWizarviewService wizarviewService;
    ICloudPosTmsApiService cloudPosTmsApiService;
}
