package com.nenggou.slsm.bankcard.presenter;

import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.PutForwardDetailInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.IdRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/26.
 */

public class PutForwardDetailPresenter implements BankCardContract.PutForwardDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.PutForwardDetailView putForwardDetailView;

    @Inject
    public PutForwardDetailPresenter(RestApiService restApiService, BankCardContract.PutForwardDetailView putForwardDetailView) {
        this.restApiService = restApiService;
        this.putForwardDetailView = putForwardDetailView;
    }

    @Inject
    public void setupListener() {
        putForwardDetailView.setPresenter(this);
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
    public void getPutForwardDetail(String id) {
        putForwardDetailView.showLoading();
        IdRequest idRequest=new IdRequest(id);
        Disposable disposable = restApiService.getPutForwardDetailInfo(idRequest)
                .flatMap(new RxRemoteDataParse<PutForwardDetailInfo>())
                .compose(new RxSchedulerTransformer<PutForwardDetailInfo>())
                .subscribe(new Consumer<PutForwardDetailInfo>() {
                    @Override
                    public void accept(PutForwardDetailInfo putForwardDetailInfo) throws Exception {
                        putForwardDetailView.dismissLoading();
                        putForwardDetailView.renderPutForwardDetail(putForwardDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        putForwardDetailView.dismissLoading();
                        putForwardDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
