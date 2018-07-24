package com.nenggou.slsm.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.google.gson.Gson;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Sherily on 2017/5/6.
 */

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";
    private static final Gson gson = new Gson();
    @Inject
    PushUtil pushUtil;

    public PushReceiver() {
        DaggerPushComponent.builder().build().inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
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
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//获取通知标题
            Log.i(TAG, "[MyReceiver]用户收到了通知,通知的标题为" + title);
            String text = bundle.getString(JPushInterface.EXTRA_ALERT);//获取通知内容
            Log.i(TAG, "[MyReceiver]用户收到了通知,通知的内容为" + text);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);//获取附加字段,是一个json数
            PushInfo pushInfo = gson.fromJson(extras, PushInfo.class);
        } else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        }
    }
}
