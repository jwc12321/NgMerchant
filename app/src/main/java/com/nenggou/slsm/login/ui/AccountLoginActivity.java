package com.nenggou.slsm.login.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.login.DaggerLoginComponent;
import com.nenggou.slsm.login.LoginContract;
import com.nenggou.slsm.login.LoginModule;
import com.nenggou.slsm.login.presenter.LoginPresenter;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/6/5.
 */

public class AccountLoginActivity extends BaseActivity implements LoginContract.LoginView{

    @Inject
    LoginPresenter loginPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter.accountLogin("18758302924", "123456", "");
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }
    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }

    @Override
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {

    }

    @Override
    public void codeSuccess() {

    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void setPasswordSuccess() {

    }
}
