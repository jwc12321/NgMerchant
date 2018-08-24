package com.nenggou.slsm.paypassword.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PayPasswordRequest;
import com.nenggou.slsm.paypassword.PayPasswordContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/22.
 */

public class PayPwPowerPresenter implements PayPasswordContract.PayPwPowerPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private PayPasswordContract.PayPwPowerView payPwPowerView;

    @Inject
    public PayPwPowerPresenter(RestApiService restApiService, PayPasswordContract.PayPwPowerView payPwPowerView) {
        this.restApiService = restApiService;
        this.payPwPowerView = payPwPowerView;
    }

    @Inject
    public void setupListener() {
        payPwPowerView.setPresenter(this);
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
    public void verifyPayPassword(String payPassword) {
        payPwPowerView.showLoading();
        PayPasswordRequest payPasswordRequest=new PayPasswordRequest(payPassword);
        Disposable disposable = restApiService.verifyPayPassword(payPasswordRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        payPwPowerView.dismissLoading();
                        payPwPowerView.verifySuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        payPwPowerView.dismissLoading();
                        payPwPowerView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
