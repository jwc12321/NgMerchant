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
       void phoneLogin(String tel, String code);
       void registerPassword(String tel, String password, String address, String type, String storeid, String code);
       void checkCode(String tel, String code, String type);
       void changepwd(String tel, String password, String type);
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void loginSuccess(PersionInfoResponse persionInfoResponse);
        void codeSuccess();
        void checkCodeSuccess();
        void setPasswordSuccess();
    }
    interface RetrievePassWordPresenter extends BasePresenter {
        void sendCaptcha(String phone);
    }

    interface RetrievePassWordView extends BaseView<RetrievePassWordPresenter> {
        void onCaptchaSend();

        void notRegister();
    }
}
