package com.nenggou.slsm.push;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.nenggou.slsm.BuildConfig;
import com.nenggou.slsm.cash.ui.ReceivablesActivity;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.CommonAppPreferences;

import java.util.List;

import javax.inject.Singleton;


/**
 * Created by Sherily on 2017/5/6.
 */
@Singleton
public class PushUtil {
    private static final Gson gson = new Gson();
    private static final String HomePaasProcessName = BuildConfig.APPLICATION_ID;

    public void parseMessage(Context context, String extras) {
        PushInfo pushInfo = gson.fromJson(extras, PushInfo.class);
        if (isAppActive(context)) {
            startInternalView(context, pushInfo);
        }
//        else {
//            Intent lauchnIntent = context.getPackageManager().getLaunchIntentForPackage(HomePaasProcessName);
//            lauchnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("PushInfo", pushInfo);
//            lauchnIntent.putExtras(bundle);
//            context.startActivity(lauchnIntent);
//        }
    }

    public static boolean isAppActive(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i);
            if (runningAppProcessInfo.processName.equals(HomePaasProcessName))
                return true;
        }
        return false;
    }

    private static void startInternalView(Context context, PushInfo pushInfo) {
        if (pushInfo == null) return;
        int type = Integer.valueOf(pushInfo.getType());
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (type) {
            case AppViewIndex.Receivables://抢单
                intent.setClass(context, ReceivablesActivity.class);
                intent.putExtra(StaticData.PUSH_INFO, pushInfo);
                break;
            default:
        }
        context.startActivity(intent);
    }
}
