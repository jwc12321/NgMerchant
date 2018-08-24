package com.nenggou.slsm.paypassword;

/**
 * Created by JWC on 2018/8/20.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.paypassword.ui.AuthenticationActivity;
import com.nenggou.slsm.paypassword.ui.InputPayPwActivity;
import com.nenggou.slsm.paypassword.ui.SecondPayPwActivity;
import com.nenggou.slsm.paypassword.ui.SmsAuthenticationActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {PayPasswordModule.class})
public interface PayPasswordComponent {
    void inject(SecondPayPwActivity secondPayPwActivity);
    void inject(AuthenticationActivity authenticationActivity);
    void inject(SmsAuthenticationActivity smsAuthenticationActivity);
    void inject(InputPayPwActivity inputPayPwActivity);
}
