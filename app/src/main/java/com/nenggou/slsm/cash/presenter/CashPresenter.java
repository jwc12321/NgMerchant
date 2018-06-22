package com.nenggou.slsm.cash.presenter;

import com.nenggou.slsm.cash.CashContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.entity.CashInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/22.
 */

public class CashPresenter implements CashContract.CashPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CashContract.CashView cashView;

    @Inject
    public CashPresenter(RestApiService restApiService, CashContract.CashView cashView) {
        this.restApiService = restApiService;
        this.cashView = cashView;
    }

    @Inject
    public void setupListener() {
        cashView.setPresenter(this);
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
    public void getCashInfo() {
        cashView.showLoading();
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getCashInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<CashInfo>())
                .compose(new RxSchedulerTransformer<CashInfo>())
                .subscribe(new Consumer<CashInfo>() {
                    @Override
                    public void accept(CashInfo cashInfo) throws Exception {
                        cashView.dismissLoading();
                        cashView.renderCashInfo(cashInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cashView.dismissLoading();
                        cashView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
