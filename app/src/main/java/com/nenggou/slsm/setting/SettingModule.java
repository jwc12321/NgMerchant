package com.nenggou.slsm.setting;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/25.
 */

@Module
public class SettingModule {
    private SettingContract.ChangeAvatarView changeAvatarView;
    private SettingContract.ModifyPasswordView modifyPasswordView;
    private SettingContract.ShiftHandsetView shiftHandsetView;
    private SettingContract.SettingView settingView;

    public SettingModule(SettingContract.ChangeAvatarView changeAvatarView) {
        this.changeAvatarView = changeAvatarView;
    }

    public SettingModule(SettingContract.ModifyPasswordView modifyPasswordView) {
        this.modifyPasswordView = modifyPasswordView;
    }

    public SettingModule(SettingContract.ShiftHandsetView shiftHandsetView) {
        this.shiftHandsetView = shiftHandsetView;
    }

    public SettingModule(SettingContract.SettingView settingView) {
        this.settingView = settingView;
    }

    @Provides
    SettingContract.ChangeAvatarView provideChangeAvatarView(){
        return changeAvatarView;
    }

    @Provides
    SettingContract.ModifyPasswordView provideModifyPasswordView(){
        return modifyPasswordView;
    }

    @Provides
    SettingContract.ShiftHandsetView provideShiftHandsetView(){
        return shiftHandsetView;
    }

    @Provides
    SettingContract.SettingView provideSettingView(){
        return settingView;
    }
}
