package com.nenggou.slsm.paypassword.presenter;


import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.SetPayPwRequest;
import com.nenggou.slsm.paypassword.PayPasswordContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/21.
 */

public class PayPasswordPresenter implements PayPasswordContract.PayPasswordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PayPasswordContract.PayPasswordView payPasswordView;

    @Inject
    public PayPasswordPresenter(RestApiService restApiService, PayPasswordContract.PayPasswordView payPasswordView) {
        this.restApiService = restApiService;
        this.payPasswordView = payPasswordView;
    }

    @Inject
    public void setupListener() {
        payPasswordView.setPresenter(this);
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
    public void setPayPassword(String payPassword, String type, String details) {
        payPasswordView.showLoading();
        SetPayPwRequest setPayPwRequest=new SetPayPwRequest(payPassword,type,details);
        Disposable disposable = restApiService.setPayPassword(setPayPwRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        payPasswordView.dismissLoading();
                        payPasswordView.renderSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        payPasswordView.dismissLoading();
                        payPasswordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
