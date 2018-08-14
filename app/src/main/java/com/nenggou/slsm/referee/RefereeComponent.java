package com.nenggou.slsm.referee;

/**
 * Created by JWC on 2018/8/14.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.referee.ui.RdListActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {RefereeModule.class})
public interface RefereeComponent {
    void inject(RdListActivity rdListActivity);
}
