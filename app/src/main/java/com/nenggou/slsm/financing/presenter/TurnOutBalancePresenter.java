package com.nenggou.slsm.financing.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PayPasswordRequest;
import com.nenggou.slsm.data.request.TurnOutBalanceRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/9/10.
 */

public class TurnOutBalancePresenter implements FinancingContract.TurnOutBalancePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.TurnOutBalanceView turnOutBalanceView;

    @Inject
    public TurnOutBalancePresenter(RestApiService restApiService, FinancingContract.TurnOutBalanceView turnOutBalanceView) {
        this.restApiService = restApiService;
        this.turnOutBalanceView = turnOutBalanceView;
    }

    @Inject
    public void setupListener() {
        turnOutBalanceView.setPresenter(this);
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
        turnOutBalanceView.showLoading();
        PayPasswordRequest payPasswordRequest = new PayPasswordRequest(payPassword);
        Disposable disposable = restApiService.verifyPayPassword(payPasswordRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        turnOutBalanceView.verifySuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        turnOutBalanceView.dismissLoading();
                        turnOutBalanceView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void turnOutBalance(String sum, String type, String paypassword) {
        TurnOutBalanceRequest turnOutBalanceRequest=new TurnOutBalanceRequest(sum,type,paypassword);
        Disposable disposable = restApiService.turnToBalance(turnOutBalanceRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        turnOutBalanceView.dismissLoading();
                        turnOutBalanceView.turnOutSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        turnOutBalanceView.dismissLoading();
                        turnOutBalanceView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
