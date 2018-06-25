package com.nenggou.slsm.setting.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.AvatarRequest;
import com.nenggou.slsm.data.request.ModifyPasswordRequest;
import com.nenggou.slsm.setting.SettingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/25.
 */

public class ModifyPasswordPresenter implements SettingContract.ModifyPasswordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SettingContract.ModifyPasswordView modifyPasswordView;

    @Inject
    public ModifyPasswordPresenter(RestApiService restApiService, SettingContract.ModifyPasswordView modifyPasswordView) {
        this.restApiService = restApiService;
        this.modifyPasswordView = modifyPasswordView;
    }

    @Inject
    public void setupListener() {
        modifyPasswordView.setPresenter(this);
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
    public void modifyPassword(String newpwd, String old, String code, String tel) {
        modifyPasswordView.showLoading();
        final ModifyPasswordRequest modifyPasswordRequest = new ModifyPasswordRequest(newpwd,old,code,tel);
        Disposable disposable = restApiService.modifyPw(modifyPasswordRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        modifyPasswordView.dismissLoading();
                        modifyPasswordView.modifyPasswordSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        modifyPasswordView.dismissLoading();
                        modifyPasswordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
