package com.nenggou.slsm.mine;

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;

import dagger.Component;

/**
 * Created by JWC on 2018/6/19.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {PersonalCenterModule.class})
public interface PersonalCenterComponent {
}
