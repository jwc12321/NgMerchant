package com.nenggou.slsm.paypassword;


import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;

/**
 * Created by JWC on 2018/8/20.
 */

public interface PayPasswordContract {
    interface PayPasswordPresenter extends BasePresenter {
        void setPayPassword(String payPassword, String type, String details);
    }

    interface PayPasswordView extends BaseView<PayPasswordPresenter> {
        void renderSuccess();
    }

    interface AuthenticationPresenter extends BasePresenter{
        void verifyPayPassword(String payPassword);
    }

    interface AuthenticationView extends BaseView<AuthenticationPresenter>{
        void verifySuccess();
    }

    interface SmsAuthenticationPresenter extends BasePresenter{
        void sendCode(String tel, String dostr);
        void checkCode(String tel, String code, String type);
    }

    interface SmsAuthenticationView extends BaseView<SmsAuthenticationPresenter>{
        void sendCodeSuccess();
        void checkCodeSuccess();
    }

    interface PayPwPowerPresenter extends BasePresenter{
        void verifyPayPassword(String payPassword);
    }

    interface PayPwPowerView extends BaseView<PayPwPowerPresenter>{
        void verifySuccess();
    }
}
