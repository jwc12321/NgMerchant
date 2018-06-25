package com.nenggou.slsm.login;


import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.PersionInfoResponse;

/**
 * Created by JWC on 2017/12/27.
 */

public interface LoginContract {

    interface LoginPresenter extends BasePresenter {
        void passwordLogin(String tel, String password);

        void sendCode(String tel, String dostr);

        void codeLogin(String tel, String code);
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void loginSuccess(PersionInfoResponse persionInfoResponse);

        void codeSuccess();
    }

    interface ForgetPasswordPresenter extends BasePresenter {
        void sendVcode(String tel, String dostr);

        void checkVcode(String tel, String code, String type);

    }

    interface ForgetPasswrodView extends BaseView<ForgetPasswordPresenter> {
        void vcodeSuccess();

        void checkVcodeSuccess();

    }

    interface SetPasswordPresenter extends BasePresenter {
        void setPassword(String tel, String code, String newpwd);
    }

    interface SetPasswordVeiw extends BaseView<SetPasswordPresenter> {
        void setPasswroeSuccess();
    }
}
