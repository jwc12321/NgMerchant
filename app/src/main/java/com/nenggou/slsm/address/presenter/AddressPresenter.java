package com.nenggou.slsm.address.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.address.AddressContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/23.
 */

public class AddressPresenter implements AddressContract.AddressPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AddressContract.AddressView addressView;

    @Inject
    public AddressPresenter(RestApiService restApiService, AddressContract.AddressView addressView) {
        this.restApiService = restApiService;
        this.addressView = addressView;
    }

    @Inject
    public void setupListener() {
        addressView.setPresenter(this);
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
    public void getAddressInfos(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            addressView.showLoading();
        }
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable=restApiService.getAppstoreInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<AppstoreInfo>>())
                .compose(new RxSchedulerTransformer<List<AppstoreInfo>>())
                .subscribe(new Consumer<List<AppstoreInfo>>() {
                    @Override
                    public void accept(List<AppstoreInfo> appstoreInfos) throws Exception {
                        addressView.dismissLoading();
                        addressView.renderAddressInfos(appstoreInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addressView.dismissLoading();
                        addressView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
