package com.nenggou.slsm.financing.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.FcOrderList;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.FcOrderListRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/9/10.
 */

public class FcOrderListPresenter implements FinancingContract.FcOrderListPresenter{
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.FcOrderListView fcOrderListView;
    private int inPCurrentIndex = 1; //持有中
    private int rbCurrentIndex=1; //已回款
    @Inject
    public FcOrderListPresenter(RestApiService restApiService, FinancingContract.FcOrderListView fcOrderListView) {
        this.restApiService = restApiService;
        this.fcOrderListView = fcOrderListView;
    }

    @Inject
    public void setupListener() {
        fcOrderListView.setPresenter(this);
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
    public void getFcOrderItemInfos(String refreshType, String status) {
        if (TextUtils.equals("1", refreshType)) {
            fcOrderListView.showLoading();
        }
        if(TextUtils.equals("0",status)){
            inPCurrentIndex=1;
        }else {
            rbCurrentIndex=1;
        }
        FcOrderListRequest fcOrderListRequest = new FcOrderListRequest(String.valueOf(1),status);
        Disposable disposable = restApiService.getFcOrderList(fcOrderListRequest)
                .flatMap(new RxRemoteDataParse<FcOrderList>())
                .compose(new RxSchedulerTransformer<FcOrderList>())
                .subscribe(new Consumer<FcOrderList>() {
                    @Override
                    public void accept(FcOrderList fcOrderList) throws Exception {
                        fcOrderListView.dismissLoading();
                        fcOrderListView.render(fcOrderList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fcOrderListView.dismissLoading();
                        fcOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreFcOrderItemInfos(String status) {
        FcOrderListRequest fcOrderListRequest;
        if(TextUtils.equals("0",status)){
            inPCurrentIndex=inPCurrentIndex+1;
            fcOrderListRequest = new FcOrderListRequest(String.valueOf(inPCurrentIndex),status);
        }else {
            rbCurrentIndex=rbCurrentIndex+1;
            fcOrderListRequest = new FcOrderListRequest(String.valueOf(rbCurrentIndex),status);
        }
        Disposable disposable = restApiService.getFcOrderList(fcOrderListRequest)
                .flatMap(new RxRemoteDataParse<FcOrderList>())
                .compose(new RxSchedulerTransformer<FcOrderList>())
                .subscribe(new Consumer<FcOrderList>() {
                    @Override
                    public void accept(FcOrderList fcOrderList) throws Exception {
                        fcOrderListView.dismissLoading();
                        fcOrderListView.renderMore(fcOrderList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        fcOrderListView.dismissLoading();
                        fcOrderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
