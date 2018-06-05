package com.nenggou.slsm;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import com.nenggou.slsm.common.unit.SPManager;

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
