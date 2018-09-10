package com.nenggou.slsm.financing.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.FcOrderDetailInfo;
import com.nenggou.slsm.data.entity.PayFcOrderInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.FinancingidRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/9/10.
 */

public class FcOrderDetailPresenter implements FinancingContract.FcOrderDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.FcOrderDetailView fcOrderDetailView;

    @Inject
    public FcOrderDetailPresenter(RestApiService restApiService, FinancingContract.FcOrderDetailView fcOrderDetailView) {
        this.restApiService = restApiService;
        this.fcOrderDetailView = fcOrderDetailView;
    }

    @Inject
    public void setupListener() {
        fcOrderDetailView.setPresenter(this);
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
    public void getFcOrderDetailInfo(String financingid) {
        fcOrderDetailView.showLoading();
        FinancingidRequest financingidRequest = new FinancingidRequest(financingid);
        Disposable disposable = restApiService.getFcOrderDetailInfo(financingidRequest)
                .flatMap(new RxRemoteDataParse<FcOrderDetailInfo>())
                .compose(new RxSchedulerTransformer<FcOrderDetailInfo>())
                .subscribe(new Consumer<FcOrderDetailInfo>() {
                    @Override
                    public void accept(FcOrderDetailInfo fcOrderDetailInfo) throws Exception {
                        fcOrderDetailView.dismissLoading();
                        fcOrderDetailView.renderFcOrderDetailInfo(fcOrderDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fcOrderDetailView.dismissLoading();
                        fcOrderDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
