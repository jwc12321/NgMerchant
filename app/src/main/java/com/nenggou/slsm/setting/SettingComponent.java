package com.nenggou.slsm.setting;

/**
 * Created by JWC on 2018/6/25.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.setting.ui.BusinessImActivity;
import com.nenggou.slsm.setting.ui.ModifyPasswordActivity;
import com.nenggou.slsm.setting.ui.ShiftHandsetActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {SettingModule.class})
public interface SettingComponent {
    void inject(BusinessImActivity businessImActivity);
    void inject(ModifyPasswordActivity modifyPasswordActivity);
    void inject(ShiftHandsetActivity shiftHandsetActivity);
}
