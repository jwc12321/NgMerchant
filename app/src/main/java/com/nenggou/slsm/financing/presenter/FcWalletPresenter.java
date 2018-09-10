package com.nenggou.slsm.financing.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.FcWalletInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.TokenRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/9/10.
 */

public class FcWalletPresenter implements FinancingContract.FcWalletPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.FcWalletView fcWalletView;

    @Inject
    public FcWalletPresenter(RestApiService restApiService, FinancingContract.FcWalletView fcWalletView) {
        this.restApiService = restApiService;
        this.fcWalletView = fcWalletView;
    }

    @Inject
    public void setupListener() {
        fcWalletView.setPresenter(this);
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
    public void getFcWalletInfo() {
        fcWalletView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getFcWalletInfo(tokenRequest)
                .flatMap(new RxRemoteDataParse<FcWalletInfo>())
                .compose(new RxSchedulerTransformer<FcWalletInfo>())
                .subscribe(new Consumer<FcWalletInfo>() {
                    @Override
                    public void accept(FcWalletInfo fcWalletInfo) throws Exception {
                        fcWalletView.dismissLoading();
                        fcWalletView.renderFcWalletInfo(fcWalletInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fcWalletView.dismissLoading();
                        fcWalletView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
