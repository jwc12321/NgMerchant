package com.nenggou.slsm.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2017/12/27.
 */
@Module
public class LoginModule {
    private LoginContract.LoginView loginView;
    private LoginContract.ForgetPasswrodView forgetPasswrodView;
    private LoginContract.SetPasswordVeiw setPasswordVeiw;


    public LoginModule(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    public LoginModule(LoginContract.ForgetPasswrodView forgetPasswrodView) {
        this.forgetPasswrodView = forgetPasswrodView;
    }

    public LoginModule(LoginContract.SetPasswordVeiw setPasswordVeiw) {
        this.setPasswordVeiw = setPasswordVeiw;
    }

    @Provides
    LoginContract.LoginView provideLoginView() {
        return loginView;
    }

    @Provides
    LoginContract.ForgetPasswrodView provideForgetPasswrodView(){
        return forgetPasswrodView;
    }

    @Provides
    LoginContract.SetPasswordVeiw provideSetPasswordVeiw(){
        return setPasswordVeiw;
    }
}
