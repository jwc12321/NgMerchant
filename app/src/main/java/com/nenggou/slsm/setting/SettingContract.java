package com.nenggou.slsm.setting;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;

/**
 * Created by JWC on 2018/6/25.
 */

public interface SettingContract {
    interface ChangeAvatarPresenter extends BasePresenter {
        void uploadFile(String photoUrl);

        void changeAvata(String avatar);
    }

    interface ChangeAvatarView extends BaseView<ChangeAvatarPresenter> {
        void uploadFileSuccess(String photoUrl);

        void changeAvataSuccess();
    }

    interface ModifyPasswordPresenter extends BasePresenter {
        void modifyPassword(String newpwd, String old, String code, String tel);
    }

    interface ModifyPasswordView extends BaseView<ModifyPasswordPresenter> {
        void modifyPasswordSuccess();
    }

    interface ShiftHandsetPresenter extends BasePresenter {
        void sendVcode(String tel, String dostr);

        void checkOldCode(String tel, String code, String type);

        void checkNewCode(String newtel, String code);
    }

    interface ShiftHandsetView extends BaseView<ShiftHandsetPresenter> {
        void vcodeSuccess();

        void checkOldCodeSuccess();

        void checkNewCodeSuccess();
    }

}
