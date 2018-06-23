package com.nenggou.slsm.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.StaticHandler;
import com.nenggou.slsm.common.unit.TokenManager;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.mainframe.ui.MainFrameActivity;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/21.
 */

public class SplashActivity extends BaseActivity {
    private static final int GO_MAIN = 1;
    private static final int GO_LOGIN = 2;
    private Handler mHandler = new MyHandler(this);
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
        if (TextUtils.isEmpty(TokenManager.getToken())) {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 1000);
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
            }
        }
    }
}
