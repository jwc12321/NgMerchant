package com.nenggou.slsm.setting.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.TokenRequest;
import com.nenggou.slsm.setting.SettingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/24.
 */

public class SettingPresenter implements SettingContract.SettingPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SettingContract.SettingView settingView;

    @Inject
    public SettingPresenter(RestApiService restApiService, SettingContract.SettingView settingView) {
        this.restApiService = restApiService;
        this.settingView = settingView;
    }


    @Inject
    public void setupListener() {
        settingView.setPresenter(this);
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
    public void isSetUpPayPw() {
        settingView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable=restApiService.isSetUpPayPw(tokenRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        settingView.dismissLoading();
                        settingView.renderIsSetUpPayPw(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        settingView.dismissLoading();
                        settingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
