package com.nenggou.slsm.login.presenter;

import android.text.TextUtils;


import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.LoginRequest;
import com.nenggou.slsm.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/4/17.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();

    @Inject
    public LoginPresenter(LoginContract.LoginView loginView, RestApiService restApiService) {
        this.loginView = loginView;
        this.restApiService = restApiService;
    }


    @Inject
    public void setupListener() {
        loginView.setPresenter(this);
    }

    /**
     * 密码登录和短信登录
     */
    @Override
    public void accountLogin(String username, String pwd, String clientid) {
        loginView.showLoading();
        LoginRequest loginRequest = new LoginRequest(username, pwd, clientid);
        Disposable disposable = restApiService.accountLogin(loginRequest)
                .flatMap(new RxRemoteDataParse<PersionInfoResponse>())
                .compose(new RxSchedulerTransformer<PersionInfoResponse>())
                .subscribe(new Consumer<PersionInfoResponse>() {
                    @Override
                    public void accept(PersionInfoResponse persionInfoResponse) throws Exception {
                        loginView.dismissLoading();
                        loginView.accountLoginSuccess(persionInfoResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    /**
     * 发送验证码
     *
     * @param tel
     * @param dostr：register(注册)/login(登录)/changetel(修改手机)/changepwd（修改密码）
     */
    @Override
    public void sendCode(String tel, String dostr) {
    }

    /**
     * 手机验证码登录
     *
     * @param tel
     * @param code
     */
    @Override
    public void phoneLogin(String tel, String code) {
    }

    /**
     * 注册和修改密码
     *
     * @param tel
     * @param password
     * @param address
     * @param type     register(注册)/changepwd(修改密码)
     */
    @Override
    public void registerPassword(String tel, String password, String address, String type, String storeid, String code) {
    }

    /**
     * 验证验证码
     *
     * @param tel
     * @param code
     * @param type
     */
    @Override
    public void checkCode(String tel, String code, String type) {
    }

    @Override
    public void changepwd(String tel, String password, String type) {
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
