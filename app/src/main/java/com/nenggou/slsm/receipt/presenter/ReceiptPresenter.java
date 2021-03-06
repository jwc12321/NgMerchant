package com.nenggou.slsm.receipt.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.entity.ChangeAppInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.DetectionVersionRequest;
import com.nenggou.slsm.data.request.TokenRequest;
import com.nenggou.slsm.receipt.ReceiptContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/21.
 */

public class ReceiptPresenter implements ReceiptContract.ReceiptPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ReceiptContract.ReceiptView receiptView;

    @Inject
    public ReceiptPresenter(RestApiService restApiService, ReceiptContract.ReceiptView receiptView) {
        this.restApiService = restApiService;
        this.receiptView = receiptView;
    }

    @Inject
    public void setupListener() {
        receiptView.setPresenter(this);
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
    public void getAppstoreInfos(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            receiptView.showLoading();
        }
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable=restApiService.getAppstoreInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<AppstoreInfo>>())
                .compose(new RxSchedulerTransformer<List<AppstoreInfo>>())
                .subscribe(new Consumer<List<AppstoreInfo>>() {
                    @Override
                    public void accept(List<AppstoreInfo> appstoreInfos) throws Exception {
                        receiptView.dismissLoading();
                        receiptView.renderAppstoreInfos(appstoreInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        receiptView.dismissLoading();
                        receiptView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void detectionVersion(String edition, String type) {
        DetectionVersionRequest detectionVersionRequest=new DetectionVersionRequest(edition,type);
        Disposable disposable=restApiService.changeApp(detectionVersionRequest)
                .flatMap(new RxRemoteDataParse<ChangeAppInfo>())
                .compose(new RxSchedulerTransformer<ChangeAppInfo>())
                .subscribe(new Consumer<ChangeAppInfo>() {
                    @Override
                    public void accept(ChangeAppInfo changeAppInfo) throws Exception {
                        receiptView.detectionSuccess(changeAppInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        receiptView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
