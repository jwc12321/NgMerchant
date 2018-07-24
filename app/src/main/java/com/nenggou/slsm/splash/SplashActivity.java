package com.nenggou.slsm.splash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.StaticHandler;
import com.nenggou.slsm.common.unit.TokenManager;
import com.nenggou.slsm.jurisdiction.JurisdictionActivity;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.mainframe.ui.MainFrameActivity;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/21.
 */

public class SplashActivity extends BaseActivity {
    private static final int GO_MAIN = 1;
    private static final int GO_LOGIN = 2;
    private static final int GO_GUIDE = 3;
    private Handler mHandler = new MyHandler(this);
    private static final String SHAREDPREFERENCES_NAME = "ngm_first_pref";
    boolean isFirstIn = false;
    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        //直接写入
        if(isFirstIn) {
            preferences.edit().putBoolean("isFirstIn", false).apply();
        }
        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {
            if (TextUtils.isEmpty(TokenManager.getToken())) {
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
            } else {
                mHandler.sendEmptyMessageDelayed(GO_MAIN, 1000);
            }
        }else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 1000);
        }
    }

    //跳转到主页
    private void goMain() {
        MainFrameActivity.start(this);
        finish();
    }

    //跳转到主页
    private void goLogin() {
        LoginActivity.start(this);
        finish();
    }

    //跳转引导页
    private void goGuide() {
        GuideActivity.start(this);
        finish();
    }

    public static class MyHandler extends StaticHandler<SplashActivity> {

        public MyHandler(SplashActivity target) {
            super(target);
        }

        @Override
        public void handle(SplashActivity target, Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    target.goMain();
                    break;
                case GO_LOGIN:
                    target.goLogin();
                    break;
                case GO_GUIDE:
                    target.goGuide();
                    break;
            }
        }
    }
}
