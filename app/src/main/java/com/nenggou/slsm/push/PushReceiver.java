package com.nenggou.slsm.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.Gson;
import com.nenggou.slsm.cash.ui.ReceivablesActivity;
import com.nenggou.slsm.common.unit.CommonAppPreferences;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;


public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";
    private static final Gson gson = new Gson();
    private ReceivablesActivity receivablesActivity = new ReceivablesActivity();
    private CommonAppPreferences commonAppPreferences;
    @Inject
    PushUtil pushUtil;

    public PushReceiver() {
        DaggerPushComponent.builder().build().inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        commonAppPreferences = new CommonAppPreferences(context);
        Bundle bundle = intent.getExtras();//获取intent里携带的数据集合
        Log.i("极光推送:", "接收成功");
        /**
         * 一个技巧:把已知的动作写在前面,未知的动作写在后面,这样在用equals时可以避免空指针异常
         */
        //当用户点击了通知
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

        }
        //当用户收到了通知(用户只有先收到通知,才能点击通知)
        else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//获取附加字段,是一个json数
            PushInfo pushInfo = gson.fromJson(extras, PushInfo.class);
            if (TextUtils.equals("1", pushInfo.getType())) {
                commonAppPreferences.setPushInfoStr(gson.toJson(pushInfo));
                Intent activityIntent = new Intent(context, receivablesActivity.getClass());
                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(activityIntent);
            }
        } else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        }
    }
}
