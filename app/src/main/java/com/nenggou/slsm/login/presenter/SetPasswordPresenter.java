package com.nenggou.slsm.login.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PageRequest;
import com.nenggou.slsm.data.request.SetPasswordRequest;
import com.nenggou.slsm.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/25.
 */

public class SetPasswordPresenter implements LoginContract.SetPasswordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LoginContract.SetPasswordVeiw setPasswordVeiw;

    @Inject
    public SetPasswordPresenter(RestApiService restApiService, LoginContract.SetPasswordVeiw setPasswordVeiw) {
        this.restApiService = restApiService;
        this.setPasswordVeiw = setPasswordVeiw;
    }

    @Inject
    public void setupListener() {
        setPasswordVeiw.setPresenter(this);
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
    public void setPassword(String tel, String code, String newpwd) {
        setPasswordVeiw.showLoading();
        SetPasswordRequest setPasswordRequest=new SetPasswordRequest(tel,code,newpwd);
        Disposable disposable=restApiService.setPassword(setPasswordRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        setPasswordVeiw.dismissLoading();
                        setPasswordVeiw.setPasswroeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setPasswordVeiw.dismissLoading();
                        setPasswordVeiw.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
