package com.nenggou.slsm.bankcard.presenter;

import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PutForwardRequest;
import com.nenggou.slsm.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/25.
 */

public class PutForwardPresenter implements BankCardContract.PutForwardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.PutForwardView putForwardView;

    @Inject
    public PutForwardPresenter(RestApiService restApiService, BankCardContract.PutForwardView putForwardView) {
        this.restApiService = restApiService;
        this.putForwardView = putForwardView;
    }

    @Inject
    public void setupListener() {
        putForwardView.setPresenter(this);
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
    public void putForward(String amount, String type, String bankid, String paypassword) {
        putForwardView.showLoading();
        PutForwardRequest putForwardRequest=new PutForwardRequest(amount,type,bankid,paypassword);
        Disposable disposable = restApiService.putForward(putForwardRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.purForwardSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    //是否设置了支付密码
    @Override
    public void isSetUpPayPw() {
        putForwardView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable=restApiService.isSetUpPayPw(tokenRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.renderIsSetUpPayPw(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardView.dismissLoading();
                        putForwardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

}
