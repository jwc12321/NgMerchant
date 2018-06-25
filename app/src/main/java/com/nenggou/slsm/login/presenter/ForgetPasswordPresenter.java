package com.nenggou.slsm.login.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.CheckCodeRequest;
import com.nenggou.slsm.data.request.SendCodeRequest;
import com.nenggou.slsm.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/25.
 */

public class ForgetPasswordPresenter implements LoginContract.ForgetPasswordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LoginContract.ForgetPasswrodView forgetPasswrodView;

    @Inject
    public ForgetPasswordPresenter(RestApiService restApiService, LoginContract.ForgetPasswrodView forgetPasswrodView) {
        this.restApiService = restApiService;
        this.forgetPasswrodView = forgetPasswrodView;
    }

    @Inject
    public void setupListener() {
        forgetPasswrodView.setPresenter(this);
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

    @Override
    public void sendVcode(String tel, String dostr) {
        forgetPasswrodView.showLoading();
        SendCodeRequest sendCodeRequest = new SendCodeRequest(tel, dostr);
        Disposable disposable = restApiService.sendCode(sendCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        forgetPasswrodView.dismissLoading();
                        forgetPasswrodView.vcodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        forgetPasswrodView.dismissLoading();
                        forgetPasswrodView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void checkVcode(String tel, String code, String type) {
        forgetPasswrodView.showLoading();
        CheckCodeRequest checkCodeRequest = new CheckCodeRequest(tel, code, type);
        Disposable disposable = restApiService.checkCode(checkCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        forgetPasswrodView.dismissLoading();
                        forgetPasswrodView.checkVcodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        forgetPasswrodView.dismissLoading();
                        forgetPasswrodView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
