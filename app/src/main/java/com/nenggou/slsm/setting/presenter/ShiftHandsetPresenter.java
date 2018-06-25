package com.nenggou.slsm.setting.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.CheckCodeRequest;
import com.nenggou.slsm.data.request.CheckNewCodeRequest;
import com.nenggou.slsm.data.request.SendCodeRequest;
import com.nenggou.slsm.data.request.SendNewVCodeRequest;
import com.nenggou.slsm.setting.SettingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/4.
 */

public class ShiftHandsetPresenter implements SettingContract.ShiftHandsetPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SettingContract.ShiftHandsetView shiftHandsetView;

    @Inject
    public ShiftHandsetPresenter(RestApiService restApiService, SettingContract.ShiftHandsetView shiftHandsetView) {
        this.restApiService = restApiService;
        this.shiftHandsetView = shiftHandsetView;
    }

    @Inject
    public void setupListener() {
        shiftHandsetView.setPresenter(this);
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
    public void sendVcode(String tel, String dostr) {
        shiftHandsetView.showLoading();
        SendCodeRequest sendCodeRequest = new SendCodeRequest(tel, dostr);
        Disposable disposable = restApiService.sendCode(sendCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.vcodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void checkOldCode(String tel, String code, String type) {
        shiftHandsetView.showLoading();
        CheckCodeRequest checkCodeRequest = new CheckCodeRequest(tel, code, type);
        Disposable disposable = restApiService.checkCode(checkCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.checkOldCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void checkNewCode(String newtel, String code) {
        shiftHandsetView.showLoading();
        CheckNewCodeRequest checkNewCodeRequest = new CheckNewCodeRequest(newtel, code);
        Disposable disposable = restApiService.checkNewCode(checkNewCodeRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.checkNewCodeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shiftHandsetView.dismissLoading();
                        shiftHandsetView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
