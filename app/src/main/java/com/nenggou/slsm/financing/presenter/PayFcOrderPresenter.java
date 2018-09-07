package com.nenggou.slsm.financing.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.PayFcOrderInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.FinancingidRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/9/7.
 */

public class PayFcOrderPresenter implements FinancingContract.PayFcOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.PayFcOrderView payFcOrderView;

    @Inject
    public PayFcOrderPresenter(RestApiService restApiService, FinancingContract.PayFcOrderView payFcOrderView) {
        this.restApiService = restApiService;
        this.payFcOrderView = payFcOrderView;
    }

    @Inject
    public void setupListener() {
        payFcOrderView.setPresenter(this);
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
    public void getPayFcOrderInfo(String financingid) {
        payFcOrderView.showLoading();
        FinancingidRequest financingidRequest=new FinancingidRequest(financingid);
        Disposable disposable = restApiService.getPayFcOrderInfo(financingidRequest)
                .flatMap(new RxRemoteDataParse<PayFcOrderInfo>())
                .compose(new RxSchedulerTransformer<PayFcOrderInfo>())
                .subscribe(new Consumer<PayFcOrderInfo>() {
                    @Override
                    public void accept(PayFcOrderInfo payFcOrderInfo) throws Exception {
                        payFcOrderView.dismissLoading();
                        payFcOrderView.renderPayFcOrderInfo(payFcOrderInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        payFcOrderView.dismissLoading();
                        payFcOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }
}
