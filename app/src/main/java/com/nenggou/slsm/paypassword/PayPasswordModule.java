package com.nenggou.slsm.paypassword;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/8/20.
 */
@Module
public class PayPasswordModule {
    private PayPasswordContract.PayPasswordView payPasswordView;
    private PayPasswordContract.AuthenticationView authenticationView;
    private PayPasswordContract.SmsAuthenticationView smsAuthenticationView;
    private PayPasswordContract.PayPwPowerView payPwPowerView;

    public PayPasswordModule(PayPasswordContract.PayPasswordView payPasswordView) {
        this.payPasswordView = payPasswordView;
    }

    public PayPasswordModule(PayPasswordContract.AuthenticationView authenticationView) {
        this.authenticationView = authenticationView;
    }

    public PayPasswordModule(PayPasswordContract.SmsAuthenticationView smsAuthenticationView) {
        this.smsAuthenticationView = smsAuthenticationView;
    }

    public PayPasswordModule(PayPasswordContract.PayPwPowerView payPwPowerView) {
        this.payPwPowerView = payPwPowerView;
    }

    @Provides
    PayPasswordContract.PayPasswordView providePayPasswordView(){
        return payPasswordView;
    }

    @Provides
    PayPasswordContract.AuthenticationView provideAuthenticationView(){
        return authenticationView;
    }

    @Provides
    PayPasswordContract.SmsAuthenticationView provideSmsAuthenticationView(){
        return smsAuthenticationView;
    }

    @Provides
    PayPasswordContract.PayPwPowerView providePayPwPowerView(){
        return payPwPowerView;
    }
}
