package com.nenggou.slsm.login;


import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.login.ui.AccountLoginActivity;

import dagger.Component;

/**
 * Created by JWC on 2017/12/27.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(AccountLoginActivity accountLoginActivity);
}
