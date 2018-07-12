package com.nenggou.slsm;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.meituan.android.walle.WalleChannelReader;
import com.nenggou.slsm.common.unit.SPManager;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MainApplication  extends MultiDexApplication {
    private static ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
        //Android7.0的照片问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        SPManager.getInstance().register(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //友盟统计
        String channelId = WalleChannelReader.getChannel(this.getApplicationContext());
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),"5b46ef5ab27b0a096a000035",channelId));
    }
    private void initDaggerComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static void setmApplicationComponent(ApplicationComponent mApplicationComponent) {
        MainApplication.mApplicationComponent = mApplicationComponent;
    }
}
