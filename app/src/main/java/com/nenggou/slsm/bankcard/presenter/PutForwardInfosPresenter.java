package com.nenggou.slsm.bankcard.presenter;

import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.PutForwardInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.StartimeRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/26.
 */

public class PutForwardInfosPresenter implements BankCardContract.PutForwardInfosPresenter{
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.PutForwardInfosView putForwardInfosView;

    @Inject
    public PutForwardInfosPresenter(RestApiService restApiService, BankCardContract.PutForwardInfosView putForwardInfosView) {
        this.restApiService = restApiService;
        this.putForwardInfosView = putForwardInfosView;
    }

    @Inject
    public void setupListener() {
        putForwardInfosView.setPresenter(this);
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
    public void getPutForwardInfos(String starttime) {
        putForwardInfosView.showLoading();
        StartimeRequest startimeRequest=new StartimeRequest(starttime);
        Disposable disposable = restApiService.getPutForwardInfos(startimeRequest)
                .flatMap(new RxRemoteDataParse<List<PutForwardInfo>>())
                .compose(new RxSchedulerTransformer<List<PutForwardInfo>>())
                .subscribe(new Consumer<List<PutForwardInfo>>() {
                    @Override
                    public void accept(List<PutForwardInfo> putForwardInfos) throws Exception {
                        putForwardInfosView.dismissLoading();
                        putForwardInfosView.renderPutForwardInfos(putForwardInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardInfosView.dismissLoading();
                        putForwardInfosView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
